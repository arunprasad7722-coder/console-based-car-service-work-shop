
# Car Service Workshop Management System

## Project Overview
The Car Service Workshop Management System is a console-based Java application developed using Object-Oriented Programming (OOP) concepts.

It helps a car service workshop register vehicles, create service jobs, assign mechanics, update service status, generate customer invoices, and track mechanic productivity.

## Features
- Vehicle registration and management.
- Creation of Basic and Premium service jobs.
- Mechanic registration and assignment.
- Mechanic skill and workload validation.
- Service status management.
- Adding spare parts and labour hours.
- Invoice generation with coupon discounts.
- GST calculation (18%).
- Daily mechanic productivity report.

## Technologies Used
- Java
- Object-Oriented Programming
- Java Collections Framework
- Java Time API

## OOP Concepts Implemented
1. **Encapsulation:** Private variables and controlled service status updates.
2. **Inheritance:** BasicServiceJob and PremiumServiceJob extend ServiceJob.
3. **Polymorphism:** Different service jobs calculate prices dynamically.
4. **Method Overloading:** Invoice generation with and without coupon codes.
5. **Method Overriding:** Different labour rates and parts pricing for Basic and Premium services.

## Project Structure

```text
CarServiceWorkshop/
│
└── carworkshop/
    ├── Vehicle.java
    ├── JobStatus.java
    ├── ServiceJob.java
    ├── BasicServiceJob.java
    ├── PremiumServiceJob.java
    ├── Mechanic.java
    ├── WorkshopService.java
    └── WorkshopAppMain.java
```

## How to Run the Project

### Step 1: Install Java
Install JDK on your computer.

### Step 2: Download the Project
Clone the repository:

```bash
git clone https://github.com/your-username/CarServiceWorkshop.git
```

### Step 3: Open the Project
Open the project in Eclipse, IntelliJ IDEA, or VS Code.

### Step 4: Compile the Java Files

From the project root directory, run:

```bash
javac carworkshop/*.java
```

### Step 5: Execute the Program

```bash
java carworkshop.WorkshopAppMain
```

## Sample Operations
- Register vehicles.
- Register mechanics.
- Create Basic and Premium service jobs.
- Assign mechanics to service jobs.
- Update job status.
- Add spare parts and working hours.
- Generate invoices.
- Display mechanic productivity.

## Coupon Codes
The application supports the following coupon codes:

| Coupon Code | Discount |
|---|---:|
| WELCOME10 | 10% |
| LOYAL15 | 15% |
| FESTIVE20 | 20% |

## Service Status Flow

```text
OPEN
  |
  v
IN_PROGRESS
  |
  v
READY
  |
  v
DELIVERED
```

## Author
**Name:** Your Name  
**Department:** Computer Science and Engineering  
**Course:** Object Oriented Programming with Java  
**Course Code:** 23CSR303  
**Academic Year:** 2026-2027


