# Story 0007: Continuous Integration

Each microservice should have its own build. 

A docker image with the build environment to build this microservice is provided/generated. A container with the build environment is spun up and the microservice gets build. This build generates a unique versioned immutable artifact e.g. a jar file. 

Predeploy tests are ran against this artifact. 

When all these tests pass, the code artifact file is pushed to a repository and a docker image is created from this artifact to deploy the microservice. This image is pushed to a docker hub.

----

An integration test pipeline will deploy the microservice artifacts and run integration tests against them. When all integration tests pass, the code artifact and the docker image are reversioned as a Release Candidate (RC) and republished to the repository/docker hub.

---

A pipeline to promote a RC to a RELEASE should also be created.