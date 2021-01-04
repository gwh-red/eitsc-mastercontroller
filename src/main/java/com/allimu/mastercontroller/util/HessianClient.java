package com.allimu.mastercontroller.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.allimu.mastercontroller.remote.service.InstructionCodeRemoteService;

@Configuration
public class HessianClient {
	
	/**
	 * 注入HessianProxyFactoryBean
	 * @author ymsn
	 * @date 2020-10-17
	 * @return
	 */
	@Bean
    public HessianProxyFactoryBean hessianProxyFactoryBean() {
        HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
        factory.setServiceUrl(CommonUtil.remoteServiceUrl);
        factory.setServiceInterface(InstructionCodeRemoteService.class);
        factory.setOverloadEnabled(false);
        return factory;
    }

}
