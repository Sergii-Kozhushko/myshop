# MyShop Application

Demo final project. Telran School, Kozhushko Sergii

### Installation
- Add certificate from /resources/ssl/rootca2.cer to trusted certificates
for your system, to browser for firefox or to system for chrome
- install and run PostgreSQL if you have
- Change db user and password for PostgreSQL DB

### Description
https://docs.google.com/document/d/1-PM3OP52vH2KnAg11DvRNAtQgSp_aITj6i_0sMadtwA/edit
### API
Anonymous:
```
[GET] https://localhost:8081/api/test/anonymous
```
Admin:
```
[GET] http://localhost:8081/api/test/admin
Authorization - Bearer Token with admin privileges
```
User:
```
[GET] http://localhost:8081/api/test/user
Authorization - Bearer Token with admin or user privileges
```

<hr>
<br>

