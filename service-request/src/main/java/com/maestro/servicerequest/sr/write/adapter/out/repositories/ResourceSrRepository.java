package com.maestro.servicerequest.sr.write.adapter.out.repositories;
import com.maestro.servicerequest.sr.write.domain.Resource;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;

@Component
public interface ResourceSrRepository extends R2dbcRepository<Resource, Integer> {
//public interface ResourceSrPermitRepository extends R2dbcRepository<ResourceR2dbcEntity, Integer> {
}
