<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.lang.*,java.util.*, java.sql.*, java.io.*,
	java.net.*,java.text.*,org.w3c.dom.*,javax.xml.parsers.*,org.xml.sax.*,org.apache.log4j.Logger,com.smartone.planservice.*"%>
<%@ include file="plan_service_wording.jsp"%>
<%
	Logger logger = Logger.getLogger("LOG");
HashMap svcList=new HashMap();
	//msisdn
	String msisdn = "";
	if (request.getHeader("x-up-calling-line-id") != null
			&& !request.getHeader("x-up-calling-line-id").equals("")) {
		msisdn = request.getHeader("x-up-calling-line-id");
		logger.info("Get msisdn from header:" + msisdn);
		request.setAttribute("msisdn",msisdn);
	} else {
		//dataAlertUtils utils = dataAlertUtils.getInstance();
		//msisdn=utils.getMsisdnFromSoapProxy(request);
		logger.info("Get msisdn from proxy (" + request.getRemoteAddr() + "):" + msisdn);
		if (!msisdn.matches("\\d{8}")) {
			msisdn = "";
			logger.info("msisdn is null.");
			return;
		}
	}
	
	//card type get from api
	String careType="C17";
	int usage=10;
	//0-eng,1-bss,2-sss
	int lang=0;
	if (careType.indexOf("7")!=-1){
		lang=1;
	}else if (careType.indexOf("9")!=-1){
		lang=2;
	}
	
	//list of sub services,for testing
	String[] planidSample = { "4gdataplan", "globeIDD", "roaming", "mox" };
	boolean showReset=false;
	boolean showSpeed=false;
	for (int m=0;m<planidSample.length;m++){
		if (planidSample[m].trim().equals("4gdataplan")||planidSample[m].trim().equals("3gdataplan")||planidSample[m].trim().equals("2daydatapack")) {
			showSpeed=true;
		}
		if (planidSample[m].trim().equals("4gdataplan")&&(usage>40)) {
			showReset=true;
		}
	}
	//get list of 
	com.smartone.planservice.XMLReader xmlr = new com.smartone.planservice.XMLReader();
	ArrayList<Category> cList =null;
	try {
		cList = xmlr.getXML(planidSample,careType);
		//request.setAttribute("cList",cList);
		//request.setAttribute("showReset",showReset);
		//request.setAttribute("showSpeed",showSpeed);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error("get xml list error.");
	}
	String ua="android";
%>
<!DOCTYPE>
<html>
<head>
<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<meta http-equiv="cache-control" content="max-age=0">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="cache-control" content="must-revalidate">
	<meta http-equiv="Expires" content="Tue, 20 Aug 2000 14:25:27 GMT">
	<meta content="width=device-width, initial-scale=1,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no" name="viewport">
	<link rel="stylesheet" type="text/css" href="CSS/style.css?000002" />
	<script type="text/javascript" charset="utf-8"  src="jquery/js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8"  src="jquery/js/jquery.js"></script>
	<style type="text/css">

	#TitleBackGuand{width:"100%";position:relative; margin-left:20px;margin-top:15px;}
	#WhiteTableBackGuand{width:92%;height:140px;position:relative;left:4%}
#OrangeNike{width:4%;height:16px;position:absolute; right:7px;Top:7px}
 #NameImage{width:90px;height:90px;position:absolute; left:10px;Top:15px}
#Name{width:80%;height:18px;position:absolute; left:118px;Top:15px}
#Text{width:60%;height:73px;position:absolute; left:118px;Top:33px}
#LearnMoreBackGuand{width:98px;height:25px;position:absolute;right:0;bottom:0}
	</style>
<title>Plans & Services</title>
<script type="text/javascript">
function tabOnClick(pid,issub){
	//alert(issub);
	if (issub)
		window.location.href="plan_service_detail.jsp?pid="+pid;
	else
		window.location.href="plan_service_sub.jsp?pid="+pid;
}
</script>
</head>
<body style="margin:0" bgcolor = "#fafafa">
<%for (int i=0;i<cList.size();i++){ 
	Category category= cList.get(i);
	String id=category.getId();
	String[] title= category.getTitle();
	ArrayList<PlanService> pList = category.getPlanList();
%>
<!-- Category -->
<div id = "TitleBackGuand" >
<table border="0" width="100%">
<tr><td nowrap  valign="middle"><font   class="Data-Voice"><%=title[lang]%></font></td><td>&nbsp;</td><td width="90%" valign="middle"><div class = "Rectangle-12"></div></td>
</table>
</div>
<!-- list service -->

<% int cnt=1;
for (int k=0;k<pList.size();k++) {
	
        		PlanService plan=pList.get(k);
        		String pid=plan.getPlanid();
         		String[] plantitle= plan.getTitle();
        		String[] descShort= plan.getDescShort();
        		String[] pathTC= plan.getPathTandc();
        		String[] descLong= plan.getDescLong();
        		String[] fee= plan.getFee();
        		boolean isSub=plan.isSub();
        		String ptitle=plantitle[lang];
        		if (!ua.equals("android") && pid.equals("cloud")){
        			continue;
        		}
        		
        		if (!showSpeed && pid.equals("speeddaypack")){
        			continue;
        		}
        		if (!showReset && pid.equals("resetdataspeed")){
        			continue;
        		}
        		plan.setShowSpeed(false);
        		plan.setShowReset(false);
        		if (isSub){
	        		if (pid.equals("4gdataplan")||pid.equals("3gdataplan")||pid.equals("2daydatapack")) {
	        			plan.setShowSpeed(true);
	        		}
	        		if (pid.equals("4gdataplan")&&(usage>40)) {
	        			plan.setShowReset(true);
	        		}
        		}
        		plan.setCid(id);
        		plan.setLang(lang);
        		svcList.put(pid, plan);
 %>      
 <div style = "margin-top:15px;"  id = "<%=pid %>" class = "Rectangle-11-ffffff" onclick="tabOnClick('<%=pid%>',<%=isSub%>)">
<div id = "NameImage" class = "Rectangle-<%=id%>-<%=cnt%>">
<table height="100%">
<tr><td width="11px">&nbsp;</td><td class = "G-Data-Plan-ffffff"><%=ptitle %></td><td width="11px">&nbsp;</td></tr>
</table>

</div>
<div   id = "Name" class = "G-Data-Plan-2f2f2f"><%=ptitle %>
</div>
<div   id = "Text" class = "To-learn-about-how-o"><%=descShort[lang] %>
</div>
<%if (isSub){ %>
	<img id = "OrangeNike" src="images/ic-added@3x.png" class="ic_added">
<%} %>
    	<img id = "LearnMoreBackGuand" src="images/ic-learnmore@3x.png" class="ic_learnmore"> 
</div> 		
<%
if (cnt==3){
	cnt=1;
}else{
	cnt++;
}

}%>
<%} 
session.setAttribute("svcList",svcList);
%>
</body>

