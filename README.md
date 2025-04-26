# АВТОМАТИЗИРОВАННЫЙ ТЕСТ ИНТЕРНЕТ-МАГАЗИНА https://n-katalog.ru/

Предназначен для тестирования интернет-магазина n-katalog.ru
- - -
### Общая информация

`src/test/java/ru/ncatalog/WebdriverSetup.java` настройка web-драйвера\
`src/test/java/ru/ncatalog/ProductSearchTests.java` автотест\
`src/main/java/pages` PO для сайта Google.com и сайта n-catalog.ru\
`src/main/java/helpers/TestRunData.java` тестовые данные
- - -
### Локальный запуск

Запуск автотеста осуществляется:

1. Выполнением команды `mvn clean test -Dtest=ProductSearchTests`

2. Ручным запуском тестов из класса `ProductSearchTests`
- - -
### Сборка Allure отчёта

Сборка Allure отчёта осуществляется:

Выполнением команды `mvn allure:serve`
- - -