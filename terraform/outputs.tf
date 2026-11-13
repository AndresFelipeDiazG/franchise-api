output "rds_endpoint" {
  description = "Host de conexión de la base de datos PostgreSQL en RDS"
  value       = aws_db_instance.postgres.address
}

output "api_public_url" {
  description = "URL pública de la API desplegada en AWS"
  value       = "http://${aws_instance.api_server.public_ip}"
}

output "health_check_url" {
  description = "Endpoint de verificación de salud"
  value       = "http://${aws_instance.api_server.public_ip}/actuator/health"
}