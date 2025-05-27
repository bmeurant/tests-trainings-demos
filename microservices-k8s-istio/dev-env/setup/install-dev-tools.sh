#!/bin/bash
set -euo pipefail

echo "🚀 Installing development tools..."

# Colors
GREEN='\033[0;32m'
NC='\033[0m' # No Color

install_if_missing() {
  local cmd="$1"
  local install_fn="$2"
  if ! command -v "$cmd" &> /dev/null; then
    echo -e "🔧 Installing ${GREEN}${cmd}${NC}..."
    eval "$install_fn"
  else
    echo -e "✅ ${GREEN}${cmd}${NC} already installed"
  fi
}

# Docker
install_if_missing "docker" "
  sudo apt-get update
  sudo apt-get install -y docker.io
  sudo usermod -aG docker \$USER
"

# Docker Compose
install_if_missing "docker-compose" "sudo apt-get install -y docker-compose"

# kubectl
install_if_missing "kubectl" "
  KUBECTL_VERSION=\$(curl -sL https://dl.k8s.io/release/stable.txt)
  curl -sL -O https://dl.k8s.io/release/\$KUBECTL_VERSION/bin/linux/amd64/kubectl
  chmod +x kubectl
  sudo mv kubectl /usr/local/bin/
"

# k3d
install_if_missing "k3d" "curl -sL https://raw.githubusercontent.com/k3d-io/k3d/main/install.sh | bash"

# Helm
install_if_missing "helm" "curl -sL https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash"

# Tilt
install_if_missing "tilt" "curl -fsSL https://raw.githubusercontent.com/tilt-dev/tilt/master/scripts/install.sh | bash"

# jq
install_if_missing "jq" "sudo apt-get install -y jq"

# kustomize
install_if_missing "kustomize" "
  KUSTOMIZE_VERSION=\$(curl -s https://api.github.com/repos/kubernetes-sigs/kustomize/releases/latest | jq -r '.tag_name')
  VERSION=\${KUSTOMIZE_VERSION#kustomize/}
  curl -sLO https://github.com/kubernetes-sigs/kustomize/releases/download/\${KUSTOMIZE_VERSION}/kustomize_\${VERSION}_linux_amd64.tar.gz
  tar -xzf kustomize_\${VERSION}_linux_amd64.tar.gz
  chmod +x kustomize
  sudo mv kustomize /usr/local/bin/
  rm kustomize_\${VERSION}_linux_amd64.tar.gz
"

# skaffold
install_if_missing "skaffold" "
  curl -sL -o skaffold https://storage.googleapis.com/skaffold/releases/latest/skaffold-linux-amd64
  chmod +x skaffold
  sudo mv skaffold /usr/local/bin/
"

echo -e "${GREEN}✅ All tools installed successfully!${NC}"
echo "👉 Please restart your session or run 'newgrp docker' if Docker was just installed."

