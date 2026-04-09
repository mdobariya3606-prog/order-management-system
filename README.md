# Order Management System

Backend system built using **Spring Boot** and **MySQL** to manage the complete order lifecycle including **order processing, billing, GST calculation, payment confirmation, and automatic inventory updates**.

The system follows a **layered architecture (Controller → Service → Repository)** ensuring clean structure, scalability, and maintainability.

Integrated notification services:
- WhatsApp bill sent to customer after order confirmation
- SMS alert sent to admin when stock reaches threshold level
- Scheduled daily email report of inventory status

---

## Project Overview

This project simulates a real-world order workflow where customers purchase products and the system automatically:

- Calculates GST
- Generates bill
- Confirms payment
- Updates inventory stock
- Sends notifications
- Generates daily inventory report

Focus is on **transaction safety**, **automation**, and **scalable backend design**.

---

## Features

### Order Processing
- Create product orders
- Automatic bill generation
- GST calculation included in total amount
- Payment confirmation workflow
- Maintains order records

### Inventory Management
- Automatic stock deduction after order
- Admin can add, update, delete products
- Low stock threshold alert system
- Prevents negative stock using transaction management

### Notification Integration
- WhatsApp bill sent to customer mobile number
- SMS alert sent to admin when stock is low
- Scheduled daily email report of inventory status
- Real-time automated communication

### Architecture
- Layered architecture implementation
- RESTful API design
- Transaction-safe workflow
- Scheduled automation using Spring Scheduler
- Modular backend structure

---

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Hibernate
- Spring Scheduler
- REST API
- Maven
- Postman

---

## Project Structure

```
order-management-system
│
├── controller        # API endpoints
├── service           # Business logic
├── repository        # Database operations
├── model             # Entity classes
├── dto               # Request and response objects
├── config            # Configuration files
├── scheduler         # Daily email scheduler
├── notification      # WhatsApp and SMS integration
└── application.properties
```

---

## System Workflow

1. Admin adds product with price and stock quantity
2. Customer selects product and quantity
3. System calculates GST
4. Bill generated automatically
5. Payment confirmation processed
6. Order stored in database
7. Stock updated automatically
8. If stock reaches threshold → SMS alert sent to admin
9. WhatsApp bill sent to customer
10. Daily email report sent to admin showing inventory status

---

## Database Design

### Product Table
- id
- product_name
- price
- stock_quantity
- threshold_quantity

### Customer Table
- id
- customer_name
- mobile_number

### Orders Table
- id
- customer_id
- total_amount
- gst_amount
- payment_status
- order_date

### Order_Items Table
- id
- order_id
- product_id
- quantity
- price

---

## Key Concepts Implemented

- Layered Architecture
- Transaction Management
- Scheduling (automated email report)
- Notification Integration (WhatsApp, SMS, Email)
- REST API Development
- Database Relationships
- GST Calculation Logic
- Inventory Threshold Alert System

---

## API Modules

### Product APIs
- Add Product
- Update Product
- Delete Product
- Get All Products

### Order APIs
- Create Order
- Get Order Details
- Get Order History

### Notification Services
- Send WhatsApp Bill
- Send Low Stock SMS Alert
- Send Daily Email Report

---

## Setup Instructions

### Prerequisites

- Java 17+
- MySQL
- Maven
- Postman
- IDE (IntelliJ recommended)

---

### Steps to Run Project

1. Clone repository

```
git clone https://github.com/mdobariya3606-prog/order-management-system.git
```

2. Configure database in application.properties

```
spring.datasource.url=jdbc:mysql://localhost:3306/order_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Run application

```
mvn spring-boot:run
```

4. Test APIs using Postman

---

## Future Improvements

- Role Based Authentication (Admin / Customer)
- Payment Gateway Integration (Razorpay / Stripe)
- Invoice PDF generation
- Admin dashboard analytics
- Docker deployment
- Pagination & filtering
- Role based notifications

---

## Author

Meet Dobariya  
Backend Developer  

Java | Spring Boot | MySQL
