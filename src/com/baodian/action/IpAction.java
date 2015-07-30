package com.baodian.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.baodian.model.file.Ip;
import com.baodian.service.ip.IpManager;
import com.baodian.util.page.IpPage;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("ip")
@Scope("prototype")//必须注解为多态
public class IpAction extends ActionSupport {
	private String json;
	private Ip ip;
	private IpPage page;
	@Resource(name = "ipManager")
	private IpManager ipManager;
	
//c
	public String addIp_js() {
		json = ipManager.addIp(ip);
		return "json";
	}
//r
	//IP管理
	public String list() {
		return SUCCESS;
	}
	public String list_js() {
		if(page == null) {
			page = new IpPage();
		}
		json = ipManager.findDownLoadIps(page);
		return "json";
	}
	public String ipList_js() {
		json = ipManager.findIpByIds(json);
		return "json";
	}
//u
	public String changeIp_js() {
		json = ipManager.changeIp(ip);
		return "json";
	}
	public String changeIpStatus_js() {
		json = ipManager.changeIpStatus(json);
		return "json";
	}
	public String changeIpType_js() {
		json = ipManager.changeIpType(json);
		return "json";
	}
//d
	public String removeIp_js() {
		json = ipManager.remove(json);
		return "json";
	}
//set get
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public IpPage getPage() {
		return page;
	}
	public void setPage(IpPage page) {
		this.page = page;
	}
	public Ip getIp() {
		return ip;
	}
	public void setIp(Ip ip) {
		this.ip = ip;
	}
}
