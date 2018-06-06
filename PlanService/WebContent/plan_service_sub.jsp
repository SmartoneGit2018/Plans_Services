<%@ include file="plan_service_common.jsp"%>
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
<meta
	content="width=device-width, initial-scale=1,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no"
	name="viewport">
<link rel="stylesheet" type="text/css" href="CSS/style.css?000005" />

<script type="text/javascript" charset="utf-8"
	src="jquery/js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="jquery/js/jquery.js"></script>
<script type="text/javascript" src="js/iscroll5.js" charset="utf-8"></script>
<script src="js/stapps_iscroll.js" type="text/javascript"></script>
<title>Plans & Services</title>
<style>
#PlansServicesTitleBackGuand {
	width: 100%;
	height: 24px;
	margin-top: 17px;
	margin-left:20px;
	margin-right: 20px;
}

#wrapper {
    background: none repeat scroll 0 0 ;
    left: 0;
    overflow: hidden;
    position: relative;
    width: 100%;
    z-index: 1;
}

#PlansServicesDetailBlackGuand {
	width: 90%;
	height: 80%;
	margin-left: 18px;
	margin-right: 18px;
	margin-top:18px;
}
/* whitebox †*/
#PlansServicesHidebg{width:100%;height:100%;position:absolute;top:0px;left:0px}
#PlansServicesAlertBackground{width:76%;height:45%;position:absolute;top:188px;left:12%}

#msgSuccessic{width:100px;height:100px;position:absolute;top:138px; left: 50%; margin-left:-50px;z-Index:4} 
#PlansServicesAlertBackground #PlansServicesAlertSuccess{width:60%;height:24px;position:absolute;left:20%;top:80px}
#PlansServicesAlertBackground #PlansServicesAlertText{width:90%;height:64px;position:absolute;left:5%;top:124px}
#PlansServicesAlertBackground #PlansServicesAlertImage-Blue-157cf3{width:90%;height:45px;position:absolute;left:5%;bottom:20px}
#PlansServicesAlertImage-Blue-157cf3 #PlansServicesAlertOK-in-Blue{width:90%;height:24px;position:absolute;left:5%;top:14px}


</style>
<script type="text/javascript">
var myScroll=null;
/*function loaded(){
	myScroll = new IScroll('#wrapper', {scrollY:true,scrollbars:true,click: true});
	
	
}*/
jQuery(document).ajaxStart(function() {
	$('#ajax_loader').removeClass().addClass('spinner');
});
jQuery(document)
		.ajaxStop(
				function() {
					$('#ajax_loader').removeClass();
					updateSvcOrientation();
				});

$(document).ready(function() {
	locateSpinnerElements();
	document.addEventListener('touchmove', function(e) {
		e.preventDefault();
	});
});
window.addEventListener('load', function() {
	updateSvcOrientation();	
	window.setTimeout(function() {
		myScroll = new IScroll('#wrapper', {
			scrollY : true,
			scrollbars : false,
			mouseWheel : true,
			hideScrollbar:true
		});
		myScroll.on('scrollStart', function() {
			$('.iScrollVerticalScrollbar').css('display', 'block');
		});
		myScroll.on('scrollEnd', function() {
			$('.iScrollVerticalScrollbar').css('display', 'none');
		});
	}, 1500);
})
window.onorientationchange=function(){window.setTimeout(function() {updateSvcOrientation()}, 0)};
window.onresize=function(){window.setTimeout(function() {updateSvcOrientation()}, 0)};

function updateSvcOrientation() {
	$('#wrapper').css('height',document.getElementById("PlansServicesDetailBlackGuand").offsetHeight- document.getElementById("planTitle").offsetHeight- document.getElementById("footer").offsetHeight);
}

function urlPost(){
	
}
function SubscribeNow(){
    document.getElementById("PlansServicesHidebg").style.display="block";
    document.getElementById("msgSuccessic").style.display="block";
    document.getElementById("PlansServicesAlertBackground").style.display="block";
}
</script>
</head>
<body style="margin: 0" bgcolor="#fafafa">
	<div id="PlansServicesTitleBackGuand">
		<table width="100%">
			<tr>
				<td nowrap width="10%" class="Plans-Services-Gary"><%=proTitle[0][lang]%></td>
				<td class="btn_RightBack" align="center">></td>
				<td class="Plans-Services-title-black"><%=title%></td>
			</tr>
		</table>

	</div>

	<div id="PlansServicesDetailBlackGuand"
		class="Rectangle_backguand_<%=cid%>">
		<div id ="planTitle" style="margin-left: 23px;">
		<table width="100%">
		<tr><td colspan"3">&nbsp;</td>
			<tr>
				<td nowrap valign="middle"><font class="Plan_in_Detail"><%=proTitle[1][lang]%></font></td>
				<td>&nbsp;</td>
				<td width="90%"><div class="Rectangle_in_detail"></div></td>
			</tr>
		</table>
		</div>
		<div id="ajax_loader"
			style="background-position: -20px 0px; display: none;" class=""></div>
		<div id="wrapper" style="height:400px;">
		<div id="content"
				style="transition-timing-function: cubic-bezier(0.1, 0.57, 0.1, 1); transition-duration: 0ms; transform: translate(0px, 0px) translateZ(0px);">
		
			<div id="serviceDetail" style="height:200px;width:100%">
				<table border="0" width="100%" height="100%">
				<tr><td align="center"><font style="valign:middle" class="G-Data-Plan_in_Detail"><%=title%></font></td></tr>
				</table>
			</div>
			<div id="subscribe" style="height:45px;width:100%">
				<table border="0" width="100%" height="100%">
				<tr><td width="20px">&nbsp;</td><td align="center">
				<input type="button" value="<%=subBtn[0][lang]%>" onclick="SubscribeNow();" class="Subscribe-Now_in_Detail"
												 ></input>
				</td><td width="20px">&nbsp;</td></tr>
				</table>
				
			</div>
			<div id="svcDesc" style="margin-left: 23px;margin-top: 23px;margin-right: 23px;">
				<table width="100%" style="margin:auto">
				<tr><td class = "Service_in_Detail"><%=proTitle[2][lang] %></td></tr>
				<tr><td class = "To-learn-about-how-_in_Detail"><%=longDesc%></td></tr>
				</table>
			</div>
			<div id="svcFee" style="margin-left: 23px;margin-right: 23px;">
				<table width="100%" style="margin:auto">
				<tr><td class = "To-learn-about-how-_in_Detail"><%=fee%></td></tr>
				</table>
			</div>
			<div id="svcTC" style="margin-left: 23px;margin-top: 17px;margin-right: 23px;">
				<table width="100%" style="margin:auto">
				<tr><td class = "Service_in_Detail"><%=proTitle[3][lang] %></td></tr>
				<tr><td class = "To-learn-about-how-_in_Detail"><font style="font-size:10px"><%=tcDesc%></font></td></tr>
				</table>
			</div>
			</div>
			<div class="iScrollVerticalScrollbar iScrollLoneScrollbar"
				style="overflow: hidden; pointer-events: none; display: none;">
				<div class="iScrollIndicator"
					style="transition-duration: 0ms; display: block; height: 8px; transform: translate(0px, -8px) translateZ(0px); transition-timing-function: cubic-bezier(0.1, 0.57, 0.1, 1);"></div>
			</div>
		</div>
		<div id="footer" style="height:20px;">&nbsp;</div>
	</div>

	
<!-- --backguand---- -->
<div id = "PlansServicesHidebg" class = "hidebg" style="display:none"></div>	
<!-- ----Logo--------- -->
<img id = "msgSuccessic" src="images/ic-msg-success@3x.png"  style="display:none" >

<!-- ----whitebox†--------- -->
<div id = "PlansServicesAlertBackground" class = "Alert-Background" style="display:none">
<div id = "PlansServicesAlertSuccess" class = "Success_in-Alert-Background">Success</div>
<div id = "PlansServicesAlertText" class = "Detail_in-Alert-Background">You have successfully subscribed to the service. .</div>
<div id = "PlansServicesAlertImage-Blue-157cf3" class = "Image-Blue-157cf3" type = "button" onclick = "OK()">
<div id = "PlansServicesAlertOK-in-Blue" class = "Next-in-Blue">Ok</div>
</div>
</div>

	
	
</body>
</html>

