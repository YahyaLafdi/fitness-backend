## Authentication - JWT

### Status codes
| Code  | Description                              |
|-------|------------------------------------------|
| `200` | Success                                  |
| `400` | Bad Request - request couldn't be parsed |
| `401` | Unauthorized - invalid login/token       |

### Workflow
1. Login to get access and refresh tokens
2. Use the access token to have access to resources that require it
3. Use the refresh token to get a new pair of tokens

>#### Notes :
>- Access tokens are valid for 5 minutes
>- Refresh tokens are valid for 2 weeks

### 1. Login
#### Request
| Method | URL             |
|--------|-----------------|
| `POST` | /api/auth/login |

#### Valid request body
```json
{
  "email" : "user1@pm.me",
  "password" : "user1password"
}
```

#### On success response
```json
{
  "accessToken": {
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwIiwicm9sZXMiOltdLCJpc3MiOiJTcG9ydCBOZXR3b3JrIiwiZXhwIjoxNjYwOTA1ODY3LCJpYXQiOjE2NjA5MDU1Njd9.YGX5rzB20I3akw8U4umVh3dkPJqOSnyYCHJWOIsm_Xg",
    "tokenType": "Bearer",
    "createdAt": "2022-08-19T10:39:27.634834511Z",
    "expiresIn": "2022-08-19T10:44:27.637584362Z"
  },
  "refreshToken": {
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwIiwiaXNzIjoiU3BvcnQgTmV0d29yayIsImV4cCI6MTY2MjExNTE2NywiaWF0IjoxNjYwOTA1NTY3fQ.nsEw5cp-xZMc8jdLV140o830Lhr0fFNr5BJog570btI",
    "tokenType": "Bearer",
    "createdAt": "2022-08-19T10:39:27.652177558Z",
    "expiresIn": "2022-09-02T10:39:27.652224824Z"
  }
}
```

### 2. Using access token
A valid JWT `accessToken` must be provided to each end point that requires it.

For reference here's how the Bearer token should be sent :

| Header          | Value                  |
|-----------------|------------------------|
| `Authorization` | `Bearer [accessToken]` |

#### Example POST Request (using `cURL`)
```shell
curl --location 'http://localhost:8080/api/join-us' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9UUkFJTkVSIiwiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJleHAiOjE2Nzk2MTE0ODQsImlhdCI6MTY3OTYxMTE4NCwianRpIjoiNjY2N2QwNzktNjRkMS00MzlkLWE5NjgtMWI3YjRhMTA3N2NhIn0.ZjBXTduvrhZdHU11qvfpcrE3rLnSZtpKvsl3NuUGsAM' \
--header 'Content-Type: application/json' \
--data '{
    "message": "message",
    "expertise": "message",
    "experience": "experience"
}'
```

#### On success response
```json
{
    "id": 6,
    "createdAt": "2023-03-23T21:29:07.1069532",
    "updatedAt": null,
    "expertise": "message",
    "message": "message",
    "approved": false,
    "sender": {
        "id": 1,
        "createdAt": "2023-03-23T13:09:50.538154",
        "updatedAt": "2023-03-23T21:28:25.048623",
        "firstName": "admin",
        "lastName": null,
        "email": "admin@admin.me",
        "birthDay": null,
        "profilePicture": null,
        "verificationCode": null,
        "bio": null,
        "gender": null,
        "address": null,
        "phoneNumber": null
    },
    "experience": "experience"
}
```

### 3. Exchange refreshToken for a new pair of tokens
Use the `refreshToken` to get a new `access` and `refresh` tokens without asking the user to re-authenticate. The `accessToken` is valid for 5 minutes before it needs to be refreshed again.

#### Request
| Method | URL             | Header          | Value                   |
|--------|-----------------|-----------------|-------------------------|
| `POST` | /api/auth/token | `Authorization` | `Bearer [refreshToken]` |

#### Example Request (using `cURL`)
```shell
curl -X POST --location "http://localhost:8080/api/auth/token" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwIiwiaXNzIjoiU3BvcnQgTmV0d29yayIsImV4cCI6MTY2MjExNTE2NywiaWF0IjoxNjYwOTA1NTY3fQ.nsEw5cp-xZMc8jdLV140o830Lhr0fFNr5BJog570btI"
```

#### On success response
```json
{
  "accessToken": {
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwIiwicm9sZXMiOltdLCJpc3MiOiJTcG9ydCBOZXR3b3JrIiwiZXhwIjoxNjYwOTEwOTU2LCJpYXQiOjE2NjA5MTA2NTZ9.VfjLTBlm4ONgItb3wmgEVTU1ekcldf_slmtIpz1Igbw",
    "tokenType": "Bearer",
    "createdAt": "2022-08-19T12:04:16.951619149Z",
    "expiresIn": "2022-08-19T12:09:16.951667843Z"
  },
  "refreshToken": {
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwIiwiaXNzIjoiU3BvcnQgTmV0d29yayIsImV4cCI6MTY2MjEyMDI1NiwiaWF0IjoxNjYwOTEwNjU2fQ.Bx6uSECZ1ExS20SvC6YV9qPuwrPWVJ2VByHInPhQm0I",
    "tokenType": "Bearer",
    "createdAt": "2022-08-19T12:04:16.952096477Z",
    "expiresIn": "2022-09-02T12:04:16.952124292Z"
  }
}
```
