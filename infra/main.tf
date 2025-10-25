# main.tf
resource "docker_container" "backend" {
  name   = "clahe-backend${local.env_suffix}"
  image  = "${var.backend_image}:${var.version}"

  # Add env tag label for tracking
  labels = {
    environment = local.environment
    version     = var.version
  }
}

resource "docker_container" "frontend" {
  name   = "clahe-frontend${local.env_suffix}"
  image  = "${var.frontend_image}:${var.version}"
  labels = {
    environment = local.environment
    version     = var.version
  }
}
