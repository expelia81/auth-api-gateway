package com.example.authgateway.filters.authcheck.port;

import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * DB에서 값을 조회해오기 위한 포트이다.
 * DB에서 직접 값을 조회해올지, 외부 api를 이용할 지는 미정.
 */
public interface DataAccessPort {

    Mono<Set<String>> findAuthorityByUserId(String userId);
}
