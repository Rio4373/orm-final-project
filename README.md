## Учебная платформа (ORM Final Project)

Учебное веб-приложение на базе **Spring Boot + Spring Data JPA + Hibernate + PostgreSQL**.  
Система поддерживает курсы, модули, уроки, задания, тесты, записи студентов на курсы, отправку и оценку работ.

### Стек

- **Java 17+**
- **Spring Boot 3** (web, data-jpa, validation)
- **Hibernate (JPA)**
- **PostgreSQL**
- **Maven**
- **Testcontainers** (интеграционные тесты)

### Запуск приложения

1. Установить Java 17+ и Maven.
2. Запустить PostgreSQL локально или через Docker:

```bash
docker run --name orm-pg -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=orm_final_project_dev -p 5432:5432 -d postgres:16-alpine
```

3. В корне проекта выполнить:

```bash
mvn spring-boot:run
```

Приложение стартует на `http://localhost:8080` с профилем `dev`.  
При старте выполняется наполнение БД демо-данными (`DataInitializer`).

### Основные REST-эндпойнты

- **Пользователи**
  - `POST /api/users?name=...&email=...&role=STUDENT|TEACHER|ADMIN`
  - `GET /api/users` — список пользователей
  - `GET /api/users/{id}` — пользователь по id

- **Курсы**
  - `POST /api/courses` — создать курс (JSON тела соответствует сущности `Course`)
  - `GET /api/courses` — список курсов
  - `GET /api/courses/{id}` — курс по id
  - `PUT /api/courses/{id}` — обновить курс
  - `DELETE /api/courses/{id}` — удалить курс

- **Запись на курс**
  - `POST /api/courses/{courseId}/enrollments?studentId=...` — записать студента
  - `DELETE /api/courses/{courseId}/enrollments?studentId=...` — отписать
  - `GET /api/courses/{courseId}/enrollments` — все записи на курс

- **Задания и решения**
  - `POST /api/lessons/{lessonId}/assignments` — создать задание (JSON `Assignment`)
  - `POST /api/assignments/{assignmentId}/submit?studentId=...&content=...` — отправить решение
  - `POST /api/submissions/{submissionId}/grade?score=...&feedback=...` — оценить
  - `GET /api/assignments/{assignmentId}/submissions` — все решения по заданию
  - `GET /api/users/{studentId}/submissions` — все решения студента

- **Тесты (Quiz)**
  - `POST /api/quizzes/{quizId}/take`

Пример тела запроса:

```json
{
  "studentId": 2,
  "answers": {
    "1": 3
  }
}
```

Ответ — сущность `QuizSubmission` с полем `score` (0–100).

### Тесты

Интеграционные тесты находятся в `src/test/java/com/example/ormfinalproject/OrmAppIntegrationTest.java` и используют **Testcontainers** для PostgreSQL.

Запуск:

```bash
mvn test
```

Тесты проверяют:

- создание и загрузку данных;
- запись/отписку на курс;
- жизненный цикл заданий и решений;
- прохождение теста и сохранение результата;
- генерацию `LazyInitializationException` при обращении к ленивой коллекции вне сессии.

### Docker

Для запуска через Docker:

1. Собрать jar:

```bash
mvn clean package -DskipTests
```

2. Собрать образ и поднять стэк:

```bash
docker-compose up --build
```

Будет запущен PostgreSQL и приложение на порту `8080`.


