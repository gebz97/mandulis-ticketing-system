# Mandulis Ticketing System

---
# Vision
## 1. Introduction

### 1.1 Purpose
The purpose of this document is to outline the vision for the development of MTS, an open source ticketing system. This system aims to be dead simple, user-friendly, performant, and scalable.

### 1.2 Scope
This document covers the goals, target audience, key features, and high-level requirements for the ticketing system.

## 2. Vision Statement

Our vision is to create a ticketing system that is:
- **Dead Simple**: Easy to understand and use, with minimal learning curve.
- **User-Friendly**: Intuitive interface that enhances user experience.
- **Performant**: Efficient and responsive, even under heavy load.
- **Scalable**: Capable of growing with the needs of its users.

## 3. Goals and Objectives

### 3.1 Primary Goals
- Develop a ticketing system that is easy to install, configure, and use.
- Ensure the system is highly performant and can handle a large number of tickets and users.
- Design the system to be scalable, allowing it to grow with the needs of its users.

### 3.2 Secondary Goals
- Foster a community of contributors and users who can help improve and maintain the system.
- Provide comprehensive documentation and support to assist users and developers.

## 4. Target Audience

The target audience for this ticketing system includes:
- Small to medium-sized businesses looking for an easy-to-use ticketing solution.
- Open source enthusiasts and developers who want to contribute to the project.
- Organizations seeking a scalable and performant ticketing system.

## 5. Key Features

### 5.1 User Interface
- Intuitive and clean design.
- Easy navigation and ticket management.
- Responsive design for use on various devices (TBD).

### 5.2 Performance
- Fast response times and efficient processing.
- Ability to handle a large number of tickets and users without performance degradation.

### 5.3 Scalability
- Modular architecture to support future growth.
- Support for distributed deployment and load balancing.

### 5.4 Community and Support
- Comprehensive documentation for users and developers.
- Active community forums and support channels (TBD).

## 6. High-Level Requirements

### 6.1 Functional Requirements
- User authentication and authorization.
- Ticket creation, assignment, and tracking.
- Reporting and analytics (TBD).

### 6.2 Non-Functional Requirements
- Performance and scalability.
- Security and data protection.
- High availability and reliability (TBD).

## 7. Conclusion

This vision document outlines the goals and objectives for the development of an open source ticketing system. By focusing on simplicity, user-friendliness, performance, and scalability, we aim to create a solution that meets the needs of a wide range of users and organizations.

---

### How to run:
1. Install PostgreSQL server
2. Change the connection string, username and password in application.properties
3. Run the following command in a terminal:
```
mvn clean spring-boot:run
```
