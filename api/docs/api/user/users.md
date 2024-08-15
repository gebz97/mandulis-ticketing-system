# User API (v1)

## Base URL
`/api/v1/public/users`

## Endpoints

### Get All Users

- **URL:** `/api/v1/public/users`
- **Method:** `GET`
- **Response:**
  - **Status:** `200 OK`
  - **Body:** List of `UserResponse` objects

### Create User

- **URL:** `/api/v1/public/users`
- **Method:** `POST`
- **Request Body:**
  - `UserRequest` object
- **Response:**
  - **Status:** `201 Created`
  - **Body:** `UserResponse` object

### Get User by ID

- **URL:** `/api/v1/public/users/id={id}`
- **Method:** `GET`
- **Path Variable:**
  - `id` (Long) - ID of the user
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `UserResponse` object

### Update User by ID

- **URL:** `/api/v1/public/users/id={id}`
- **Method:** `PUT`
- **Path Variable:**
  - `id` (Long) - ID of the user
- **Request Body:**
  - `UserRequest` object
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `UserResponse` object

### Delete User by ID

- **URL:** `/api/v1/public/users/id={id}`
- **Method:** `DELETE`
- **Path Variable:**
  - `id` (Long) - ID of the user
- **Response:**
  - **Status:** `200 OK`

### Get Full User Info by ID

- **URL:** `/api/v1/public/users/fullinfo/id={id}`
- **Method:** `GET`
- **Path Variable:**
  - `id` (Long) - ID of the user
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `UserResponse` object
  - **Status:** `404 Not Found`

### Get Full User Info by Name

- **URL:** `/api/v1/public/users/fullinfo/name={name}`
- **Method:** `GET`
- **Path Variable:**
  - `name` (String) - Username of the user
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `UserResponse` object
  - **Status:** `404 Not Found`

### Search Users

- **URL:** `/api/v1/public/users/search`
- **Method:** `GET`
- **Query Parameters:**
  - `username` (String) - Optional
  - `firstName` (String) - Optional
  - `lastName` (String) - Optional
  - `email` (String) - Optional
- **Response:**
  - **Status:** `200 OK`
  - **Body:** List of `UserResponse` objects

## DTOs

### UserRequest

```json
{
  "username": "string",
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "password": "string",
  "role": "string"
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