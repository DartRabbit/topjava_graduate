## CURL commands
###### for topjava_graduate

### Admin - Users command

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
* User get ome by e-mail
```
$ curl 'http://localhost:8080/rest/admin/users/by?email=admin@gmail.com' -i -u 'admin@gmail.com:admin'
```