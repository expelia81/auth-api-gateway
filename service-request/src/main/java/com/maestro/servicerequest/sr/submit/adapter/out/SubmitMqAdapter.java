package com.maestro.servicerequest.sr.submit.adapter.out;

import com.maestro.servicerequest.sr.submit.domain.Resource;
import com.maestro.servicerequest.sr.submit.exception.MessageQueueException;
import com.maestro.servicerequest.sr.submit.port.out.SubmitMessageQueuePort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SubmitMqAdapter implements SubmitMessageQueuePort {
    @Override
    public Mono<Resource> messageQueuePort(Resource resource) throws MessageQueueException {



        return Mono.fromCallable(() -> {
            return resource;
        });
    }
}
