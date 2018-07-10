# Story 0002: Refactor Project

## ASIS

A project is defined by a name, a collection of work orders and budgets. It also keeps tracked of booked hours.

As is, it is mostly seen and correlated with by a collection of work orders, but for most, if not all, clients a project's definition will be much more refined.



## TOBE

A client should be able to define their projects in all kind of ways. The goal of this refactoring is to provide some standard ways to define a project (a collection of work orders, a certain prefix in the booking comment, ..) but also make it extensible so that bespoke project definitions are possible.

![TOBE](project_tobe.png "TOBE")

A Project has a ProjectDefinition and a ProjectDefinition consists of various MatchingRules. When a booking matches with one of the rules in the ProjectDefinition, it is considered a booking for the Project.



* Creating a project (name, budgets)
* Updating a project (name, budgets)
* Removing a project
* Adding matching rules to project
* Removing matching rules from project
* Existing features still work: budget used, monthly and yearly views



The current Project microservice will no longer be a microservice on its own but will become a dependency. By adding this dependency to their own microservice, the available endpoints will be automatically added and configured, but the configuration will be stored in the database managed by the microservice.

## Estimation
Refactoring in 2 stories:
Refactoring matching rules: 6md
Refactoring ProjectBudgets: 2 md
