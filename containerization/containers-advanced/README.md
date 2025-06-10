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