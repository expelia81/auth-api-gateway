package com.maestro.servicerequest.sr.permit.port.in;

import com.maestro.servicerequest.sr.submit.domain.Resource;
import reactor.core.publisher.Mono;

public interface PermitUseCase {

    Mono<Resource> permitRequest(PermitRequestCommand command);

    Mono<Resource> rejectRequest(PermitRequestCommand managedCommand);

    record PermitRequestCommand(Integer resourceId, String managerId) {
        public PermitRequestCommand registerManager(String managerId) {
            return new PermitRequestCommand(this.resourceId, managerId);
        }
    }
}
