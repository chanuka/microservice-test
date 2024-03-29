package com.cba.core.auth.util;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component // this is for storing user data through out the request scope
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class UserBeanUtil {

    private String username;
    private String remoteAdr;

}
