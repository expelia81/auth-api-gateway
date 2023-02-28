package com.maestro.servicerequest.sr.permit.port.out;

import com.maestro.servicerequest.sr.submit.domain.Resource;
import reactor.core.publisher.Mono;

public interface PermitExternalApiPort {

    Mono<Resource> checkStatus(Resource inputResource);
}
