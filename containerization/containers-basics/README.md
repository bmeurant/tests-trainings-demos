# 1. Install tools

[script](./tools/install.sh)

## Verify installation

### Docker

```bash
docker run hello-world
```

** Result:**

```text
Hello from Docker!
This message shows that your installation appears to be working correctly.
```

### Docker Compose

```bash
docker compose version
```

** Result:**

```text
Docker Compose version v2.36.2
```

### Kubernetes

```bash
kubectl version --client
```

** Result:**

```text
Client Version: v1.33.1
Kustomize Version: v5.6.0
```

### Minikube

```bash
minikube version
```

** Result:**

```text
minikube version: v1.36.0
commit: f8f52f5de11fc6ad8244afac475e1d0f96841df1-dirty
```

### Helm

```bash
helm version
```

** Result:**

```text
version.BuildInfo{Version:"v3.18.2", GitCommit:"04cad4610054e5d546aa5c5d9c1b1d5cf68ec1f8", GitTreeState:"clean", GoVersion:"go1.24.3"}
```

# 2. Simple docker image

**Build and run a simple docker image**

- build docker image
- bind port 5000 to 8080
- run docker image
- navigate to http://localhost:8080
