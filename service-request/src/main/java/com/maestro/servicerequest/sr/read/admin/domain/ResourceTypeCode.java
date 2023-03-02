package com.maestro.servicerequest.sr.read.admin.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ResourceTypeCode {
    Finale_VPC(1);


    private final int code;

    ResourceTypeCode(int code) {
        this.code = code;
    }

    private static final Map<Integer, String> map = new HashMap<>();

    static {
        for (ResourceTypeCode resourceTypeCode : ResourceTypeCode.values()) {
            map.put(resourceTypeCode.code, resourceTypeCode.name());
        }
    }

    public static ResourceTypeCode getResourceType(int code) {
        return ResourceTypeCode.valueOf(map.get(code));
    }
}
