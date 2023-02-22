package com.example.authgateway.filters.authcheck.port;

import reactor.core.publisher.Mono;

import java.util.Set;

public interface CacheAccessPort {

    Mono<Set<String>> findAuthorityByCache(String userName);

    Mono<Void> registApiSet(Set<String> apis, String userName);
}
