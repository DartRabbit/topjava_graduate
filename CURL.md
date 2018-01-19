## CURL commands
###### for topjava_graduate

### Admin - Users commands

* User create
```
$ curl 'http://localhost:8080/rest/admin/users/' -i -u 'admin@gmail.com:admin' -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{
  "name" : "New",
  "email" : "new@gmail.com",
  "password" : "newPass",
  "isAdmin" : true
}
```
---
* User delete
```
$ curl 'http://localhost:8080/rest/admin/users/100000' -i -u 'admin@gmail.com:admin' -X DELETE
```
---
* User update
```
$ curl 'http://localhost:8080/rest/admin/users/100000' -i -u 'admin@gmail.com:admin' -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{
  "id" : 100000,
  "name" : "UpdatedName",
  "email" : "user1@yandex.ru",
  "password" : "password",
  "isAdmin" : true
}'
```
---
* User get all
```
$ curl 'http://localhost:8080/rest/admin/users/' -i -u 'admin@gmail.com:admin'
```
---
* User get one by id
```
$ curl 'http://localhost:8080/rest/admin/users/100000' -i -u 'admin@gmail.com:admin'
```
---
* User get one by e-mail
```
$ curl 'http://localhost:8080/rest/admin/users/by?email=admin@gmail.com' -i -u 'admin@gmail.com:admin'
```


### Admin - Restaurants commands
* Restaurant create
```
$ curl 'http://localhost:8080/rest/admin/restaurants/' -i -u 'admin@gmail.com:admin' -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{
  "name" : "Чижик-Пыжик"
}'
```
---
* Restaurant delete
```
$ curl 'http://localhost:8080/rest/admin/restaurants/100004' -i -u 'admin@gmail.com:admin' -X DELETE
```
---
* Restaurant update
```
$ curl 'http://localhost:8080/rest/admin/restaurants/100004' -i -u 'admin@gmail.com:admin' -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{
  "id" : 100004,
  "name" : "UpdatedName"
}'
```
---
* Restaurant get all
```
$ curl 'http://localhost:8080/rest/admin/restaurants/' -i -u 'admin@gmail.com:admin'
```
---
* Restaurant get one by id
```
$ curl 'http://localhost:8080/rest/admin/restaurants/100004' -i -u 'admin@gmail.com:admin'
```
---
* Restaurant get by name
```
$ curl 'http://localhost:8080/rest/admin/restaurants/by?name=%D0%81%D0%BB%D0%BA%D0%B8-%D0%9F%D0%B0%D0%BB%D0%BA%D0%B8' -i -u 'admin@gmail.com:admin
```
---
* Get restaurants with dishes today
```
$ curl 'http://localhost:8080/rest/admin/restaurants/with_dishes' -i -u 'admin@gmail.com:admin'
```
---
* Get restaurants with dishes by date
```
$ curl 'http://localhost:8080/rest/admin/restaurants/with_dishes?date=2017-12-29' -i -u 'admin@gmail.com:admin'
```

### Admin - Dishes commands
* Get dish by id & restaurant id
```
$ curl 'http://localhost:8080/rest/admin/restaurants/100004/dish/100007' -i -u 'admin@gmail.com:admin'
```
---

* Delete dish by id & restaurant id
```
$ curl 'http://localhost:8080/rest/admin/restaurants/100004/dish/100009' -i -u 'admin@gmail.com:admin' -X DELETE
```
---

* Create dish on restaurant with restaurant id
```
$ curl 'http://localhost:8080/rest/admin/restaurants/100004/dish' -i -u 'admin@gmail.com:admin' -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{
  "name" : "Рассольник",
  "date" : "2017-12-29",
  "price" : 150.0
}'
```
---

* Update dish by id & restaurant id
```
$ curl 'http://localhost:8080/rest/admin/restaurants/100004/dish/100008' -i -u 'admin@gmail.com:admin' -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{
  "id" : 100008,
  "name" : "Холодец",
  "date" : "2017-12-29",
  "price" : 225.8
}'
```
---

### User - Profile commands
* Delete profile
```
$ curl 'http://localhost:8080/rest/profile/' -i -u 'user1@yandex.ru:password' -X DELETE
```
---
* Update profile
```
$ curl 'http://localhost:8080/rest/profile/' -i -u 'user1@yandex.ru:password' -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{
  "id" : 100000,
  "name" : "newName",
  "email" : "newmail@yandex.ru",
  "password" : "newpass"
}'
```
---
* Get profile
```
$ curl 'http://localhost:8080/rest/profile/' -i -u 'user1@yandex.ru:password'
```
---

### User - Restaurants commands

* Vote today fail
```
$ curl 'http://localhost:8080/rest/restaurants/100004/vote' -i -u 'user1@yandex.ru:password' -X POST
```
```
HTTP/1.1 406 Not Acceptable
ache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
X-Content-Type-Options: nosniff
```
---
* Vote today success
```
$ curl 'http://localhost:8080/rest/restaurants/100005/vote' -i -u 'user4@yandex.ru:password' -X POST
```
```
HTTP/1.1 200 OK
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
X-Content-Type-Options: nosniff
Content-Type: application/json;charset=UTF-8
Content-Length: 126

{
  "voteId" : {
    "userId" : 100003,
    "date" : "2018-01-19"
  },
  "date" : "2018-01-19",
  "restaurant" : null
}
```
---
* Vote on date
```
$ curl 'http://localhost:8080/rest/restaurants/100004/vote?date=2017-12-29' -i -u 'user1@yandex.ru:password' -X POST
```
---

