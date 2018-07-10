# Story 0002: Refactor Project

## ASIS

A project is defined by a name, a collection of work orders and budgets. It also keeps tracked of booked hours.

As is, it is mostly seen and correlated with by a collection of work orders, but for most, if not all, clients a project's definition will be much more refined.



## TOBE

A client should be able to define their projects in all kind of ways. The goal of this refactoring is to provide some standard ways to define a project (a collection of work orders, a certain prefix in the booking comment, ..) but also make it extensible so that bespoke project specifications are possible.

![TOBE](project_tobe.png "TOBE")

A Project has a ProjectSpecification and a ProjectSpecification consists of various MatchingRules. When a booking matches with one of the rules in the ProjectSpecification, it is considered a booking for the Project.

Not only the way a project is defined is different for each client, the same thing applies to how each client defines their budgets. Budgets could be yearly but also quarterly or monthly. A ProjectBudget will consist of a Period and a budget in hours.



* Creating a project
* Updating a project (name, ..)
* Removing a project
* Adding budgets to project
* Updating budget to project
* Removing budgets from project
* Adding matching rules to project
* Updating matching rule from project
* Removing matching rules from project
* Existing features still work: budget used, monthly and yearly views



The Project microservice will be configuration driven. This means that each client can deploy its own microservice which is the same code but uses different configuration.

## Estimation
Refactoring in 2 stories:

* Refactoring matching rules: 6md
* Refactoring ProjectBudgets: 2 md