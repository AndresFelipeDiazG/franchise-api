terraform {
  required_version = ">= 1.5.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.aws_region
}

# Usar la VPC y subredes por defecto de la cuenta AWS
data "aws_vpc" "default" {
  default = true
}

data "aws_subnets" "default" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.default.id]
  }
}

# Última AMI oficial de Amazon Linux 2023
data "aws_ami" "amazon_linux_2023" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["al2023-ami-2023*-x86_64"]
  }
}

# Security Group para la API (puerto 80 y 8080 abiertos al público)
resource "aws_security_group" "api_sg" {
  name        = "${var.project_name}-api-sg"
  description = "Permite trafico HTTP a franchise-api"
  vpc_id      = data.aws_vpc.default.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Security Group para RDS PostgreSQL (solo acepta conexiones desde la API)
resource "aws_security_group" "rds_sg" {
  name        = "${var.project_name}-rds-sg"
  description = "Permite trafico PostgreSQL unicamente desde la instancia API"
  vpc_id      = data.aws_vpc.default.id

  ingress {
    from_port       = 5432
    to_port         = 5432
    protocol        = "tcp"
    security_groups = [aws_security_group.api_sg.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Subnet Group para RDS
resource "aws_db_subnet_group" "rds_subnets" {
  name       = "${var.project_name}-db-subnet-group"
  subnet_ids = data.aws_subnets.default.ids
}

resource "aws_db_parameter_group" "postgres_params" {
  name   = "${var.project_name}-pg16-params"
  family = "postgres16"

  parameter {
    name  = "rds.force_ssl"
    value = "0"
  }
}

# Instancia de Base de Datos PostgreSQL en Amazon RDS (IaC)
resource "aws_db_instance" "postgres" {
  identifier             = "${var.project_name}-db"
  engine                 = "postgres"
  engine_version         = "16"
  instance_class         = "db.t4g.micro"
  allocated_storage      = 20
  storage_type           = "gp2"
  db_name                = var.db_name
  username               = var.db_username
  password               = var.db_password
  db_subnet_group_name   = aws_db_subnet_group.rds_subnets.name
  vpc_security_group_ids = [aws_security_group.rds_sg.id]
  skip_final_snapshot    = true
  publicly_accessible    = false
}

# Instancia EC2 que ejecuta el contenedor Docker conectado a RDS
resource "aws_instance" "api_server" {
  ami                         = data.aws_ami.amazon_linux_2023.id
  instance_type               = "t3.micro"
  vpc_security_group_ids      = [aws_security_group.api_sg.id]
  associate_public_ip_address = true

  user_data = <<-EOF
              #!/bin/bash
              dnf update -y
              dnf install -y docker
              systemctl start docker
              systemctl enable docker

              docker run -d \
                --name franchise-api \
                --restart always \
                -p 80:8080 \
                -p 8080:8080 \
                -e DB_HOST=${aws_db_instance.postgres.address} \
                -e DB_PORT=5432 \
                -e DB_NAME=${var.db_name} \
                -e DB_USER=${var.db_username} \
                -e DB_PASSWORD=${var.db_password} \
                ${var.docker_image}
              EOF

  depends_on = [aws_db_instance.postgres]

  tags = {
    Name = "${var.project_name}-server"
  }
}