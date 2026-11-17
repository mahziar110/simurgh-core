 <p align="center">
  <img src="assets/simurgh-logo.png" alt="Simurgh Logo" width="220">
</p>

<h1 align="center">Simurgh</h1>

<p align="center">
  <strong>Monitoring & Observability Platform</strong>
</p>

<p align="center">
  Engineering Reliable Digital Solutions
</p>

---

Simurgh is a monitoring and observability platform designed to help organizations monitor, understand, and manage their infrastructure and software systems through a unified platform.

The project aims to provide a flexible, extensible, and self-hosted solution for collecting telemetry, monitoring infrastructure, analyzing operational data, and managing system events across distributed environments.

> **Project Status: Under Active Development**
>
> Simurgh is currently in its early development stage. Its architecture, APIs, and features are evolving. It is not yet ready for production use.

## Overview

Modern infrastructure consists of interconnected systems, services, and technologies that require continuous monitoring and reliable operational visibility.

Simurgh aims to address this challenge by bringing infrastructure monitoring, telemetry collection, data management, and observability capabilities together in a single extensible platform.

The platform is being designed around two primary components:

- **Simurgh Core:** The central management and data processing platform.
- **Simurgh Feather:** A lightweight and extensible agent platform responsible for collecting telemetry from monitored environments.

The long-term goal is to provide a unified monitoring experience across different infrastructure environments, from individual Linux servers to complex distributed systems.

## Vision

Simurgh is being built with the following principles in mind:

- **Self-Hosted:** Organizations should have control over their monitoring infrastructure and collected data.
- **Extensible Architecture:** Support for different monitoring technologies through a modular plugin architecture.
- **Scalability:** Designed to evolve from small deployments to large distributed environments.
- **Technology Agnostic:** Ability to monitor heterogeneous infrastructure and services.
- **Open Source:** An open development model that encourages transparency and community contributions.
- **Operational Visibility:** Bringing metrics, logs, events, and other telemetry together to improve system observability.

## Architecture

Simurgh follows a distributed architecture consisting of a central server and lightweight monitoring agents.

### Simurgh Core

The central component responsible for managing the monitoring platform.

Its planned responsibilities include:

- User and access management.
- Agent registration and management.
- Telemetry ingestion and processing.
- Historical data storage.
- Metrics and log management.
- Event and alert management.
- Infrastructure monitoring coordination.
- Centralized configuration management.

### Simurgh Feather

Simurgh Feather is the agent-side component responsible for collecting information from monitored infrastructure.

It is designed around a modular plugin architecture, allowing monitoring capabilities to be extended without changing the core agent.

Planned monitoring integrations include:

- Linux system monitoring.
- Docker and container monitoring.
- Kubernetes monitoring.
- Database monitoring.
- Web server monitoring.
- Custom third-party monitoring plugins.

The architecture is intended to support both first-party and third-party plugins, with the possibility of using different programming languages.

## Technology Stack

The current backend development is based on the following technologies:

| Technology | Purpose |
|---|---|
| Java 21 | Primary development language |
| Spring Boot | Backend application framework |
| Spring Data JPA | Data persistence |
| PostgreSQL | Relational database |
| MapStruct | Object mapping |
| Maven | Build and dependency management |

Additional technologies and infrastructure components will be introduced as the architecture evolves.

## Development Roadmap

Simurgh is being developed incrementally. The roadmap below represents the intended development direction, not a list of completed features.

### Phase 1 — Core Foundation

- [x] Initial project structure
- [ ] Identity and access management
- [ ] User management
- [ ] Role and permission management
- [ ] Authentication and session management
- [ ] Core application infrastructure

### Phase 2 — Agent Management

- [ ] Simurgh Feather architecture
- [ ] Agent registration and identification
- [ ] Secure server-agent communication
- [ ] Agent lifecycle management
- [ ] Plugin management foundation

### Phase 3 — Telemetry Collection

- [ ] Telemetry ingestion pipeline
- [ ] Metrics collection
- [ ] Log collection
- [ ] Historical data management
- [ ] Data processing and aggregation

### Phase 4 — Monitoring & Observability

- [ ] Infrastructure monitoring
- [ ] Metrics analysis
- [ ] Log analysis
- [ ] Events and alerting
- [ ] Monitoring dashboards
- [ ] System health management

### Phase 5 — Advanced Capabilities

- [ ] Distributed monitoring
- [ ] Advanced analytics
- [ ] Plugin ecosystem
- [ ] Advanced alert management
- [ ] AI-assisted monitoring and analysis

The roadmap is subject to change as development progresses and technical requirements become clearer.

## Project Structure

The project is being organized around modular components.

The intended high-level structure is:

```text
simurgh/
│
├── simurgh-core/
│   └── Central management and monitoring platform
│
├── simurgh-feather/
│   └── Infrastructure monitoring agent
│
├── simurgh-panel/
│   └── Web-based management interface
```

Individual components may evolve into separate repositories as the project matures.

## Getting Started

Simurgh is currently under active development, and a stable installation and deployment process has not yet been established.

Installation instructions, configuration guides, API documentation, and deployment examples will be published as the relevant components become ready.

For now, the repository is primarily intended for development, architectural exploration, and following the project's progress.

## Contributing

Simurgh is being developed as an open-source project, and community involvement is welcome.

As the project evolves, contribution guidelines, development documentation, and issue templates will be introduced to make collaboration easier.

If you are interested in the project, you can follow its development, review its progress, and share feedback through GitHub Issues.

Please note that the architecture and implementation are still evolving, so it is recommended to discuss significant changes before submitting major contributions.

## License

The project's open-source licensing terms will be published separately.

---

<p align="center">
  <strong>Simurgh</strong><br>
  Engineering Reliable Digital Solutions<br><br>
  Developed by <strong>Simurgh Solutions</strong>
</p>
