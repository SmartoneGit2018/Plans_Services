/*
 * Created on 2018Äê5ÔÂ16ÈÕ
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 * $Log: Category.java,v $
 */

package com.smartone.planservice;

import java.util.ArrayList;

public class Category {
	String id;
	String[] title;
	ArrayList<PlanService> planList= new  ArrayList<PlanService>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getTitle() {
		return title;
	}
	public void setTitle(String[] title) {
		this.title = title;
	}
	public ArrayList<PlanService> getPlanList() {
		return planList;
	}
	public void setPlanList(ArrayList<PlanService> planList) {
		this.planList = planList;
	}
}
