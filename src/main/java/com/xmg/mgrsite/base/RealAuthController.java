package com.xmg.mgrsite.base;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.base.util.UploadUtil;
import com.xmg.p2p.base.util.UserContext;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 实名认证审核相关
 * @Author Elvis Chen
 * @Date 2019/1/7 14:56
 * @Version 1.0
 **/
@Controller
public class RealAuthController {

    @Autowired
    private IRealAuthService realAuthService;
    
    @Autowired
    private IUserinfoService userinfoService;
    
	@Autowired
	private ServletContext servletContext ;

    @RequireLogin
	@RequestMapping("realAuth")
	public String realAuth(Model model) {
		// 如果用户没有实名认证 并且realAuthId为空 跳转到realAuth填写
		Userinfo current = this.userinfoService.get(UserContext.getCurrent()
				.getId());
		if (!current.getIsRealAuth() && current.getRealAuthId() == null) {
			return "realAuth";
		} else {
			if (!current.getIsRealAuth()) { 
				model.addAttribute("auditing", true);
			} else {
				model.addAttribute("realAuth",this.realAuthService.get(current.getRealAuthId()));
			}
			return "realAuth_result";
		}
	}
    
	@RequestMapping("realAuthUpload")
	@ResponseBody
	public String upload(MultipartFile file){
		String basePath = servletContext.getRealPath("/upload");
		String fileName = UploadUtil.upload(file, this.servletContext.getRealPath(basePath));
		return "/upload/"+ fileName; 
	}
    
	@RequireLogin
	@RequestMapping("realAuth_save")
	@ResponseBody
	public JSONResult realAuthSave(RealAuth realAuth) {
		this.realAuthService.apply(realAuth);
		return new JSONResult();
	}
    
}
