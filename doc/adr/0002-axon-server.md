# 2. axon server

Date: 2022-11-25

## Status

Accepted

## Context

We want to use event sourcing and CQRS for this application for illustration and training purposes.

## Decision

We will use [Axon Server](https://github.com/AxonIQ/axon-server-se) as an event store *only*. See the [RabbitMQ ADR]
(./0010-rabbitmq.md) for event bus / event distribution.

## Consequences

We have an event store that works well with Axon Framework.
