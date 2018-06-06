package com.smartone.planservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



/*
 * Created on 2018Äê5ÔÂ16ÈÕ
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 * $Log: XMLReader.java,v $
 */

public class XMLReader {
	Logger logger = Logger.getLogger("LOG");
	String[] langType= {"eng","bss","sss"};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XMLReader xmlr=new XMLReader();
		try {
			ArrayList<Category> cList=xmlr.getXML();
			//logger 
			xmlr.outprintObj(cList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Category> getXML() throws Exception {
		String propPath=getClass().getClassLoader().getResource("")+"planservice.xml";
		logger.info(propPath);
		if (propPath.indexOf("file:")!=-1){
			propPath=propPath.substring(propPath.indexOf("file:/")+"file:/".length());
		}
		logger.info(propPath);
		InputStream inputStream= new FileInputStream(new File(propPath));
		BufferedReader bf= new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
		String inputLine;
		StringBuffer respon = new StringBuffer();

		while ((inputLine = bf.readLine()) != null) {
			respon.append(inputLine);
		}
		String result= respon.toString();
		bf.close();
		logger.info(result);
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
		        is.setCharacterStream(new StringReader(result));
		        doc = db.parse(is); 
			} catch (Exception e) {
                logger.error(e);
			} 
	        NodeList nodesList = doc.getElementsByTagName("Category");
	        ArrayList<Category> cList=new ArrayList<Category>();		
	        if(nodesList!=null&&nodesList.getLength()>0){
	        	for(int i=0;i<nodesList.getLength();i++){ 
	        		logger.info("###########################################");
					Element entry=(Element)nodesList.item(i); 
					String id=entry.getElementsByTagName("c_id").item(0).getFirstChild().getNodeValue();
					String[] title= new String[3];
					for (int l=0;l<langType.length;l++) {
	        			String lang=langType[l];
	        			String titleLang="";
	        			if (entry.getElementsByTagName("c_title_"+lang).item(0).getFirstChild()!=null) 		
	        				titleLang=entry.getElementsByTagName("c_title_"+lang).item(0).getFirstChild().getNodeValue();
	        			title[l]=titleLang;
					}
					ArrayList<PlanService> plist=new ArrayList<PlanService>();
					NodeList nodesPlanList = entry.getElementsByTagName("item");
					if(nodesPlanList!=null&&nodesPlanList.getLength()>0){
			        	for(int j=0;j<nodesPlanList.getLength();j++){ 
			        		logger.info("-------------item"+j+"-------------");
			        		Element entryPlan=(Element)nodesPlanList.item(j); 
			        		String icon="";
			        		String unsub="";
			        		String reset="";
			        		String resetMsg="";
			        		String showMenu="";
			        		
			        		String pid=entryPlan.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
			        		if (entryPlan.getElementsByTagName("icon").item(0).getFirstChild()!=null)
			        			icon=entryPlan.getElementsByTagName("icon").item(0).getFirstChild().getNodeValue();
			        		if (entryPlan.getElementsByTagName("unsub").item(0).getFirstChild()!=null)
			        			unsub=entryPlan.getElementsByTagName("unsub").item(0).getFirstChild().getNodeValue();
			        		if (entryPlan.getElementsByTagName("reset").item(0).getFirstChild()!=null)
			        			reset=entryPlan.getElementsByTagName("reset").item(0).getFirstChild().getNodeValue();
			        		if (entryPlan.getElementsByTagName("reset_msg").item(0)!=null && entryPlan.getElementsByTagName("reset_msg").item(0).getFirstChild()!=null)
			        			resetMsg=entryPlan.getElementsByTagName("reset_msg").item(0).getFirstChild().getNodeValue();
			        		if (entryPlan.getElementsByTagName("show_in_menu").item(0).getFirstChild()!=null) 
			        			showMenu=entryPlan.getElementsByTagName("show_in_menu").item(0).getFirstChild().getNodeValue();
			        		//
			        		logger.info(pid+","+icon+","+unsub+","+reset+","+resetMsg+","+showMenu);
			        		String[] ptitle= new String[3];
			        		String[] descShort= new String[3];
			        		String[] pathTC= new String[3];
			        		String[] descLong= new String[3];
			        		String[] fee= new String[3];
			        		for (int k=0;k<langType.length;k++) {
			        			String lang=langType[k];
			        			String ptitleLang="";
			        			String pdescshortLang="";
			        			String ptcLang="";
			        			String pdescLongLang="";
			        			String pfeeLang="";
			        			if (entryPlan.getElementsByTagName("title_"+lang).item(0).getFirstChild()!=null) 		
			        				ptitleLang=entryPlan.getElementsByTagName("title_"+lang).item(0).getFirstChild().getNodeValue();
			        			ptitle[k]=ptitleLang;
			        			
			        			if (entryPlan.getElementsByTagName("title_"+lang).item(0).getFirstChild()!=null) 		
				        			pdescshortLang=entryPlan.getElementsByTagName("title_"+lang).item(0).getFirstChild().getNodeValue();
			        			descShort[k]=pdescshortLang;
			        			
			        			if (entryPlan.getElementsByTagName("path_tandc_"+lang).item(0).getFirstChild()!=null)
			        				ptcLang=entryPlan.getElementsByTagName("path_tandc_"+lang).item(0).getFirstChild().getNodeValue();
			        			pathTC[k]=ptcLang;
			        			
			        			if (entryPlan.getElementsByTagName("desc_long1_"+lang).item(0).getFirstChild()!=null)
			        				pdescLongLang=entryPlan.getElementsByTagName("desc_long1_"+lang).item(0).getFirstChild().getNodeValue();
			        			descLong[k]=pdescLongLang;

			        			if (entryPlan.getElementsByTagName("fee_"+lang).item(0).getFirstChild()!=null)
			        				pfeeLang=entryPlan.getElementsByTagName("fee_"+lang).item(0).getFirstChild().getNodeValue();
			        			fee[k]=pfeeLang;
			        			logger.info(ptitle[k]+","+descShort[k]+","+pathTC[k]+","+descLong[k]+","+fee[k]);
			        		}
			        		PlanService plan=new PlanService();
							plan.setPlanid(pid);
							plan.setTitle(ptitle);
							plan.setDescShort(descShort);
							plan.setDescLong(descLong);
							plan.setPathTandc(pathTC);
							plan.setFee(fee);
							plan.setIcon(icon);
							plan.setReset(reset);
							plan.setUnsub(unsub);
							plan.setResetMsg(resetMsg);
							plan.setShowInMenu(showMenu);
							plist.add(plan);
			        	}
					}
					Category category=new Category();
					category.setId(id);
					category.setTitle(title);
					category.setPlanList(plist);
					cList.add(category);
				}
	        }
	       
	      return cList;  
		
	}
	
	
	public ArrayList<Category> getXML(String[] planSubId,String cardType) throws Exception {
		
		String propPath=getClass().getClassLoader().getResource("")+"planservice.xml";
		logger.info(propPath);
		if (propPath.indexOf("file:")!=-1){
			propPath=propPath.substring(propPath.indexOf("file:/")+"file:/".length());
		}
		logger.info(propPath);
		InputStream inputStream= new FileInputStream(new File(propPath));
		BufferedReader bf= new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
		String inputLine;
		StringBuffer respon = new StringBuffer();

		while ((inputLine = bf.readLine()) != null) {
			respon.append(inputLine);
		}
		String result= respon.toString();
		bf.close();
		logger.info(result);
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
		        is.setCharacterStream(new StringReader(result));
		        doc = db.parse(is); 
			} catch (Exception e) {
                logger.error(e);
			} 
	        NodeList nodesList = doc.getElementsByTagName("Category");
	        ArrayList<Category> cList=new ArrayList<Category>();		
	        if(nodesList!=null&&nodesList.getLength()>0){
	        	for(int i=0;i<nodesList.getLength();i++){ 
	        		//logger.info("###########################################");
					Element entry=(Element)nodesList.item(i); 
					String id=entry.getElementsByTagName("c_id").item(0).getFirstChild().getNodeValue();
					String[] title= new String[3];
					for (int l=0;l<langType.length;l++) {
	        			String lang=langType[l];
	        			String titleLang="";
	        			if (entry.getElementsByTagName("c_title_"+lang).item(0).getFirstChild()!=null) 		
	        				titleLang=entry.getElementsByTagName("c_title_"+lang).item(0).getFirstChild().getNodeValue();
	        			title[l]=titleLang;
					}
					ArrayList<PlanService> plist=new ArrayList<PlanService>();
					NodeList nodesPlanList = entry.getElementsByTagName("item");
					if(nodesPlanList!=null&&nodesPlanList.getLength()>0){
			        	for(int j=0;j<nodesPlanList.getLength();j++){ 
			        		//logger.info("-------------item"+j+"-------------");
			        		Element entryPlan=(Element)nodesPlanList.item(j); 
			        		//card type checking
			        		boolean isShow=false;
			        		String showMenu="Y";
			        		if (entryPlan.getElementsByTagName("show_in_menu").item(0).getFirstChild()!=null) 
			        			showMenu=entryPlan.getElementsByTagName("show_in_menu").item(0).getFirstChild().getNodeValue();

			        		if (showMenu.equals("N")) {
			        			
			        			continue;
			        		}
			        		
			        		NodeList nodesTypeList = entryPlan.getElementsByTagName("type");
			        		if(nodesTypeList!=null&&nodesTypeList.getLength()>0){
			        			for(int n=0;n<nodesTypeList.getLength();n++){ 
					        		Element entryType=(Element)nodesTypeList.item(n); 
					        		String type=entryType.getFirstChild().getNodeValue();
					        		if (type.equals(cardType.trim())) {
					        			isShow=true;
					        			break;
					        		}
			        			}
			        		}
			        		if (!isShow) {
			        			//logger.info("card type:"+cardType+" not show in menu.");
			        			continue;
			        		}
			        		String icon="";
			        		String unsub="";
			        		String reset="";
			        		String resetMsg="";
			        		
			        		boolean isSub=false;
			        		String pid=entryPlan.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
			        		if (entryPlan.getElementsByTagName("icon").item(0).getFirstChild()!=null)
			        			icon=entryPlan.getElementsByTagName("icon").item(0).getFirstChild().getNodeValue();
			        		if (entryPlan.getElementsByTagName("unsub").item(0).getFirstChild()!=null)
			        			unsub=entryPlan.getElementsByTagName("unsub").item(0).getFirstChild().getNodeValue();
			        		if (entryPlan.getElementsByTagName("reset").item(0).getFirstChild()!=null)
			        			reset=entryPlan.getElementsByTagName("reset").item(0).getFirstChild().getNodeValue();
			        		if (entryPlan.getElementsByTagName("reset_msg").item(0)!=null && entryPlan.getElementsByTagName("reset_msg").item(0).getFirstChild()!=null)
			        			resetMsg=entryPlan.getElementsByTagName("reset_msg").item(0).getFirstChild().getNodeValue();
			        					        		//
			        		//logger.info(pid+","+icon+","+unsub+","+reset+","+resetMsg+","+showMenu);
			        		String[] ptitle= new String[3];
			        		String[] descShort= new String[3];
			        		String[] pathTC= new String[3];
			        		String[] descLong= new String[3];
			        		String[] fee= new String[3];
			        		for (int k=0;k<langType.length;k++) {
			        			String lang=langType[k];
			        			String ptitleLang="";
			        			String pdescshortLang="";
			        			String ptcLang="";
			        			String pdescLongLang="";
			        			String pfeeLang="";
			        			if (entryPlan.getElementsByTagName("title_"+lang).item(0).getFirstChild()!=null) 		
			        				ptitleLang=entryPlan.getElementsByTagName("title_"+lang).item(0).getFirstChild().getNodeValue();
			        			ptitle[k]=ptitleLang;
			        			
			        			if (entryPlan.getElementsByTagName("desc_short1_"+lang).item(0).getFirstChild()!=null) 		
				        			pdescshortLang=entryPlan.getElementsByTagName("desc_short1_"+lang).item(0).getFirstChild().getNodeValue();
			        			descShort[k]=pdescshortLang;
			        			
			        			if (entryPlan.getElementsByTagName("path_tandc_"+lang).item(0).getFirstChild()!=null)
			        				ptcLang=entryPlan.getElementsByTagName("path_tandc_"+lang).item(0).getFirstChild().getNodeValue();
			        			pathTC[k]=ptcLang;
			        			
			        			if (entryPlan.getElementsByTagName("desc_long1_"+lang).item(0).getFirstChild()!=null)
			        				pdescLongLang=entryPlan.getElementsByTagName("desc_long1_"+lang).item(0).getFirstChild().getNodeValue();
			        			descLong[k]=pdescLongLang;

			        			if (entryPlan.getElementsByTagName("fee_"+lang).item(0).getFirstChild()!=null)
			        				pfeeLang=entryPlan.getElementsByTagName("fee_"+lang).item(0).getFirstChild().getNodeValue();
			        			fee[k]=pfeeLang;
			        			//logger.info(ptitle[k]+","+descShort[k]+","+pathTC[k]+","+descLong[k]+","+fee[k]);
			        		}
			        		for (int m=0;m<planSubId.length;m++){
			        			if (planSubId[m].trim().equals(pid)) {
			        				isSub=true;
			        				break;
			        			}
			        		}
			        		PlanService plan=new PlanService();
			        		
			        		NodeList nodesDetailList = entryPlan.getElementsByTagName("table_detail");
			        		logger.info(nodesDetailList.getLength());
			        		if(nodesDetailList!=null&&nodesDetailList.getLength()>0){
			        			String[] detailTab = new String[nodesDetailList.getLength()];
			        			
				        		
			        			for(int n=0;n<nodesDetailList.getLength();n++){ 
			        				Element entryDetail=(Element)nodesDetailList.item(n); 
					        		String detail=entryDetail.getFirstChild().getNodeValue();
					        		logger.info(pid+":"+n+":"+detail);
					        		detailTab[n]=detail;
			        			}
			        			plan.setDetailTab(detailTab);
			        		}
			        		
			        		
			        		
							plan.setPlanid(pid);
							plan.setTitle(ptitle);
							plan.setDescShort(descShort);
							plan.setDescLong(descLong);
							plan.setPathTandc(pathTC);
							plan.setFee(fee);
							plan.setIcon(icon);
							plan.setReset(reset);
							plan.setUnsub(unsub);
							plan.setResetMsg(resetMsg);
							plan.setShowInMenu(showMenu);
							plan.setSub(isSub);
							plist.add(plan);
			        	}
					}
					Category category=new Category();
					category.setId(id);
					category.setTitle(title);
					category.setPlanList(plist);
					cList.add(category);
				}
	        }
	       
	      return cList;  
		
	}

	public void outprintObj( ArrayList<Category> cList) {
		for (int i=0;i<cList.size();i++) {
        	Category category= cList.get(i);
        	String id=category.getId();
        	String[] title= category.getTitle();
        	ArrayList<PlanService> pList = category.getPlanList();
        	logger.info("###########################################");
        	logger.info("Category "+id);
        	for (int j=0;j<langType.length;j++) {
        		String lang=langType[j];
        		logger.info("Category_title_"+lang+":"+title[j]);
        	}
        	for (int k=0;k<pList.size();k++) {
        		PlanService plan=pList.get(k);
        		logger.info("-------------item:"+k+"-------------");
        		logger.info("id:"+plan.getPlanid());
        		logger.info("icon:"+plan.getIcon());
        		logger.info("reset:"+plan.getReset());
        		logger.info("unsub:"+plan.getUnsub());
        		logger.info("reset_msg:"+plan.getResetMsg());
        		logger.info("show_in_menu:"+plan.getShowInMenu());
        		String[] ptitle= plan.getTitle();
        		String[] descShort= plan.getDescShort();
        		String[] pathTC= plan.getPathTandc();
        		String[] descLong= plan.getDescLong();
        		String[] fee= plan.getFee();
        		for (int j=0;j<langType.length;j++) {
        			String lang=langType[j];
	        		logger.info("title_"+lang+":"+ptitle[j]);
	        		logger.info("descShort_"+lang+":"+descShort[j]);
	        		logger.info("pathTC_"+lang+":"+pathTC[j]);
	        		logger.info("descLong_"+lang+":"+descLong[j]);
	        		logger.info("fee_"+lang+":"+fee[j]);
	        	}
        	}
        }
	}
	
	
	public String readInputStream(InputStream paramInputStream, String paramString) throws Exception {
		InputStreamReader localInputStreamReader = null;
		BufferedReader localBufferedReader = null;
		StringBuffer localStringBuffer = new StringBuffer("");
		try {
			if (paramString.equals(""))
				localInputStreamReader = new InputStreamReader(paramInputStream);
			else {
				localInputStreamReader = new InputStreamReader(paramInputStream, paramString);
			}

			localBufferedReader = new BufferedReader(localInputStreamReader);
			char[] arrayOfChar = new char[512];
			int i = -1;
			while ((i = localBufferedReader.read(arrayOfChar)) != -1) {
				if (localStringBuffer.length() > 9999999) {
					throw new Exception("readInputStream[input too long]");
				}
				localStringBuffer.append(arrayOfChar, 0, i);
			}
		} finally {
			if (localInputStreamReader != null)
				try {
					localInputStreamReader.close();
				} catch (Exception localException1) {
				}
			if (localBufferedReader != null)
				try {
					localBufferedReader.close();
				} catch (Exception localException2) {
				}
		}
		return localStringBuffer.toString();
	}
	
	public StringBuffer return_stringbuffer(String paramString1, String paramString2){
		FileInputStream localFileInputStream = null;
		StringBuffer localStringBuffer1 = new StringBuffer();
		char[] arrayOfChar = new char[512];
		try {
			localFileInputStream = new FileInputStream(paramString1);
			StringBuffer localStringBuffer2;
			if (localFileInputStream == null)
				return localStringBuffer1;
			localStringBuffer1.append(readInputStream(localFileInputStream, paramString2));
			return localStringBuffer1;
		} catch (Exception e) {
		}finally {
			if (localFileInputStream != null)
				try {
					localFileInputStream.close();
				} catch (Exception localException) {
				}
		}
		return localStringBuffer1;
	}
	
	public StringBuffer return_tcpp(String doc_path) {
		StringBuffer text_return = new StringBuffer();
		try {
			StringBuffer content_raw = return_stringbuffer(doc_path,"utf-8");
			StringBuffer content_output= new StringBuffer();

			Pattern p = Pattern.compile("(?m)^(.*)$");
		 	Matcher m = p.matcher(content_raw);
		 	while (m.find()) {
		 		content_output.append("<div class=\"whiteword\">");
		    	m.appendReplacement(content_output, "$1");
		    	content_output.append("&nbsp;</div>");
		 	}
		 	m.appendTail(content_output);

			/*text_return.append("<div class=\"view\">");
			//text_return.append("<div class=\"doctopbar\"><span class=\"title\">"+title+"</span></div>");
			text_return.append("<div class=\"To-learn-about-how-_in_Detail\">"+content_output+"</div>");
			text_return.append("</div>");*/
		 	text_return.append(content_output);
		} catch (Exception e) {
		}
		return text_return;
	}
}
