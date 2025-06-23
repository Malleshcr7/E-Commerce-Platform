# E-commerce Application

This is a full-stack e-commerce application featuring a Java Spring Boot backend and a React Native (Expo) frontend. The project provides a robust platform for online shopping, including user authentication, product management, order processing, and payment integration.

---

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Backend Setup (Spring Boot)](#backend-setup-spring-boot)
- [Frontend Setup (Expo React Native)](#frontend-setup-expo-react-native)
- [Configuration](#configuration)
- [Testing](#testing)
- [References](#references)
- [License](#license)

---

## Features

- RESTful API for e-commerce operations
- User authentication and authorization (JWT, Spring Security)
- MySQL database integration (Spring Data JPA)
- Email notifications (SMTP)
- Payment gateway integration (Razorpay, Stripe)
- Modern mobile frontend (React Native + Expo)
- Unit and integration tests

---

## Project Structure

```
.
├── ecommerce/                # Spring Boot backend
│   ├── src/
│   ├── pom.xml
│   └── README.md
└── ecommerce-frontend/       # Expo React Native frontend
    ├── app/
    ├── components/
    ├── package.json
    └── README.md
```

---

## Backend Setup (Spring Boot)

### Requirements

- Java 17 or higher
- Maven
- MySQL

### Getting Started

1. **Clone the repository**
    ```bash
    git clone <your-repo-url>
    cd ecommerce
    ```

2. **Configure the application**

    Edit `src/main/resources/application.properties` with your database, JWT, and email credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
    spring.datasource.username=YOUR_DB_USER
    spring.datasource.password=YOUR_DB_PASSWORD
    jwt.secret=YOUR_SECRET_KEY
    spring.mail.username=YOUR_EMAIL
    spring.mail.password=YOUR_EMAIL_PASSWORD
    ```

3. **Build and run the application**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
    The backend will start at [http://localhost:8080](http://localhost:8080).

---

## Frontend Setup (Expo React Native)

### Requirements

- Node.js (v18+ recommended)
- npm or yarn
- [Expo CLI](https://docs.expo.dev/get-started/installation/)

### Getting Started

1. **Install dependencies**
    ```bash
    cd ../ecommerce-frontend
    npm install
    ```

2. **Start the app**
    ```bash
    npx expo start
    ```
    - Use the Expo Go app or an emulator to preview the app.

---

## Configuration

- **Backend:** All configuration is handled via `application.properties`.
- **Frontend:** Edit API endpoints in your frontend code as needed to point to your backend server.

---

## Testing

- **Backend:** Run tests with:
    ```bash
    mvn test
    ```
- **Frontend:** Use Expo's built-in tools and your preferred testing libraries.

---

## References

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Razorpay Java SDK](https://github.com/razorpay/razorpay-java)
- [Stripe Java SDK](https://github.com/stripe/stripe-java)
- [Expo Documentation](https://docs.expo.dev/)

---

## License

This project is for demonstration purposes. 