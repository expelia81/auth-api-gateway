package com.maestro.servicerequest.sr.permit.application.eventListener;


import com.maestro.servicerequest.sr.exceptions.ServerNotRespondedException;
import com.maestro.servicerequest.sr.permit.port.out.PermitDataAccessPort;
import com.maestro.servicerequest.sr.submit.domain.Resource;
import com.maestro.servicerequest.sr.submit.domain.ResourceStatusCode;
import com.maestro.servicerequest.sr.permit.port.out.PermitExternalApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
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
    public void resourceCreate(Resource inputResource) {


        /* 피날레에 요청을 보낸다. */
        Disposable disposable = permitExternalApiPort.registerFinaleResource(inputResource).delayElement(Duration.ofSeconds(3)).subscribe();

        Flux.range(1, 1000)
            .delayElements(Duration.ofSeconds(2))
            .filter(i -> {
                if (disposable.isDisposed()){ //피날레에 보낸 요청이 종료되었음이 확인 되는 순간부터 상태 확인을 시작한다.
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
                return permitExternalApiPort.checkStatus(inputResource); // 이 포트에서 피날레에서 상태를 확인하는데,
            })
            /* 요청이 완료 / 혹은 실패일 때,  */
            .filter(resource -> resource.getResourceStatus().equals(ResourceStatusCode.COMPLETED.code) || resource.getResourceStatus().equals(ResourceStatusCode.FAILED.code))
            .flatMap(resource -> { // 피날레 작업 완료 확인시 DB에 상태 업데이트
                log.info("피날레 작업종료. \n작업결과 : " + (resource.getResourceStatus()==ResourceStatusCode.COMPLETED.code?"성공":"실패"));
                return permitDataAccessPort.updateStatus(resource.updateStatus(resource.getResourceStatus()));
            }).onErrorResume(e -> {
                if (e instanceof ServerNotRespondedException){
                    log.error("피날레 작업 실패. 자원 상태를 초기로 되돌립니다. \n실패원인 : " + e.getMessage());
                    return permitDataAccessPort.updateStatus(inputResource.updateStatus(ResourceStatusCode.NOT_RESPONDED.code));
                } else {
                    log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
                    return Mono.error(e);
                }
            })
            .take(1)  // -> take()는 업스트림을 cancel하기 때문에, Flux.range에 남은 다른 요소들을 무시하고 자원을 회수할 수 있게 한다.
            .subscribe();
    }
}
