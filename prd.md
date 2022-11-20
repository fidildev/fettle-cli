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

## Solution

### Software Fitness Report Card

#### Personas 

* Developer
* Domain Admin

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

### Signup/In with GitHub

#### Personas

* Developer
* Domain Admin

#### Description

The default signup/in shall be with GitHub SSO. 

#### Requirements

* New users are registered as Developer users
* New users are presented with a default home screen that includes:
  * Description of what fettle does
  * Option to add a repository for analysis
  * Option to add a domain
* Users registering must authorize access to repositories and repository settings.

### Domain User Management

#### Personas

* Domain Admin

#### Description

Domain Admins can add/remove authorized users to a domain. By default users added to a domain are Developer users. 
Domain Admins may choose to elevate privileges to Domain Admin. Developer users have permission to view repositories 
and grades only. Domain admins can add/remove/re-run analysis.

#### Requirements

* 

### Manage Repositories

#### Personas

* Developer
* Domain Admin

#### Description

Users have the ability to review grades for their repository, re-run an analysis, and add/remove domains.

#### Requirements

* Developer users can add/remove repositories they own.
* Domain Admin users can add/remove repositories from domains they own.
  * Domain Admin can add repositories they are authorized for in GitHub.
* All personas can view current state of repositories. 
    * List of existing repositories and their grades
    * Repositories are grouped by domain if a domain exists.
* Domain Admin users can add/remove repositories from a domain.
* All repositories have an option to re-run the grading analysis.
  * Domain Admin can do this for all repositories in a domain.
  * Domain Admin can do this for individual repositories in a domain.









