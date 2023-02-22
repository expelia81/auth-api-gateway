package com.example.authgateway.filters.authcheck;//package com.example.authgateway.filters.authcheck;
//
//import com.example.authgateway.filters.authcheck.exception.NotPermissionException;
//import com.example.authgateway.filters.authcheck.exception.RegistApiSetCacheException;
//import com.example.authgateway.filters.authcheck.pojo.Token;
//import com.example.authgateway.filters.authcheck.port.CacheAccessPort;
//import com.example.authgateway.filters.authcheck.port.DataAccessPort;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.core.annotation.Order;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Base64;
//import java.util.Set;
//
//@RequiredArgsConstructor
//@Slf4j
//@Order(-6)
//public class AuthCheckFilter_origin<Config> extends AbstractGatewayFilterFactory<Config> {
//
//    private final DataAccessPort dataAccessPort;
//    private final CacheAccessPort cacheAccessPort;
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> getToken(exchange)
//                                        .flatMap(this::getPermissionSet)
//                                        .flatMap(set -> set.contains(exchange.getRequest().getPath().value()) ?
//                                                            chain.filter(exchange) : Mono.error(NotPermissionException::new));
//    }
//
//    /**
//     *  To Do 1 ->  DB에서 permissions 조회
//     *  To Do 2 ->  Token의 UserId가 Redis에 있는지 확인 후 없으면 조회
//     *
//     *  로직 구조
//     *      - 먼저 캐시를 조회한다.
//     *          - 캐시가 있을 경우, DB를 조회하지 않는다.
//     *          - 캐시가 없을 경우, DB를 조회한다.
//     *      - DB 구조는 나중에 요구사항이 변경될 것을 고려하여 헥사고날 구조의 일부를 차용함.
//     */
//    private Mono<Set<String>> getPermissionSet(Token token) {
//        /* 레디스나 캐시가 있을 경우, 레디스에서 값 조회함. */
//        Mono<Set<String>> apiSet = cacheAccessPort.findApiByCache(token.getUserName());
//        /* 캐싱데이터가 있으면 바로 통과, 없으면 DB에서 긁어오고, 캐싱데이터 등록 */
//        if(!apiSet.equals(Mono.empty())){ // 있을 경우, 캐시에서 조회.
//            return Mono.fromCallable(() -> token.getAllRoles())
//                    .flatMap(roles -> dataAccessPort.findApiByRoles(roles));
//        } else {  //없을 경우, DB 조회
//            return Mono.fromCallable(() -> token.getAllRoles())
//                    .flatMap(roles -> dataAccessPort.findApiByRoles(roles))
//                    .map(apis -> {
//                        /* 캐시에 조회된 api list 추가 */
//                        cacheAccessPort.registApiSet(apis, token.getUserName())
//                                .onErrorResume(e -> {
//                                    /* 메인 파이프라인이 아니므로, 메인 파이프라인에서 예외를 처리할 수 있도록 던져준다. */
//                                    throw new RegistApiSetCacheException(e.getMessage());
//                                });
//                        return apis;
//                    });
//        }
//    }
//
//    /**
//     * 토큰 검증 및 파싱.
//     */
//
//    private Mono<Token> getToken(ServerWebExchange exchange) {
//        return Mono.fromCallable(() -> exchange.getRequest().getHeaders().get("Authorization").get(0).substring(7))
//                .map(jwt -> {
//
//                    // 여기서 jwt 검증을 실시함.
//
//                    return jwt.split("\\.")[1];
//                })
//                .map(s -> new String(Base64.getDecoder().decode(s)))
//                .flatMap(s -> Mono.fromCallable(() -> new ObjectMapper().readValue(s, Token.class))); //에러발생시 resume하기위해 flatmap
//    }
//
//
//    public static class Config{
//
//    }
//}
