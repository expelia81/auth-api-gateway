package com.maestro.servicerequest.sr.write.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Getter
public enum ResourceStatusCode {
    REQUESTED(1),
    IN_PROGRESS(2),
    COMPLETED(3),
    FAILED(4),
    REJECTED(5),

    NOT_RESPONDED(6),

    CANCELED(7);


    private final int code;


    ResourceStatusCode(int i) {
        this.code = i;
    }

    private final static Map<Integer, String> RESOURCE_STATUS_MAP = new HashMap<>();

    static {
        for (ResourceStatusCode status : ResourceStatusCode.values()) {
            RESOURCE_STATUS_MAP.put(status.code, status.name());
        }
    }

    /**
     * DB에서 Status Code를 받아서, Enum으로 변환한다.
     */
    public static ResourceStatusCode getResourceStatus(int resourceStatusCode) {
        return ResourceStatusCode.valueOf(RESOURCE_STATUS_MAP.get(resourceStatusCode));
    }
}
