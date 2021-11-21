# Weather Aggregator Api

API агрегатора погоды с возможностью получения погоды с нескольких сторонних сервисов

![WAPI Example](./assets/wapi.gif)

## Описание

"Weather Aggregator Api" - API агрегатора погоды с различных источников, таких как OpenWeather и Яндекс.Погода.
Позволяет пользователю выбрать несколько сторонних сервисов погоды и получить погоду со всех выбранных источников
в рамках одного JSON ответа.

Дополнительно имеется возможность получить основную информацию по погоде через сайт.

Проект построен на основе микросервисной архитектуры и состоит из следующих модулей, взаимодействующих по REST API:
1. Dispatcher - модуль для маршрутизации запроса клиента по различным модулям приложения.
Дополнительно валидирует токен;
2. Auth API - модуль для сохранения нового пользователя в базе данных и выдачи токена;
3. Data API - модуль обработки ответа от загрузчика (Loader API) и формирования единого ответа клиенту
по различным погодным сервисам в полном (без обработки ответа от Loader API) или кратком формате;
4. Loader API - загрузчик погоды с различных сервисов и отправки ответа без обработки модулю Data API.
5. Front - модуль с фронтом, через который возможно получить краткий информативный ответ по погоде.

### Особенности архитектуры

![Аrchitecture](./assets/architecture.png)

В случае положительного результата, возвращается ответ со следующим шаблоном и статусом 200 (OK):
   ```
    {
        "status": "report",
        "report": {
            ...
        }
    }
   ```
В случае ошибки, возвращается ответ со статусом 409 (CONFLICT) с описанием ошибки. Пример ответа:
   ```
    {
        "status": "error",
        "error": {
            "errorCode": "USER_NOT_FOUND",
            "message": "User with login test not found"
        }
    }
   ```
В случае, если на модуль Dispatcher от другого модуля приходит ответ с кодом ошибки 500 (INTERNAL ERROR),
то клиенту возвращается ответ со статусом 409 (CONFLICT) в следующем формате:
   ```
    {
        "status": "error",
        "error": {
            "errorCode": "INTERNAL_ERROR",
            "message": "An internal error occurred. Try later"
        }
    }
   ```

---

## Примеры запросов и ответов

1. Регистрация пользователя:

   Пример запроса
   ```
    {
        "login": "test",
        "email": "test@gmail.com",
        "password": "qwerty"
    }
   ```
   Пример ответа
   ```
    {
        "status": "report",
        "report": {
            "key": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjM2MDM1MjMzLCJleHAiOjE2MzYwMzUyOTN9.ROlNhAe_Iyn-O9F39Jjvzvq7uXF_Qi-tRxFFvrS3VTE"
        }
    }
   ```
2. Получение погоды в кратком формате:

   Пример запроса
   ```
    {
        "city": "moscow",
        "services": [
            "openweather",
            "yandexweather"
        ],
        "needDetailed": false,
        "key": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjM2MDM1NzcyLCJleHAiOjE2MzYwMzU4MzJ9.-CSupaSkP2Bv_rJ5evTV6PrN0fuqAhOwTeGW7VSLuXY"
    }
   ```
   Пример ответа
   ```
    {
        "status": "report",
        "report": {
            "openweather": {
                ...
            },
            "yandexweather": {
                ...
            }
        }
    }
   ```
   
## Установка

Для запуска проекта выполните следующие действия:
1. Клонируйте проект:
   ```
   $ git clone https://github.com/phenomiks/weather_aggregator_api.git
   ```
2. Перенесите файл "private.properties" (./{module_name}/config) в "./{module_name}/src/main/resources" 
для каждого модуля.

    Auth API:
   ```
   jwt.secret={secret}
   jwt.expiredTime={time}
   
   # secret - секретное слово для создания JWT токена;
   # time - время в минутах (целое число).
   ```
   
    Dispatcher
   ```
   jwt.secret={secret}
   
   # secret - секретное слово для расшифроки токена (должно совпадать с ключем из Auth API).
   ```
   
    Loader  API:
   ```
    api.key.open.weather={key}
    api.key.yandex.weather={key}
   
   # key - ключи от сервисов OpenWeather и Яндекс.Погода.
   ```
   
    Front:
   ```
   jwt.key={key}
   
   # key - токен, выпущенный модулем Auth API.
   ```
3. Соберите каждый модуль проекта:
   ```
   mvn clean install
   ```
4. Запустите каждый модуль проекта:
   ```
   java -jar {jar_file}.jar
   ```

<br>

---
#### Используемый стек технологий:

---

Java 11, Spring Boot, Spring Web, Spring Data JPA, Spring Validation, H2 Database, Flyway, 
Swagger, JUnit, Mockito, Thymeleaf, Maven