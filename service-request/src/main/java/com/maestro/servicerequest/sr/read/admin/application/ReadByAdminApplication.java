package com.maestro.servicerequest.sr.read.admin.application;

import com.maestro.servicerequest.sr.read.admin.domain.Resource;
import com.maestro.servicerequest.sr.read.admin.port.in.ReadByAdminUseCase;
import com.maestro.servicerequest.sr.read.admin.port.out.ReadByAdminDataAccessPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReadByAdminApplication implements ReadByAdminUseCase {

    private final ReadByAdminDataAccessPort readByAdminDataAccessPort;
    @Override
    public Mono<List<Resource>> findServiceRequestList(Integer workspaceId, String sort) {


        return readByAdminDataAccessPort.findServiceRequestList(workspaceId, sort);
    }
}
