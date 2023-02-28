package com.maestro.servicerequest.sr.permit.application;

import com.maestro.servicerequest.sr.exceptions.NotRequestedService;
import com.maestro.servicerequest.sr.permit.port.in.PermitUseCase;
import com.maestro.servicerequest.sr.permit.port.out.PermitDataAccessPort;
import com.maestro.servicerequest.sr.submit.domain.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class PermitApplication implements PermitUseCase {

    private final ApplicationEventPublisher eventPublisher;

    private final PermitDataAccessPort permitDataAccessPort;

    @Override
    public Mono<Resource> permitRequest(PermitRequestCommand command) {

        return permitDataAccessPort.findResourceRequest(command.resourceId())
                .flatMap(resource -> {
                    if (resource.getResourceStatus()==1){
                        Resource managedResource = resource.registerManager(command.managerId());
                        log.info("관리자 정보 : " + managedResource.getResourceManager());
                        log.info("상태 코드 : " + managedResource.getResourceStatus());
                        eventPublisher.publishEvent(managedResource); // 피날레에 요청 송신 및 요청 상태 확인 후 진행상태 업데이트.
                        return permitDataAccessPort.updateStatus(managedResource); // DB에 진행상태 업데이트. (진행중)
                    } else { // 진행중이 아닐 때
                        return Mono.error(new NotRequestedService("대기중인 요청이 아닙니다."));
                    }
                });
    }
}
