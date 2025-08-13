## Product Controller (`/api/products`)

### Create Product
- POST `/api/products?sellerId=1`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Content-Type: application/json
  Accept: application/json
  ```
- Body:
```json
{
  "title": "iPhone 15 Pro",
  "description": "Latest iPhone with advanced features",
  "mrpPrice": 129900,
  "sellingPrice": 99900,
  "color": "Space Black",
  "images": [
    "https://cdn.example.com/iphone15pro/front.jpg",
    "https://cdn.example.com/iphone15pro/back.jpg"
  ],
  "category": "electronics",
  "category2": "mobiles",
  "category3": "apple",
  "sizes": "128GB,256GB,512GB"
}
```

### Get Product by ID
- GET `/api/products/{productId}`
- Headers:
  ```
  Authorization: Bearer <jwt_token>
  Accept: application/json
  ```

### Search Products
- GET `/api/products/search?query=iPhone`
- Headers:
  ```
  Accept: application/json
  ```

### Get All Products with Filters
- GET `/api/products?category=electronics&brand=Apple&colors=black&sizes=128GB&minPrice=1000&maxPrice=100000&minDiscount=10&sort=price_asc&stock=IN_STOCK&pageNumber=0&query=iphone`
- Headers:
  ```
  Accept: application/json
  ```

### Apply Discount
- POST `/api/products/{productId}/discount?discountPercentage=20`
- Headers:
  ```
  Accept: application/json
  ```

### Update Product
- PATCH `/api/products/{productId}/update`
- Headers:
  ```
  Content-Type: application/json
  Accept: application/json
  ```
- Body (Product fields example):
```json
{
  "title": "iPhone 15 Pro Max",
  "mrpPrice": 139900,
  "sellingPrice": 109900,
  "discountPercentage": 15,
  "quantity": 45,
  "color": "Natural Titanium",
  "images": [
    "https://cdn.example.com/iphone15promax/front.jpg"
  ],
  "sizes": "256GB,512GB",
  "brand": "Apple",
  "weight": 0.187,
  "dimensions": "146.6 x 70.6 x 8.25"
}
```

### Delete Product
- DELETE `/api/products/{productId}/delete`
- Headers:
  ```
  Accept: application/json
  ``` 