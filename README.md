###Search request Gatling test

Нагрузочное тестирование с использованием Gatling'а для [Search request logger'a](https://github.com/efserg/search-request-logger)

####Конфигурирование
Настройка параметров происходит путем редактирования свойств в `conf/performanceTest.conf`

* `url="http://localhost:8080" ` - базовый url
* `users=10000` - число запросов
* `duration=10` - продолжительность теста (в секундах)

При описанной конфигурации будет послано 10000 запросов за 10 секунд (то есть в среднем 1000 запросов в секунду)

####Запуск тестирования
1. Запустите [search request logger](https://github.com/efserg/search-request-logger)
1. Склонируйте репозиторий `git clone ...`
1. Нужным образом отредактируйте `conf/performanceTest.conf`
1. Выполните `mvn -Dgatling.simulationClass=BasicSimulation gatling:test` в директории проекта
1. Gatling отошлет нужное число запросов на создание записи в базе
1. Отчет по тестированию будет доступен по пути `target\gatling\basicsimulation-<timestamp>\index.html`