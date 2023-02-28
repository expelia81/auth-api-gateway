package com.example.authgateway.filters.authcheck.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Token {
//    @JsonProperty("realm_access")
//    private RealmAccess realmAccess;
//
//    @JsonProperty("resource_access")
//    private ResourceAccess resourceAccess;

    @JsonProperty("preferred_username")
    private String userName;

    @JsonProperty("sub")
    private String userId;

}