package com.example.authgateway.filters.authcheck.port;

import reactor.core.publisher.Mono;

import java.util.Set;

public interface DataAccessPort {
    Mono<Set<String>> findApiByRoles(Set set);

    Mono<Set<String>> findAuthorityByUserName(String name);
}
