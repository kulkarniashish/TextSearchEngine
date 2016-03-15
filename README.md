# TextSearchEngine
TextSearch - Intelliment Online Test

To build:
mvn clean install

To run the application:
mvn spring-boot:run

Used Spring security for protecting resource. Please use below credentials(You can change it in application.properties)
Username: test
Password: test

Calling the API's through command-line:
curl http://localhost:9000/counter-api/search -H"Authorization: Basic dGVzdDp0ZXN0" -d "{\"searchText\":[\"the\"]}" -H"Content-Type: application/json" -X POST

curl http://localhost:9000/counter-api/top/5 -H"Authorization: Basic dGVzdDp0ZXN0" -H"Accept: text/csv"

