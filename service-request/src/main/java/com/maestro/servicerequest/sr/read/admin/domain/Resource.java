package com.maestro.servicerequest.sr.read.admin.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maestro.servicerequest.sr.write.domain.ResourceStatusCode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Resource {

    private String resourceId;

    private String resourceName;

    private User resourceRequester;

    private User resourceManager;

    private Solution solution;

    private ResourceStatusCode resourceStatus;  // 리소스 상태

    private ResourceTypeCode resourceTypeCode;  // 리소스 종류

    private String resourceContent;  // 리소스 요청 내용

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime resourceRequestTime;  // 리소스 요청 시간

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime resourceCreateStartTime;  // 리소스 생성 시작 시간

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime resourceCreateEndTime;  // 리소스 생성 종료 시간

    private String resourceCidr;  // VPC 상세 정보. resourceType에 의해 결정됨.
}
