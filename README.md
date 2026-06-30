# NextGen HRMS – Training & Learning Management Module (Frontend Client)

## Overview

The **NextGen HRMS – Training & Learning Management Module** is a standalone frontend application developed as part of an HRMS project. This module enables employees to submit training records and certificate details, while providing Reporting Officers (Admins) with tools to audit submissions, control view permissions, and provision user access credentials.

This project is built using a modern frontend stack with a complete client-side mock backend powered by `localStorage`, enabling full standalone capability in the browser.

---

## Features

### Employee Portal (User Profile)
* **Submit Training Records**: Add training completion information including Employee ID, Department, Training Module, and Certificate Number.
* **Upload Certificates**: Drag-and-drop or select certificate documents.
* **Personal Dashboard**: View only your registered training history (when logged in as a standard Employee).
* **Grid and Table View Support**: Switch between responsive cards and detailed tabular views.

### Reporting Officer Portal (Admin Profile)
* **Access Control**: Switch profiles to view and manage organizational data (restricted to Admin accounts).
* **Audit Training Records**: View, search, and filter all certificate submissions across all departments and employees.
* **Provision Employee Access**: Dynamically set roles (`USER`/`ADMIN`) and passwords for registered employees to enable portal logins.
* **Record Management**: Safely delete training entries with instant UI feedback and persistence in mock storage.

### Security and Usability
* **Session-Locked Portal Profiles**: 
  * Authenticated **Admins** cannot switch to the employee view while logged in.
  * Authenticated **Employees** are strictly blocked from switching to the Admin view.
* **Mock Authentication**: Full JWT token simulation and secure session state.
* **Lag-Free Deletions**: Optimistic state updates and layout-aware exit animations for smooth grid rearrangements.

---

## Tech Stack

* **Framework**: React.js (v19)
* **Build Tool**: Vite
* **Styling**: Tailwind CSS (v4)
* **Animations**: Framer Motion
* **Icons**: Lucide React
* **Data Persistence**: Client-side `localStorage`

---

## Setup Instructions

### Clone Repository

```bash
git clone https://github.com/abSenpai/Next-Gen-HRMS.git
cd Next-Gen-HRMS
```

### Run Frontend

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Run the development server:
   ```bash
   npm run dev
   ```

4. Open the application in your browser:
   [http://localhost:5173](http://localhost:5173)

---

## Default Accounts for Testing

The mock database is pre-seeded with the following credentials:

* **Admin (Reporting Officer)**:
  * **Employee ID**: `EMP001`
  * **Password**: `password`
* **Standard Employee (User)**:
  * **Employee ID**: `TE-030316`
  * **Password**: `password`

---

## Author

**abSenpai**

* **GitHub**: [https://github.com/abSenpai](https://github.com/abSenpai)
