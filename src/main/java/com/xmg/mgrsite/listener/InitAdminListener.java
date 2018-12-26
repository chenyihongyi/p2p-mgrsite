/**
 * 
 */
package com.xmg.mgrsite.listener;

import com.xmg.p2p.base.service.ILogininfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description: 
 * @Author: chenyihong
 * @Date: 2018年12月26日
 */
@Component
public class InitAdminListener implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private ILogininfoService logininfoService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
	logininfoService.initAdmin();

		
	}

}
