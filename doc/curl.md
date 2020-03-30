1. get all meals \
GET "/topjava/rest/meals": \
curl "http://localhost:8080/topjava/rest/meals"

2. get meal with id = 100002 \
 GET "/topjava/rest/meals/100002": \
curl "http://localhost:8080/topjava/rest/meals/100002"

3. delete meal with id = 100003 \
 DELETE "/topjava/rest/meals/100003": \
curl -X DELETE "http://localhost:8080/topjava/rest/meals/100003"
    
4. get filtered meals \
GET "/topjava/rest/meals/filter?startDate=2020-01-31&startTime=00:00&endDate=2020-01-31&endTime=12:00": \
curl  GET "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=00:00&endDate=2020-01-31&endTime=12:00"

5. update meal with id = 100002 \
PUT "/topjava/rest/meals/100002" \
{\
    "id" : "100002",\
    "dateTime" : "2020-01-31T00:10",\
    "description" : "newMeal",\
    "calories" : "200"\
}
:\
curl -X PUT -H "Content-Type: application/json" -d '{"id":"100002","dateTime":"2020-01-31T00:10","description":"newMeal","calories":"200"}' "http://localhost:8080/topjava/rest/meals/100002"

6. create new meal\
 POST "/topjava/rest/meals"\
{\
"dateTime" : "2020-02-02T00:00",\
"description" : "newMeal",\
"calories" : "123"\
} 
:\
curl -H "Content-Type: application/json" -X POST -d '{"dateTime":"2020-02-02T00:00","description":"newMeal","calories":"123"}' "http:/localhost:8080/topjava/rest/meals"
