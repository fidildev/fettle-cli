# fettle

## [Product Requirements](./doc/prd.md)
## [Architecture](./doc/architecture.md)
## [ADRs](https://fidildev.github.io/fettle/doc/adr/index.html)

## Running Locally

Fist get a GitHub personal access token.

Set the GitHub PAT in your environment as `GH_TOKEN`

```shell
./gradlew build
```

```shell
gunzip fettle-0.0.1-SNAPSHOT.zip
```

```shell
cd build/fettle-0.0.1-SNAPSHOT/bin
```

```shell
./fettle branchProtection -o fidildev -r fettle -a $GH_TOKEN -u {github_username}
```
