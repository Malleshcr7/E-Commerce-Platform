## Wishlist Controller (`/api/wishlist`)

### Get Wishlist
- GET `/api/wishlist`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```

### Toggle Add/Remove Product
- POST `/api/wishlist/add-product/{productId}`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ``` 