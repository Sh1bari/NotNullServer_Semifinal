<h1 align="center">Для запуска сервера</h1>
<h3>PostgreSql 15</h3>
<h4>1. Создать роль входа</h4>
Username: root<br/>
Password: 123<br/>
Дать все права
<h4>2. Создать БД</h4>
Перейти в pgAdmin 4<br/>
Создать базу данных Servers->PostgreSQL 15->Базы Данных<br/>
Имя notNullServer
<a>Владелец root</a>
<h4>3. Сверить файл application.properties</h4>
Путь файла: src/main/resources<br/>
Строка spring.datasource.url =
<h4>4. Подключиться к базе данных в Java project (если возможно)</h4>
Database->New->Data Source->PostgreSql<br/>
Подключиться к БД по данным пункта 1 и 2
<h3>Java приложение</h3>
<h4>1. Обновить maven</h4>
Подгрузить зависимости<br/>
В Maven запустить maven->NotNullServer->Plugins->protobuf->protobuf:compile<br/>
После создания протокола ещё раз обновить Maven