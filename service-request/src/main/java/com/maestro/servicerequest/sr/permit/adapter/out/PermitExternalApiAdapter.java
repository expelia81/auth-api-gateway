package com.maestro.servicerequest.sr.permit.adapter.out;

import com.maestro.servicerequest.sr.permit.application.eventListener.EventListeners;
import com.maestro.servicerequest.sr.submit.domain.Resource;
import com.maestro.servicerequest.sr.permit.port.out.PermitExternalApiPort;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Random;

@Component
@NoArgsConstructor
@Slf4j
public class PermitExternalApiAdapter implements PermitExternalApiPort {
    private final Integer MAX_VALUE = new Random().nextInt(10);
    @Override
    public Mono<Resource> checkStatus(Resource inputResource) {
        return Mono.fromCallable(() -> {
            /**
             * 피날레에 쏘기
             * 피날레에 쐈을때  진행중이 아닐 때까지 쏘기
             *
             * 진행중이면 faluse
             * 진행중이 아니면 true
             */


            if (EventListeners.VALUE < MAX_VALUE) {
                EventListeners.VALUE++;
                return inputResource;
            } else if (EventListeners.VALUE == MAX_VALUE) { // 자원 생성이 완료 되었을 때
                Resource resource = inputResource.resourceCreateComplete();
                return resource;
            } else { // 피날레가 실패했을 때
                Resource resource = inputResource.resourceCreateFail();
                return resource;
            }

        });
    }
}
