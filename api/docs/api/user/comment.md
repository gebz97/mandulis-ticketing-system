# Comment API Documentation

## Endpoints

### Create Comment by Username
**URL:** `/comments/by-username`  
**Method:** `POST`  
**Description:** Creates a new comment by username.

**Request Parameters:**
- `username` (String): The username of the user creating the comment.

**Request Body:**
```json
{
  "content": "string",
  "ticketId": "number"
}
```

**Response:**
```json
{
  "id": "number",
  "ticketId": "number",
  "userId": "number",
  "content": "string"
}
```

### Create Comment by User ID
**URL:** `/comments/by-userid`  
**Method:** `POST`  
**Description:** Creates a new comment by user ID.

**Request Parameters:**
- `userId` (Long): The ID of the user creating the comment.

**Request Body:**
```json
{
  "content": "string",
  "ticketId": "number"
}
```

**Response:**
```json
{
  "id": "number",
  "ticketId": "number",
  "userId": "number",
  "content": "string"
}
```

### Get All Comments by User
**URL:** `/comments/by-user/{userId}`  
**Method:** `GET`  
**Description:** Retrieves all comments by a particular user.

**Path Parameters:**
- `userId` (Long): The ID of the user.

**Response:**
```json
[
  {
    "id": "number",
    "ticketId": "number",
    "userId": "number",
    "content": "string"
  }
]
```

### Get All Comments by Ticket
**URL:** `/comments/by-ticket/{ticketId}`  
**Method:** `GET`  
**Description:** Retrieves all comments on a particular ticket.

**Path Parameters:**
- `ticketId` (Long): The ID of the ticket.

**Response:**
```json
[
  {
    "id": "number",
    "ticketId": "number",
    "userId": "number",
    "content": "string"
  }
]
```

### Update Comment
**URL:** `/comments/{commentId}`  
**Method:** `PUT`  
**Description:** Updates a particular comment by comment ID.

**Path Parameters:**
- `commentId` (Long): The ID of the comment to update.

**Request Body:**
```json
{
  "content": "string",
  "ticketId": "number"
}
```

**Response:**
```json
{
  "id": "number",
  "ticketId": "number",
  "userId": "number",
  "content": "string"
}
```

### Delete Comment
**URL:** `/comments/{commentId}`  
**Method:** `DELETE`  
**Description:** Deletes a particular comment by comment ID.

**Path Parameters:**
- `commentId` (Long): The ID of the comment to delete.

**Response:** `204 No Content`

### Delete All Comments by User
**URL:** `/comments/by-user/{userId}`  
**Method:** `DELETE`  
**Description:** Deletes all comments for a particular user.

**Path Parameters:**
- `userId` (Long): The ID of the user.

**Response:** `204 No Content`

### Delete All Comments by User on Ticket
**URL:** `/comments/by-user/{userId}/by-ticket/{ticketId}`  
**Method:** `DELETE`  
**Description:** Deletes all comments for a particular user on a particular ticket.

**Path Parameters:**
- `userId` (Long): The ID of the user.
- `ticketId` (Long): The ID of the ticket.

**Response:** `204 No Content`

### Delete All Comments by Ticket
**URL:** `/comments/by-ticket/{ticketId}`  
**Method:** `DELETE`  
**Description:** Deletes all comments on a particular ticket.

**Path Parameters:**
- `ticketId` (Long): The ID of the ticket.

**Response:** `204 No Content`

---