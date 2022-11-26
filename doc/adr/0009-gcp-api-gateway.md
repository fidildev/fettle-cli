# 9. gcp api gateway

Date: 2022-11-25

## Status

Accepted

## Context

We need an API Gateway to manage the exposure of our application and its REST endpoints to the Internet. We would 
like to ensure this is managing routes, authn/z, and K8s ingress resources. 

## Decision

We will use [GCPs API Gateway](https://cloud.google.com/api-gateway).

## Consequences

Provides us good integration with GCP and K8s and no need to manage another piece of infrastructure (such as Envoy/AES).
