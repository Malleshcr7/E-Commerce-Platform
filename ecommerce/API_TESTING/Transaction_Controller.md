## Transaction Controller (`/api/transaction`)

### Get Transactions For Seller (by JWT)
- GET `/api/transaction/seler`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```

### Get All Transactions (admin or open depending on security config)
- GET `/api/transaction`
- Headers:
  ```
  Accept: application/json
  ``` 