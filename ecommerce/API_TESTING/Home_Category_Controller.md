## Home Category Controller (`/api/homeCategories`)

### Create Home Category
- POST `/api/homeCategories`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body (single or array of HomeCategory objects; example single):
```json
{
  "name": "Featured Products",
  "image": "https://cdn.example.com/banners/featured.png",
  "categoryId": "electronics",
  "section": "FEATURED"
}
```

### Get Home Categories (admin listing)
- GET `/api/homeCategories/admin/home-categories`
- Headers:
  ```
  Accept: application/json
  ```

### Create Multiple Categories
- POST `/api/homeCategories/createCategories`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body (array of HomeCategory):
```json
[
  {
    "name": "Top Deals",
    "image": "https://cdn.example.com/banners/deals.png",
    "categoryId": "deals",
    "section": "DEALS"
  }
]
``` 