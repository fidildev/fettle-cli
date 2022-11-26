# 3. axon framework

Date: 2022-11-25

## Status

Accepted

## Context

We want to use event sourcing and CQRS in this application for illustration and training purposes. These concepts 
can be somewhat difficult to setup manually.

## Decision

We will use [Axon Framework](https://docs.axoniq.io/reference-guide/axon-framework/introduction). It works well with 
Spring Boot/Kotlin. 

## Consequences

ES and CQRS patterns will be handled through the Axon Framework Spring Boot starter making the implementation of 
these patterns simple.
