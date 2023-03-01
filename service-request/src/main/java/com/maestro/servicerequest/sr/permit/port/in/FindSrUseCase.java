package com.maestro.servicerequest.sr.permit.port.in;

import com.maestro.servicerequest.sr.submit.domain.Resource;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FindSrUseCase {

    /**
     * 2. orgId를 입력받고 해당 조직 전체의 자원을 보여주는 api
     * 3. 특정 workId를 입력받고 해당 워크스페이스 전체의 자원을 보여주는 api
     * 4. 특정 resourceId를 입력받고 해당 자원의 상세 정보를 보여주는 api
     */

    /**
     *  구현
     *  - 기본적으로, Application Layer에서는 List를 리턴한다.
     *  - 리턴받은 List는 Controller에서 Response용 객체로 래핑된다.
     *  - Response용 객체로 래핑되는 과정에서, Stream으로 특정 상태인 값들만 추려내고, 페이징 처리까지 완료하여 리턴한다.
     *  - 만약에, DB단에서 쿼리 하나로 카운트가 싹 다 해결이 된다면, 페이징 처리 자체도 DB단에서 실행한다.
     *  - 그런데, 우리는 키클락 DB를 같이 사용하고있기 때문에, DB에 부담을 주지 않기 위해서는 이게 맞다.
     */
    Mono<List<Resource>> findServiceRequestList(Integer workspaceId);
}
