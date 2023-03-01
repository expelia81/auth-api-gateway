package com.maestro.servicerequest.sr.permit.adapter.out;

import com.maestro.servicerequest.sr.exceptions.ResourceNotFoundException;
import com.maestro.servicerequest.sr.permit.port.out.PermitDataAccessPort;
import com.maestro.servicerequest.sr.submit.adapter.out.repositories.ResourceSrRepository;
import com.maestro.servicerequest.sr.submit.domain.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
@Slf4j
public class PermitDataAccessAdapter implements PermitDataAccessPort {

    private final ResourceSrRepository resourceSrRepository;


    @Override
    public Mono<Resource> findResourceRequest(Integer resourceId) {
        return resourceSrRepository.findById(resourceId)
                                   .switchIfEmpty(Mono.error(new ResourceNotFoundException("존재하지 않는 리소스입니다.")));
    }

    @Override
    public Mono<Resource> updateStatus(Resource resource) {
        return resourceSrRepository.save(resource);
    }

    @Override
    public Mono<Resource> findById(Integer resourceId) {
        return resourceSrRepository.findById(resourceId);
    }
}
