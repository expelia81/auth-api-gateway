package com.maestro.servicerequest.sr.read.admin.adapter.out;

import com.maestro.servicerequest.sr.read.admin.domain.*;
import com.maestro.servicerequest.sr.read.admin.port.out.ReadByAdminDataAccessPort;
import com.maestro.servicerequest.sr.write.domain.ResourceStatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReadByAdminDataAccessAdapter implements ReadByAdminDataAccessPort {

    private final DatabaseClient databaseClient;
    @Override
    public Mono<List<Resource>> findServiceRequestList(Integer workspaceId, String sort) {
        String sql = """
                    SELECT
                           res.resource_id
                         , res.resource_name
                         , rt.resource_type_name
                         , sol.solution_id
                         , sol.solution_name
                         , soltype.solution_type_name
                         , res.resource_requester as requester_id
                         , requester.FIRST_NAME + ' ' + requester.LAST_NAME as requester_name
                         , requester.EMAIL as requester_email
                         , res.resource_content as content
                         , res.resource_manager as manager_id
                         , manager.FIRST_NAME + ' ' + manager.LAST_NAME as manager_name
                         , manager.EMAIL as manager_email
                         , res.resource_status
                         , res.resource_request_time as request_time
                         , res.resource_create_start_time as start_time
                         , res.resource_create_end_time as end_time
                         , res.resource_cidr
                      FROM T_RESOURCE res
                      JOIN T_SOLUTION sol on res.solution_id = sol.solution_id
                      JOIN T_SOLUTION_TYPE soltype on sol.solution_type_code = soltype.solution_type_code
                      JOIN T_RESOURCE_TYPE rt on res.resource_type_code = rt.resource_type_code
                      JOIN USER_ENTITY requester on res.resource_requester = requester.ID
                      LEFT OUTER JOIN USER_ENTITY manager on res.resource_manager = manager.ID
                     WHERE sol.workspace_id = :workspaceId
                     ORDER BY res.resource_request_time""" + " " + sort;
        return databaseClient.sql(sql)
                .bind("workspaceId", workspaceId)
                .map((row, rowMetadata) ->
                        Resource.builder()
                                .resourceId(row.get("resource_id").toString())
                                .resourceName(row.get("resource_name").toString())
                                .resourceTypeCode(ResourceTypeCode.valueOf(row.get("resource_type_name").toString()))
                                .resourceContent(row.get("content") == null ? null : row.get("content").toString())
                                .resourceStatus(ResourceStatusCode.getResourceStatus(Integer.parseInt(row.get("resource_status").toString())))
                                .resourceRequestTime(LocalDateTime.parse(row.get("request_time").toString()))
                                .resourceCreateStartTime(row.get("start_time") == null? null : LocalDateTime.parse(row.get("start_time").toString()))
                                .resourceCreateEndTime(row.get("end_time") == null? null : LocalDateTime.parse(row.get("end_time").toString()))
                                .resourceCidr(row.get("resource_cidr").toString())
                                .solution(Solution.builder()
                                                .solutionId(Integer.parseInt(row.get("solution_id").toString()))
                                                .solutionName(row.get("solution_name").toString())
                                                .solutionTypeCode(SolutionTypeCode.valueOf(row.get("solution_type_name").toString()))
                                                .build())
                                .resourceRequester(row.get("requester_id") == null ? null :
                                                User.builder()
                                                    .userId(row.get("requester_id").toString())
                                                    .userName(row.get("requester_name").toString())
                                                    .userEmail(row.get("requester_email").toString())
                                                    .build())
                                .resourceManager(row.get("manager_id") == null ? null :
                                                User.builder()
                                                    .userId(row.get("manager_id").toString())
                                                    .userName(row.get("manager_name").toString())
                                                    .userEmail(row.get("manager_email").toString())
                                                    .build())
                                .build())
                .all()
                .collectList();
    }
}
