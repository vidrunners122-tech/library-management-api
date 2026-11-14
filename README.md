# Library Management API

REST API لإدارة مكتبة (كتب، أعضاء، استعارة وإرجاع) مبني بـ Spring Boot.

## التقنيات
- Java 17+
- Spring Boot (Web, Data JPA, Validation)
- PostgreSQL
- Hibernate
- Maven

## الميزات
- CRUD كامل للكتب
- البحث عن كتاب بالعنوان
- تسجيل الأعضاء
- استعارة وإرجاع الكتب مع @Transactional
- معالجة مركزية للأخطاء (404 و409 و400) بـ @RestControllerAdvice
- Validation على المدخلات

## هيكل المشروع
controller ← service ← repository ← PostgreSQL

## التشغيل
1. أنشئ قاعدة بيانات اسمها library في PostgreSQL
2. عدّل application.properties بكلمة السر
3. شغّل LibraryApplication

## أهم الـ Endpoints
| Method | Path | الوصف |
|---|---|---|
| POST | /api/books | إضافة كتاب |
| GET | /api/books | عرض كل الكتب |
| GET | /api/books/{id} | عرض كتاب |
| PUT | /api/books/{id} | تعديل كتاب |
| DELETE | /api/books/{id} | حذف كتاب |
| GET | /api/books/search?title= | بحث بالعنوان |
| POST | /api/members | إضافة عضو |
| POST | /api/loans/borrow?bookId=&memberId= | استعارة |
| POST | /api/loans/return?bookId= | إرجاع |
## الواجهة
بعد تشغيل المشروع افتح `http://localhost:8080` (واجهة غامقة: عرض الكتب، بحث، إضافة، استعارة، إرجاع، حذف).

## الاختبارات
3 اختبارات وحدة على `LoanService` بـ Mockito (استعارة ناجحة، كتاب مستعار، إرجاع بدون استعارة مفتوحة).


## ملاحظات
- Pagination: `GET /api/books/page?page=0&size=5`
- الأعضاء: `GET/POST /api/members`
- سجل الاستعارات: `GET /api/loans`

## الخطوات القادمة
- Spring Security (أدوار ADMIN / USER)
- DTOs بدل إرجاع الـ Entity مباشرة
