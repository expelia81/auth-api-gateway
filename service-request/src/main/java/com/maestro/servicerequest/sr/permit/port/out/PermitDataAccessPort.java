package com.maestro.servicerequest.sr.permit.port.out;

import com.maestro.servicerequest.sr.submit.domain.Resource;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public interface PermitDataAccessPort {
    Mono<Resource> findResourceRequest(Integer resourceId);

    Mono<Resource> updateStatus(Resource inputResource);

    Mono<Resource> findById(Integer resourceId);
}
