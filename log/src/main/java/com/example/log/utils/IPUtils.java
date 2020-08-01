package com.example.log.utils;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

public class IPUtils {
    private static InetUtils.HostInfo hostInfo;
    static {
            Environment environment = new StandardEnvironment();
            InetUtilsProperties target = new InetUtilsProperties();
            ConfigurationPropertySources.attach(environment);
            Binder.get(environment).bind(InetUtilsProperties.PREFIX,
                    Bindable.ofInstance(target));
            try (InetUtils utils = new InetUtils(target)) {
                hostInfo = utils.findFirstNonLoopbackHostInfo();
            }
    }

    public static String getIpAddress(){
        return hostInfo.getIpAddress();
    }
}
