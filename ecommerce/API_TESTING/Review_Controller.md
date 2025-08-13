## Review Controller (`/api/reviews`)

### Create Review
- POST `/api/reviews/product/{productId}`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body:
```json
{
  "reviewText": "Excellent product, highly recommended!",
  "rating": 5,
  "productImages": [
    "https://cdn.example.com/reviews/img1.jpg"
  ]
}
```

### Get Product Reviews
- GET `/api/reviews/product/{productId}`
- Headers:
  ```
  Accept: application/json
  ```

### Update Review
- PUT `/api/reviews/reviews/{reviewId}`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body:
```json
{
  "reviewText": "Updated review comment",
  "rating": 4
}
``` 