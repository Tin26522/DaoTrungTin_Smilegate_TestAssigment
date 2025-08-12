# Ứng dụng Quản lý Game (Spring Boot)

## Tính năng
- Trang Danh sách Game: tìm kiếm (ID/tên), lọc theo thể loại (category), phân trang, xóa nhiều, nút đăng ký game.
- Trang Đăng ký/Sửa Game: tạo/sửa game, tên đa ngôn ngữ (EN/KO/JA), đặt ngôn ngữ mặc định, validate phía client + server.
- REST API: CRUD, phân trang, lọc, xóa hàng loạt.

## Công nghệ
- Java 17, Spring Boot 3 (Web, Data JPA, Validation)
- Thymeleaf (UI render trên server)
- MySQL 8 (có thể chạy bằng Docker)


## API

- GET `/api/games?category=ADVENTURE&q=zelda&page=0&size=10` — danh sách + phân trang + lọc
- GET `/api/games/{id}` — chi tiết game
- POST `/api/games` — tạo (trả 201 + Location)
- PUT `/api/games/{id}` — cập nhật
- DELETE `/api/games/{id}` — xóa (204)
- DELETE `/api/games?ids=ID1&ids=ID2` — xóa nhiều (204)

Payload mẫu:
```json
{
  "id": "UNCHARTED4",
  "category": "ADVENTURE",
  "defaultLanguage": "EN",
  "names": [
    { "language": "EN", "value": "Uncharted 4" },
    { "language": "KO", "value": "언차티드 4" }
  ]
}
```

## Trang giao diện
- `GET /games` — Trang danh sách
- `GET /games/register` — Trang tạo mới
- `GET /games/{id}/edit` — Trang chỉnh sửa

## Quy tắc kiểm tra dữ liệu (Validation)
- **id**: bắt buộc, chữ in hoa/số/`_`/`-`, tối đa 64 ký tự (`^[A-Z0-9_\-]+$`).
- **category**: bắt buộc (enum).
- **defaultLanguage**: bắt buộc; phải có trong danh sách `names`.
- **names**: tối thiểu 1 mục; không trùng `language`; mỗi `value` không rỗng, tối đa 100 ký tự.
- Lỗi từ server trả JSON có chi tiết field; UI hiển thị thông báo ngay trên nút Save.

