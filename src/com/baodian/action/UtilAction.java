package com.baodian.action;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.baodian.service.role.SecuManager;
import com.baodian.service.role.impl.SecuManagerImpl;
import com.baodian.service.user.UserManager;
import com.baodian.service.util.InitDataManager;
import com.baodian.service.util.StaticDataManager;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("util")
@Scope("prototype")//必须注解为多态
public class UtilAction extends ActionSupport {
//依赖注入
	@Resource(name="secuManager")
	private SecuManager secuManager;
	@Resource(name="staticData")
	private StaticDataManager sdata;
	@Resource(name="initData")
	private InitDataManager idata;
	@Resource(name="userManager")
	private UserManager userManager;
//页面属性
	private String json;
//访问方法
	@SuppressWarnings("unchecked")
	public String index() {
		JSONObject result = new JSONObject();
		result.put("fileSizeLimit", InitDataManager.upload[6]);
		json = result.toJSONString();
		return "index";
	}
	public String admin() {
		json = SecuManagerImpl.findMenu().toString();
		return "admin";
	}
	public String lguser() {
		json = userManager.findlgUser();
		return SUCCESS;
	}
//刷新内存
	//
	public String rfall_rf() {
		this.secuManager.refreshAll_RA_();
		this.secuManager.refreshMenu();
		this.sdata.reload();
		this.idata.reload();
		json = "{\"status\":0}";
		return "json";
	}
	//权限角色
	public String refresh_rf() {
		this.secuManager.refreshAll_RA_();
		json = "{\"status\":0}";
		return "json";
	}
	//权限菜单
	public String rfmenu_rf() {
		this.secuManager.refreshMenu();
		json = "{\"status\":0}";
		return "json";
	}
	//值班部门
	public String rfduty_rf() {
		this.sdata.reload();
		//sdata.output();
		json = "{\"status\":0}";
		return "json";
	}
	//配置文件
	public String rfdata_rf() {
		this.idata.reload();
		//idata.output();
		json = "{\"status\":0}";
		return "json";
	}
	
//set get
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public org.springframework.security.core.userdetails.User getUser() {
		return SecuManagerImpl.currentUser();
	}
}
