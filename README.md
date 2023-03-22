# fettle

<p align="center">
  <img src="/doc/diagrams/fettle-logo.png">
</p>

## [Product Requirements](./doc/prd.md)

## [Architecture](./doc/architecture.md)

## [ADRs](https://fidildev.github.io/fettle/doc/adr/index.html)

## Running Locally

Fist get a GitHub personal access token.

Set the GitHub PAT in your environment as `GH_TOKEN`

Running this command will generate a precommit hook for ktlint formatting.

```shell
./gradlew addKtlintFormatGitPreCommitHook
```

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
