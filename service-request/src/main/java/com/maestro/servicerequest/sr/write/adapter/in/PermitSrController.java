package com.maestro.servicerequest.sr.write.adapter.in;

import com.maestro.servicerequest.sr.exceptions.AlreadyProcessedResourceRequestException;
import com.maestro.servicerequest.sr.exceptions.ResourceNotFoundException;
import com.maestro.servicerequest.sr.write.port.in.PermitUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/sr")
@RestControllerAdvice
public class PermitSrController {
    private final PermitUseCase permitUseCase;

    @PatchMapping("/permit")
    public Mono<String> permitServiceRequest(PermitUseCase.PermitRequestCommand command, ServerHttpRequest request) {
        log.info("담당자 : " + request.getHeaders().get("userId").get(0));
        log.info("resourceId : " + command.resourceId());
        return Mono.fromCallable(() -> command.registerManager(request.getHeaders().get("userId").get(0)))
                .flatMap(permitUseCase::permitRequest)
                .map(resource -> resource.getResourceName() + "자원 생성 요청이 완료되었습니다.");
    }

    @PatchMapping("/reject")
    public Mono<String> rejectServiceRequest(PermitUseCase.PermitRequestCommand command, ServerHttpRequest request) {
        log.info("담당자 : " + request.getHeaders().get("userId").get(0));
        log.info("resourceId : " + command.resourceId());
        return Mono.fromCallable(() -> command.registerManager(request.getHeaders().get("userId").get(0)))
                .flatMap(permitUseCase::rejectRequest)
                .map(resource -> resource.getResourceName() + "자원 생성 요청이 거절되었습니다.");
    }


    // ------------------------------------ Exception Handling ------------------------------------
    // ------------------------------------ Exception Handling ------------------------------------
    // ------------------------------------ Exception Handling ------------------------------------
    // ------------------------------------ Exception Handling ------------------------------------


    @ExceptionHandler(AlreadyProcessedResourceRequestException.class)
    public Mono<ResponseEntity<String>> handleAlreadyProcessedResourceRequestException(Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getMessage(); // Mono.error 만들어지는 시점에 메세지 만들어지게 되어있음.

        return Mono.just(ResponseEntity.status(status).body(message));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<String>> handleResourceNotFoundException(Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "해당 리소스는 존재하지 않습니다. 잘못된 요청일 가능성이 높습니다.";

        return Mono.just(ResponseEntity.status(status).body(message));
    }



}
