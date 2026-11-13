variable "aws_region" {
  description = "Región de AWS donde se desplegará la infraestructura"
  type        = string
  default     = "us-east-1"
}

variable "project_name" {
  description = "Prefijo para nombrar los recursos"
  type        = string
  default     = "franchise-api"
}

variable "db_name" {
  description = "Nombre de la base de datos PostgreSQL"
  type        = string
  default     = "franchise"
}

variable "db_username" {
  description = "Usuario maestro de PostgreSQL en RDS"
  type        = string
  default     = "franchise"
}

variable "db_password" {
  description = "Contraseña maestra de PostgreSQL en RDS (mínimo 8 caracteres)"
  type        = string
  sensitive   = true
}

variable "docker_image" {
  description = "Imagen Docker publicada en Docker Hub"
  type        = string
  default     = "tuusuario/franchise-api:1.0.0"
}