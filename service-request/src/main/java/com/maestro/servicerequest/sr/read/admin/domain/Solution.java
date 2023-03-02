package com.maestro.servicerequest.sr.read.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Solution {

    private int solutionId;
    private String solutionName;
    @JsonIgnore
    private int workspaceId;
    private SolutionTypeCode solutionTypeCode;

//
//    public static Solution of(int solutionId, String solutionName, int workspaceId, SolutionTypeCode solutionTypeCode) {
//        return Solution.builder()
//                .solutionId(solutionId)
//                .solutionName(solutionName)
//                .workspaceId(workspaceId)
//                .solutionTypeCode(solutionTypeCode)
//                .build();
//    }
}