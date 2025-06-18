# Observability Spring Boot Demo

This project demonstrates a Spring Boot application integrated with a comprehensive observability stack.
The goal is to provide a testbed for exploring distributed tracing, metrics, and log aggregation using modern tools.

The application is a simple Product and Order management system.

## Features
- Spring Boot 3.x (specifically 3.3.0 in this setup) application using Java 21.
- Maven build system.
- RESTful APIs for managing Products and Orders.
- Scheduled task for background activity generation.
- **Observability Stack (via Docker Compose):**
    - **OpenTelemetry Collector**: Receives telemetry data (traces, metrics, logs) from the application.
    - **Jaeger**: For distributed tracing visualization.
    - **Prometheus**: For metrics collection and querying.
    - **Grafana**: For visualizing metrics and logs in dashboards.
- Structured logging with trace and span ID correlation.
- Pre-configured Grafana dashboard.
- Load testing script to generate telemetry data.

## Prerequisites
Before you begin, ensure you have the following installed:
- **Java 21 JDK** (or later)
- **Apache Maven** (3.6.x or later)
- **Docker** (latest version)
- **Docker Compose** (latest version, typically bundled with Docker Desktop or installable as a plugin for Docker CE on Linux)

## Project Structure
```
observability-spring/
├── config/                            # Configuration files for observability tools
│   ├── otel/
│   │   └── otel-collector-config.yml  # OpenTelemetry Collector configuration
│   └── prometheus/
│       └── prometheus.yml             # Prometheus configuration
├── grafana/                           # Grafana provisioning
│   └── provisioning/
│       ├── dashboards/
│       │   └── main-dashboard.json    # Pre-configured Grafana dashboard
│       └── datasources/
│           └── datasource.yml         # Grafana datasource (Prometheus)
├── scripts/
│   └── load-test.sh                   # Script to generate load on the application
├── src/                               # Spring Boot application source code
│   ├── main/
│   │   ├── java/com/example/observabilityspring/  # Main application code
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── scheduler/
│   │   │   └── service/
│   │   │   └── ObservabilitySpringApplication.java
│   │   └── resources/
│   │       ├── application.properties # Spring Boot application configuration
│   │       └── static/                # (empty)
│   └── test/                          # Unit/integration tests (not extensively developed in this demo)
├── docker-compose.yml                 # Docker Compose file for the observability stack
├── pom.xml                            # Maven project file
└── README.md                          # This file
```

## Setup and Running the Application

### 1. Clone the Repository
If you haven't already, clone the repository that contains this project.
```bash
# git clone <repository-url>
# cd <path-to-cloned-repo>/observability-spring
```
(Assuming you are already in the `observability-spring` directory for subsequent commands)

### 2. Build the Spring Boot Application
Compile the application and package it into a JAR file using Maven.
```bash
./mvnw clean install
# or if you don't have mvnw and mvn is in your PATH:
# mvn clean install
```
This will generate a JAR file in the `target/` directory (e.g., `target/observability-spring-0.0.1-SNAPSHOT.jar`).

### 3. Start the Observability Stack
Use Docker Compose to start all the observability tools (OTel Collector, Jaeger, Prometheus, Grafana).
```bash
docker compose up -d
```
- `-d` runs the containers in detached mode (in the background).
- To see the logs of the containers: `docker compose logs -f`
- To see specific service logs: `docker compose logs -f <service_name>` (e.g., `otel-collector`)

### 4. Run the Spring Boot Application
Once the observability stack is running, you can start the Spring Boot application.
```bash
java -jar target/observability-spring-0.0.1-SNAPSHOT.jar
```
The application will start and connect to the OpenTelemetry Collector (running at `http://localhost:4318`) to send its telemetry data.

## Exploring the Observability Stack

### Application API Endpoints
The Spring Boot application exposes the following main API endpoints (base URL: `http://localhost:8080/api`):
- **Products:**
    - `GET /api/products`
    - `POST /api/products`
    - `GET /api/products/{id}`
    - `PUT /api/products/{id}`
    - `DELETE /api/products/{id}`
- **Orders:**
    - `POST /api/orders`

You can interact with these using tools like `curl` or Postman.

### Jaeger (Traces)
- **URL**: [http://localhost:16686](http://localhost:16686)
- **Service to look for**: `observability-spring`
- **What to explore**:
    - Search for traces by service name.
    - Inspect individual traces to see the flow of requests through the application (e.g., a request to `/api/orders` might show interactions with `OrderService` and `ProductService`).
    - Analyze the duration and metadata of each span within a trace.
    - Look for errors if any requests failed.

### Prometheus (Metrics)
- **URL**: [http://localhost:9090](http://localhost:9090)
- **What to explore**:
    - **Graph Tab**: You can query and graph metrics.
        - Example metrics (exposed by Spring Boot Actuator via OTel Collector):
            - `http_server_requests_seconds_count`: Count of HTTP requests.
            - `http_server_requests_seconds_sum`: Total time spent in HTTP requests.
            - `http_server_requests_seconds_bucket`: Histogram for request latencies.
            - `jvm_memory_used_bytes`: JVM memory usage.
            - `jvm_gc_pause_seconds_count`, `jvm_gc_pause_seconds_sum`: Garbage collection metrics.
            - `process_cpu_usage`: CPU usage of the application.
            - (Metrics sent from the app will typically have `service_name="observability-spring"` label)
    - **Targets Page**: (`http://localhost:9090/targets`) Check if Prometheus is successfully scraping metrics from:
        - `otel-collector` (which gets metrics from the Spring Boot app).
        - `spring-boot-app` (if direct scraping from app's `/actuator/prometheus` is also configured, though the primary path is via OTel collector).

### Grafana (Dashboards & Logs)
- **URL**: [http://localhost:3000](http://localhost:3000)
- **Login**: `admin` / `admin` (you'll be prompted to change this on first login)
- **What to explore**:
    - **Pre-built Dashboard**:
        - Navigate to Dashboards (usually a four-squares icon on the left).
        - Look for a dashboard named "`observability-spring Application Dashboard`".
        - This dashboard provides visualizations for:
            - HTTP request rates and latencies.
            - JVM memory and CPU usage.
            - Application logs.
    - **Logs**:
        - The dashboard includes a logs panel. This panel attempts to show logs from the application, which are sent via OpenTelemetry Collector to Grafana's OTLP ingestion endpoint.
        - If the logs panel on the dashboard doesn't automatically show logs, you might need to select the correct data source in the panel's settings. Grafana 10+ should handle OTLP logs well.
        - You can also use Grafana's "Explore" section to query logs. Select the appropriate data source (likely related to OpenTelemetry or a default Grafana logs source). You can filter by attributes like `service_name="observability-spring"`.
    - **Metrics in Explore**:
        - Use the "Explore" section with the "Prometheus" data source to query and visualize any metric available in Prometheus.

### Viewing Raw Logs (Docker)
You can also view the raw console output (which includes structured logs with trace/span IDs) from the Spring Boot application and other services:
```bash
# Logs from the Spring Boot application (if run via java -jar)
# (Check the console where you ran it)

# Logs from Docker containers
docker compose logs -f otel-collector
docker compose logs -f jaeger
docker compose logs -f prometheus
docker compose logs -f grafana
```

## Running the Load Test Script

Note: The script needs to be executed from the correct path. You can either navigate into the `scripts/` directory first, or run it using its relative path from the project root (`observability-spring/`).
To generate some sample data and see the observability tools in action, run the load testing script.
Ensure the Spring Boot application is running before executing this script.

```bash
cd scripts/
./load-test.sh
```
This script will:
- Create multiple products.
- Fetch product lists and individual products.
- Update products.
- Place orders.
- Delete some products.
- It prints output to the console about its actions.

Observe the dashboards in Grafana, traces in Jaeger, and metrics in Prometheus while the script is running or after it completes.

## Stopping the Environment

### 1. Stop the Spring Boot Application
If you ran the application from your terminal, stop it using `Ctrl+C`.

### 2. Stop the Observability Stack
Shut down and remove the Docker containers:
```bash
docker compose down
```
- This stops and removes containers, networks, and default volumes.
- If you used named volumes for persistent storage (e.g., for Grafana) and want to remove them, you might need an additional `docker compose down -v`.

## Troubleshooting / Notes
- **Port Conflicts**: If any of the default ports (8080, 9090, 16686, 3000, 4317, 4318, 8889) are already in use on your system, you may need to adjust the `ports` configuration in `docker-compose.yml` and/or `application.properties`.
- **`host.docker.internal`**: The Prometheus configuration (`prometheus.yml`) uses `host.docker.internal:8080` to scrape the Spring Boot application's `/actuator/prometheus` endpoint. This works if the Spring Boot application is running directly on your host machine (not in a Docker container managed by this `docker-compose.yml`). If you decide to containerize the Spring Boot app within the same Docker Compose setup, you'd change this target to the app's service name (e.g., `observability-spring-app:8080`).
- **Initial Grafana Dashboard Load**: The Grafana dashboard is provisioned automatically. It might take a minute or two after Grafana starts for the dashboard to appear.
- **Log Panel in Grafana**: The "Application Logs" panel in the pre-configured Grafana dashboard is set up to display logs. The data source for logs in Grafana can sometimes require manual selection in the panel editor if the automatic provisioning doesn't perfectly match Grafana's internal OTLP log data source name/UID. Grafana 10.2+ has good support for direct OTLP log ingestion.

This project provides a starting point. Feel free to extend the application, add more complex scenarios, or customize the observability stack further!
