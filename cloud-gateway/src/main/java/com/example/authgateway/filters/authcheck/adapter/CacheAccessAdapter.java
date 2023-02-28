package com.example.authgateway.filters.authcheck.adapter;

import com.example.authgateway.filters.authcheck.port.CacheAccessPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheAccessAdapter implements CacheAccessPort {


    private final Map<String,Set<String>> cacheMap;

    @Override
    public Mono<Set<String>> findAuthorityByCache(String userId) {
        /*
         * 레디스 구현 전, 일단 맵으로 대충 구현해둔 단계.
         */

        return Mono.fromCallable(() -> {
                    log.info("캐시 확인 중...");
                    /* 통합 테스트 전에 반드시 삭제해야할 로그 */
                    Set<String> set = cacheMap.get(userId);
                    if (set==null || set.isEmpty()){
                        log.info("캐시를 찾을 수 없음 : 해당 사용자에 대한 캐시가 없습니다.");
                    } else {
                        log.info("캐시가 존재하므로, 캐시에서 권한을 조회합니다.");
                    }
                    return cacheMap.get(userId);
                })
                .flatMap(cachedAuthSet -> cachedAuthSet==null ? Mono.empty() : Mono.just(cacheMap.get(userId)));
    }
}
