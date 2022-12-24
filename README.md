# GraduateWork
[![Build status](https://ci.appveyor.com/api/projects/status/wcdaa281l8ugbj3t?svg=true)](https://ci.appveyor.com/project/OlegFilippoff/graduatework)

[План выполнения работ](https://github.com/OlegFilippoff/GraduateWork/blob/master/Docs/Plan.md)

[Отчет Allure](https://github.com/OlegFilippoff/GraduateWork/blob/master/Docs/Report.md)

### Руководство

1. Скопировать содержимое репозитория https://github.com/OlegFilippoff/GraduateWork
2. Открыть проект в среде разработки, например, IntelliJ Idea
3. Запустить развертывание БД Node.js, Mysql, Postgres из терминала командой docker-compose up
4. Открыть новое окно терминала и запустить приложение на сервере Mysql командой java -jar ./aqa-shop.jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/app
5. Открыть новое окно терминала и запустить тесты командой ./gradlew clean test -Durl=jdbc:mysql://localhost:3306/app -Duser=app -Dpassword=pass

Для запуска приложения с БД Postgres:
1. Повторить пункты 1-3 последовательно
2. Открыть новое окно терминала и запустить приложение на сервере Postgres командой java -jar ./aqa-shop.jar -Dspring.datasource.url=jdbc:postgresql://localhost:5432/appPG
3. Открыть новое окно терминала и запустить тесты для MySQL командой ./gradlew clean test -Durl=jdbc:postgresql://localhost:5432/appPG -Duser=app -Dpassword=pass

### Отчеты Allure
Написать в командной строке ./gradlew allureReport для создания отчетности Allure
