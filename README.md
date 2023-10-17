# Order-Processing-Microservices
The system employs a microservices architecture where the inventory and payment services operate independently. 
The payment service handles order placement, while the inventory service is responsible for managing stock availability.
They communicate through RESTful APIs. Additionally, Kafka events (order-completed-event and order-cancelled-event) facilitate real-time updates between the services.

# Prerequisites
*  Java 8 or higher
*  Apache Maven
*  Kafka (for event handling)
*  MySQL
*  Spring Boot

# Getting started
1. Clone the repository
```
git clone https://github.com/kedarnathrishit/Order-Processing-Microservices.git
```
2.  Start zookeeper
```
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```
3.  Start kafka server
```
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```
4.  Create the required topics order-completed-event and order-cancelled-event
```
bin\windows\kafka-topics.bat --create --topic topicname --bootstrap-server localhost:9092
```
5. Build the inventory and payment services
```
mvn clean install
```
6. Start the microservices
```
mvn spring-boot:run
```

# Usage
Once the services are up and running, please go through the end points. The main endpoint, the endpoint
to place the order is "/payments/placeOrder".

