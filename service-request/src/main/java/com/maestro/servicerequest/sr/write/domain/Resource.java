package com.maestro.servicerequest.sr.write.domain;

import com.maestro.servicerequest.sr.write.port.in.SubmitUseCase;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table("T_RESOURCE")
public class Resource {

    @Id
    private Integer resourceId;  // 리소스 ID

    private String resourceName;  // 리소스 이름

    private Integer resourceTypeCode;  // 리소스 코드

    private Integer resourceStatus;  // 리소스 상태

    private LocalDateTime resourceRequestTime;  // 리소스 요청 시간
    
    private LocalDateTime resourceCreateStartTime;  // 리소스 생성 시작 시간

    private LocalDateTime resourceCreateEndTime;  // 리소스 생성 종료 시간

    private String resourceRequester;  // 리소스 요청자 아이디

    private String resourceManager;  // 리소스 관리자

    private String resourceContent;  // 리소스 요청 내용

    private Integer solutionId;  // 솔루션 ID

    private String resourceCidr;  // VPC 상세 정보. resourceType에 의해 결정됨.


    public static Resource defaultVPCServiceRequestCreator(SubmitUseCase.SRSubmitCommand command){
            /* 리소스 관리자, 종료시간, 리소스 Id 입력안해야함. */
        return Resource.builder()
                .resourceName(command.resourceName())
                .resourceTypeCode(command.resourceTypeCode())
                .resourceStatus(ResourceStatusCode.REQUESTED.getCode())
                .resourceRequestTime(LocalDateTime.now())
                .resourceRequester(command.submitUserId())
                .resourceContent(command.resourceContent())
                .resourceCidr(command.resourceCidr())
                .solutionId(command.solutionId())
                .build();
    }

    /**
     * 리소스 관리자 등록, 리소스 상태 변경
     */
    public Resource registerManagerAndUpdateStatus(String managerId, Integer resourceStatus){
        return Resource.builder()
                .resourceId(this.resourceId)
                .resourceName(this.resourceName)
                .resourceTypeCode(this.resourceTypeCode)
                .resourceStatus(resourceStatus)
                .resourceRequestTime(this.resourceRequestTime)
                .resourceCreateStartTime(LocalDateTime.now())
                .resourceCreateEndTime(this.resourceCreateEndTime)
                .resourceRequester(this.resourceRequester)
                .resourceManager(managerId)
                .resourceContent(this.resourceContent)
                .solutionId(this.solutionId)
                .resourceCidr(this.resourceCidr)
                .build();
    }
    public Resource updateStatus(Integer resourceStatus){
        return Resource.builder()
                .resourceId(this.resourceId)
                .resourceName(this.resourceName)
                .resourceTypeCode(this.resourceTypeCode)
                .resourceStatus(resourceStatus)
                .resourceRequestTime(this.resourceRequestTime)
                .resourceCreateStartTime(this.resourceCreateStartTime)
                .resourceCreateEndTime(this.resourceCreateEndTime)
                .resourceRequester(this.resourceRequester)
                .resourceManager(this.resourceManager)
                .resourceContent(this.resourceContent)
                .solutionId(this.solutionId)
                .resourceCidr(this.resourceCidr)
                .build();
    }
    public Resource endResourceCreation(Integer resourceStatus){
        return Resource.builder()
                .resourceId(this.resourceId)
                .resourceName(this.resourceName)
                .resourceTypeCode(this.resourceTypeCode)
                .resourceStatus(resourceStatus)
                .resourceRequestTime(this.resourceRequestTime)
                .resourceCreateStartTime(this.resourceCreateStartTime)
                .resourceCreateEndTime(LocalDateTime.now())
                .resourceRequester(this.resourceRequester)
                .resourceManager(this.resourceManager)
                .resourceContent(this.resourceContent)
                .solutionId(this.solutionId)
                .resourceCidr(this.resourceCidr)
                .build();
    }

}
