package com.maestro.servicerequest.sr.submit.adapter.out;

import com.maestro.servicerequest.sr.submit.adapter.out.repositories.ResourceSrRepository;
import com.maestro.servicerequest.sr.submit.domain.Resource;
import com.maestro.servicerequest.sr.submit.port.in.SubmitUseCase;
import com.maestro.servicerequest.sr.submit.port.out.SubmitDataAccessPort;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SubmitDataAccessAdapter implements SubmitDataAccessPort {

    private final DatabaseClient databaseClient;
    private final ResourceSrRepository resourceSrRepository;
    @Override
    public Mono<Resource> registerRequest(Resource inputResource) {
        return resourceSrRepository.save(inputResource);
    }


    @Override
    public Mono<Void> rollbackForRegisterRequest(SubmitUseCase.SRSubmitCommand command) {
        return Mono.empty();
    }

}
