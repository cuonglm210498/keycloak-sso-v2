package com.lecuong.keycloakssov2.modal.response;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Data
@Component
@RequestScope
public class Payload {

    private String exp;
    private String iat;
    private String jti;
    private String iss;
    private String aud;
    private String sub;
    private String typ;
    private String azp;
    private String session_state;
    private String acr;
    private RealmAccess realm_access;
    private ResourceAccess resource_access;
    private String scope;
    private String sid;
    private String email_verified;
    private String name;
    private String preferred_username;
    private String given_name;
    private String family_name;
    private String email;

    @Data
    public class RealmAccess {
        private List<String> roles;
    }

    @Data
    public class ResourceAccess {
        private Account account;

        @Data
        public class Account {
            private List<String> roles;
        }
    }
}
