# Для запуска MySQL используется docker
``` bash
docker run --name MySQL -e MYSQL_ROOT_PASSWORD=Strong!Password -e MYSQL_DATABASE=lab6 -p 3306:3306 -d mysql:8.0
```
# Интерактивный режим MySQL
``` bash
docker exec -it MySQL mysql -uroot -p
```
