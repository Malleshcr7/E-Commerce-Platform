## Order Controller (`/api/orders`)

### Create Order
- POST `/api/orders?paymentMethod=RAZORPAY`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body (Address):
```json
{
  "name": "John Doe",
  "adress": "123 Main Street",
  "locality": "Andheri East",
  "city": "Mumbai",
  "state": "Maharashtra",
  "country": "IN",
  "pincode": "400001",
  "mobile": "9876543210"
}
```

### Get Order by ID
- GET `/api/orders/{orderId}`
- Headers:
  ```
  Accept: application/json
  ```

### Get User's Order History
- GET `/api/orders/user`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```

### Get Seller's Orders
- GET `/api/orders/seller/{sellerId}`
- Headers:
  ```
  Accept: application/json
  ```

### Update Order Status
- PUT `/api/orders/{orderId}/status`
- Headers:
  ```
  Content-Type: application/json
  Accept: application/json
  ```
- Body: `"CONFIRMED"`

### Cancel Order
- PUT `/api/orders/{orderId}/cancel`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ``` 