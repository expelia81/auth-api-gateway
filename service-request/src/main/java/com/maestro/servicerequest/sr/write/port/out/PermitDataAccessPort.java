package com.maestro.servicerequest.sr.write.port.out;

import com.maestro.servicerequest.sr.write.domain.Resource;
import reactor.core.publisher.Mono;

public interface PermitDataAccessPort {
    Mono<Resource> findResourceRequest(Integer resourceId);

    Mono<Resource> updateStatus(Resource inputResource);

    Mono<Resource> findById(Integer resourceId);
}
