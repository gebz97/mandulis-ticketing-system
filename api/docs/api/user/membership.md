# Membership API (v1)

## Base URL
`/api/v1/user/membership`

## Endpoints

### Add Single Membership

- **URL:** `/api/v1/user/membership`
- **Method:** `POST`
- **Request Body:**
  - `MembershipRequest` object
- **Response:**
  - **Status:** `201 Created`
  - **Body:** `MembershipResponse` object
  - **Status:** `400 Bad Request`
  - **Body:** `{ "error": "Either the user/group doesn't exist, or already a member" }`

### Add Multiple Memberships

- **URL:** `/api/v1/user/membership/bulk`
- **Method:** `POST`
- **Request Body:**
  - List of `MembershipRequest` objects
- **Response:**
  - **Status:** `200 OK`
  - **Body:** List of `MembershipResponse` objects
  - **Status:** `404 Not Found`
  - **Body:** `{ "error": "One or more invalid user or group" }`

### Delete Single Membership

- **URL:** `/api/v1/user/membership`
- **Method:** `DELETE`
- **Request Body:**
  - `MembershipRequest` object
- **Response:**
  - **Status:** `204 No Content`
  - **Status:** `404 Not Found`
  - **Body:** `{ "error": "One or more invalid user or group" }`

### Delete Multiple Memberships

- **URL:** `/api/v1/user/membership/bulk`
- **Method:** `DELETE`
- **Request Body:**
  - List of `MembershipRequest` objects
- **Response:**
  - **Status:** `200 OK`
  - **Body:** List of `MembershipRequest` objects
  - **Status:** `400 Bad Request`
  - **Body:** `{ "error": "One or more invalid memberships" }`

## DTOs

### MembershipRequest

```json
{
  "userId": "number",
  "groupId": "number"
}
```

### MembershipResponse
```json
{
  "userId": "number",
  "groupId": "number"
}
```