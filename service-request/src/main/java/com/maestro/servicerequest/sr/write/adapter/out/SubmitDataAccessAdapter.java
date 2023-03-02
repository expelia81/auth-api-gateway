package com.maestro.servicerequest.sr.write.adapter.out;

import com.maestro.servicerequest.sr.write.adapter.out.repositories.ResourceSrRepository;
import com.maestro.servicerequest.sr.write.domain.Resource;
import com.maestro.servicerequest.sr.write.port.out.SubmitDataAccessPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SubmitDataAccessAdapter implements SubmitDataAccessPort {

    private final ResourceSrRepository resourceSrRepository;
    @Override
    public Mono<Resource> registerRequest(Resource inputResource) {
        return resourceSrRepository.save(inputResource);
    }

    @Override
    public Mono<Resource> findById(Integer resourceId) {
        return resourceSrRepository.findById(resourceId);
    }

    @Override
    public Mono<Resource> cancelRequest(Resource updatedResource) {
        return resourceSrRepository.save(updatedResource);
    }


}
