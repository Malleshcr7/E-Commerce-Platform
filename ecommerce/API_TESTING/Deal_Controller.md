## Deal Controller (`/api/deals`)

### Get All Deals
- GET `/api/deals`
- Headers:
  ```
  Accept: application/json
  ```

### Create Deal (Admin)
- POST `/api/deals`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body (Deal):
```json
{
  "title": "Flash Sale",
  "description": "Limited time offer",
  "discountPercentage": 30,
  "validFrom": "2024-01-01T00:00:00Z",
  "validUntil": "2024-01-07T23:59:59Z"
}
```

### Update Deal (Admin)
- PATCH `/api/deals/{id}`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body: Deal fields to update

### Delete Deal (Admin)
- DELETE `/api/deals/{id}`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ``` 