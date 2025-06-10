# 1. Prerequisites

[Install tools](../README.md)

# 2. Simple docker image

**Build and run a simple docker image**

- build docker image

  ```bash
  docker build -t my-flask-app:latest .
  ```
  
- check the image

  ```bash
  docker images
  ```
  **Expected output:**

  ```text
  REPOSITORY                                TAG        IMAGE ID       CREATED          SIZE
  my-flask-app                              latest     0f640016ee1e   25 minutes ago   129MB
  ```
  
- run the docker image with port bindings

  ```bash
  docker run -p 8080:5000 --name my-flask-test-container my-flask-app:latest
  ```
  
  **Expected output:**

  ```text
  Failed to connect to Redis at localhost:6379. Error: Error 111 connecting to localhost:6379. Connection refused.
  * Serving Flask app 'app'
  * Debug mode: off
    WARNING: This is a development server. Do not use it in a production deployment. Use a production WSGI server instead.
  * Running on all addresses (0.0.0.0)
  * Running on http://127.0.0.1:5000
  * Running on http://10.10.0.3:5000
  Press CTRL+C to quit
    ```

- navigate to http://localhost:8080

  **Expected output:**

  ```html
  Hello from containerized application! (Redis not connected)
  ```
  
  ```text
  10.10.0.1 - - [09/Jun/2025 22:09:37] "GET /hello HTTP/1.1" 500 -
  10.10.0.1 - - [09/Jun/2025 22:09:37] "GET /favicon.ico HTTP/1.1" 404 -
  ```

- stop and Remove the container

  ```bash
  docker stop my-flask-test-container
  docker rm my-flask-test-container
  ```
  
# 3. Simple docker compose

- launch the Application with Docker Compose

  ```bash
  docker compose up -d
  ```
  
  **Expected output:**

  ```text
  ✔ web                                  Built                                                                                                          0.0s
  ✔ Network containers-basics_default    Created                                                                                                        0.0s
  ✔ Container containers-basics-redis-1  Started                                                                                                        0.4s
  ✔ Container containers-basics-web-1    Started                                                                                                        0.5s
  ```

- navigate to http://localhost:8080 and refresh the page, see the increased number of requests
- stop containers and networks

    ```bash
    docker compose down
    ```
  
    **Expected output:**

    ```text
    ✔ Container containers-basics-web-1    Removed                                                                                                       10.3s
    ✔ Container containers-basics-redis-1  Removed                                                                                                        0.3s
    ✔ Network containers-basics_default    Removed                                                                                                        0.1s
    ```

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
  **Expected output:**

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
    
    **Expected output:**
    
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
  
    **Expected output:**

    ```text
    No resources found in default namespace.
    NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
    kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   8h
    No resources found in default namespace.
    ```

# 4. Orchestration with Helm

- install the Helm chart

  ```bash
  helm install my-app-release ./my-flask-app-chart
  ```
  
  **Expected output:**

  ```text
  NAME: my-app-release
  LAST DEPLOYED: Tue Jun 10 01:00:09 2025
  NAMESPACE: default
  STATUS: deployed
  REVISION: 1
  TEST SUITE: None
  ```
  
  ```bash
  helm list
  kubectl get all -l app.kubernetes.io/instance=my-app-release
  ```
  
  ```text
  NAME            NAMESPACE       REVISION        UPDATED                                         STATUS          CHART                           APP VERSION
  my-app-release  default         1               2025-06-10 01:16:23.239290437 +0200 CEST        deployed        my-flask-app-chart-0.1.0        1.16.0     
  NAME                                       TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)        AGE
  service/my-app-release-flask-app-service   LoadBalancer   10.98.132.51     <pending>     80:30192/TCP   28s
  service/my-app-release-redis-service       ClusterIP      10.100.219.237   <none>        6379/TCP       28s
  
  NAME                                                  READY   UP-TO-DATE   AVAILABLE   AGE
  deployment.apps/my-app-release-flask-app-deployment   2/2     2            2           28s
  deployment.apps/my-app-release-redis-deployment       1/1     1            1           28
  ```

- Upgrade the Helm Chart (scaling)

    ```bash
    helm upgrade my-app-release ./my-flask-app-chart --set flaskApp.replicaCount=3
    ```
    
    **Expected output:**
    
    ```text
    Release "my-app-release" has been upgraded. Happy Helming!
    NAME: my-app-release
    LAST DEPLOYED: Tue Jun 10 01:07:43 2025
    NAMESPACE: default
    STATUS: deployed
    REVISION: 2
    TEST SUITE: None
    ```
    ```bash
    helm list
    kubectl get all -l app.kubernetes.io/instance=my-app-release
    ```

  ```text
  NAME            NAMESPACE       REVISION        UPDATED                                 STATUS          CHART                           APP VERSION
  my-app-release  default         2               2025-06-10 01:22:28.87395545 +0200 CEST deployed        my-flask-app-chart-0.1.0        1.16.0     
  NAME                                       TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)        AGE
  service/my-app-release-flask-app-service   LoadBalancer   10.98.132.51     <pending>     80:30192/TCP   6m8s
  service/my-app-release-redis-service       ClusterIP      10.100.219.237   <none>        6379/TCP       6m8s
  
  NAME                                                  READY   UP-TO-DATE   AVAILABLE   AGE
  deployment.apps/my-app-release-flask-app-deployment   3/3     3            3           6m8s
  deployment.apps/my-app-release-redis-deployment       1/1     1            1           6m8s
  ```

- rollback a Helm Chart

    ```bash
    helm rollback my-app-release 1
    ```
    
    **Expected output:**
    
    ```text
    Rollback was a success! Happy Helming!
    ```
    
    ```bash
    helm list
    kubectl get all -l app.kubernetes.io/instance=my-app-release
    ```

  ```text
  NAME            NAMESPACE       REVISION        UPDATED                                 STATUS          CHART                           APP VERSION
  my-app-release  default         3               2025-06-10 01:24:00.6256686 +0200 CEST  deployed        my-flask-app-chart-0.1.0        1.16.0     
  NAME                                       TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)        AGE
  service/my-app-release-flask-app-service   LoadBalancer   10.98.132.51     <pending>     80:30192/TCP   7m43s
  service/my-app-release-redis-service       ClusterIP      10.100.219.237   <none>        6379/TCP       7m43s
  
  NAME                                                  READY   UP-TO-DATE   AVAILABLE   AGE
  deployment.apps/my-app-release-flask-app-deployment   2/2     2            2           7m43s
  deployment.apps/my-app-release-redis-deployment       1/1     1            1           7m43s
  ```
- uninstall the Helm Chart

  ```bash
  helm uninstall my-app-release
  ```
    
  **Expected output:**
    
  ```text
  release "my-app-release" uninstalled
  ```
    
  ```bash
  helm list
  kubectl get all -l app.kubernetes.io/instance=my-app-release
  ```

  ```text
  NAME    NAMESPACE       REVISION        UPDATED STATUS  CHART   APP VERSION
  No resources found in default namespace.
  ```