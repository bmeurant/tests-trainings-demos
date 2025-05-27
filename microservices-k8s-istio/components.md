| **Component**               | **Tool / Stack**                                                    | **Purpose**                                  |
| --------------------------- | ------------------------------------------------------------------- | -------------------------------------------- |
| Language                    | Java 21 + Spring Boot 3.4.0 + Spring Cloud 2024.0.1                 | Modern microservices                         |
| **Project Structure**       | Maven Monorepo                                                      | Centralized and modular organization         |
| Microservices               | `service-a`, `service-b`, `gateway`, `config-server`, `test-client` | REST services + test client                  |
| **Configuration**           | Spring Cloud Config + Kafka + Spring Cloud Bus                      | Centralized config with live reload          |
| **Service Discovery**       | Kubernetes DNS (no Eureka)                                          | Native DNS in K8s with Istio                 |
| **Service Mesh**            | Istio                                                               | Security, routing, resilience, observability |
| **Security**                | JWT Auth via Spring Security (Gateway + Services)                   | Authentication and role propagation via JWT  |
| **Tracing**                 | OpenTelemetry SDK + Istio                                           | Trace ID, Span ID, automatic propagation     |
| **Logging & Observability** | Prometheus, Grafana, Jaeger, Stdout Logs                            | Full monitoring and observability            |
| **Hot Reload**              | Skaffold + Spring DevTools                                          | Auto rebuild and live sync                   |
| **Local K8s**               | k3d + kubectl + Helm + Kustomize                                    | Local deployment aligned with production     |
| **Local Dependencies**      | Docker Compose (Kafka, Grafana, etc.)                               | Quick startup of services outside K8s        |
| **Tool Installation**       | `install-dev-tools.sh` script                                       | Automatic setup of all required tools        |

