package com.maestro.servicerequest.sr.write.application;

import com.maestro.servicerequest.sr.write.domain.Resource;
import com.maestro.servicerequest.sr.write.domain.ResourceStatusCode;
import com.maestro.servicerequest.sr.write.port.in.SubmitUseCase;
import com.maestro.servicerequest.sr.write.port.out.SubmitDataAccessPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class SubmitApplication implements SubmitUseCase {

    private final SubmitDataAccessPort dataAccessPort;      // DB 처리 위한 포트


    @Override
    public Mono<Resource> submitRequest(SRSubmitCommand command) {
        /*
         * 1. SR 요청
         * 2. DB에 SR 저장
         */
        return Mono.fromCallable(() -> Resource.defaultVPCServiceRequestCreator(command))
                .flatMap(dataAccessPort::registerRequest)
                .onErrorResume(e -> {
                    log.info("에러명 : " + e.getClass().getSimpleName());
                    log.info("에러 메세지 : " + e.getMessage());
                    /* 예외 타입 정해질 경우, 핸들링. 일단은 예외 파악용으로 사용. */
                    return Mono.error(e);
                });
    }

    @Override
    public Mono<Resource> cancelRequest(SRSubmitCommand srSubmitCommand) {
        return dataAccessPort.findById(srSubmitCommand.resourceId())
                .flatMap(resource -> {
                    if (resource.getResourceStatus()== ResourceStatusCode.REQUESTED.getCode()){
                        return dataAccessPort.cancelRequest(resource.updateStatus(ResourceStatusCode.CANCELED.getCode()));
                    } else {
                        return Mono.error(new RuntimeException("이미 처리된 요청입니다."));
                    }
                })
                .onErrorResume(e -> {
                    log.info("에러명 : " + e.getClass().getSimpleName());
                    log.info("에러 메세지 : " + e.getMessage());
                    /* 예외 타입 정해질 경우, 핸들링. 일단은 예외 파악용으로 사용. */
                    return Mono.error(e);
                });
    }

}
