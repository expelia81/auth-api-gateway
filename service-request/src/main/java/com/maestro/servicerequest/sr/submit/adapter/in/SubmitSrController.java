package com.maestro.servicerequest.sr.submit.adapter.in;

import com.maestro.servicerequest.sr.submit.port.in.SubmitUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sr")
public class SubmitSrController {

    private final SubmitUseCase submitUseCase;


    @PostMapping("/submit")
    public Mono<String> submitServiceRequest(SubmitUseCase.SRSubmitCommand command, ServerHttpRequest request) {
        /*
         * 토큰에서 사용자 정보를 뽑아서 써야할지, 헤더에 넣어서 써야할지 고민중.
         * 일단 Command 재사용중.
         */
        log.info(request.getHeaders().get("Authorization").get(0));
        log.info(request.getHeaders().get("userId").get(0));
        return Mono.fromCallable(() -> command.registerRequester(request.getHeaders().get("userId").get(0)))
                .flatMap(submitUseCase::submitRequest)
                .map(result -> result.getResourceName() + " 요청에 성공함");
    }
}
