<h1 align="center">Для запуска сервера</h1>
<h3>PostgreSql 15</h3>
<h4>1. Создать роль входа</h4>
<a>Username: root
Password: 123
Дать все права</a>
<h4>2. Создать БД</h4>
<a>Перейти в pgAdmin 4</a>
<a>Создать базу данных Servers->PostgreSQL 15->Базы Данных</a>
<a>Имя notNullServer</a>
<a>Владелец root</a>
<h4>3. Сверить файл application.properties</h4>
<a>Путь файла: src/main/resources</a>
<a>Строка spring.datasource.url = </a>
<h4>4. Подключиться к базе данных в Java project (если возможно)</h4>
<a>Database->New->Data Source->PostgreSql</a>
<a>Подключиться к БД по данным пункта 1 и 2</a>
<h3>Java приложение</h3>
<h4>1. Обновить maven</h4>
<a>Подгрузить зависимости</a>
<a>В Maven запустить maven->NotNullServer->Plugins->protobuf->protobuf:compile</a>
<a>После создания протокола ещё раз обновить Maven</a>