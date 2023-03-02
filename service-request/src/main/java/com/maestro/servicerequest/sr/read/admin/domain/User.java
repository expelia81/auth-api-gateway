package com.maestro.servicerequest.sr.read.admin.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    String userId;
    String userName;
    String userEmail;
}
