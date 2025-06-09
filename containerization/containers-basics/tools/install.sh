# Goal: Ensure the list of available packages is up-to-date.
# This is the first thing to do before any installation.
sudo apt update

# Goal: Install necessary tools for downloading packages from secure repositories (HTTPS).
# ca-certificates: Certificate authorities to verify server authenticity.
# curl: Tool for transferring data with URLs (used to download Docker's GPG key).
# apt-transport-https: Allows apt to use repositories over HTTPS.
# software-properties-common: Provides 'add-apt-repository', a tool to easily add repositories.
sudo apt install ca-certificates curl apt-transport-https software-properties-common -y

# Goal: Add Docker's GPG (GNU Privacy Guard) key.
# This key allows your system to verify the authenticity of Docker packages you will download.
# Without it, apt would refuse to install packages from an untrusted repository.
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

# Goal: Add the official Docker repository to Ubuntu's package sources list.
# 'deb [arch=...]' : Indicates this is a Debian repository (Ubuntu is based on Debian) and specifies the architecture.
# 'signed-by=...' : Indicates that packages from this repository must be signed by the GPG key we just added.
# 'https://download.docker.com/linux/ubuntu' : The URL of the Docker repository for Ubuntu.
# '$(. /etc/os-release && echo "$VERSION_CODENAME") stable' : Retrieves the codename of your Ubuntu version (e.g., 'jammy' for Ubuntu 22.04) and specifies the 'stable' branch.
# 'sudo tee ... > /dev/null' : Writes the repository line to the '/etc/apt/sources.list.d/docker.list' file.
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Goal: Update the package index again.
# This time, apt will read the new '/etc/apt/sources.list.d/docker.list' file and include Docker packages in its lists.
sudo apt update

# Goal: Install the core Docker components.
# docker-ce: The Docker engine (Community Edition).
# docker-ce-cli: The Docker command-line interface.
# containerd.io: A high-level container runtime that manages the container lifecycle. Docker uses it internally.
# docker-buildx-plugin: A plugin to extend 'docker build' capabilities (for multiple architectures, etc.).
# docker-compose-plugin: The new plugin for Docker Compose (replaces the older separate installation).
sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y

# Goal: Add your user to the 'docker' group.
# By default, only the 'root' user can communicate with the Docker daemon.
# By adding your user to the 'docker' group, you get the necessary permissions to run 'docker' commands without 'sudo'.
sudo usermod -aG docker $USER

# Goal: Apply group changes immediately for your current terminal session.
# Although 'newgrp' attempts to apply changes, it is often insufficient for a complete effect.
# RECONNECTING to your user session (or a reboot) is the most reliable method.
newgrp docker

# Goal: Download and install kubectl.
# curl -LO ... : Downloads the kubectl executable from the official Kubernetes website.
# sudo install ... : Copies the downloaded executable to /usr/local/bin, a standard directory for system executables.
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl &&  rm kubectl

# Goal: Download and install Minikube.
# Similar to kubectl, this downloads the Minikube executable and copies it to /usr/local/bin.
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube && rm minikube-linux-amd64

# Goal: Download and execute the official Helm installation script.
# This script will download the latest version of Helm and install it to /usr/local/bin.
curl [https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3](https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3) | bash