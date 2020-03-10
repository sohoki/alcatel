<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Content-Language" content="ko" >
    <title><spring:message code="URL.TITLE" /></title>
    <link href="/css/reset.css" rel="stylesheet" />
    <link href="/css/global.css" rel="stylesheet" />
    <link href="/css/page.css" rel="stylesheet" />
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
<style>
body{background-color:#000; color:#fff;}
a, span, div{color:#fff;}
#container{
padding-left:4px;
}
.redBtn{font-size: 12px;padding: 0px 8px;border-radius: 4px;}
</style>
</head>
<body>
<noscript>자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
	<span id="backgroundgif"></span>


	<form:form name="regist" commandName="regist" method="post" >
	<input type="hidden" name="mediaType" id="mediaType"  value="${regist.mediaType }" />
  	<input type="hidden" name="atchFileId" id="atchFileId"  value="${regist.atchFileId }" />
  	<input type="hidden" name="fileWidth" id="fileWidth"  value="${regist.fileWidth}" />
  	<input type="hidden" name="fileHeight" id="fileHeight"  value="${regist.fileHeight }" />
  	<input type="hidden" name="playTime" id="playTime"  value="${regist.playTime }" />
  	<input type="hidden" name="streFileNm" id="streFileNm"  value="${regist.streFileNm }" />
	<c:if test="${fn:startsWith(regist.fileStreCours , '//')}">
	   <input type="hidden" name="fileStreCours" id="fileStreCours"  value='${fn:replace(regist.fileStreCours, "//", "/upload/")}' />
	</c:if>
	<c:if test="${!fn:startsWith(regist.fileStreCours , '//')}">
	   <input type="hidden" name="fileStreCours" id="fileStreCours"  value="${regist.fileStreCours }" />
	</c:if>
	
  	
  	
  	
  	
  	
	<div id="container" style="top:16px; width:100%;">
		<!--내용시작-->		
		<span id="mainText"></span>
		<span id="wText"></span><span id="fileW"></span><span id="hText"></span><span id="fileH"></span><a class="redBtn" onclick="pageClose();">확인완료</a>
		
		<!--
		<span id="mainText">파일정보</span>
		<span id="wText">넓이 : </span><span id="fileW"></span><span id="hText">, 높이 : </span><span id="fileH"></span>-->
		<c:if test="${regist.mediaType eq '동영상' }">
			<br>재생시간 : <span id="fileD"></span>
		</c:if>
		<br><br>
		<c:choose>
			<c:when test="${empty regist.fileThumnail}">
				<img name="atchFileId" src="<c:url value='${regist.fileStreCours}${regist.streFileNm}'/>"  width="320px" id="contentImg"/>
			</c:when>
			<c:otherwise>
				<video width="320px" autoplay loop id="viewVideo">
					<c:if test="${fn:startsWith(regist.fileStreCours , '//')}">
					1
					   <source src="<c:url value='${fn:replace(regist.fileStreCours, "//", "/upload/")}${regist.streFileNm}'/>" id="${regist.streFileNm}" type="video/${regist.fileExtsn}">
					</c:if>
					<c:if test="${!fn:startsWith(regist.fileStreCours , '//')}">
					2
					   <source src="<c:url value='${regist.fileStreCours}${regist.streFileNm}'/>" id="${regist.streFileNm}" type="video/${regist.fileExtsn}" />
					</c:if>
				</video>	
			</c:otherwise>					        							        
		</c:choose>
		<br>
	</div>
	</form:form>
    <script type="text/javascript">
    
    /* IMAGE LOADING */
    var imageWidth = "0";
    var imageHeight = "0";
    var loadComplete = false;
    var documentReady = false;
    var defferentValue = false;
    $("img").one("load", function() {         
    	
    	
    	
       	imageWidth = this.naturalWidth;
       	imageHeight = this.naturalHeight;
       	loadComplete = true;
       	
       	if(documentReady){
       		imageSettingComplete();
       	}       	
       	
       	if(imageWidth != $("#fileWidth").val() && imageHeight != $("#fileHeight").val()){
			//업데이트 된 값과 현재 값에 차이가 있음
			if(documentReady){
				imageSettingComplete();	
			} else {
				defferentValue = true;	
			}
       	}

	 }).each(function() {
		    if(this.complete) jQuery(this).trigger('load');
	 });
    
    
    $(document).ready(function(){
		$("#mainText").text("콘텐츠 로딩중...");
		
    	if ($("#fileHeight").val()!= "" && $("#mediaType").val() == "IMAGE"){
    		if($("#fileWidth").val()-$("#fileHeight").val() > 0){
    			// 가로
    			$("Img").attr("width", "640px");
    		}else{
    			// 세로
    			$("Img").attr("width", "360px");
    		}
			$("#mainText").text("파일정보 | ");
			$("#wText").text("너비 : ");
			$("#fileW").text($("#fileWidth").val() );
			$("#hText").text(", 높이 : ");
			$("#fileH").text($("#fileHeight").val() + " | ");
		}else {
			loadingStart();
			var fileUrl = "http://"+$(location).attr('host')+$("#fileStreCours").val()+$("#streFileNm").val();
			
			
    		// var fileUrl = "http://"+$(location).attr('host')+"/upload/"+ $("#streFileNm").val().substring(0,6) +"/"+$("#streFileNm").val();
    		
        	if ($("#mediaType").val() == "IMAGE" ){
        		documentReady = true;
        		if(loadComplete){
        			// load완료
        			imageSettingComplete();    
        		}
        	}else {
        		mediaUpdate(fileUrl);
        		
        	}
    	}
    	
    	if(!defferentValue){
    		// 아직 이미지 로딩이 끝나지 않은 상태임
    		documentReady = true;
    	} else {
    		// 이미지 로딩이 완료 된 상태
    		imageSettingComplete();
    	}
    	
	
    });
    
	function imageSettingComplete(){
	    
	 	$("#mainText").text("미디어 로딩 완료 ");
		$("#wText").text("");
		$("#fileW").text("");
		$("#hText").text("");
		$("#fileH").text("");
		$("#fileD").text("");
	    
		callback(imageWidth,imageHeight );
	    
	}
    
    
    function mediaUpdate(url){    	
    	
    	var video=document.createElement("video");
  	    video.autoplay=true;
  	    video.oncanplay=function(){

		if($("#fileWidth").val()-$("#fileHeight").val() > 0){
			// 가로
			$("#viewVideo").attr("width", "640px");
		}else{
			// 세로
			$("#viewVideo").attr("width", "360px");
		}
  	    
  	    
  	    	
  	     $("#fileWidth").val(this.offsetWidth);
		 $("#fileHeight").val(this.offsetHeight);
		 $("#playTime").val(this.duration);

		$("#mainText").text("파일정보 | ")
		$("#wText").text("너비 : ");
		$("#fileW").text($("#fileWidth").val() );
		$("#hText").text(", 높이 : ");
		$("#fileH").text($("#fileHeight").val() + " | ");
		$("#fileD").text(    $("#playTime").val() );
		 
		 
    	 callback(this.offsetWidth, this.offsetHeight);
    		
  		 this.src="about:blank";
  		 document.body.removeChild(video);   
  	   };
  	  document.body.appendChild(video);
  	  video.src=url;
    }
    function callback(w, h){
    	
    	 apiExecute(
				  "POST", 
				  "/backoffice/sub/conManage/conFileUpdate.do",
 					{
					  atchFileId : $("#atchFileId").val(),
					  playTime : fn_emptyReplace($("#playTime").val(), "1") ,
					  fileWidth : w,
					  fileHeight : h
 					},
 					null,				
 					function(result) {							
  						if (result != null) {	         							
  							
  							if (result == "O"){
  								if($("#playTime").val() == ""){
  									// IMAGE
  									if($("#fileWidth").val()-$("#fileHeight").val() > 0){
  	  									// 가로
  	  					    			$("Img").attr("width", "640px");
  	  					    		}else{
  	  					    			// 세로
  	  					    			$("Img").attr("width", "360px");
  	  								}
  								}else{
  									// MEDIA
  									if($("#fileWidth").val()-$("#fileHeight").val() > 0){
  	  									// 가로
  	  					    			$("#viewVideo").attr("width", "640px");
  	  					    		}else{
  	  					    			// 세로
  	  					    			$("#viewVideo").attr("width", "360px");
  	  								}
  								}
  								
  								
  								

  							}else {
  								alert("등록시 문제가 생겼습니다.");
  								
  							}
  						}
  						loadingFinish();
  					},
 					null,
 					null
				);	
    }
    function pageClose(){
    	
    	var href = opener.document.location.href;
    	var l_i = Number(href.lastIndexOf("/"))+1;
    	var path = href.substring(l_i);
    	if(path == "conMutiView_back.do"){
    		window.opener.contentLst();
    	} else {
        	opener.document.location.reload();
    	}
    	self.close();
    	
    	
    }
	function loadingStart(){
	   	var loadingimg = "<div style='width:100%;height:100%;z-index:100;background-color:#ffffff;opacity:0.6; position:fixed;text-align:center;vertical-align:middle;'><img src='/images/loading_img.gif'></img></div>";    	
	   	$("#backgroundgif").html(loadingimg);
	}
	function loadingFinish(){
		$("#backgroundgif").html("");
	}
    </script>
</body>
</html>		