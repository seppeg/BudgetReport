# Story 0003 Security

We want to secure the budget monitor so that only authorized users have access. 

We will use token-based security. A shared security component to verify access tokens will be built. The microservices will use this component to verify the access token on requests, requests without or with invalid token will be denied. 

We will implement this using Spring Security. As a token server we will use the Cegeka Slack server.