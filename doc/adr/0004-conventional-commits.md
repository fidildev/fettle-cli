# 4. Conventional Commits

Date: 2023-03-09

## Status

Accepted

## Context

Using conventional commits will standardize the way we name our PRs, allow for easier Semantic Versioning, and allow for developer friendly release notes to be automatically generated.

You can learn more about conventional commits [here](https://www.conventionalcommits.org/en/v1.0.0/)

## Decision

We will enable `Squash Merge` only and enforce the PR title to have one of the following prefixes:

- `feat:` for a new feature
- `fix:` for a bug fix
- `deps:` for a dependency update
- `docs:` for a documentation update
- `chore:` for a miscellaneous change that isn't covered above
- `breaking change:` for a breaking change

Note, for simplicity, this isn't done on the commit level, rather we will leverage the PR name.

## Consequences

All contributors must Squash Merge their PRs and the title of the PR must contain one of the descriptors above.
