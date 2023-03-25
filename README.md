# fettle

<p align="center">
  <img src="/doc/diagrams/fettle-logo.png">
</p>

## [Product Requirements](./doc/prd.md)

## [Architecture](./doc/architecture.md)

## [ADRs](https://fidildev.github.io/fettle/doc/adr/index.html)

## Prehook setup

Running this command will generate a precommit hook for ktlint formatting.

```shell
./gradlew addKtlintFormatGitPreCommitHook
```

## Building

First get a GitHub personal access token.

Set the GitHub PAT in your environment as `GH_TOKEN`

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

## Running from Build

### Getting Fettle Grade

```shell
GH_TOKEN={github_token} /
GH_USER={github_user} /
./fettle score -o fidildev -r fettle
```

This will run all registered `FettleFunction`s and provide the grade for a given repo based on the boolean response from
each.

For example, at the time of writing this is the Fettle Grade for `fettle`:

```shell
GH_TOKEN={github_token} /
GH_USER={github_user} /
$ ./fettle score -o fidildev -r fettle
Fettle Score: F
╭ᥥ╮(´• ᴗ •`˵)╭ᥥ╮
```

### Running Individual `FettleFunction`s

Branch Protection Enabled

```shell
GH_TOKEN={github_token} /
GH_USER={github_user} /
./fettle branchProtection -o fidildev -r fettle
```

Dependabot Active

```shell
GH_TOKEN={github_token} /
GH_USER={github_user} /
./fettle dependabot -o fidildev -r fettle
```

Static Code Analysis Active

```shell
GH_TOKEN={github_token} /
GH_USER={github_user} /
./fettle staticCodeAnalysis -o fidildev -r fettle
```

Code Coverage Exists

```shell
GH_TOKEN={github_token} /
GH_USER={github_user} /
./fettle codeCoverage -o fidildev -r fettle
```

Deployment Pipelines Exist

```shell
GH_TOKEN={github_token} /
GH_USER={github_user} /
./fettle checkForDeployments -o fidildev -r fettle
```
