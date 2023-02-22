package com.example.authgateway.filters.authcheck.adapter;

import com.example.authgateway.filters.authcheck.port.CacheAccessPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@Slf4j
public class CacheAccessAdapter implements CacheAccessPort {
    @Override
    public Mono<Set<String>> findAuthorityByCache(String userName) {

        try {
            log.info("Cache 어댑터 접속 : 캐쉬가 없으므로 Mono.empty()를 반납합니다.");
        }catch (Throwable throwable){
            /**
             * Mono.empty를 던지면 상관없으나, 저쪽에서 exception이나 Mono.error를 던지는 경우를 파악해야함.
             * 파악이 완료되면 예외처리 필요.
             */
            return Mono.empty();
        }


        return Mono.empty();
    }

    @Override
    public Mono<Void> registApiSet(Set<String> apis, String userName) {
        return Mono.empty();
    }
}
