output "environment" {
  description = "Evaluated target environment (prod or dev)"
  value       = local.environment
}

output "deployment_name" {
  value = {
    backend  = docker_container.backend.name
    frontend = docker_container.frontend.name
  }
}

output "status" {
  value = "Deployed environment '${local.environment}' for version '${var.version}'."
}
