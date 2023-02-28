package com.maestro.servicerequest.sr.permit.adapter.in;

import com.maestro.servicerequest.sr.permit.port.in.PermitUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/sr")
public class PermitSrController {
    private final PermitUseCase permitUseCase;

    @PatchMapping("/permit")
    public Mono<String> permitServiceRequest(PermitUseCase.PermitRequestCommand command, ServerHttpRequest request) {
        log.info("허가자 : " + request.getHeaders().get("userId").get(0));
        log.info("resourceId : " + command.resourceId());
        log.info("자원 생성을 시작합니다.");
        return Mono.fromCallable(() -> command.registerManager(request.getHeaders().get("userId").get(0)))
                .flatMap(managedCommand -> permitUseCase.permitRequest(managedCommand))
                .map(resource -> resource.getResourceName() + "자원 생성 요청이 완료되었습니다.");
    }


}
