var intervalId = 0;
var lastUrl='';
var lastSection=0;
var lang=0;
var svc2_image_nature = 0;
var owlImg_maxwidth = 0;
var owlImg_maxheight = 0;

// Spinner
PKSpinnerFrame=0;
function advanceSpinner(){
	PKSpinnerFrame+=1;
	var C=document.querySelectorAll(".spinner");
	for(var B=0;B<C.length;B++){
		var A=C[B];
		A.style.backgroundPosition=""+(-20*(PKSpinnerFrame%12))+"px 0px"
	}
}
function locateSpinnerElements(){
	var A=document.querySelectorAll(".spinner");
	if(A.length>0){
		window.clearInterval(intervalId);
		intervalId = window.setInterval(advanceSpinner,100);
	}
}

function iOS7SMS(lvUrl, zone, op) {
	if (navigator.userAgent!=null){
		var is_iOS7 = (navigator.userAgent.indexOf("OS 7_0 ")>0||navigator.userAgent.indexOf("OS 7_0_1 ")>0||navigator.userAgent.indexOf("OS 7_0_2 ")>0?true:false);
		var appmode = (window.navigator.standalone?true:false);
		if (is_iOS7 && appmode) {
			window.location.href = "stapps2_addbm_appmode.jsp?zone="+zone;
		} else {
			window.location.href = lvUrl;
		}
	}
}

//General
function getHTML(lvUrl, section, history_action){
	$('#'+section).empty();
	$('#bmBar').hide();
	$('#ajax_loader').show().removeClass().addClass('spinner');
	if (lvUrl.indexOf('stapps2_footer')<0){
		if (lvUrl.indexOf('&lang')>-1)
			lastUrl = lvUrl.substr(0,lvUrl.indexOf('&lang='));
		else lastUrl = lvUrl;
	}

	if ( (lvUrl.indexOf('stapps2_bm')>=0 || lvUrl.indexOf('lookout')>=0 || lvUrl.indexOf('soliton')>=0) && navigator.userAgent!=null ){
		var is_iOS7 = (navigator.userAgent.indexOf("OS 7_0 ")>0||navigator.userAgent.indexOf("OS 7_0_1 ")>0||navigator.userAgent.indexOf("OS 7_0_2 ")>0?true:false);
		var appmode = (window.navigator.standalone?true:false);
		if (is_iOS7 && appmode) {
			window.location.href = "stapps2_addbm_appmode.jsp?zone="+(lvUrl.indexOf('stapps2_bm')>=0?"main":(lvUrl.indexOf('lookout')>=0?"lookout":""));
		}
	}
	updateButton('');

	if (section == 'standalone') {
		window.location.href = lvUrl;
	} else if (section == 'content') {
		if (history_action == 'clear_history') {
			history_links = [];
			history_links.push(lvUrl);
		} else if (history_action == 'click_back') {
			history_links.pop();
		} else if (history_action != 'not_save_history' ) {
			history_links.push(lvUrl);
		}
	}

	$.ajax({
		url:lvUrl,
		dataType: 'html',
		timeout: 30000,
		error: function(){
		},
		success: function(data){
			$('#ajax_loader').removeClass().hide();
			$('#'+section).html(data);

			if (myScroll!=null){
				$('#wrapper').css('height', updateContentHeight());
				$('.iScrollVerticalScrollbar').css('display','block');
				myScroll.refresh();
				$('.iScrollVerticalScrollbar').css('display','none');
				myScroll.scrollTo(0,0);
			}
		}
	})
} 

function updateOrientation(user_agent,tloc){
	if (user_agent.indexOf('iPhone')>-1)
		window.scrollTo(0,1); 
	if(navigator.userAgent.match(/Android/i))
    window.scrollTo(0,1);
  
	if ( user_agent.indexOf('iPhone')>-1 && user_agent.indexOf('Safari')>-1 && (user_agent.indexOf('OS 3')>-1||user_agent.indexOf('OS 4')>-1||user_agent.indexOf('OS 5')>-1||user_agent.indexOf('OS 6')>-1) ){
		$('#footer').css('bottom','-60px');
		window.scrollTo(0,1);
	}
	updateAppListFrag();
	$('#wrapper').css('height', updateContentHeight());
	if (myScroll!=null) {
		$('.iScrollVerticalScrollbar').css('display','block');
		myScroll.refresh();
		$('.iScrollVerticalScrollbar').css('display','none');
	}

	if (document.getElementById("promomain")!=null) {
		startSlide();
	}

	if (document.getElementById("others_svc_table")!=null) {
		$('.others_icon_td').css('width',Math.floor($('.icon_service').width()*1.1));
	}

	if ( document.getElementById("alert")!=null && document.getElementById("alert").style.display!="none" ) {
		alertBoxSize();
	}
}

function updateAppListFrag() {
	if (document.getElementById("sAppList")!=null) {
		var item_per_row = 4;
		if ( $(window).width()>=$('.icon_service').width()*1.5*8 ) {
			item_per_row = 8;
		} else if ( $(window).width()>=$('.icon_service').width()*1.5*7 ) {
			item_per_row = 7;
		} else if ( $(window).width()>=$('.icon_service').width()*1.5*6 ) {
			item_per_row = 6;
		} else if ( $(window).width()>=$('.icon_service').width()*1.5*5 ) {
			item_per_row = 5;
		}
		var width_container = Math.floor($(window).width()/item_per_row);
		var width_margin = Math.floor((width_container-$('.app_thumbnail').width())/2);
		$('.sAppListLI').css('width',width_container);
		$('.app_thumbnail').css('margin-left',width_margin);
		$('.app_thumbnail_others').css('margin-left',width_margin);
	}
}

function updateContentHeight(){
	var content_height = window.innerHeight - document.getElementById("banner").offsetHeight
											  - document.getElementById("toolbar").offsetHeight; 
	if (document.getElementById("bmBar")!=null && document.getElementById("bmBar").style.display != "none")	
		content_height = content_height - document.getElementById("bmBar").offsetHeight;
		
 	return content_height;
}

function updateButton(backUrl){
	if (backUrl!=''){
		if (backUrl=='showback_history') {
			$('#menu_left_btn').unbind('click').click(function(){
				getHTML('javascript:history.go(-1)','content', 'click_back');
			});
			$('#menu_left_btn').show();
		} else if (history_links.length-2 >= 0) {
			backUrl = history_links[history_links.length-2];

			if ( backUrl!='' && backUrl!=null ) {
				$('#menu_left_btn').unbind('click').click(function(){
					getHTML(backUrl,'content', 'click_back');
					if (backUrl.indexOf('webapp_myusage')>-1){
						updateButton(myAccountServer+'/servlet/webapp_myacc?layout=0&lang='+backUrl.substring(backUrl.indexOf('lang=')+5,backUrl.length));
					}
				});
				$('#menu_left_btn').show();
			}
		}
	} else {
		$('#menu_left_btn').hide();
	}
}

// Functions
function displayBMMenu(nextSec, currSec){
	if (nextSec!=-1){
		$('#section'+nextSec).show().css('z-index','1');
		$('#section'+currSec).hide().css('z-index','0');

		if (lastUrl.indexOf('?')>-1)
			lastUrl = lastUrl.substr(0,lastUrl.indexOf('?'));
		lastUrl = lastUrl+'?sec='+nextSec;

		var listLength = $('#section_total').val();
		var prevSecInt = (nextSec>0?nextSec-1:-1);
		var nextSecInt = (nextSec<listLength-1?nextSec+1:-1);

		updateSectionBar(prevSecInt, nextSecInt, nextSec);
		history_links.pop();
		history_links.push('/wmc/jsp/main/stapps2_bm.jsp?sec='+nextSec);

		if (myScroll!=null) {
			$('.iScrollVerticalScrollbar').css('display','block');
			myScroll.refresh();
			$('.iScrollVerticalScrollbar').css('display','none');
			myScroll.scrollTo(0,0);
		}
	}
}

function updateSectionBar(prevSec, nextSec, currSec){
	$('#sectionBar').empty().append(
		'<table style="height:40px">'+
			'<tr align="center" valign="middle">'+
				'<td width="5%" onclick="displayBMMenu('+prevSec+','+currSec+')">'+
					'<img src="/xhtml/bimg/shortcut/common/btn-prev.png" width="25" height="25" style="'+(prevSec==-1?"opacity:0.5;":"")+'"></td>'+
				'<td id="sectionTitle" class="sectionTitle2">'+$('#section_name'+currSec).val()+'</td>'+
				'<td width="5%" onclick="displayBMMenu('+nextSec+','+currSec+')">'+
					'<img src="/xhtml/bimg/shortcut/common/btn-next.png" width="25" height="25" style="'+(nextSec==-1?"opacity:0.5;":"")+'"></td>'+
			'</tr>'+
		'</table>');
	$('#bmBar').show();
	$('#wrapper').css('height', updateContentHeight());
}

function changeLanguage(lang){
	if (lang==0) lang=1;
	else lang=0;
	var tmpUrl = lastUrl;
	if (tmpUrl.indexOf('lang=')>-1)
		tmpUrl = tmpUrl.substring(0,tmpUrl.indexOf('lang=')-1);
	tmpUrl = tmpUrl + (tmpUrl.indexOf('?')<0?'?':'&')+'lang='+lang;
	
	$('#add_to_hs_word').html(txt_add_to_hs_word[lang]);
	var tmpImg = $('#menu_left_btn').attr("src");
	if (lang==0)
		$('#menu_left_btn').attr("src",tmpImg.replace('btn-back2.png','btn-back2-tc.png'));
	else $('#menu_left_btn').attr("src",tmpImg.replace('btn-back2-tc.png','btn-back2.png'));
	getHTML('stapps2_footer.jsp?sec='+lastSection+'&lang='+lang,'toolbar');
	getHTML(tmpUrl, 'content', 'not_save_history');
	lastUrl = tmpUrl;
	
	if (myScroll!=null) {
		$('.iScrollVerticalScrollbar').css('display','block');
		myScroll.refresh();
		$('.iScrollVerticalScrollbar').css('display','none');
	}
}

function changeMenuIcon(item, total){
	// Turn off all icons
	for (var i=0;i<total;i++){
		var img_loc = $('#menu_icon'+i).attr('src');
		img_loc = img_loc.replace('-on','-off');
		$('#menu_icon'+i).attr('src',img_loc);
		$('#btn_menu'+i).removeClass().addClass('btn_menu_off');
	}
	if (item!=2) $('#bmBar').hide();
	$('#wrapper').css('height', updateContentHeight());
	
	// Turn on the selected icon
	var img_loc = $('#menu_icon'+item).attr('src');
	img_loc = img_loc.replace('-off','-on');
	$('#menu_icon'+item).attr('src',img_loc);
	$('#btn_menu'+item).removeClass().addClass('btn_menu_on');
	lastSection = item;
	
	// Turn off the bottom shadow if "Featured"
	if (item==0)
		$('#bottom_shadow').hide();
	else $('#bottom_shadow').show();
}

function changePromoBanner(bVal,total){//alert(bVal+","+total+","+parseInt($('#promo_num').val()));
	if (genTimer != null) {
		clearTimeout(genTimer);
		genTimer = null;
	}
	var currPromo = parseInt($('#promo_num').val());
	for (var i=0;i<total;i++){
		if (document.getElementById("promo_banner"+i)!=null && document.getElementById("promo_banner"+i).style.display != "none")	
			currPromo = i;
		$('#promo_banner'+i).hide();
	}
	
	var nextPromo = (currPromo+bVal);//alert(nextPromo);
	if (nextPromo<0) nextPromo = total-1;
	if (nextPromo>=total) nextPromo = 0;
	//alert(nextPromo);
	$('#promo_num').val(nextPromo);
	//$('#promo_banners').css('marginLeft',(-320 * nextPromo) + 'px'); 
	$('#promo_banner'+nextPromo).show();
	autoPromoSlideshow();
}

function alertBoxSize() {
	var image_width = $(window).width();
	var image_height = $(window).height();
	var tmp_factor = 0.9;
	while ( image_width>$(window).width()*0.88 || image_height>$(window).height()-80 ) {
		image_height = Math.round($(window).height()*tmp_factor);
		image_width = Math.round(image_height/(owlImg_maxheight/owlImg_maxwidth));
		tmp_factor = tmp_factor - 0.05;
	}
	var image_container_width = image_width+20;
/*$('#alert_close').html(image_width+'-'+image_height+'-'+owlImg_maxwidth+'-'+owlImg_maxheight+'-'+(Math.round($(window).width()/2)-Math.round(image_container_width/2)));*/
	$('.svc2_scroll_img2').css({
		width: image_width,
/*		height: image_height*/
	});

	if (owlScroll2 != null) {
		owlScroll2.data("owlCarousel").options.itemWidth = image_container_width;
		owlScroll2.data("owlCarousel").reload();
	}

	$('#alert').find('.owl-wrapper').css({
		marginTop: Math.round($(window).height()/2)-Math.round(image_height/2),
		marginLeft: Math.round($(window).width()/2)-Math.round(image_container_width/2)
	});
}

/*for blackberry----------*/
function onSTClickTag(sType, sUrl , sHomeIcon, sHomeItemTitle) { 
    var result = sType +" " + encodeURI(sUrl) + " " + encodeURI(sHomeIcon) 
	          + " " + encodeURI(sHomeItemTitle); 
    navigator.cascades.postMessage( result );
    return '';
} 

function ST_onClick(elem) {

  /*st_type="0" st_title="ST App" st_link="http://wap.smartone.com/bm_win/?/ext/youtubehdrez/" st_icon="http://widg.smartone.com/service-panel/WindowsPhone/images/v1/chi/youtube.png" */
  if (elem.getAttribute('st_type') != null) {
  	   sLang = elem.getAttribute('st_lang');
  	   if (sLang ==null) 
  	        sLang='0';
  	   sType = elem.getAttribute('st_type');
  	   sUrl = elem.getAttribute('st_link');
  	   sHomeIcon = elem.getAttribute('st_icon');
  	   sHomeItemTitle = elem.getAttribute('st_title');
  	   if (sHomeItemTitle ==null) 
  	        sHomeItemTitle='ST APP';
  	   sDownloadLink = elem.getAttribute('st_dllink');
  	   if (sDownloadLink ==null)
  	   		sDownloadLink='null';
  	        
	     var result = sType +" " + encodeURI(sUrl) + " " + encodeURI(sHomeIcon) 
	          + " " + encodeURI(sHomeItemTitle)+" " +sLang + " " +encodeURI(sDownloadLink); 
	          
       navigator.cascades.postMessage( result );
  }
	 
  return false;
}

function ST_Alert(msg) {
    navigator.cascades.postMessage( '9001 ' + msg ); 
}

function update_zonecounter(zone, gourl, addvalue){
	if (addvalue > 0) {
		var times = parseInt(getCookie('sa_'+zone));
		if (times >= 0) {
			times = times + 1;
		} else {
			times = 1;
		}
		setCookie('sa_'+zone, times, 0);
	}

	if (gourl != '') {
		window.location.href = gourl;
	} else {
		return true;
	}
}

    function setCookie(c_name,value,exdays)
    {	exdays=1825;
      var exdate=new Date();
      exdate.setDate(exdate.getDate() + exdays);
      var c_value=escape(value) + ((exdays==null) ? "" : ("; expires="+exdate.toUTCString()));
      document.cookie=c_name + "=" + c_value;
    }

    function getCookie(c_name)
    {
     var i,x,y,ARRcookies=document.cookie.split(";");
     for (i=0;i<ARRcookies.length;i++)
     {
      x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
      y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
      x=x.replace(/^\s+|\s+$/g,"");
      if (x==c_name)
      {
       return unescape(y);
      }
     }
    }
/*for blackberry----------*/

function showDetail(img_name, div_name, scrollupvalue){
	if (document.getElementById(div_name).style.display == 'none'){
		window.setTimeout(function() {
			window.scrollBy(0, scrollupvalue);
		},300);
	  $('#'+div_name).slideDown('fast', function() {
	  	document.getElementById(img_name).src='/xhtml/bimg/shortcut/common/arrow_up.png';
	  });
	} else {
		$('#'+div_name).slideUp('fast', function() {
			document.getElementById(img_name).src='/xhtml/bimg/shortcut/common/arrow_down.png';
	  });
	}
	myScroll.refresh();
}
