# 10. rabbitmq

Date: 2022-11-25

## Status

Accepted

## Context

We want to build an event driven system. We need an event broker to manage the exchange of events between services. 
We don't want to use Axon Server for this as the open source version of Axon Server is missing many useful features 
such as binding events to bounded contexts.

## Decision

We will use [RabbitMQ](https://www.rabbitmq.com/) which has first-class integrations with Spring Boot and Axon 
Framework.

## Consequences

Rabbit is pretty easy to manage and has a nice UI for managing things. Allows us to be event-driven quickly for 
low-cost.
