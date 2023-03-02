package com.maestro.servicerequest.sr.read.admin.port.out;

import com.maestro.servicerequest.sr.read.admin.domain.Resource;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReadByAdminDataAccessPort {
    Mono<List<Resource>> findServiceRequestList(Integer workspaceId, String sort);
}
