# Product Requirements

## Problem

* Engineering teams don’t have an easy way to measure the health of their software engineering processes, practices, 
and standards. 
* Engineering leadership doesn’t have a macro-view of all their engineering teams. 
* FIDIL doesn’t have a tool that can be used with new customers to give them insights into areas of improvement.

### Target Audience

* Software engineering teams
* Software engineering leadership
* FIDIL consultants

## Goals

* Tool used by FIDIL consultants to quickly assess how engineering teams are doing.
* Example of how to build software in real life (beyond hello world examples).
* Walk the walk, not just talk the talk. “These concepts work and here is living proof”.
* Make enough so we can buy laptops where the “e” key doesn't stick.
* Raise awareness of FIDIL
* Build credibility of FIDIL
* SaaS product, with a recurring revenue subscription model.

### Non-Goals

* Being a static code analysis tool. We care that you use static code analysis, but we ourselves don’t do it.

## Risks

* Running out of financial runway. Costing money without making any money.
* Tool doesn't provide the value we think it will.

## Personas

### Organization Admin

Owner of an Organization. Has full permissions to manage users, Repositories, and Domains within the Organization.

### Domain Admin

Manages one or more Domains. Only Organization admins can grant Domain Admin privileges. Domain admins can 
add/remove Repositories from Domains they have permissions for. Can re-run analysis on entire Domain or individual 
Repositories. Can add Developer users to domains but cannot add users to Organization.

### Developer

Has read only access to Organization Repositories and Domains they have been granted access from by an Organization 
Admin or Domain Admin. Can re-run analysis on Repositories in Domains they have been granted access to.

## Solution

### Software Fitness Report Card

#### Personas 

* Developer
* Domain Admin
* Organization Admin

#### Description

Provides a toolset that can be run against a GitHub repository to provide a grade (A-F) based on our opinionated set of quality/fitness checks. These checks are done based on known file and configuration locations, they don’t actually run scans (execute tools).

#### Requirements

* Repository checks:
  * Repo has branch protection enabled for main
  * Repo uses deployment pipelines
  * Determined by the presence of GitHub Actions workflows
  * Repo pipeline has static code analysis
  * Determined by a static code analysis tool in the GitHub Actions workflows
  * Repo pipeline has code coverage (jacoco on classpath? Just java/kotlin/jvm?)
    * Jacoco for JVM
    * Istanbul for JavaScript
  * Repo pipeline has Static application security testing (SAST)
  * Dependabot enabled on the repo
* Each check has a weight of 1 and a grade is determined by dividing successful checks by the total number of checks.
  * Example: Given 8 checks, a repository with 4 passing checks, the repository would receive a grade of 50% `4/8=.5`.
* Missing checks are provided are marked as recommended changes.
* Grades can be reviewed by any authorized user in an Organization.

### Registration with GitHub

#### Personas

* Developer
* Domain Admin
* Organization Admin

#### Description

The default signup/in shall be with GitHub SSO.

#### Requirements

* New users are registered as Developer users
* Users registering must authorize access to repositories and repository settings.


### Organization Management

#### Personas

* Organization Admin

#### Description

Organization Admins can manage Users, Domains, and Repositories. Domain Admins can manage Repositories within a Domain.

#### Requirements

* New Users (not invited) are presented with a screen to create an Organization.
* Organization Admins can invite new users with an email address (or GitHub username?)
* Invited Users have read access to Repositories and Domains within the Organization.
* Organization Admins can elevate Developer Users to Domain Admin or Organization Admin.
* Organization Admins can create/remove Repositories and Domains.
* Domain Admins can create/remove Repositories within a Domain.








