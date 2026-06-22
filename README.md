# NextGen HRMS – Training & Learning Management Module

## Overview

The **NextGen HRMS – Training & Learning Management Module** is a full-stack web application developed as part of an HRMS internship project. The module enables employees to manage training records and certification details while providing a foundation for future HRMS functionalities.

The application follows a modern client-server architecture using **React.js** for the frontend, **Spring Boot** for the backend, and **PostgreSQL** for data persistence.

---

## Features

### Employee Features

* Submit training records
* Upload training certificate details
* Track training status
* View submitted training information
* Search and filter training records

### Training Management Features

* Training module management
* Employee training tracking
* Certificate record management
* Status monitoring (Completed, In Progress, Under Review, Expired)
* Department-wise training organization

### System Features

* REST API Integration
* PostgreSQL Database Integration
* Responsive User Interface
* Real-time Data Fetching
* Form Validation
* Dynamic Search & Filtering
* Grid and Table View Support

---

## Tech Stack

### Frontend

* React.js
* Vite
* JavaScript (ES6+)
* Tailwind CSS
* Framer Motion
* Lucide React Icons

### Backend

* Spring Boot 4
* Java 21
* Spring Data JPA
* REST APIs

### Database

* PostgreSQL

### Development Tools

* Git
* GitHub
* VS Code
* Maven
* Postman

---

## System Architecture

```text
React Frontend
       │
       ▼
Spring Boot REST APIs
       │
       ▼
PostgreSQL Database
```

---

## Implemented REST APIs

### Fetch All Training Records

```http
GET /api/certificates/all
```

Returns all available training records.

---

### Save Training Record

```http
POST /api/certificates/save
```

Stores employee training information and certificate details.

---

### Get Employee Training Records

```http
GET /api/certificates/employee/{employeeId}
```

Fetches training records for a specific employee.

---

### Download Certificate

```http
GET /api/certificates/download/{recordId}
```

Downloads or previews a stored certificate document.

---

## Database Design

### Entities

#### Employee

* Employee ID
* Employee Name
* Department

#### Department

* Department ID
* Department Name

#### Training Module

* Module ID
* Module Name
* Training Type

#### Training Record

* Record ID
* Employee Reference
* Module Reference
* Certificate Number
* Instructor Name
* Issue Date
* Status
* Remarks
* Uploaded Certificate Details

---

## Project Structure

```text
src
│
├── assets
│   ├── hero.png
│   ├── meity_logo.svg
│   ├── nic_logo.svg
│   ├── tripura_logo.png
│   └── tripura_logo.svg
│
├── components
│   ├── BackToTop.jsx
│   ├── FaqAccordion.jsx
│   ├── Footer.jsx
│   ├── Header.jsx
│   ├── Hero.jsx
│   ├── JsonPreview.jsx
│   ├── TrainingCards.jsx
│   └── TrainingForm.jsx
│
├── data
│   └── trainingData.json
│
├── pages
│   └── Home.jsx
│
├── services
│   └── trainingService.js
│
├── App.jsx
├── App.css
├── index.css
└── main.jsx

Backend
│
├── controller
│   └── TrainingRecordController.java
│
├── entity
│   ├── Department.java
│   ├── Employee.java
│   ├── TrainingModule.java
│   └── TrainingRecord.java
│
├── repository
│   ├── DepartmentRepository.java
│   ├── EmployeeRepository.java
│   ├── TrainingModuleRepository.java
│   └── TrainingRecordRepository.java
│
├── service
│   └── TrainingRecordService.java
│
└── HrmsApplication.java
```

---

## Setup Instructions

### Clone Repository

```bash
git clone https://github.com/manikanta-tamminana/Training_Module_of_NextGen_HRMS.git
cd Training_Module_of_NextGen_HRMS
```

---

### Frontend Setup

Install dependencies:

```bash
npm install
```

Run development server:

```bash
npm run dev
```

Frontend runs at:

```text
http://localhost:5173
```

---

### Backend Setup

Configure PostgreSQL database credentials inside:

```text
src/main/resources/application.properties
```

Run backend:

```bash
./mvnw spring-boot:run
```

Backend runs at:

```text
http://localhost:8080
```

---

## Current Progress

### Completed

* React Frontend Development
* Spring Boot Backend Development
* PostgreSQL Integration
* REST API Integration
* Training Submission Form
* Dynamic Record Fetching
* Employee & Training Data Modeling
* Search Functionality
* Status Filtering
* Responsive UI
* Training Record Persistence

### In Progress

* Record Deletion API
* Enhanced Validation
* Role-Based Access Control
* Authentication & Authorization

---

## Future Enhancements

* User Authentication
* Role-Based Access Control
* Employee Dashboard
* Analytics & Reporting
* Certificate File Management
* Leave Management Module
* Attendance Management Module
* Performance Management Module
* Notification System

---

## Learning Outcomes

Through this project, the following concepts were implemented and practiced:

* React Components & Props
* State Management using useState
* React Hooks
* REST API Integration
* Spring Boot Development
* JPA & Hibernate
* PostgreSQL Database Design
* Full Stack Application Development
* Git & GitHub Workflow

---

## Author

**Manikanta Tamminana**

Intern – NextGen HRMS Project

GitHub:
https://github.com/manikanta-tamminana
