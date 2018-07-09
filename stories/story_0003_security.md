# Story 0003 Security

We want to secure the budget monitor so that only authorized users have access. 

This will be done on network level. The budget monitor consists of multiple internal networks to ensure that only services supposed to talk to each other can do so. External services can only talk to the budget monitor services through a load balancer (Traefik). 

Clients will have their own microservice which will be deployed in the budget monitors network giving them network access to other services inside the network.  Clients are responsible for securing their own microservice.