# 6. mongodb

Date: 2022-11-25

## Status

Accepted

## Context

We need at least one option for how we store and manage read model databases.

## Decision

We will use [Bitnami MongoDB](https://github.com/bitnami/charts/tree/main/bitnami/mongodb) via Helm sidecar 
deployments for any service that requires a read model data store.

## Consequences

Keeps our options small for the short-term and allows for cheap usage of MongoDB. In the future Mongo Atlas may be 
considered as an alternative.
