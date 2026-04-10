# CRM System - Seller Management Service

## Описание проекта

CRM система для управления продавцами и транзакциями. Сервис предоставляет функционал для CRUD операций с продавцами, управления транзакциями, а также аналитики эффективности продавцов.

### Основной функционал

#### Управление продавцами (Seller Service)
- Создание нового продавца
- Получение информации о продавце по ID
- Обновление данных продавца
- Мягкое удаление продавца (деактивация)
- Получение списка активных продавцов с пагинацией

#### Управление транзакциями (Transaction Service)
- Создание транзакции для продавца
- Получение деталей транзакции
- Получение списка всех транзакций с пагинацией
- Получение транзакций конкретного продавца

#### Аналитика (Analytics Service)
- Получение самого продуктивного продавца за период
- Получение списка непродуктивных продавцов (с доходом ниже порогового значения)

## Используемые технологии

### Стек
- Java 21
- PostgreSQL 15
- Docker

### Зависимости проекта

```gradle
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.flywaydb:flyway-core'
	implementation 'org.flywaydb:flyway-database-postgresql'
	runtimeOnly 'org.postgresql:postgresql'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'

    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    implementation 'org.projectlombok:lombok'
    // Source: https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5'
}
```


## Сборка и запуск проекта

Для запуска проекта необходимо:
1. Склонировать репозиторий:
```bash
git clone https://github.com/AsmrS4/shift-lab-test.git
cd shift-lab-test/crm
```
2. Ввести команду для сборки и запуска контейнеров:
```bash
docker compose up -d
```
После сборки и запуска контейнеров приложение будет запущено на порту 9000.

Для тестирования API после локального запуска:
```
 http://localhost:9000/swagger-ui/index.html#/
```

## Документация
Документация к проекту представлена в виде yaml спецификаций к каждому из контроллеров. Файлы расположены в директории
```
shift-lab-test/crm/documentation
```
