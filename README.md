# Inventory Management System

A Java-based retail resource planning application developed as a group initiative. This project utilizes Object-Oriented Programming (OOP) principles to manage staff records and inventory data through a persistent file-based system.

## Technical Overview
### Architecture
The system is built on a modular Object-Oriented architecture. It utilizes specific classes to model real-world entities, such as `EmployeeUser`, ensuring high cohesion and low coupling.
* **Runtime Environment:** Java SE (Standard Edition).
* **Data Persistence:** Custom file I/O implementation. The system parses and writes to `Employees.txt`, acting as a lightweight database.
* **Data Serialization:** Implements custom string representation methods (e.g., `lineRepresentation()`) to serialize object data into comma-separated values for storage.

### Functional Specifications
#### 1. User Role Management
The application distinguishes between different user privileges to maintain system security and data integrity:
* **Admin:** Grants elevated privileges for workforce management. Admins are the only users authorized to onboard new staff or remove records for departed employees.
* **Employee:** Represents the standard staff user. The system stores detailed profiles for each employee, including `EmployeeId`, `Name`, `Email`, `Address`, and `PhoneNumber`.

#### 2. Data Persistence & Retrieval
* **Record Parsing:** The system reads from `Employees.txt` upon startup, converting raw text lines into dynamic Java objects.
* **Search Functionality:** efficient lookup algorithms allow the system to retrieve specific employee details using their unique `EmployeeId` as a search key.
* **Atomic Writes:** Modifications to the employee registry (additions/deletions) are written directly to the file system to prevent data loss between sessions.

## Contributors
* Mohamed Bahig
* Sara Hany
* Hayat Tarek
* Shaden Rafik
