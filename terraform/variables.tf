variable "do_token" {
  description = "DigitalOcean API token"
  type        = string
  sensitive   = true   # Terraform masque cette valeur dans les logs
}

variable "ssh_key_fingerprint" {
  description = "Fingerprint de la clé SSH sur DigitalOcean"
  type        = string
}

variable "region" {
  description = "Région DigitalOcean"
  type        = string
  default     = "fra1"  # Frankfurt — plus proche de Lomé que us-east
}

variable "droplet_size" {
  type    = string
  default = "s-1vcpu-1gb"  # 1 CPU, 1GB RAM — suffisant pour Flask
}
