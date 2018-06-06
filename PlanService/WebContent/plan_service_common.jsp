<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.lang.*,java.util.*, java.sql.*, java.io.*,
	java.net.*,java.text.*,org.w3c.dom.*,javax.xml.parsers.*,org.xml.sax.*,org.apache.log4j.Logger,com.smartone.planservice.*"%>
<%@ include file="plan_service_wording.jsp"%>
<%
	Logger logger = Logger.getLogger("LOG");
	com.smartone.planservice.XMLReader xmlr = new com.smartone.planservice.XMLReader();
	String msisdn=(String)request.getAttribute("msisdn");
	HashMap svcList =(HashMap)session.getAttribute("svcList");
	//boolean showReset=(boolean)request.getAttribute("showReset");
	//boolean showSpeed=(boolean)request.getAttribute("showSpeed");
	String pid=request.getParameter("pid");
	//get plan service
	if (svcList==null){
		response.sendRedirect("plan_service.jsp");
		return;
	}
	PlanService plan=(PlanService)svcList.get(pid);
	boolean isSub=plan.isSub();
	int lang=plan.getLang();
	logger.info(svcList.size()+"");
	logger.info(plan.getTitle()[lang]);
	String title=plan.getTitle()[lang];
	String cid= plan.getCid();
	String longDesc=plan.getDescLong()[lang];
	String fee= plan.getFee()[lang];
	String tcPath=plan.getPathTandc()[lang];
	StringBuffer str=xmlr.return_stringbuffer(tcPath,"utf-8");
	String tcDesc="";
	if (str!=null && !str.equals(""))
		 tcDesc=xmlr.return_stringbuffer(tcPath,"utf-8").toString();
	boolean showSpeed= plan.isShowSpeed();
	boolean showReset= plan.isShowReset();
	String unSub= plan.getUnsub();
	String billDate="2018-07-01 00:00";
	String dataUsg="1 GB";
	String expDate="2018-07-01 00:00";
	String remainMin="70mins";
	HashMap dataConfig=new HashMap();
	dataConfig.put("Bill Date",billDate);
	dataConfig.put("Data Usage",dataUsg);
	dataConfig.put("Expiry Date",expDate);
	dataConfig.put("Remaining minutes",remainMin);
	
	String[] detailTab = plan.getDetailTab();
	String[][] detail=null;
	if (detailTab!=null&& detailTab.length>0){
		detail=new String[detailTab.length][2];
		for (int i=0;i<detailTab.length;i++){
			String tab=detailTab[i];
			detail[i][1]=(String)dataConfig.get(tab);
			for (int j=0;j<detailStr.length;j++){
				String strTitle=detailStr[j][0];
				if (tab.equals(strTitle)){
					detail[i][0]=detailStr[j][lang];
					break;
				}
			}
			logger.info(detail[i][0]+","+detail[i][1]);
			/*if (tab.equals("Bill Date")){
				detail[i][0]=detailStr[0][lang];
				detail[i][1]=(String)dataConfig.get(tab);
			}
			
			if (tab.equals("Data Usage")){
				detail[i][0]=detailStr[1][lang];
				detail[i][1]=(String)dataConfig.get(tab);
			}
			
			if (tab.equals("Expiry Date")){
				detail[i][0]=detailStr[2][lang];
				detail[i][1]=(String)dataConfig.get(tab);
			}
			
			if (tab.equals("Remaining minutes")){
				detail[i][0]=detailStr[0][lang];
				detail[i][1]=(String)dataConfig.get(tab);
			}*/
		}
		
	}
	
	
%>