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