package com.example.authgateway.filters.authcheck;

import com.example.authgateway.filters.authcheck.exception.NotPermissionException;
import com.example.authgateway.filters.authcheck.pojo.Token;
import com.example.authgateway.filters.authcheck.port.CacheAccessPort;
import com.example.authgateway.filters.authcheck.port.DataAccessPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;

@RequiredArgsConstructor
@Slf4j
@Component
@Order(-6)
public class AuthCheckFilter extends AbstractGatewayFilterFactory {

    private final DataAccessPort dataAccessPort;
    private final CacheAccessPort cacheAccessPort;

    @Override
    public GatewayFilter apply(Object config) {
        /*
         * 코드 주석
         * 
         * 1. 헤더에서 토큰 추출  ->  getToken, getUserName
         *      1-1. 토큰 확인
         *      1-2. 토큰 검증
         *      1-3. 사용자 이름 추출
         *
         * 2. 사용자 권한 추출  ->  authorizationVerification
         *      2-1. cache 에서 추출 시도
         *      2-2. cache 존재하지 않을 경우, DB에서 추출
         *
         * 3. 예외처리, 상태코드 및 메시지 확인  ->  onErrorResume
         */
        log.info("권한 체크 필터 등록됨");
        return (exchange, chain) -> getToken(exchange)
                .map(token -> registerUserIdInHttpHeader(exchange,token))
                .flatMap(userId -> authorizationVerification(userId,exchange.getRequest().getPath().value()))
                .flatMap(result -> result ? chain.filter(exchange) : Mono.error(NotPermissionException::new))
                .onErrorResume(e -> {
                    log.info(e.getClass().getSimpleName());
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    return response.writeWith(Mono.fromCallable(() -> response.bufferFactory().wrap(e.getMessage().getBytes())));
                });
    }

    /**
     * 토큰에서 userId를 꺼내 반환하면서, 헤더에 userId를 추가한다.
     */
    private static String registerUserIdInHttpHeader(ServerWebExchange exchange, Token token) {
        exchange.getRequest().mutate().header("userId",token.getUserId()).build();
        return token.getUserId();
    }

    /**
     * 권한 확인 메소드. 먼저 cache를 조회하고, cache가 비어있으면 DB에서 조회한다.   현재는 캐시가 어플리케이션과 주기를 함께하는데, 추후 변경필요.
     */
    private Mono<Boolean> authorizationVerification(String userId, String path){

        log.info("입력된 api 경로 : " + path);
        /* 캐시를 먼저 조회하고, 비어있을 경우 DB에서 조회함.
         * 단, 캐시는 사용자가 바뀌거나, 권한이 바뀔 경우 비도록 해야한다. */
        return cacheAccessPort.findAuthorityByCache(userId)
                                .switchIfEmpty(dataAccessPort.findAuthorityByUserId(userId))
                                .map(set -> set.contains(path));
    }

    /**
     * 토큰 검증 및 파싱.  현재는 검증하지 않고, 파싱만 한다.  토큰 자체가 OAuth2 Proxy에 의해 검증되었다고 가정하기 때문.
     */
    private Mono<Token> getToken(ServerWebExchange exchange) {
        return Mono.fromCallable(() -> exchange.getRequest().getHeaders().get("Authorization").get(0).substring(7))
//        return Mono.fromCallable(() -> exchange.getRequest().getHeaders().get("X-Auth-Request-Access-Token").get(0).substring(7))
                .map(jwt -> {
                    log.info("토큰 검증중...");
                    // 여기서 jwt 검증을 실시함.
                    log.info("토큰 내용 : " + jwt);

                    return jwt.split("\\.")[1];
                })
                .map(s -> new String(Base64.getDecoder().decode(s)))
                .flatMap(s -> Mono.fromCallable(() -> new ObjectMapper().readValue(s, Token.class))); //에러발생시 resume하기위해 flatmap
    }

}
