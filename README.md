# E-commerce Mobile Application

A modern e-commerce mobile application built with React Native (Expo) frontend and Java Spring Boot backend. This application provides a seamless shopping experience with features like product browsing, cart management, and user authentication.

## Features

- 📱 Modern UI with React Native and Expo
- 🛍️ Product browsing and search
- 🛒 Shopping cart management
- 💫 Smooth animations and transitions
- 🔐 User authentication (Email/OTP)
- 💾 Persistent cart storage
- 📱 Bottom tab navigation
- 🎯 Product categories
- ❤️ Wishlist functionality
- 👤 User profile management
- 🏪 Seller dashboard and management
- 📊 Order tracking and history
- 📍 Multiple delivery addresses
- 💳 Secure payment integration

## Tech Stack

### Frontend
- React Native (Expo)
- TypeScript
- Expo Router for navigation
- Context API for state management
- Axios for API communication
- AsyncStorage for local storage
- React Native Paper for UI components
- React Native Reanimated for animations

### Backend
- Java Spring Boot
- Spring Security with JWT
- Spring Data JPA
- Spring Cloud (for microservices)
- MySQL/PostgreSQL Database
- Redis for caching
- RabbitMQ for message queuing
- Docker for containerization
- Swagger for API documentation

## Getting Started

### Prerequisites
- Node.js (v14 or later)
- npm or yarn
- Expo CLI
- Java JDK 11 or later
- Maven
- Docker and Docker Compose
- MySQL/PostgreSQL

### Frontend Setup

1. Clone the repository:
```bash
git clone https://github.com/yourusername/ecommerce-app.git
cd ecommerce-app/ecommerce-frontend
```

2. Install dependencies:
```bash
npm install
# or
yarn install
```

3. Start the development server:
```bash
npx expo start
```

4. Run on your device:
   - Install Expo Go app on your mobile device
   - Scan the QR code from the terminal
   - Or press 'a' for Android emulator / 'i' for iOS simulator

### Backend Setup

1. Navigate to backend directory:
```bash
cd ../ecommerce-backend
```

2. Configure database:
```bash
# Update application.properties with your database credentials
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Run with Maven:
```bash
mvn spring-boot:run
```

4. Or use Docker:
```bash
docker-compose up -d
```

### Environment Variables

#### Frontend (.env)
```
API_BASE_URL=your_backend_api_url
STRIPE_PUBLIC_KEY=your_stripe_public_key
```

#### Backend (application.properties)
```
spring.profiles.active=dev
jwt.secret=your_jwt_secret
jwt.expiration=86400000
stripe.secret.key=your_stripe_secret_key
```

## API Documentation

### Authentication Endpoints
```
POST /api/auth/register - Register new user
POST /api/auth/login - User login
POST /api/auth/refresh - Refresh token
POST /api/auth/logout - Logout user
```

### Product Endpoints
```
GET /api/products - Get all products
GET /api/products/{id} - Get product by ID
GET /api/products/category/{category} - Get products by category
POST /api/products - Create new product (Seller only)
PUT /api/products/{id} - Update product (Seller only)
DELETE /api/products/{id} - Delete product (Seller only)
```

### Cart Endpoints
```
GET /api/cart - Get user's cart
POST /api/cart/add - Add item to cart
PUT /api/cart/update - Update cart item
DELETE /api/cart/{itemId} - Remove item from cart
```

### Order Endpoints
```
POST /api/orders - Create new order
GET /api/orders - Get user's orders
GET /api/orders/{id} - Get order details
PUT /api/orders/{id}/status - Update order status (Seller only)
```

## Project Structure

```
ecommerce-app/
├── ecommerce-frontend/
│   ├── app/
│   │   ├── (tabs)/
│   │   │   ├── home.tsx
│   │   │   ├── categories.tsx
│   │   │   ├── cart.tsx
│   │   │   ├── wishlist.tsx
│   │   │   └── profile.tsx
│   │   ├── product-details.tsx
│   │   └── register.tsx
│   ├── components/
│   ├── contexts/
│   ├── services/
│   └── types/
└── ecommerce-backend/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/ecommerce/
    │   │   │       ├── controllers/
    │   │   │       ├── models/
    │   │   │       ├── repositories/
    │   │   │       └── services/
    │   │   └── resources/
    │   └── test/
    ├── docker/
    └── pom.xml
```

## Deployment

### Frontend Deployment (Expo)

1. Build the app:
```bash
eas build --platform ios
eas build --platform android
```

2. Submit to stores:
```bash
eas submit --platform ios
eas submit --platform android
```

### Backend Deployment

1. Build JAR file:
```bash
mvn clean package
```

2. Deploy using Docker:
```bash
docker build -t ecommerce-backend .
docker push your-registry/ecommerce-backend
```

3. Deploy to cloud (AWS example):
```bash
aws ecs update-service --cluster your-cluster --service your-service --force-new-deployment
```

## Troubleshooting

### Common Issues

1. **Frontend Build Fails**
   - Clear npm cache: `npm cache clean --force`
   - Delete node_modules: `rm -rf node_modules`
   - Reinstall dependencies: `npm install`

2. **Backend Connection Issues**
   - Verify database connection
   - Check application.properties configuration
   - Ensure correct environment variables

3. **JWT Token Issues**
   - Clear AsyncStorage
   - Verify token expiration time
   - Check backend logs for token validation errors

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Expo team for the amazing framework
- React Native community
- Spring Boot team
- All contributors and users of this application 