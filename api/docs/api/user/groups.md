# Groups API (v1)

## Base URL
`/api/v1/user/groups`

## Endpoints

### Get Group by ID

- **URL:** `/api/v1/user/groups/id={id}`
- **Method:** `GET`
- **Path Variable:**
  - `id` (Long) - ID of the group
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `GroupResponse` object
  - **Status:** `404 Not Found`
  - **Body:** `{ "error": "Group not found" }`

### Create Group

- **URL:** `/api/v1/user/groups`
- **Method:** `POST`
- **Request Body:**
  - `GroupRequest` object
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `GroupResponse` object
  - **Status:** `400 Bad Request`
  - **Body:** `{ "error": "Invalid Input" }`

### Update Group

- **URL:** `/api/v1/user/groups/id={id}`
- **Method:** `PUT`
- **Path Variable:**
  - `id` (Long) - ID of the group
- **Request Body:**
  - `GroupRequest` object
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `GroupResponse` object
  - **Status:** `404 Not Found`
  - **Body:** `{ "error": "Category not found" }`

### Delete Group

- **URL:** `/api/v1/user/groups/id={id}`
- **Method:** `DELETE`
- **Path Variable:**
  - `id` (Long) - ID of the group
- **Response:**
  - **Status:** `200 OK`
  - **Body:** `{ "message": "Group deleted successfully" }`
  - **Status:** `404 Not Found`
  - **Body:** `{ "error": "Group not found" }`

## DTOs

### GroupRequest

```json
{
  "name": "string",
  "description": "string"
}
```

### GroupResponse

```json
{
  "id": "number",
  "name": "string",
  "description": "string"
}
```