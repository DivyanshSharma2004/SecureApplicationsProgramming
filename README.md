SecureApp – Spring Boot Application
Overview
SecureApp is a Java-based Spring Boot web application developed for academic purposes.
The project demonstrates both unsecured and secured implementations of a web application using Spring Security.

The repository contains two branches:

insecure – baseline implementation without security controls
secure – enhanced version with Spring Security integration
The application uses an embedded SQLite database, allowing it to run locally without external dependencies.

Technologies Used
Java 21
Spring Boot 3.5.10-SNAPSHOT
Maven
Spring MVC
Spring Data JPA
Spring Security
Thymeleaf
SQLite (JDBC)
Hibernate ORM
Branch Structure
insecure
Core application functionality
insecure exceptions handeling
broken authentication logic
Plaintext Password Storage & Comparison
SQL Injection vulnerability
Cross-Site Request Forgery (CSRF) Disabled
Missing Content Security Policy (CSP)
XSS rendered,dom,stored vulnerabilities
secure
Builds upon the insecure version
Implements Spring Security features
CSP implemented
XSS attacks removed
database entity protection from null values
more secure exceptions handeling
logging
password is now encrypted using bcrypt
sql injection vulnerability removed
CSRF is still disabled
Ensure the correct branch is checked out before running the application.

Prerequisites
The following must be installed on the system:

Java JDK 21
Maven 3.8+
Git
An IDE capable of running Spring Boot applications (IntelliJ IDEA recommended)
Project Setup
1. Clone the Repository
run the SecureApplicaitons main class then go over to http://localhost:8080/login
