package com.maestro.servicerequest.sr.write.port.out;

import com.maestro.servicerequest.sr.write.domain.Resource;
import reactor.core.publisher.Mono;

public interface PermitExternalApiPort {

    Mono<Resource> checkStatus(Resource inputResource);

    Mono<Resource> registerFinaleResource(Resource inputResource);
}
