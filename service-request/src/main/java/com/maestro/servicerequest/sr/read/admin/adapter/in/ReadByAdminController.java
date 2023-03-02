package com.maestro.servicerequest.sr.read.admin.adapter.in;

import com.maestro.servicerequest.sr.read.admin.adapter.in.response.ResourceResponse;
import com.maestro.servicerequest.sr.read.admin.port.in.ReadByAdminUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/sr/admin")
public class ReadByAdminController {

    private final ReadByAdminUseCase readByAdminUseCase;


    @GetMapping("/list")
    public Mono<ResourceResponse> findServiceRequestList(Integer workspaceId,
                                                         @RequestParam(defaultValue = "1")Integer page,
                                                         @RequestParam(defaultValue = "5")Integer size,
                                                         @RequestParam(defaultValue = "DESC")String sort) {
        return readByAdminUseCase.findServiceRequestList(workspaceId, sort)
                .map(ResourceResponse::getResponseOfResourceListByWorkspaceId)
                .map(response -> response.pagination(page, size));
    }

//    private ResourceResponse resourceListToResponse(List<Resource> resources) {
//        ResourceResponse response = ResourceResponse.registerResources(resources);
//        resources.stream()
//                .forEach(resource -> {
//                    switch (resource.getResourceStatus()) {
//                        case REQUESTED:
//                            response.setWaitingCount(response.getWaitingCount() + 1);
//                            break;
//                        case IN_PROGRESS:
//                            response.setProcessingCount(response.getProcessingCount() + 1);
//                            break;
//                        case COMPLETED:
//                            response.setCompletedCount(response.getCompletedCount() + 1);
//                            break;
//                        case FAILED, NOT_RESPONDED:
//                            response.setFailedCount(response.getFailedCount() + 1);
//                            break;
//                        case REJECTED:
//                            response.setRejectedCount(response.getRejectedCount() + 1);
//                            break;
//                        case CANCELED:
//                            response.setCanceledCount(response.getCanceledCount() + 1);
//                            break;
//                    }
//                });
//        return response;
//    }

}
