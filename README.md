# WebClipBoard

- 간단한 텍스트/파일을 저장하는 API 서버

## 저장 형식

- 파일명에 파일 ID, 타입, 만든이, 상태를 저장
- 아래 json을 url safe base64 인코딩해 파일명으로 사용

```json
{
  "id": "8f7a67aa-834f-11ec-a8a3-0242ac120002",
  "type": "text",
  "creator": "admin",
  "hash": "5eb63bbbe01eeed093cb22bb8f5acdc3"
}
```

- id: 파일 식별용 uuid
- type: text | file
- creator: 만든이
- hash: 파일 내용 md5 해시
  - 업로드 완료 후 해시를 계산하므로 업로드 실패시 값이 null일 것

## API

### 파일 목록

#### Request

- GET /api/files/{creator}

| Parameter | Description |
| --------- | ----------- |
| creator   | 만든이      |

#### Response

| Status Code | Description |
| ----------- | ----------- |
| 200 OK      | 성공        |

**Example**

```json
[
  {
    "id": "8f7a67aa-834f-11ec-a8a3-0242ac120002",
    "type": "text",
    "creator": "admin",
    "hash": "5eb63bbbe01eeed093cb22bb8f5acdc3"
  },
  {
    "id": "3f7a67aa-834f-11ec-a8a3-0242ac120002",
    "type": "file",
    "creator": "admin",
    "hash": "1eb63bbbe01eeed093cb22bb8f5acdc3"
  }
]
```

### 파일 삭제

#### Request

- DELETE /api/file/{id}/{creator}

| Parameter | Description |
| --------- | ----------- |
| id        | 파일 id     |
| creator   | 만든이      |

#### Response

| Status Code      | Description               |
| ---------------- | ------------------------- |
| 200 OK           | 성공                      |
| 401 Unauthorized | 파일 만든이가 아님        |
| 404 Not Found    | id에 해당하는 파일이 없음 |

### 텍스트 내용

#### Request

- GET /api/text/{id}/{creator}

| Parameter | Description |
| --------- | ----------- |
| id        | 파일 id     |
| creator   | 만든이      |

#### Response

| Status Code      | Description               |
| ---------------- | ------------------------- |
| 200 OK           | 성공                      |
| 401 Unauthorized | 파일 만든이가 아님        |
| 404 Not Found    | id에 해당하는 파일이 없음 |

**Example**

```json
"hello world"
```

### 텍스트 저장

#### Request

- POST /api/text/{creator}

```json
"this is content"
```

| Parameter    | Description |
| ------------ | ----------- |
| creator      | 만든이      |
| Request Body | 내용        |

#### Response

| Status Code      | Description        |
| ---------------- | ------------------ |
| 200 OK           | 성공               |
| 401 Unauthorized | 파일 만든이가 아님 |

성공시 id 반환

**Example**

```json
"1eb63bbbe01eeed093cb22bb8f5acdc3"
```

### 파일 다운로드

#### Request

- GET /api/file/{id}/{creator}

| Parameter | Description |
| --------- | ----------- |
| id        | 파일 id     |
| creator   | 만든이      |

#### Response

| Status Code      | Description               |
| ---------------- | ------------------------- |
| 200 OK           | 성공                      |
| 401 Unauthorized | 파일 만든이가 아님        |
| 404 Not Found    | id에 해당하는 파일이 없음 |

### 파일 업로드

#### Request

- POST /api/file/{creator}

| Parameter    | Description   |
| ------------ | ------------- |
| creator      | 만든이        |
| Request Body | MultipartFile |

#### Response

| Status Code | Description |
| ----------- | ----------- |
| 200 OK      | 성공        |

성공시 id 반환

**Example**

```json
"1eb63bbbe01eeed093cb22bb8f5acdc3"
```
