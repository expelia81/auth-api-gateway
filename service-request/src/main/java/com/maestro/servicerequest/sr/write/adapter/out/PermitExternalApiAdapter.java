package com.maestro.servicerequest.sr.write.adapter.out;

import com.maestro.servicerequest.sr.exceptions.ServerNotRespondedException;
import com.maestro.servicerequest.sr.write.domain.Resource;
import com.maestro.servicerequest.sr.write.port.out.PermitExternalApiPort;
import com.maestro.servicerequest.sr.write.application.eventListener.ResourceCreateListener;
import com.maestro.servicerequest.sr.write.domain.ResourceStatusCode;
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
             *
             * 서버 미응답시
             * @ServerNotRespondedException 발생시키면 된다.
             *
             * 성공실패 양쪽 다 테스트하기 위한 로직일 뿐이므로, 신경쓰지말 것
             */
            log.info("value : " + ResourceCreateListener.VALUE);
            log.info("target value : " + MAX_VALUE);

            if (MAX_VALUE<2){
                log.info("피날레 실패");
                return inputResource.updateStatus(ResourceStatusCode.FAILED.getCode());
            }

            if (ResourceCreateListener.VALUE < MAX_VALUE) {
                ResourceCreateListener.VALUE++;
                return inputResource;
            } else if (ResourceCreateListener.VALUE.equals(MAX_VALUE)) { // 자원 생성이 완료 되었을 때
                return inputResource.endResourceCreation(ResourceStatusCode.COMPLETED.getCode());
            } else { // 피날레가 실패했을 때
                return inputResource.endResourceCreation(ResourceStatusCode.FAILED.getCode());
            }

        });
    }

    /**
     * 피날레에 VPC 요청 보내는 메소드.
     * 서버 미응답시
     *  -- @ServerNotRespondedException 발생
     */
    @Override
    public Mono<Resource> registerFinaleResource(Resource inputResource) {
        return Mono.just(inputResource)
                .onErrorResume(e -> {
                    if (e instanceof ServerNotRespondedException){
                        log.error("피날레 서버 미응답");
                        return Mono.error(e);
                    }
                    return Mono.error(e);
                });
    }
}
