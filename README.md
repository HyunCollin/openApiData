# openApiData

스프링부트 실행시 Active profile loc  


# Properties API KEY 
open.data.api.key= ApiKey

open.data.api.key.encode = ApiKey

# Swagger-ui 
http://localhost:8081/api/swagger-ui/index.html

# Docker Mysql 설치 및 명령어 

docker pull mysql

docker images

docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password --name mysql_test mysql

docker ps -a

docker exec -i -t mysql_test bash

mysql -u root -p

CREATE USER 'developer'@'%' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON *.* TO 'developer'@'%';

quit

mysql -u developer -p

show databases;

CREATE DATABASE open_api;

use open_api;

show tables;


# DB table 생성

-- auto-generated definition
create table photo_gallery
(
gal_content_id           bigint unsigned not null
primary key,
gal_content_type_id      bigint unsigned not null,
gal_title                varchar(200)    not null,
gal_web_image_url        varchar(200)    not null,
gal_createdtime          bigint unsigned not null,
gal_modifiedtime         bigint unsigned not null,
gal_photography_month    bigint unsigned not null,
gal_photography_location varchar(200)    not null,
gal_photographer         varchar(200)    not null,
gal_search_keyword       varchar(200)    not null
);

