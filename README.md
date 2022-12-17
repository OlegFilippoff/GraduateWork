# GraduateWork
[![Build status](https://ci.appveyor.com/api/projects/status/wcdaa281l8ugbj3t?svg=true)](https://ci.appveyor.com/project/OlegFilippoff/graduatework)

[План выполнения работ](https://github.com/OlegFilippoff/GraduateWork/blob/master/Docs/Plan.md)

[Отчет Allure](https://github.com/OlegFilippoff/GraduateWork/blob/master/Docs/Report.md)

### Руководство

1. Скопировать содержимое репозитория https://github.com/OlegFilippoff/GraduateWork
2. Открыть проект в среде разработки, например, IntelliJ Idea
3. Запустить развертывание БД Node.js, Mysql, Postgres из терминала командой docker-compose up
4. Открыть новое окно терминала и Запустить приложение на сервере Mysql командой java -jar ./aqa.jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/app
5. Открыть новое окно терминала и Запустить тесты командой ./gradlew clean test

Для запука приложения с БД Postgres:
1. Повторить пункты 1-3 последовательно
2. Открыть новое окно терминала и Запустить приложение на сервере Postgres командой java -jar ./aqa.jar -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app
3. Открыть новое окно терминала и Запустить тесты командой ./gradlew clean test

### Отчеты Allure
Написать в командной стреке ./gradlew allureReport для создания отчетности Allure
