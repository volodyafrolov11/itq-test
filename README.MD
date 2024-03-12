## Использованный стек
- JDK 17
- Spring (v. 3.2.3)
- Maven
- Flyway
- JPA + Hibernate
- СУБД PostgreSQL
- Open API (Swagger)
- Тесты с JUnit, Mockito, MockMVC

## Сервисы
1. [generate-order-number-service](/generate-order-number-service/) - сервис для генерации номера заказа. Поднимается локально на 5001 порту.

2. [order-service](/order-service/) - сервис для работы с заказами. Подниматеся на 5002 порту, подвязан на БД. Общается по REST с generate-order-number-service.

## База данных
Для выполнения задания использовалась СУБД Postgres. База поднималась локально с дефолтными [настройками](/order-service/src/main//resources/application.properties). Название базы "itq-test".

## Документация
Документация описана с помощью swagger из openapi. Swagger UI поднимался так же локально для каждого сервиса. Соответственно url для API генерации номера заказа: http://localhost:5001/swagger-ui/index.html, а для API по работе с заказом: http://localhost:5002/swagger-ui/index.html.