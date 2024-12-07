# Binary-Sort-Microservice

API to sort numbers based on binary representation.

## Binary Representation Sorting Microservice

This is a **Java** microservice that takes a list of integers and returns the list sorted based on the number of ones in the binary representation of each number.

### Description

- The microservice accepts a list of integers as input.
- The numbers are sorted by the number of ones (`1`) in their binary representation.
- If two numbers have the same number of ones, they are sorted by their numeric value.

### Example

**Input:**

```json
[7, 2, 1, 9, 5, 3, 8, 25, 42]
```

**Output:**

```json
[1, 2, 8, 3, 5, 9, 7, 25, 42]
```

### Binary Representation of Numbers

| Number | Binary  | Ones |
|--------|---------|------|
| 7      | `111`   | 3    |
| 2      | `10`    | 1    |
| 1      | `1`     | 1    |
| 9      | `1001`  | 2    |
| 5      | `101`   | 2    |
| 3      | `11`    | 2    |
| 8      | `1000`  | 1    |
| 25     | `11001` | 3    |
| 42     | `101010`| 3    |

### Requirements

- **Java 17**

### Swagger UI Documentation

Access the API documentation at: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)



