## Payment Controller (`/api/payment`)

### Payment Success Handler
- GET `/api/payment/{paymentId}?paymentLinkId=plink_123`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```
- Notes:
  - `paymentId`: gateway payment identifier
  - `paymentLinkId`: ID of the payment link created earlier
  - Returns message on success: "Payment Successfull" 