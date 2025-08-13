## Coupon Controller (`/api/coupons`)

### Create Coupon (Admin)
- POST `/api/coupons/admin/create`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body:
```json
{
  "code": "SAVE20",
  "discountpercentage": 20,
  "validityStartDate": "2024-01-01",
  "validityEndDate": "2024-12-31",
  "minimumAmount": 1000,
  "active": true
}
```

### Get All Coupons
- GET `/api/coupons`
- Headers:
  ```
  Accept: application/json
  ```

### Apply Or Remove Coupon
- POST `/api/coupons/apply?code=SAVE20&apply=true&orderValue=1500`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```

### Remove Coupon (explicit)
- POST `/api/coupons/remove?code=SAVE20`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ``` 