<h1 align="center">
  TODO List
</h1>

Descricao do projeto.

## Tecnologias

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- SpringDoc OpenAPI 3
- H2 Db

## Práticas adotadas

- SOLID, DRY, YAGNI, KISS
- API REST
- Consultas com Spring Data JPA
- Injeção de Dependências
- Tratamento de respostas de erro
- Geração automática do Swagger com a OpenAPI 3

## Como Executar

- Clonar repositório git
- Construir o projeto(em ambiente GNU/Linux):
```
$ ./gradlew clean bootJar
```
- Executar a aplicação:
```
$ java -jar ./build/application.jar
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
$ http POST :8080/api/tasks description="Todo 1" is_active=true

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
$ http PUT :8080/api/tasks/{id} description="Todo 1 Up" prioridade=2

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
http DELETE :8080/api/tasks/{id}

[ ]
```