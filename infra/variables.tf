###################################
# General Metadata
###################################

variable "project_name" {
  description = "Project name or identifier"
  type        = string
  default     = "clahe-service"
}

variable "version" {
  description = "Git Tag version passed from CI"
  type        = string
  default     = "latest"
}

###################################
# Dynamic Environment Management
###################################

# Environment is computed internally based on version:
# If version == latest → prod, otherwise → dev
# (Handled through a local variable in main.tf)
variable "environment" {
  description = "Deployment category. Will default to 'dev' unless version == 'latest'."
  type        = string
  default     = "dev"
}

###################################
# Container / Deployment Parameters
###################################

variable "backend_image" {
  description = "Backend container image name or URL"
  type        = string
  default     = "my-backend"
}

variable "frontend_image" {
  description = "Frontend container image name or URL"
  type        = string
  default     = "my-frontend"
}

###################################
# Optional Infrastructure Settings
###################################

variable "cloud_provider" {
  description = "Target platform (aws, gcp, azure, or local) for user‑specific overrides"
  type        = string
  default     = "local"
}

variable "shared_network" {
  description = "Set to true if dev and prod share resources (single‑infra dual‑deploy setup)"
  type        = bool
  default     = true
}

###################################
# Dynamic Local Configuration
###################################

locals {
  environment = var.version == "latest" ? "prod" : "dev"

  # Optional suffix for naming resources (unique per release tag)
  env_suffix = var.version == "latest" ? "" : "-${replace(var.version, ".", "-")}"

  # A resource‑label mapping
  labels = {
    env      = local.environment
    version  = var.version
    project  = var.project_name
    provider = var.cloud_provider
  }
}
