## Cart Controller (`/api/cart`)

### Get User Cart
- GET `/api/cart`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```

### Add Item to Cart
- PUT `/api/cart/add`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body:
```json
{
  "productId": 1,
  "size": 42,
  "quantity": 2
}
```

### Remove Cart Item
- DELETE `/api/cart/item/{cartItemId}`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```

### Update Cart Item
- PUT `/api/cart/item/{cartItemId}`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body (minimum):
```json
{
  "quantity": 3
}
``` 