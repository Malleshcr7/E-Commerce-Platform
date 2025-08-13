## Authentication Controller (`/auth`)

### Important Headers
```
Content-Type: application/json
Accept: application/json
```

### Customer Auth Flow (Create + Login)
- Send OTP (role: customer)
  - POST `/auth/send/login-signup-otp`
  - Headers:
    ```
    Content-Type: application/json
    Accept: application/json
    ```
  - Body:
    ```json
    {
      "email": "user@example.com",
      "role": "ROLE_CUSTOMER"
    }
    ```
- Create account (first time only)
  - POST `/auth/signup`
  - Headers:
    ```
    Content-Type: application/json
    Accept: application/json
    ```
  - Body (SignupRequest):
    ```json
    {
      "email": "user@example.com",
      "fullName": "John Doe",
      "otp": "123456"
    }
    ```
- Login
  - POST `/auth/signing_`
  - Headers:
    ```
    Content-Type: application/json
    Accept: application/json
    ```
  - Body (LoginRequest):
    ```json
    {
      "email": "user@example.com",
      "otp": "123456"
    }
    ```

---

### Endpoints

#### Send OTP
- POST `/auth/send/login-signup-otp`
- Headers:
  ```
  Content-Type: application/json
  Accept: application/json
  ```
- Body:
```json
{
  "email": "user@example.com",
  "role": "ROLE_CUSTOMER"
}
```

#### User Registration
- POST `/auth/signup`
- Headers:
  ```
  Content-Type: application/json
  Accept: application/json
  ```
- Body:
```json
{
  "email": "user@example.com",
  "fullName": "John Doe",
  "otp": "123456"
}
```
- Note: First call `POST /auth/send/login-signup-otp` to receive the OTP for the given email, then use that OTP here.

#### User Login
- POST `/auth/signing_`
- Headers:
  ```
  Content-Type: application/json
  Accept: application/json
  ```
- Body:
```json
{
  "email": "user@example.com",
  "otp": "123456"
}
``` 