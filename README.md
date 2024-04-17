# Get Started With Docker

- install Docker Desktop 
- Set up network for container-to-container communication:
  - ```docker network create redis_server --driver bridge```
- run redis connected to previously made network and listening to default port. "redis-stack" is the name of server on bridge. "-p 8001:8001" is the port for redis insights, which you can browse to in the browser.
  - ```docker run -d --name redis-stack -p 0.0.0.0:6379:6379 -p 8001:8001 --network redis_server redis/redis-stack:latest```
- Install and run Docker Desktop
- Build and run this project using the following docker commands in the terminal at the project's root folder.
  - ```docker build -t templateapp .```
  - ```docker run --rm -p 8080:9000 --network redis_server --name templateapp templateapp```

You should now be able to hit this service's endpoints at localhost:8080

### Sample Request:

```localhost:8080/v1/health```