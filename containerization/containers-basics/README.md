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
- ``docker run`` to run docker image
- navigate to http://localhost:8080
- ``docker stop`` to stop the container
- ``docker rm`` to delete the container

# 3. Simple docker compose

**Run a simple docker compose**

```bash
docker compose up -d
```

- navigate to http://localhost:8080 and refresh the page
- see the increased number of requests
- ``dcker compose stop`` to stop the containers
- ``docker compose down`` to remove containers and networks

# 4. Orchestration with Minikube

- start minikube

  ```bash
  minikube start --driver=docker
  ```

- allow pulling local images

  ```bash
  eval $(minikube docker-env)
  ```

- deploy Resources to Kubernetes

    ```bash
    kubectl apply -f redis-deployment.yaml
    kubectl apply -f redis-service.yaml
    kubectl apply -f flask-deployment.yaml
    kubectl apply -f flask-service.yaml
    ```
- check the status of your deployments and services

    ```bash
    kubectl get deployments
    kubectl get services
    kubectl get pods
    ```
  **Expected:**

  ```text
  NAME                   READY   UP-TO-DATE   AVAILABLE   AGE
  flask-app-deployment   2/2     2            2           9m32s
  redis-deployment       1/1     1            1           23m
  NAME                TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)        AGE
  flask-app-service   LoadBalancer   10.98.111.63   <pending>     80:32557/TCP   22m
  kubernetes          ClusterIP      10.96.0.1      <none>        443/TCP        8h
  redis-service       ClusterIP      10.98.131.66   <none>        6379/TCP       22m
  NAME                                    READY   STATUS    RESTARTS   AGE
  flask-app-deployment-6957858b58-cvqs8   1/1     Running   0          3m6s
  flask-app-deployment-6957858b58-pvpzs   1/1     Running   0          3m5s
  redis-deployment-8499599c9c-gpvwg       1/1     Running   0          23m
    ```
- Get the URL of the Flask app service

    ```bash
    minikube service flask-app-service --url
    ```
    
    **Expected:**
    
    ```text
    http://127.0.0.1:37363
    ```
- Navigate to the URL in your browser : http://127.0.0.1:37363/hello
- Delete Kubernetes Resources

    ```bash
    kubectl delete -f redis-deployment.yaml
    kubectl delete -f redis-service.yaml
    kubectl delete -f flask-deployment.yaml
    kubectl delete -f flask-service.yaml
    ```
- check the status of your deployments and services

    ```bash
    kubectl get deployments
    kubectl get services
    kubectl get pods
    ```
  
    **Expected:**

    ```text
    No resources found in default namespace.
    NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
    kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   8h
    No resources found in default namespace.
    ```
  