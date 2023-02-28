package com.maestro.servicerequest.sr.permit.application.eventListener;


import com.maestro.servicerequest.sr.permit.port.out.PermitDataAccessPort;
import com.maestro.servicerequest.sr.submit.domain.Resource;
import com.maestro.servicerequest.sr.submit.domain.ResourceStatusCode;
import com.maestro.servicerequest.sr.submit.port.out.SubmitDataAccessPort;
import com.maestro.servicerequest.sr.permit.port.out.PermitExternalApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import com.maestro.servicerequest.sr.submit.domain.ResourceStatusCode;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventListeners {

    public static Integer VALUE = 0;  // 테스트용 변수

    private final PermitExternalApiPort permitExternalApiPort;
    private final PermitDataAccessPort permitDataAccessPort;

    @EventListener
    public void submitRequest(Resource inputResource) {

            Disposable disposable = Mono.fromCallable(() -> permitExternalApiPort.registerFinaleResource(inputResource)).subscribe();

            Flux.interval(Duration.ofSeconds(2))
                .filter(i -> {
                    if (disposable.isDisposed()){
                        log.info("생성 요청되었으므로, 상태 확인 시작됨.");
                        return true;
                    } else {
                        log.info("생성 요청이 완료되지 않았으므로, 상태 확인 시작되지 않음.");
                        return false;
                    }
                })
                .flatMap(i -> {
                    // 피날레에 쏘기.  아직 보내는 기능은 없음.
                    log.info("피날레에 " + i + " 번째 쏨.");
                    /* 요청이 완료 / 혹은 실패일 때,  */
                    return permitExternalApiPort.checkStatus(inputResource);
                    })
                  .filter(resource -> resource.getResourceStatus().equals(ResourceStatusCode.COMPLETED.code) || resource.getResourceStatus().equals(ResourceStatusCode.FAILED.code))
                .flatMap(resource -> { // 피날레 작업 완료 확인시 DB에 상태 업데이트
                    log.info("피날레 작업종료. \n작업결과 : " + (resource.getResourceStatus()==ResourceStatusCode.COMPLETED.code?"성공":"실패"));
                    return permitDataAccessPort.updateStatus(resource);
                }).take(1).subscribe();
    }
}
