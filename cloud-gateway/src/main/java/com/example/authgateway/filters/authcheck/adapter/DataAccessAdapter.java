package com.example.authgateway.filters.authcheck.adapter;

import com.example.authgateway.filters.authcheck.port.DataAccessPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class DataAccessAdapter implements DataAccessPort {
    @Override
    public Mono<Set<String>> findApiByRoles(Set set) {
        return Mono.empty();
    }

    @Override
    public Mono<Set<String>> findAuthorityByUserName(String name) {

        log.info("DB 어댑터 접속 : Cache가 비었으므로, DB에서 권한을 조회한다.");

        Set set = new HashSet();

        set.add("/test/rock");

        return Mono.just(set);
    }
}
