package com.example.authgateway.filters.authcheck.adapter;

import com.example.authgateway.filters.authcheck.port.DataAccessPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataAccessAdapter implements DataAccessPort {

    private final DatabaseClient databaseClient;
    private final Map<String,Set<String>> cacheMap;

    @Override
    public Mono<Set<String>> findAuthorityByUserId(String userId) {


        return findFunctions(userId)
                .doOnSuccess(set -> {
                    log.info("DB 어댑터 접속 : Cache가 비었으므로, DB에서 권한을 조회하고, 캐시에 등록한다.");
                    cacheMap.put(userId, set);
                });
    }




    private Mono<Set<String>> findFunctions(String userId){
        String query = """
                            SELECT
                                   func.function_name funcName
                              FROM T_ROLE_BY_USER userrole
                              JOIN T_AUTHORITY_BY_ROLE roleauth on userrole.role_id = roleauth.role_id
                              JOIN T_FUNCTION_BY_AUTHORITY authfunc on roleauth.authority_id = authfunc.authority_id
                              JOIN T_FUNCTION func on authfunc.function_id = func.function_id
                             WHERE userrole.user_id = :userId
                             GROUP BY func.function_name
                           """;
        return databaseClient.sql(query)
                .bind("userId", userId)
                .map((row, rowMetadata) -> row.get("funcName").toString())
                .all()
                .log()
                .collect(HashSet::new, (set, s) -> set.add(s));
    }
}
