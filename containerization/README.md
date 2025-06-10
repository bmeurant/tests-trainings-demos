# Install tools

[script](./tools/install.sh)

## Verify installation

### Docker

```bash
docker run hello-world
```

**Expected output:**

```text
Hello from Docker!
This message shows that your installation appears to be working correctly.
```

### Docker Compose

```bash
docker compose version
```

**Expected output:**

```text
Docker Compose version v2.36.2
```

### Kubernetes

```bash
kubectl version --client
```

**Expected output:**

```text
Client Version: v1.33.1
Kustomize Version: v5.6.0
```

### Minikube

```bash
minikube version
```

**Expected output:**

```text
minikube version: v1.36.0
commit: f8f52f5de11fc6ad8244afac475e1d0f96841df1-dirty
```

### Helm

```bash
helm version
```

**Expected output:**

```text
version.BuildInfo{Version:"v3.18.2", GitCommit:"04cad4610054e5d546aa5c5d9c1b1d5cf68ec1f8", GitTreeState:"clean", GoVersion:"go1.24.3"}
```