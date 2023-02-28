package com.maestro.servicerequest.sr.submit.adapter.out.repositories;
import com.maestro.servicerequest.sr.submit.domain.Resource;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface ResourceSrRepository extends R2dbcRepository<Resource, Integer> {
//public interface ResourceSrRepository extends R2dbcRepository<ResourceR2dbcEntity, Integer> {
}
