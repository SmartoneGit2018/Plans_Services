/*
 * Created on 2018Äê5ÔÂ16ÈÕ
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 * $Log: Plan.java,v $
 */

package com.smartone.planservice;

public class PlanService {
	String cid;
	String planid;
	String[] title;
	int lang;
	String[] descShort;
	String[] pathTandc;
	String[] descLong;
	String[] fee;
	String icon;
	String unsub;
	String reset;
	String resetMsg;
	String showInMenu;
	String [] detailTab;
	boolean isSub;
	boolean showReset=false;
	boolean showSpeed=false;
	public boolean isSub() {
		return isSub;
	}
	public void setSub(boolean isSub) {
		this.isSub = isSub;
	}
	public String getPlanid() {
		return planid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	public String[] getTitle() {
		return title;
	}
	public void setTitle(String[] title) {
		this.title = title;
	}
	public String[] getDescShort() {
		return descShort;
	}
	public void setDescShort(String[] descShort) {
		this.descShort = descShort;
	}
	public String[] getPathTandc() {
		return pathTandc;
	}
	public void setPathTandc(String[] pathTandc) {
		this.pathTandc = pathTandc;
	}
	public String[] getDescLong() {
		return descLong;
	}
	public void setDescLong(String[] descLong) {
		this.descLong = descLong;
	}
	public String[] getFee() {
		return fee;
	}
	public void setFee(String[] fee) {
		this.fee = fee;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUnsub() {
		return unsub;
	}
	public void setUnsub(String unsub) {
		this.unsub = unsub;
	}
	public String getReset() {
		return reset;
	}
	public void setReset(String reset) {
		this.reset = reset;
	}
	public String getResetMsg() {
		return resetMsg;
	}
	public void setResetMsg(String resetMsg) {
		this.resetMsg = resetMsg;
	}
	public String getShowInMenu() {
		return showInMenu;
	}
	public void setShowInMenu(String showInMenu) {
		this.showInMenu = showInMenu;
	}
	public int getLang() {
		return lang;
	}
	public void setLang(int lang) {
		this.lang = lang;
	}
	public boolean isShowReset() {
		return showReset;
	}
	public void setShowReset(boolean showReset) {
		this.showReset = showReset;
	}
	public boolean isShowSpeed() {
		return showSpeed;
	}
	public void setShowSpeed(boolean showSpeed) {
		this.showSpeed = showSpeed;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String[] getDetailTab() {
		return detailTab;
	}
	public void setDetailTab(String[] detailTab) {
		this.detailTab = detailTab;
	}
	
}
