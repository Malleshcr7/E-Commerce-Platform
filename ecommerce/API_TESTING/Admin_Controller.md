## Admin Controller (`/api/admin`)

### Get All Users
- GET `/api/admin/users`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```

### Get All Orders
- GET `/api/admin/orders`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```

### Get All Products
- GET `/api/admin/products`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```

### Update User Status
- PUT `/api/admin/users/{id}/status`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body: `"ACTIVE"` 