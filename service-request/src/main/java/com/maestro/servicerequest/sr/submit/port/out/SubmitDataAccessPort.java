package com.maestro.servicerequest.sr.submit.port.out;

import com.maestro.servicerequest.sr.submit.domain.Resource;
import com.maestro.servicerequest.sr.submit.port.in.SubmitUseCase;
import reactor.core.publisher.Mono;

public interface SubmitDataAccessPort {
    Mono<Resource> registerRequest(Resource resource);

    Mono<Void> rollbackForRegisterRequest(SubmitUseCase.SRSubmitCommand command);

}
