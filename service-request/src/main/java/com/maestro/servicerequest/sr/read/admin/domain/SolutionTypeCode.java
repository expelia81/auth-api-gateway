package com.maestro.servicerequest.sr.read.admin.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum SolutionTypeCode {
    Finale(1);


    private final int code;

    SolutionTypeCode(int code) {
        this.code = code;
    }

    private static final Map<Integer, String> map = new HashMap<>();

    static {
        for (SolutionTypeCode solutionTypeCode : SolutionTypeCode.values()) {
            map.put(solutionTypeCode.code, solutionTypeCode.name());
        }
    }

    public static SolutionTypeCode getSolutionType(int code) {
        return SolutionTypeCode.valueOf(map.get(code));
    }
}
