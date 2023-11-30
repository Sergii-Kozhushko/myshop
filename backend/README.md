# MyShop Application

Demo final project. Telran IT School, Kozhushko Sergii

### Installation

- Add certificate from /resources/ssl/rootca2.cer to trusted certificates
  for your system (for chrome), or directly browser for firefox
- install and run PostgreSQL if you have
- Change db user and password for PostgreSQL DB
- JDK OpenJDK 17.0.7
- Install Keycloak 18.0.0 Server and add real-export.json configuration
- or use Docker container
- test2

### Description

Project description:
https://docs.google.com/document/d/1h1l6gdgCScMxxN_wndSy8YsTHeqmgm_FY1T4GQWVrIE/edit?usp=sharing

Security scheme:
https://docs.google.com/document/d/1U3Ql3_eVqkFPlnjKt8xsAzZh3lLFTWJ0a_0d1Xql3W8/edit?usp=sharing

### API

Swagger API: https://localhost:8081/swagger-ui/index.html

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

