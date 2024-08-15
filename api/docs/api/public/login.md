# Login API (v1)

## Base URL
`/api/v1/public/login`

## Endpoints

### User Login

- **URL:** `/api/v1/public/login`
- **Method:** `POST`
- **Request Body:**
  - `LoginRequest` object
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `JwtResponse` object

## DTOs

### LoginRequest

```json
{
  "username": "string",
  "password": "string"
}
```

### JwtResponse

```json
{
  "token": "string",
  "type": "string",
  "id": "number",
  "username": "string",
  "roles": ["string"]
}

```