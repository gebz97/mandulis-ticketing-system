# Category API (v1)

## Base URL
`/api/v1/user/category`

## Endpoints

### Get All Categories

- **URL:** `/api/v1/user/category`
- **Method:** `GET`
- **Response:**
  - **Status:** `200 OK`
  - **Body:** List of `CategoryResponse` objects

### Get Category by ID

- **URL:** `/api/v1/user/category/id={id}`
- **Method:** `GET`
- **Path Variable:**
  - `id` (Long) - ID of the category
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `CategoryResponse` object
  - **Status:** `404 Not Found`
  - **Body:** `{ "error": "Category not found" }`

### Create Category

- **URL:** `/api/v1/user/category`
- **Method:** `POST`
- **Request Body:**
  - `CategoryRequest` object
- **Response:**
  - **Status:** `201 Created`
  - **Body:** `CategoryResponse` object
  - **Status:** `400 Bad Request`
  - **Body:** `{ "error": "Error creating category" }`

### Update Category

- **URL:** `/api/v1/user/category/id={id}`
- **Method:** `PUT`
- **Path Variable:**
  - `id` (Long) - ID of the category
- **Request Body:**
  - `CategoryRequest` object
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `CategoryResponse` object
  - **Status:** `404 Not Found`
  - **Body:** `{ "error": "Category not found" }`

### Delete Category

- **URL:** `/api/v1/user/category/id={id}`
- **Method:** `DELETE`
- **Path Variable:**
  - `id` (Long) - ID of the category
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `{ "message": "Category deleted successfully" }`
  - **Status:** `404 Not Found`
  - **Body:** `{ "error": "Category not found" }`

## DTOs

### CategoryRequest

```json
{
  "name": "string",
  "description": "string"
}
```

### CategoryResponse

```json
{
  "id": "number",
  "name": "string",
  "description": "string"
}
```
