terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "~> 2.0"
    }
  }
}

provider "digitalocean" {
  token = var.do_token
}

data "digitalocean_ssh_key" "deploy_key" {
  name = "server-monitor-key"
}

resource "digitalocean_droplet" "monitor" {
  name     = "server-monitor"
  image    = "ubuntu-22-04-x64"
  size     = var.droplet_size
  region   = var.region
  ssh_keys = [data.digitalocean_ssh_key.deploy_key.id]
  tags     = ["server-monitor", "terraform"]
}

resource "digitalocean_firewall" "monitor_fw" {
  name        = "server-monitor-firewall"
  droplet_ids = [digitalocean_droplet.monitor.id]

  inbound_rule {
    protocol         = "tcp"
    port_range       = "22"
    source_addresses = ["0.0.0.0/0", "::/0"]
  }
  inbound_rule {
    protocol         = "tcp"
    port_range       = "5000"
    source_addresses = ["0.0.0.0/0", "::/0"]
  }
  outbound_rule {
    protocol              = "tcp"
    port_range            = "1-65535"
    destination_addresses = ["0.0.0.0/0", "::/0"]
  }

  outbound_rule {
    protocol              = "udp"
    port_range            = "1-65535"
    destination_addresses = ["0.0.0.0/0", "::/0"]
  }

  outbound_rule {
    protocol              = "icmp"
    destination_addresses = ["0.0.0.0/0", "::/0"]
  }
}
