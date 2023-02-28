package com.maestro.servicerequest.sr.permit.port.out;

import com.maestro.servicerequest.sr.submit.domain.Resource;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public interface PermitExternalApiPort {

    Mono<Resource> checkStatus(Resource inputResource);

    Mono<Resource> registerFinaleResource(Resource inputResource);
}
