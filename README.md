Markdown
# Production-Ready Patient Management System (Microservices)

An enterprise-grade, distributed Patient Management System architected using a microservices pattern. This system leverages Java Spring Boot for backend computing, Apache Kafka for decoupled asynchronous event streams, high-performance gRPC pipelines for critical internal point-to-point requests, and secure API perimeter routing via an API Gateway. The entire infrastructure is defined programmatically using the AWS CDK in Java and deployed locally via LocalStack Pro.

---

##  System Architecture & Stack

[ Frontend / API Clients ]
│ (REST / JSON over HTTP)
▼
[ API Gateway (4044) ] ──(Sync Validate)──► [ Auth Service (4045) ]
│                                          │
│ (Authenticated REST)                     │ (Postgres RDS)
▼                                          ▼
[ Patient Service (4000) ]                   [ Auth DB (5001) ]
│               │
│ (gRPC)        └──(Async Event Burst)──► [ Kafka Broker (MSK) ]
▼                                                │
[ Billing Service (4001) ]                           ▼
[ Analytics Service (4002) ]


### Core Components
* **Backend Framework:** Java 21, Spring Boot 3.x, Spring Data JPA
* **API Perimeter & Routing:** Spring Cloud Gateway (Reactive Streams)
* **Databases:** PostgreSQL (Production engine configurations), H2 (Local rapid runtime testing)
* **Asynchronous Streams:** Apache Kafka (Event Broker logs)
* **Internal Sync Fabric:** gRPC & Protocol Buffers (Protobuf v3)
* **Security Layer:** Spring Security, JSON Web Tokens (JWT) with customized payload roles
* **Testing Suites:** JUnit 5, Rest Assured (Automated integration chains)
* **Infrastructure as Code (IaC):** AWS CDK (Java Libraries)
* **Local Cloud Virtualization:** LocalStack Pro (Simulating AWS VPC, ECS Fargate, RDS, and MSK clusters)

---

## Multi-Module Monorepo Layout

```text
Patient-Mangement-System/
│
├── api-gateway/            # Spring Cloud Gateway routing rules, security layers, and token filters
├── auth-service/           # Handles user registrations, BCrypt hashing, and signed JWT issuance
├── patient-service/        # Master CRUD domain, Kafka event generation, and gRPC client integrations
├── billing-service/        # High-performance gRPC server handling financial profile actions
├── analytics-service/      # Asynchronous worker group capturing patient timeline modifications
├── integration-tests/      # End-to-end endpoint verification chains driven by Rest Assured
└── infrastructure/         # AWS CDK Java application detailing cloud resource configurations
⚙️ Service Engineering Mechanics
1. API Perimeter Security (api-gateway)
Configured using a reactive web filter stack running on host port 4044.

Dynamically executes a custom JWTValidationGatewayFilterFactory on protected routes, transforming inbound endpoints while verifying security payloads seamlessly via internal back-channel requests to the auth-service.

2. User Security & Identity (auth-service)
Seeds a protected users context with secure admin profiles on instantiation via data.sql scripts using safe hashing policies.

Issues cryptographically signed access strings via a compact JWTUtil block utilizing claims tags to establish distinct platform context (e.g., ROLE_ADMIN, ROLE_VIEWER).

3. Core Business Engine (patient-service)
Drives classic layered patterns (Controller -> Service -> Repository) decoupled cleanly via Data Transfer Objects (DTOs) and structural Mappers.

Implements a dual transactional runtime path on resource creation events:

Synchronous Call: Calls the billing-service via a blocking gRPC pipeline stub to establish payment contexts immediately.

Asynchronous Call: serializes transaction records down to high-performance byte logs and posts them straight onto the downstream Kafka stream.

4. Financial Context Stub (billing-service)
Hosts an enterprise-grade gRPC listener on port 9091 inside the system perimeter.

Compiles strongly typed data structures transparently from descriptive raw .proto definitions using automated build-time Maven plugin hooks.

5. Stream Analytics Engine (analytics-service)
Deploys custom Spring Kafka listeners associated under dedicated tracking consumer group layouts.

Captures high-frequency raw data logs, deserializes byte messages back into target model instances, and logs auditing markers defensively within a trial block structure.

🛠️ Compilation & Local Container Builds
Prerequisites
Java 21 SDK

Docker Engine / Docker Desktop

AWS CLI v2 (Configured with dummy profiles test/us-east-1 for LocalStack context matching)

Local Build Cycle
Ensure all internal protocols are freshly generated prior to constructing your application layout:

Bash
# Compile and generate internal gRPC and Protobuf source layers
mvn clean compile

# Construct standalone microservice image footprints manually (Example)
cd ./patient-service
docker build -t patient-service:latest .
☁️ Continuous Delivery via Infrastructure as Code (AWS CDK)
The system details resource architecture declaratively within the infrastructure/ package using the official AWS Cloud Development Kit.

Managed Cloud Layout Blueprint
VPC Setup: Generates dynamic isolated subnets (Private/Public distribution matrices) with a multi-AZ allocation strategy.

Amazon RDS PostgreSql: Configures database resources hooked up to dynamic credentials attachments using AWS Secrets Manager variables.

Amazon MSK Cluster: Virtualizes an asynchronous Kafka broker log structure wired explicitly inside the internal private network boundaries.

Amazon ECS (Fargate Engine): Orchestrates tasks out-of-the-box, fine-tuning task memory, logging properties, and resource parameters cleanly.

Application Load Balancer (ALB): Sets up public edge proxies to interface with outside endpoints while securing inner backend microservices.

Launching the Cloud Stack
Synthesize Assets:
Execute your infrastructure engine locally to parse out target stack designs into unified CloudFormation JSON layouts.

Bash
# Generates stack output targets inside infrastructure/cdk.out/
Deploy via LocalStack Shell Engine:
Use the customized infrastructure automation controller scripts to securely push assets to LocalStack (http://localhost:4566):

Bash
cd ./infrastructure
./localstack-deploy.sh
🧪 Automated Testing Pipeline
An isolated verification engine runs inside integration-tests/ using Rest Assured to simulate exact real-world operations outside the internal network cluster:

Bash
# Trigger the automated quality suites from root workspace locations
mvn test -pl integration-tests
Integration Workflow Assertions
Arrange: Constructs raw string payloads formatting client payload maps explicitly.

Act: Posts payload parameters to the Gateway endpoint ruleset to process identity requests safely.

Assert: Parses resulting payload objects programmatically, extracts token tokens safely, and hooks them directly into subsequent header contexts to verify data integrity over downstream services.
