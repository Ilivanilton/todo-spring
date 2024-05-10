CREATE
http -v POST :8080/api/tasks description=lilo is_active=true
List
http -v :8080/api/tasks
