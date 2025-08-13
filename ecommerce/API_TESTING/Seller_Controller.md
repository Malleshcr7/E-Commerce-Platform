## Seller Controller (`/sellers`)

### Seller Login (OTP-based)
- POST `/sellers/login`
- Headers:
  ```
  Content-Type: application/json
  Accept: application/json
  ```
- Body:
```json
{
  "email": "seller@example.com",
  "otp": "123456"
}
```
- Note: The system prefixes email internally for sellers; just pass the plain email.

### Create Seller
- POST `/sellers`
- Headers:
  ```
  Content-Type: application/json
  Accept: application/json
  ```
- Body (Seller):
```json
{
  "sellarName": "Jane Smith",
  "mobile": "9876543210",
  "email": "seller@example.com",
  "password": "password123",
  "businessDetails": {
    "businessName": "Tech Store",
    "businessAddress": "456 Business Street, Mumbai",
    "GSTIN": "22AAAAA0000A1Z5"
  },
  "bankDetails": {
    "accountHolderName": "Jane Smith",
    "accountNumber": "1234567890",
    "ifsc": "HDFC0001234"
  },
  "pickupAdress": {
    "name": "Warehouse",
    "adress": "456 Business Street",
    "locality": "MIDC",
    "city": "Mumbai",
    "state": "MH",
    "country": "IN",
    "pincode": "400093",
    "mobile": "9876543210"
  },
  "GSTIN": "22AAAAA0000A1Z5"
}
```

### Get Seller Profile
- GET `/sellers/profile`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```

### Get Seller Report
- GET `/sellers/report`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```

### Update Seller
- PATCH `/sellers`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body: partial Seller fields 