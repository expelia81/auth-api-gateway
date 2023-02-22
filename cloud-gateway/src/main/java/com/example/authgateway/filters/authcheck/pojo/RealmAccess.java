package com.example.authgateway.filters.authcheck.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RealmAccess {
    private Set<String> roles = new HashSet<>();
}
