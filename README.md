# 📘 Spring Kafka Wiki

## 📖 Описание

**Spring Kafka Wiki** — это учебный проект, демонстрирующий интеграцию между:
- 🔄 **Apache Kafka** — системой обмена сообщениями;
- ☕ **Spring Boot** — фреймворком для создания микросервисов;
- 🐦 **Firebird** — СУБД для хранения поступающих данных.

Проект имитирует потоковое получение событий правки из Wiki, отправку их в Kafka, а затем приём и сохранение в базу данных.

![Java](https://img.shields.io/badge/Java_21+-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Firebird](https://img.shields.io/badge/Firebird-FF6C37?style=for-the-badge&logo=databricks&logoColor=white)

## 🛠️ Основные функции

- 📡 Получение потоковых данных из внешнего API
- 📤 Отправка данных в Kafka топик
- 📥 Приём сообщений из Kafka
- 💾 Сохранение полученных данных в базу данных Firebird
- 📊 Простая настройка и конфигурация через `application.properties`

## 🧾 Требования

Для успешного запуска проекта убедитесь, что у вас установлено следующее:

- ☕ **Java**: версия **21** или выше.  
- 🐦 **Firebird**: база данных для хранения данных.  
- 🐳 **Docker**: для контейнеризации Kafka.

## ⚙️ Настройка и конфигурация

1. Установите **Java 21+**.  
    Убедитесь, что Java установлена и доступна в переменной окружения `JAVA_HOME`.  

2. Установите и настройте **Firebird**.  
    - Скачайте Firebird с [официального сайта](https://firebirdsql.org/).

3. Установите и запустите **Kafka** (через Docker):
    ```bash
    docker run -d --name=kafka -p 9092:9092 apache/kafka
    ```
    Убедитесь, что контейнер запущен и порт `9092` доступен приложению.  
    Также проверьте переменные окружения в Kafka, если требуется тонкая настройка.

4. Настройте подключение к БД в `application.properties`:
    ```properties
    spring.datasource.url=jdbc:firebirdsql://localhost:3050/your-database
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    ```

## 🚀 Запуск проекта

1. Склонируйте репозиторий:
    ```bash
    git clone https://github.com/DKAVrZoV65F/spring-kafka-wiki.git
    ```

2. Соберите проект с помощью **Maven**:
    ```bash
    mvn clean install
    ```

3. Запустите приложение:
    ```bash
    java -jar kafka-consumer-database/target/kafka-consumer-database-0.0.1-SNAPSHOT.jar
    ```
    
    ```bash
    java -jar kafka-producer-wikimedia/target/kafka-producer-wikimedia-0.0.1-SNAPSHOT.jar
    ```
