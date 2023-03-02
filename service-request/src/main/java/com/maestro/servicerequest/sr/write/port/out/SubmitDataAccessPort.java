package com.maestro.servicerequest.sr.write.port.out;

import com.maestro.servicerequest.sr.write.domain.Resource;
import reactor.core.publisher.Mono;

public interface SubmitDataAccessPort {
    Mono<Resource> registerRequest(Resource resource);

    Mono<Resource> findById(Integer resourceId);

    Mono<Resource> cancelRequest(Resource updateStatus);
}
