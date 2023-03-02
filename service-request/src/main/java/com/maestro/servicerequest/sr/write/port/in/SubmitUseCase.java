package com.maestro.servicerequest.sr.write.port.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maestro.servicerequest.sr.write.domain.Resource;
import lombok.Builder;
import reactor.core.publisher.Mono;

/**
 *  SR을 요청하는 유스케이스
 */
public interface SubmitUseCase {

    Mono<Resource> submitRequest(SRSubmitCommand command);

    Mono<Resource> cancelRequest(SRSubmitCommand srSubmitCommand);


    @JsonIgnoreProperties(ignoreUnknown=true)
    @Builder
    record SRSubmitCommand(
//            Integer orgId,  // 조직 soulutionId에 포함되므로, 필요없음.
//            Integer workspaceId,
            Integer solutionId,
            Integer resourceId,
            String submitUserId,  // 사용자 이메일
            Integer resourceTypeCode,    // 리소스 타입.   현재는 1로 통일.  1 = Finale-VPC
            String resourceName,    // 리소스 이름   (현재는 VPC 이름)
            String resourceContent,     // 리소스 상세 설명
            String resourceCidr  // VPC CIDR,
    ){
        public SRSubmitCommand of(String userId){
            return SRSubmitCommand.builder()
                                    .solutionId(this.solutionId)
                                    .resourceId(this.resourceId)
                                    .submitUserId(userId)
                                    .resourceTypeCode(this.resourceTypeCode)
                                    .resourceName(this.resourceName)
                                    .resourceContent(this.resourceContent)
                                    .resourceCidr(this.resourceCidr)
                                    .build();
        }
    }
}
