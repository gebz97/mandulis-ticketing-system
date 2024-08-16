# User API (v1)

## Base URL
`/api/v1/user/users`

## Endpoints

### Get All Users

- **URL:** `/api/v1/user/users`
- **Method:** `GET`
- **Response:**
  - **Status:** `200 OK`
  - **Body:** List of `UserResponse` objects

### Get All Users Paginated

- **URL:** `/api/v1/user/users/paginate?page={page}&size={size}`
- **Method:** `GET`
- **Response:**
  - **Status:** `200 OK`
  - **Body:** Page of `UserResponse` object


### Create User

- **URL:** `/api/v1/user/users`
- **Method:** `POST`
- **Request Body:**
  - `UserRequest` object
- **Response:**
  - **Status:** `201 Created`
  - **Body:** `UserResponse` object

### Get User by ID

- **URL:** `/api/v1/user/users/id={id}`
- **Method:** `GET`
- **Path Variable:**
  - `id` (Long) - ID of the user
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `UserResponse` object

### Update User by ID

- **URL:** `/api/v1/user/users/id={id}`
- **Method:** `PUT`
- **Path Variable:**
  - `id` (Long) - ID of the user
- **Request Body:**
  - `UserRequest` object
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `UserResponse` object

### Delete User by ID

- **URL:** `/api/v1/user/users/id={id}`
- **Method:** `DELETE`
- **Path Variable:**
  - `id` (Long) - ID of the user
- **Response:**
  - **Status:** `200 OK`

### Get Full User Info by ID

- **URL:** `/api/v1/user/users/fullinfo/id={id}`
- **Method:** `GET`
- **Path Variable:**
  - `id` (Long) - ID of the user
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `UserResponse` object
  - **Status:** `404 Not Found`

### Get Full User Info by Name

- **URL:** `/api/v1/user/users/fullinfo/name={name}`
- **Method:** `GET`
- **Path Variable:**
  - `name` (String) - Username of the user
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `UserResponse` object
  - **Status:** `404 Not Found`

### Filter Users

- **URL:** `/api/v1/user/users/filter`
- **Method:** `GET`
- **Query Parameters:**
  - `username` (String) - Optional
  - `firstName` (String) - Optional
  - `lastName` (String) - Optional
  - `email` (String) - Optional
- **Response:**
  - **Status:** `200 OK`
  - **Body:** List of `UserResponse` objects

### Filter Users Paginated

- **URL:** `/api/v1/user/users/filter/paginate?page={page}&size={size}`
- **Method:** `GET`
- **Query Parameters:**
  - `username` (String) - Optional
  - `firstName` (String) - Optional
  - `lastName` (String) - Optional
  - `email` (String) - Optional
- **Response:**
  - **Status:** `200 OK`
  - **Body:** Page of `UserResponse` object

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