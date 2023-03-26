# Fettle

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/4aafbf3194974a0e9097fb536dd05af1)](https://app.codacy.com/gh/fidildev/fettle/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade) [![Twitter Follow](https://img.shields.io/twitter/follow/fidildev?style=social)](https://twitter.com/fidildev)

<img align="right" src="/doc/diagrams/fettle-logo.png" width="200">

## Overview

- [What is Fettle](#what-is-fettle)
- [Product Requirements](./doc/prd.md)
- [Architecture](./doc/architecture.md)
  - [Architecture Decision Records (ARDs)](https://fidildev.github.io/fettle/doc/adr/index.html)
- [How to Build](#building)
- [Get your Fettle Grade](#getting-fettle-grade)
- [Run individual Fettle Function](#running-an-individual-fettlefunction)

## What is Fettle?

An opinionated tool created by FIDIL to quickly asses the health of a software team.

## Fettle Functions

A specific check, inspired by fitness functions.

| Fettle Function                                      | Description                                  |
|------------------------------------------------------|----------------------------------------------|
| [Branch Protection](#branch-protection-enabled)      | Validates branch protection is enabled       |
| [Code Coverage](#code-coverage-exists)               | Validates code coverage is calculated        |
| [Dependabot Configured](#dependabot-configured)      | Validates Dependabot is configured           |
| [Deployment Pipelines](#deployment-pipelines-exist)  | Validates the repo uses deployment pipelines |
| [Static Code Analysis](#static-code-analysis-active) | Validates static code analysis exists        |

## Contributing

### Prehook Setup

Before contributing on Fettle, ensure you have the precommit hooked setup. Running the command below will generate a precommit hook for `ktlint` formatting.

```shell
./gradlew addKtlintFormatGitPreCommitHook
```

### Building

```shell
./gradlew build
```

```shell
cd build/distributions
```

```shell
unzip fettle-0.0.1-SNAPSHOT.zip
```

```shell
cd fettle-0.0.1-SNAPSHOT/bin
```

## How to Run

Running Fettle Functions requires two environment variables to be set

1. `export GH_USER = <yourGitHubUser>`
1. `GH_TOKEN = <yourGitHubPersonalAccessToken>`

### Arguments

| Argument | Description  |
|----------|--------------|
| -o       | organization |
| -r       | repository   |

### Getting Fettle Grade

```shell
./fettle score -o fidildev -r fettle
```

This will run all registered `FettleFunction`s and provide the grade for a given repo based on the boolean response from
each.

For example, at the time of writing this is the Fettle Grade for `fettle`:

```shell
$ ./fettle score -o fidildev -r fettle
Fettle Score: F
╭ᥥ╮(´• ᴗ •`˵)╭ᥥ╮
```

### Running an Individual `FettleFunction`

#### Branch Protection Enabled

```shell
./fettle branchProtection -o fidildev -r fettle
```

#### Code Coverage Exists

```shell
./fettle codeCoverage -o fidildev -r fettle
```

#### Dependabot Configured

```shell
./fettle dependabot -o fidildev -r fettle
```

#### Deployment Pipelines Exist

```shell
./fettle checkForDeployments -o fidildev -r fettle
```

#### Static Code Analysis Active

```shell
./fettle staticCodeAnalysis -o fidildev -r fettle
```

## Star Count

[![Stargazers over time](https://starchart.cc/fidildev/fettle.svg)](https://starchart.cc/fidildev/fettle)
