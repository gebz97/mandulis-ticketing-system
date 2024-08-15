# Registration API (v1)

## Base URL
`/api/v1/public/register`

## Endpoints

### User Registration

- **URL:** `/api/v1/public/register`
- **Method:** `POST`
- **Request Body:**
  - `RegistrationRequest` object
- **Response:**
  - **Status:** `201 Created`
  - **Body:** `UserResponse` object

## DTOs

### RegistrationRequest

```json
{
  "username": "string",
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "password": "string"
}
```

### UserResponse

```json
{
  "id": "number",
  "username": "string",
  "firstName": "string",
  "lastName": "string",
  "groups": [
    {
      "groupId": "number",
      "groupName": "string"
    }
  ],
  "role": "string",
  "email": "string"
}
```