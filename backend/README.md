# Crowd Detection System

This application is a distributed crowd monitoring system designed to estimate the number of people in specific areas using Wi-Fi probe requests from nearby devices.

## Overview

The system collects anonymized probe requests using Raspberry Pi devices operating in monitor mode. These devices capture nearby Wi-Fi signals and send aggregated data to a backend system for processing and visualization.

## How It Works

1. **Data Collection (Edge Layer)**  
   Raspberry Pi nodes scan Wi-Fi channels and collect probe requests from nearby devices. The data includes anonymized MAC addresses, signal strength (RSSI), and timestamps.

2. **Data Ingestion (Backend)**  
   The collected data is sent to the backend through an API Gateway. It is validated and forwarded to a message broker (Kafka) for asynchronous processing.

3. **Data Processing & Storage**  
   - A timeseries service stores the data in InfluxDB for live and historical analysis.  
   - Metadata such as zones and nodes are stored in a relational database (PostgreSQL).

4. **Data Access & Visualization**  
   Processed data is accessed through a Read API and visualized using Grafana dashboards, providing real-time and historical crowd insights.

## Features

- Real-time crowd estimation per zone  
- Historical data analysis  
- Scalable microservices architecture  
- Fault-tolerant data pipeline using Kafka  
- Secure access control with authentication mechanisms  

## Tech Stack

- **Backend:** Spring Boot (Microservices)  
- **Databases:** InfluxDB, PostgreSQL  
- **Message Broker:** Apache Kafka  
- **Visualization:** Grafana  
- **Containerization:** Docker & Kubernetes  

## Notes
- WORK IN PROGRESS
- Crowd estimation is based on detected devices, not exact people count.  
- Accuracy may vary depending on device behavior and environmental conditions.  

---
