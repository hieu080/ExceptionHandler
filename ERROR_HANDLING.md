# 📌 Error Handling Guideline

## 1. Mục tiêu
- Chuẩn hóa **mã lỗi** (error code) trong toàn dự án.  
- Giúp **Backend – Frontend – Tester** hiểu và xử lý lỗi thống nhất.  
- Dễ dàng mở rộng khi phát sinh bài toán mới.  

---

## 2. Cấu trúc mã lỗi
Mỗi mã lỗi gồm 3 phần:  

```
<MODULE>_<ID>
```

- **MODULE**: nhóm chức năng chính (AUTH, USER, TOUR, BOOKING, PAYMENT, SYS, HTTP, …).  
- **ID**: số thứ tự tăng dần trong module, dạng 3 chữ số (`001`, `002`, …).  

Ví dụ:  
- `AUTH_001`: Token không hợp lệ  
- `USER_002`: Trạng thái người dùng không hợp lệ  
- `SYS_001`: Lỗi hệ thống  

---

## 3. Format JSON lỗi
Mọi response lỗi phải tuân theo format `ErrorResponse`:

```json
{
  "timestamp": "2025-08-17T04:43:52.750Z",
  "status": 404,
  "errorCode": "USER_001",
  "message": "Không tìm thấy user id=999",
  "traceId": "abc123",
  "details": {
    "field": "username"
  },
  "path": "/api/users/999"
}
```

---

## 4. Bảng mã lỗi chuẩn (hiện tại)

| ErrorCode       | HTTP Status | Default Message                | Ý nghĩa                                    | Hành vi FE gợi ý |
|-----------------|-------------|--------------------------------|--------------------------------------------|------------------|
| **AUTH_001**    | 401         | Token không hợp lệ             | Token hết hạn hoặc giả mạo                  | Redirect login   |
| **AUTH_002**    | 403         | Không có quyền truy cập        | User không có role phù hợp                  | Hiện popup cảnh báo |
| **AUTH_003**    | 401         | Sai username hoặc password     | Login thất bại                              | Báo lỗi form login |
| **USER_001**    | 404         | Người dùng không tồn tại       | Không tìm thấy user trong DB                | Popup báo lỗi    |
| **USER_002**    | 409         | Trạng thái người dùng không hợp lệ | User đã bị khóa / conflict trạng thái | Báo lỗi UI |
| **VALID_001**   | 400         | Dữ liệu không hợp lệ           | Input sai định dạng / thiếu field           | Highlight form lỗi |
| **SYS_001**     | 500         | Lỗi hệ thống nội bộ            | Exception không lường trước                 | Hiện “Có lỗi xảy ra” |
| **SYS_002**     | 502         | Lỗi dịch vụ bên ngoài          | Call API khác thất bại                      | Hiện thông báo retry |
| **HTTP_404**    | 404         | API không tồn tại              | Request sai endpoint                        | Hiện thông báo lỗi |
| **HTTP_405**    | 405         | Phương thức không được hỗ trợ  | Gọi sai method (POST thay vì GET)           | Hiện thông báo lỗi |
| **HTTP_400**    | 400         | Yêu cầu không hợp lệ           | Request payload không hợp lệ                | Hiện thông báo lỗi |

---

## 5. Hướng dẫn sử dụng

### 5.1. Khi throw exception (Backend)
Backend phải dùng `AppException` với `ErrorCode` tương ứng:  

```java
throw new AppException(ErrorCode.USER_NOT_FOUND, "Không tìm thấy user id=" + userId);
```

### 5.2. Khi xử lý lỗi ở Frontend
FE **không hard-code message**, mà check `errorCode`:  

```js
if (error.errorCode === "AUTH_001") {
  logoutUser();
  showMessage("Phiên đăng nhập hết hạn, vui lòng login lại.");
}
```

---

## 6. Hướng dẫn thêm mã lỗi mới

1. **Xác định module**: lỗi thuộc về AUTH, USER, TOUR, BOOKING, PAYMENT…  
2. **Kiểm tra enum** `ErrorCode` xem đã có lỗi phù hợp chưa.  
   - Nếu có → dùng lại.  
   - Nếu chưa có → tạo mới.  
3. **Đặt tên mã lỗi**:  
   - Theo format `<MODULE>_<ID>`  
   - ID tăng dần, không trùng với mã cũ.  
   - Ví dụ: thêm lỗi `TOUR_NOT_FOUND` → `TOUR_001`.  
4. **Khai báo trong enum**:  

```java
TOUR_NOT_FOUND("TOUR_001", "Tour không tồn tại", HttpStatus.NOT_FOUND),
BOOKING_INVALID_DATE("BOOKING_001", "Ngày đặt tour không hợp lệ", HttpStatus.BAD_REQUEST),
```

5. **Cập nhật guideline** (file `docs/ERROR_HANDLING.md`) để team khác nắm được.  
6. **Thông báo cho FE/Tester** để update kịch bản xử lý.  

---

## 7. Nguyên tắc chung
- **Không hard-code status/message** ở controller → luôn dùng `ErrorCode`.  
- **Không tạo mã lỗi trùng** → nếu phân vân, thảo luận trong team trước.  
- **Message** trong enum là default message, có thể override khi cần.  
- **FE** không dựa vào `message` để xử lý logic → luôn check `errorCode`.  

---
