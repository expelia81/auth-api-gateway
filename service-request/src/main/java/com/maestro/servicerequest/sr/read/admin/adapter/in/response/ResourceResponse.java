package com.maestro.servicerequest.sr.read.admin.adapter.in.response;

import com.maestro.servicerequest.sr.read.admin.domain.Resource;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Slf4j
public class ResourceResponse {
    // step 1
    private Integer totalCount = 0;  // 1차 승인, 2차 승인 대신 들어감

    // step 2
    private Integer waitingCount = 0; // 대기중인 수 == requested

    private Integer processingCount = 0; // 진행중인 수  == InProgress

    private Integer failedCount = 0; // 실패한 수
    
    // step 3
    private Integer completedCount = 0;

    private Integer rejectedCount = 0;

    private Integer canceledCount = 0;

    

    private List<Resource> resources = new ArrayList<>();

    public static ResourceResponse getResponseOfResourceListByWorkspaceId(List<Resource> resources) {
        ResourceResponse response = ResourceResponse.builder()
                .totalCount(resources.size())
                .waitingCount(0)
                .processingCount(0)
                .failedCount(0)
                .completedCount(0)
                .rejectedCount(0)
                .canceledCount(0)
                .resources(resources)
                .build();
        return response.counting();
    }

    public ResourceResponse pagination(Integer page, Integer size) {
        page--;
        int totalPage = totalCount / size;
        if (totalPage < page) throw new IllegalArgumentException("page is out of range");

        return ResourceResponse.builder()
                .totalCount(this.totalCount)
                .waitingCount(this.waitingCount)
                .processingCount(this.processingCount)
                .failedCount(this.failedCount)
                .completedCount(this.completedCount)
                .rejectedCount(this.rejectedCount)
                .canceledCount(this.canceledCount)
                .resources(this.resources.subList(page * size, Math.min(resources.size(), (page + 1) * size)))
                .build();
    }

    private ResourceResponse counting() {
        resources.forEach(resource -> {
            switch (resource.getResourceStatus()) {
                case REQUESTED -> this.waitingCount++;
                case IN_PROGRESS -> this.processingCount++;
                case COMPLETED -> this.completedCount++;
                case FAILED, NOT_RESPONDED -> this.failedCount++;
                case REJECTED -> this.rejectedCount++;
                case CANCELED -> this.canceledCount++;
            }
                });
        return this;
    }
}
