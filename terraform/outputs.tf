output "droplet_ip" {
  value = digitalocean_droplet.monitor.ipv4_address
}

output "ssh_command" {
  value = "ssh -i ~/.ssh/do_server_monitor root@${digitalocean_droplet.monitor.ipv4_address}"
}
