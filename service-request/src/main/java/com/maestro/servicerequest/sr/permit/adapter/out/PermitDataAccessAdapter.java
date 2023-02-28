package com.maestro.servicerequest.sr.permit.adapter.out;

import com.maestro.servicerequest.sr.permit.port.out.PermitDataAccessPort;
import com.maestro.servicerequest.sr.submit.adapter.out.repositories.ResourceSrRepository;
import com.maestro.servicerequest.sr.submit.domain.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class PermitDataAccessAdapter implements PermitDataAccessPort {

    private final ResourceSrRepository resourceSrRepository;


    @Override
    public Mono<Resource> findResourceRequest(Integer resourceId) {
        return resourceSrRepository.findById(resourceId);
    }

    @Override
    public Mono<Resource> updateStatus(Resource resource) {
        return resourceSrRepository.save(resource);
    }
}
