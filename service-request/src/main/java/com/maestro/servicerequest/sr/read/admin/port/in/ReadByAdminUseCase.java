package com.maestro.servicerequest.sr.read.admin.port.in;

import com.maestro.servicerequest.sr.read.admin.domain.Resource;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReadByAdminUseCase {

    Mono<List<Resource>> findServiceRequestList(Integer workspaceId, String sort);
}
