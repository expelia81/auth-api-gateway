package com.maestro.servicerequest.sr.submit.port.out;

import com.maestro.servicerequest.sr.submit.domain.Resource;
import com.maestro.servicerequest.sr.submit.exception.MessageQueueException;
import reactor.core.publisher.Mono;

public interface SubmitMessageQueuePort {
    Mono<Resource> messageQueuePort(Resource resource) throws MessageQueueException;
}
