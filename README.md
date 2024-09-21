<h1 align="center">
  TODO List
</h1>

Descricao do projeto.

## Tecnologias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [Mysql](https://dev.mysql.com/downloads/)

## Práticas adotadas

- SOLID, DRY, YAGNI, KISS
- API REST
- Consultas com Spring Data JPA
- Injeção de Dependências
- Tratamento de respostas de erro
- Geração automática do Swagger com a OpenAPI 3

## Como Executar

- Clonar repositório git
- Construir o projeto:
```
$ ./mvnw clean package
```
- Executar a aplicação:
```
$ java -jar target/todolist-0.0.1-SNAPSHOT.jar
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080).
O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [httpie](https://httpie.io):

- Listar Tarefas
```
$ http GET :8080/api/tasks

{
    "currentPage":0,
    "perPage":10,
    "total":1,
    "items":[
        {
            "id": "a83458a5-96f0-456e-8a13-dcb69b2400e0",
            "description": "Todo 1",
            "is_active": true,
            "created_at": "2024-09-17T17:45:26.345450Z",
            "deleted_at": null
        }
    ]
}
```

- Criar Tarefa
```
$ http POST :8080/todos description="Todo 1" is_active=true

{
  "description":"Todo 1",
  "is_active":true
}
```

- Get Tarefa
```
$ http GET :8080/api/tasks/a83458a5-96f0-456e-8a13-dcb69b2400e0

{
  "id": "a83458a5-96f0-456e-8a13-dcb69b2400e0",
  "description": "lilo",
  "is_active": true,
  "created_at": "2024-09-17T17:45:26.345450Z",
  "deleted_at": null
}
```
- Atualizar Tarefa
```
$ http PUT :8080/todos/1 nome="Todo 1 Up" descricao="Desc Todo 1 Up" prioridade=2

[
  {
    "descricao": "Desc Todo 1 Up",
    "id": 1,
    "nome": "Todo 1 Up",
    "prioridade": 2,
    "realizado": false
  }
]
```

- Remover Tarefa
```
http DELETE :8080/todos/1

[ ]
```

CREATE
http -v POST :8080/api/tasks description=lilo is_active=true
List
http -v :8080/api/tasks
