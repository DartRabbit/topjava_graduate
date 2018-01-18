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
* Get restaurants with votes & dishes today
```
$ curl 'http://localhost:8080/rest/admin/restaurants/with_votes' -i -u 'admin@gmail.com:admin'
```
---
* Get restaurants with votes & dishes by date
```
$ curl 'http://localhost:8080/rest/admin/restaurants/with_votes?date=2017-12-29' -i -u 'admin@gmail.com:admin'
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
* Get restaurants with dishes & votes today
```
$ curl 'http://localhost:8080/rest/restaurants/' -i -u 'user1@yandex.ru:password'
```
---
* Get restaurants with dishes & votes by date
```
$ curl 'http://localhost:8080/rest/restaurants/?date=2017-12-29' -i -u 'user1@yandex.ru:password'
```
---
* Vote today
```
$ curl 'http://localhost:8080/rest/restaurants/100004/vote' -i -u 'user1@yandex.ru:password' -X POST
```
---
* Vote on date
```
$ curl 'http://localhost:8080/rest/restaurants/100004/vote?date=2017-12-29' -i -u 'user1@yandex.ru:password' -X POST
```
---

