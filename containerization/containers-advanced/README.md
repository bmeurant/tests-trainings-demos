# 1. Prerequisites

[Install tools](../README.md)

# 2. Multi-stage Docker build

- build a no multistage docker image

  ```bash
  docker build -f Dockerfile.no-multistage -t todo-api-no-multistage:latest .
  ```

- build a multistage docker image

  ```bash
  docker build -t todo-api-multistage:latest .
  ```

- check the image size

  ```bash
  docker images | grep "todo-api"
  ```
  **Expected output:**

  ```text
  todo-api-multistage           latest    85bf68d98115   8 seconds ago        204MB
  todo-api-no-multistage        latest    b12930da9d07   About a minute ago   1.09GB
  ```

# 3. Docker Compose: Advanced Persistence and Networking

- launch the Application with Docker Compose

  ```bash
  docker compose up -d
  ```
  **Expected output:**

  ```text
  ✔ todo-api                                  Built                                                                                                                                            0.0s 
  ✔ Network containers-advanced_app_network   Created                                                                                                                                          0.1s
  ✔ Volume "containers-advanced_db_data"      Created                                                                                                                                          0.0s
  ✔ Container containers-advanced-db-1        Created                                                                                                                                          1.8s
  ✔ Container containers-advanced-todo-api-1  Created
  ```
  
- browse FastAPI swagger UI: open browser and go to [http://localhost:8000/docs](http://localhost:8000/docs)
- use endpoints to create some tasks (``POST /tasks``) and list them (``GET /tasks``)

  **Expected output:**

  ```json
  [
    {
      "title": "Learn Docker Compose Advanced",
      "description": "Deep dive into volumes and networks",
      "completed": true,
      "id": 1
    },
    {
      "title": "Master Multi-stage Builds",
      "description": "Reduce image size significantly",
      "completed": false,
      "id": 2
    },
    {
      "title": "task-1",
      "description": "task-1",
      "completed": false,
      "id": 3
    },
    {
      "title": "task-2",
      "description": "task-2",
      "completed": true,
      "id": 4
    }
  ]
  ```
  
- stop the Docker Compose services

  ```bash
  docker compose stop
  ```
  
  or

  ```bash
  docker compose down
  ```
  
  **Expected output:**

  ```text
  [+] Stopping 2/2
  ✔ Container containers-advanced-todo-api-1  Stopped                                                                                                                                          0.5s
  ✔ Container containers-advanced-db-1        Stopped 
  ```
  - restart the Docker Compose services

  ```bash
  docker compose start
  ```
  
  or

  ```bash
  docker compose up -d
  ```
  
  **Expected output:**
    
  ```text
  [+] Running 2/2
  ✔ Container containers-advanced-db-1        Started                                                                                                                                          0.3s
  ✔ Container containers-advanced-todo-api-1  Started
  ```

- go to [http://localhost:8000/docs](http://localhost:8000/docs)
- list tasks (``GET /tasks``)

  **Expected output:**

  Tasks are still there, even after stopping and starting the services

  ```json
  [
    {
      "title": "Learn Docker Compose Advanced",
      "description": "Deep dive into volumes and networks",
      "completed": true,
      "id": 1
    },
    {
      "title": "Master Multi-stage Builds",
      "description": "Reduce image size significantly",
      "completed": false,
      "id": 2
    },
    {
      "title": "task-1",
      "description": "task-1",
      "completed": false,
      "id": 3
    },
    {
      "title": "task-2",
      "description": "task-2",
      "completed": true,
      "id": 4
    }
  ]
  ```  
  
- check networks

  ```bash
  docker network ls
  ```

  **Expected output:**

  ```text
  containers-advanced_app_network   bridge    containers-advanced_app_network   local
  ```

  ```bash
  docker network inspect containers-advanced_app_network
  ```

  **Expected output:**

  Both containers (todo-api and db) are connected to the same network

  ```json
  ...
  "Containers": {
            "7c153928d80c5341b0f36c6275887de97527bfa57f850aa9eefa2bb229c6ab87": {
                "Name": "containers-advanced-db-1",
                "EndpointID": "cf000ce72e2d32393fd943d365161931a7095f989a12e6e74baf040b3f4aca43",
                "MacAddress": "de:2b:2a:da:f8:42",
                "IPv4Address": "10.10.1.2/24",
                "IPv6Address": ""
            },
            "f57290d90199f98b443a2d276d7de9bfeb6930c25da58084477538478c13fea2": {
                "Name": "containers-advanced-todo-api-1",
                "EndpointID": "1ddd5b8cb949b81184fe82485325a015423e9ae68e0986acb4fb946ffa40251c",
                "MacAddress": "a2:a3:05:e1:fc:35",
                "IPv4Address": "10.10.1.3/24",
                "IPv6Address": ""
            }
        },
  ...
  ```
  
- clean

    ```bash
    docker compose down -v --remove-orphans
    ```
    
    **Expected output:**
    
    ```text
    [+] Stopping 2/2
    ✔ Container containers-advanced-todo-api-1  Stopped                                                                                                                                          0.5s
    ✔ Container containers-advanced-db-1        Stopped                                                                                                                                          0.5s
    [+] Removing 2/2
    ✔ Volume "containers-advanced_db_data"      Removed                                                                                                                                          0.0s
    ✔ Network containers-advanced_app_network   Removed                                                                                                                                          0.0s
    ```
  
# 4. Secrets and autoscalling management with Kubernetes

- ensure minikube is running and Docker configured

  ```bash
  minikube start --driver=docker
  eval $(minikube docker-env) # CRUCIAL pour que Minikube utilise votre image locale !
  docker build -t todo-api:latest . # Rebuild your image if you haven't after eval
  ```

- create and deploy and configmap from sql initialization script

  ```bash
  kubectl create configmap postgres-init-script --from-file=db-init-scripts/init.sql 
  ```
  
  **Expected output:**

  ```text
  configmap/postgres-init-script created
  ```
  
- deploy db services with Kubernetes

  ```bash
  kubectl apply -f k8s/db
  ```
  
  **Expected output:**
    
  ```text
  secret/db-credentials created
  persistentvolumeclaim/postgres-pvc created
  deployment.apps/postgres-db created
  service/postgres-db-service created
  ```

- check the status of the deployment
  
  ```bash
  kubectl get deployments
  kubectl get services
  kubectl get pods
  kubectl get configmaps
  kubectl get pvc
  kubectl get secrets
  ```
  
  **Expected output:**
    
  ```text
  NAME          READY   UP-TO-DATE   AVAILABLE   AGE
  postgres-db   1/1     1            1           24s
  
  NAME                  TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
  kubernetes            ClusterIP   10.96.0.1       <none>        443/TCP    21h
  postgres-db-service   ClusterIP   10.97.184.151   <none>        5432/TCP   24s
  
  NAME                           READY   STATUS    RESTARTS   AGE
  postgres-db-676bf5457f-2mjrd   1/1     Running   0          24s
  
  NAME                   DATA   AGE
  kube-root-ca.crt       1      21h
  postgres-init-script   1      52s
  
  NAME           STATUS   VOLUME                                     CAPACITY   ACCESS MODES   STORAGECLASS   VOLUMEATTRIBUTESCLASS   AGE
  postgres-pvc   Bound    pvc-78ce787a-3812-4a0b-91e9-ad8909296130   1Gi        RWO            standard       <unset>                 25s
  
  NAME             TYPE     DATA   AGE
  db-credentials   Opaque   1      25s
  ```

- deploy api services with Kubernetes

  ```bash
  kubectl apply -f k8s/api
  ```

  **Expected output:**

  ```text
  deployment.apps/todo-api-deployment created
  service/todo-api-service created
  ```

- check the status of the deployment

  ```bash
  kubectl get deployments
  kubectl get services
  ```

  **Expected output:**

  ```text
  NAME                  READY   UP-TO-DATE   AVAILABLE   AGE
  postgres-db           1/1     1            1           3m19s
  todo-api-deployment   1/1     1            1           52s
  
  NAME                  TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
  kubernetes            ClusterIP   10.96.0.1       <none>        443/TCP    22h
  postgres-db-service   ClusterIP   10.111.107.28   <none>        5432/TCP   3m20s
  todo-api-service      ClusterIP   10.105.0.64     <none>        80/TCP     53s
  ```
 
- make API accessible from outside the cluster

  ```bash
  kubectl port-forward service/todo-api-service 8000:80
  ```

  **Expected output:**

  ```text
  Forwarding from 127.0.0.1:8000 -> 8000
  Forwarding from [::1]:8000 -> 8000
  ```
  
  Let this terminal open

- Access the API from yur browser: [http://localhost:8000/docs](http://localhost:8000/docs)

  **Expected output:**

  You should see the FastAPI Swagger UI with the endpoints available

- use endpoints to create some tasks (``POST /tasks``) and list them (``GET /tasks``)

  **Expected output:**

  ```json
  [
    {
      "title": "Learn Docker Compose Advanced",
      "description": "Deep dive into volumes and networks",
      "completed": true,
      "id": 1
    },
    {
      "title": "Master Multi-stage Builds",
      "description": "Reduce image size significantly",
      "completed": false,
      "id": 2
    },
    {
      "title": "task-1",
      "description": "task-1",
      "completed": false,
      "id": 3
    },
    {
      "title": "task-2",
      "description": "task-2",
      "completed": true,
      "id": 4
    }
  ]
  ```

- stop forwarding with ``Ctrl+C``
- delete pod to simulate a restart or a relocation

  ```bash
  kubectl delete pod -l app=todo-api
  ```

  **Expected output:**

  ```text
  pod "todo-api-deployment-688b588b98-rfxzm" deleted
  ```

- wait for the pod to be automatically recreated, check with ``kubectl get pods``
- relaunch port-forwarding

  ```bash
  kubectl port-forward service/todo-api-service 8000:80
  ```

- go to [http://localhost:8000/docs](http://localhost:8000/docs)
- list tasks (``GET /tasks``)

  **Expected output:**

  Tasks are still there, even after stopping and starting the services

  ```json
  [
    {
      "title": "Learn Docker Compose Advanced",
      "description": "Deep dive into volumes and networks",
      "completed": true,
      "id": 1
    },
    {
      "title": "Master Multi-stage Builds",
      "description": "Reduce image size significantly",
      "completed": false,
      "id": 2
    },
    {
      "title": "task-1",
      "description": "task-1",
      "completed": false,
      "id": 3
    },
    {
      "title": "task-2",
      "description": "task-2",
      "completed": true,
      "id": 4
    }
  ]
  ```  

- enable metrics server

  ```bash
  minikube addons enable metrics-server
  ```

  **Expected output:**

  ```text
  The 'metrics-server' addon is enabled
  ```
  
- apply HPA

  ```bash
  kubectl apply -f k8s/hpa
  ```
  
  **Expected output:**
  
  ```text
  horizontalpodautoscaler.autoscaling/todo-api-hpa created
  ```

- check HPA & pods status

  ```bash
  kubectl get hpa
  kubectl get pods
  ```
  
  **Expected output:**
    
  ```text
  NAME           REFERENCE                        TARGETS       MINPODS   MAXPODS   REPLICAS   AGE
  todo-api-hpa   Deployment/todo-api-deployment   cpu: 2%/50%   2         5         2          6m16s
  
  NAME                                   READY   STATUS    RESTARTS   AGE
  postgres-db-676bf5457f-2dzbf           1/1     Running   0          23m
  todo-api-deployment-688b588b98-mkfx5   1/1     Running   0          6m1s
  todo-api-deployment-688b588b98-r5pq4   1/1     Running   0          12m
  ``
  
- install a load testing tool

  ```bash
  sudo apt install apache2-utils
  ```

- relaunch port-forwarding

  ```bash
  kubectl port-forward service/todo-api-service 8000:80
  ```
- run load test

  ```bash
  ab -n 10000 -c 50 http://localhost:8000/tasks/
  ```

- watch the HPA scale up the number of pods

  ```bash
  kubectl get hpa -w
  ```
  
  **Expected output:**
  
  ```text
  NAME           REFERENCE                        TARGETS       MINPODS   MAXPODS   REPLICAS   AGE
  todo-api-hpa   Deployment/todo-api-deployment   cpu: 2%/50%   2         5         2          61m
  todo-api-hpa   Deployment/todo-api-deployment   cpu: 139%/50%   2         5         2          63m
  todo-api-hpa   Deployment/todo-api-deployment   cpu: 139%/50%   2         5         4          63m
  todo-api-hpa   Deployment/todo-api-deployment   cpu: 139%/50%   2         5         5          63m
  todo-api-hpa   Deployment/todo-api-deployment   cpu: 242%/50%   2         5         5          64m
  todo-api-hpa   Deployment/todo-api-deployment   cpu: 96%/50%    2         5         5          65m
  todo-api-hpa   Deployment/todo-api-deployment   cpu: 6%/50%     2         5         5          66m
  todo-api-hpa   Deployment/todo-api-deployment   cpu: 2%/50%     2         5         5          67m
  todo-api-hpa   Deployment/todo-api-deployment   cpu: 2%/50%     2         5         5          70m
  todo-api-hpa   Deployment/todo-api-deployment   cpu: 2%/50%     2         5         2          71m
  ```

# 5. External access with Kubernetes Ingress

- activate ingress

  ```bash
  minikube addons enable ingress
  ```
  
  **Expected output:**
  
  ```text
  The 'ingress' addon is enabled
  ```
  
- deploy ingress

  ```bash
  kubectl apply -f k8s/ingress
  ```
  
  **Expected output:**
  
  ```text
  ingress.networking.k8s.io/todo-api-ingress created
  ```
  
- configure `/etc/hosts` to access the API via `todo-api.local`

  ```bash
  echo "$(minikube ip) todo.local" | sudo tee -a /etc/hosts
  ```
  
- launch minikube tunnel (and keep it open)

    ```bash
    minikube tunnel
    ```
  
- test in console

  ```bash
  curl -L http://todo.local/tasks
  ```
    
  **Expected output:**
  
  ```json
  [{"title":"Learn Docker Compose Advanced","description":"Deep dive into volumes and networks","completed":true,"id":1},{"title":"Master Multi-stage Builds","description":"Reduce image size significantly","completed":false,"id":2},{"title":"task-1","description":"task-1","completed":false,"id":3},{"title":"task-2","description":"task-2","completed":true,"id":4}]
  ```

- go to [http://todo.local/docs](http://todo.local/docs)

- clean up

  ```bash
  minikube stop
  minikube delete
  docker image rm todo-api:latest
  ```
  
# 6. Application deployment & management with Helm

- create and start minikube cluster

  ```bash
  minikube start --driver=docker
  eval $(minikube docker-env) # Allows Minikube to use local Docker images
  minikube addons enable ingress # Enable Ingress addon if not already
  minikube addons enable metrics-server # Enable Metrics Server addon (ESSENTIAL for HPA)
  docker build -t todo-api:latest . # Rebuild your image if you haven't after eval
  ```
  
- launch minikube tunnel (and keep it open)

  ```bash
  minikube tunnel
  ```

  **Expected output:**

  ```text
  ✅  Tunnel successfully started
  📌  NOTE: Please do not close this terminal as this process must stay alive for the tunnel to be accessible ...
  ```
  
- create a kubernetes secret for DB password

  ```bash
  # Define your DB password (replace "your_secret_password" with a strong password)
  DB_PASSWORD="password"
  
  # Create the Kubernetes Secret (base64 encode the password)
  kubectl create secret generic todo-db-credentials \
  --from-literal=db_password=$(echo -n "$DB_PASSWORD" | base64)
  ```
  
  **Expected output:**
  
  ```text
  secret/todo-db-credentials created
  ```
  
  ```bash
  # Verify the secret was created (optional, for checking only, do not log real secrets)
  # kubectl get secret todo-db-credentials -o yaml
  ```

  **Expected output:**

  ```yaml
  apiVersion: v1
  data:
  db_password: Y0dGemMzZHZjbVE9
  kind: Secret
  metadata:
  creationTimestamp: "2025-06-10T15:34:40Z"
  name: todo-db-credentials
  namespace: default
  resourceVersion: "1014"
  uid: 4faa5590-589d-49e5-89b1-1b6a4ad1fca8
  type: Opaque
  ```

- install app

  ```bash
  helm install my-todo-app ./todo-api-chart
  ```
  
  **Expected output:**
  
  ```text
  NAME: my-todo-app
  LAST DEPLOYED: Tue Jun 10 19:24:41 2025
  NAMESPACE: default
  STATUS: deployed
  REVISION: 1
  ```
  
- verify deployment

  ```bash
  helm list
  kubectl get all -l app.kubernetes.io/instance=my-todo-app
  kubectl get hpa -l app.kubernetes.io/instance=my-todo-app
  ```
  
  **Expected output:**
  
  ```text
  NAME            NAMESPACE       REVISION        UPDATED                                         STATUS          CHART                   APP VERSION
  my-todo-app     default         1               2025-06-10 19:24:41.219342405 +0200 CEST        deployed        todo-api-chart-0.1.0    1.0.0      
  
  NAME                                                             READY   STATUS    RESTARTS   AGE
  pod/my-todo-app-todo-api-chart-api-deployment-77f97d54c4-2vcdm   1/1     Running   0          2m7s
  pod/my-todo-app-todo-api-chart-api-deployment-77f97d54c4-sxm9l   1/1     Running   0          2m7s
  pod/my-todo-app-todo-api-chart-postgres-db-79f9cd7c9c-46v8p      1/1     Running   0          2m7s
  
  NAME                                                     TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
  service/my-todo-app-todo-api-chart-api-service           ClusterIP   10.107.47.61    <none>        80/TCP     2m7s
  service/my-todo-app-todo-api-chart-postgres-db-service   ClusterIP   10.102.74.226   <none>        5432/TCP   2m7s
  
  NAME                                                        READY   UP-TO-DATE   AVAILABLE   AGE
  deployment.apps/my-todo-app-todo-api-chart-api-deployment   2/2     2            2           2m7s
  deployment.apps/my-todo-app-todo-api-chart-postgres-db      1/1     1            1           2m7s
  
  NAME                                                                   DESIRED   CURRENT   READY   AGE
  replicaset.apps/my-todo-app-todo-api-chart-api-deployment-77f97d54c4   2         2         2       2m7s
  replicaset.apps/my-todo-app-todo-api-chart-postgres-db-79f9cd7c9c      1         1         1       2m7s
  
  NAME                                                                 REFERENCE                                              TARGETS       MINPODS   MAXPODS   REPLICAS   AGE
  horizontalpodautoscaler.autoscaling/my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%   2         5         2          2m7s
  
  NAME                             REFERENCE                                              TARGETS       MINPODS   MAXPODS   REPLICAS   AGE
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%   2         5         2          2m8s
  ```
  
- Access the API from yur browser: [http://todo.local/docs](http://todo.local/docs)

  **Expected output:**
  
  You should see the FastAPI Swagger UI with the endpoints available

- use endpoints to create some tasks (``POST /tasks``) and list them (``GET /tasks``)

  **Expected output:**

  ```json
  [
    {
      "title": "Learn Docker Compose Advanced",
      "description": "Deep dive into volumes and networks",
      "completed": true,
      "id": 1
    },
    {
      "title": "Master Multi-stage Builds",
      "description": "Reduce image size significantly",
      "completed": false,
      "id": 2
    },
    {
      "title": "task-1",
      "description": "task-1",
      "completed": false,
      "id": 3
    },
    {
      "title": "task-2",
      "description": "task-2",
      "completed": true,
      "id": 4
    }
  ]
  ```

# 7. Autoscalling with HPA & Helm

- watch HPA status

  ```bash
  # Terminal 1: Watch HPA status
  kubectl get hpa -w
  ```
  
- watch API Pods

  ```bash
  # Terminal 2: Watch API pods
  kubectl get pods -l app.kubernetes.io/instance=my-todo-app,app.kubernetes.io/component=api -w
  ```

- run load test

  ```bash
  ab -n 10000 -c 50 http://todo.local/tasks/
  ```
  
  **Expected output:**

  ```text
  NAME                             REFERENCE                                              TARGETS       MINPODS   MAXPODS   REPLICAS   AGE
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%   2         5         2          104m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 302%/50%   2         5         2          107m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 302%/50%   2         5         4          107m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 302%/50%   2         5         5          108m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 107%/50%   2         5         5          108m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 432%/50%   2         5         5          111m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 437%/50%   2         5         5          112m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 409%/50%   2         5         5          113m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%     2         5         5          114m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%     2         5         5          119m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%     2         5         2          119m
  ```

- change maxReplicas and upgrade the Helm release

  ```bash
  helm upgrade my-todo-app ./todo-api-chart --set hpa.maxReplicas=7
  ```

  **Expected output:**

  ```text
  Release "my-todo-app" has been upgraded. Happy Helming!
  NAME: my-todo-app
  LAST DEPLOYED: Tue Jun 10 21:25:38 2025
  NAMESPACE: default
  STATUS: deployed
  REVISION: 2
  ```
  
- verify the upgrade

  ```bash
  kubectl get hpa -l app.kubernetes.io/instance=my-todo-app
  ```

  **Expected output:**

  ```text
  NAME                             REFERENCE                                              TARGETS       MINPODS   MAXPODS   REPLICAS   AGE
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%   2         7         2          1m
  ```

- run load test

  ```bash
  ab -n 10000 -c 50 http://todo.local/tasks/
  ```

  **Expected output:**

  ```text
  NAME                             REFERENCE                                              TARGETS       MINPODS   MAXPODS   REPLICAS   AGE
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%   2         7         2          121m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 343%/50%   2         7         2          122m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 343%/50%   2         7         4          122m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 343%/50%   2         7         7          123m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 71%/50%    2         7         7          123m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%     2         7         7          124m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%     2         7         7          128m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%     2         7         7          129m
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%     2         7         2          129m
  ```
  
# 8. Rollback Helm Chart

- check revision history

  ```bash
  helm history my-todo-app
  ```

  **Expected output:**

  ```text
  REVISION        UPDATED                         STATUS          CHART                   APP VERSION     DESCRIPTION     
  1               Tue Jun 10 19:24:41 2025        superseded      todo-api-chart-0.1.0    1.0.0           Install complete
  2               Tue Jun 10 21:25:38 2025        deployed        todo-api-chart-0.1.0    1.0.0           Upgrade complete
  ```
  
- rollback to previous revision

  ```bash
  helm rollback my-todo-app 1
  ```
  
  **Expected output:**
    
  ```text
  Rollback was a success! Happy Helming!
  ```
  
- verify HPA

  ```bash
  kubectl get hpa -l app.kubernetes.io/instance=my-todo-app
  ```
  
  **Expected output:**
  
  ```text
  NAME                             REFERENCE                                              TARGETS       MINPODS   MAXPODS   REPLICAS   AGE
  my-todo-app-todo-api-chart-hpa   Deployment/my-todo-app-todo-api-chart-api-deployment   cpu: 2%/50%   2         5         2          133m
  ```
  
- verify rollback

  ```bash
  helm history my-todo-app
  ```
  
  **Expected output:**
  
  ```text
  REVISION        UPDATED                         STATUS          CHART                   APP VERSION     DESCRIPTION     
  1               Tue Jun 10 19:24:41 2025        superseded      todo-api-chart-0.1.0    1.0.0           Install complete
  2               Tue Jun 10 21:25:38 2025        superseded      todo-api-chart-0.1.0    1.0.0           Upgrade complete
  3               Tue Jun 10 21:37:50 2025        deployed        todo-api-chart-0.1.0    1.0.0           Rollback to 1  
  ```
  
# 9. Clean up

- delete the Helm release

  ```bash
  helm uninstall my-todo-app
  ```

  **Expected output:**

  ```text
  release "my-todo-app" uninstalled
  ```
  
- verify 

  ```bash
  helm list
  kubectl get all -l app.kubernetes.io/instance=my-todo-app
  kubectl get hpa -l app.kubernetes.io/instance=my-todo-app
  ```

  **Expected output:**

  ```text
  NAME    NAMESPACE       REVISION        UPDATED STATUS  CHART   APP VERSION
  No resources found in default namespace.
  No resources found in default namespace.
  ```
  
- delete db secret

  ```bash
  kubectl delete secret todo-db-credentials
  ```
  
  **Expected output:**
  
  ```text
  secret "todo-db-credentials" deleted
  ```
  
- stop minikube

  ```bash
  minikube stop
  ```
  
  **Expected output:**
  
  ```text
  ✋  Stopping node "minikube"  ...
  🛑  Powering off "minikube" via SSH ...
  🛑  1 node stopped.
  ```