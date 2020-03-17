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
    <title><spring:message code="URL.TITLE" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">    
    <link href="/css/reset.css" rel="stylesheet" />
    <link href="/css/global.css" rel="stylesheet" />
    <link href="/css/page.css" rel="stylesheet" />
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/jscript" src="/SE/js/HuskyEZCreator.js" ></script>
    <script src="/js/popup.js"></script>
    <script type="text/javascript" src="/js/cms_display.js"></script>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="/js/cms_agent.js"></script>
    <link rel="stylesheet" href="/css/needpopup.css">
	<style>
		.joinSubTxt{
			float:right;
		} 
		.id-pw-warning{
			font-weight: bold;
			color : #EA4335;
		}
		.id-pw-posible{
			font-weight: bold;
			color : #34A853;
		}
		.join_pop_main_subTxt{
			float:left;
			font-weight: bold;
		}
		.pw_pop_main_subTxt{
			float:left;
			font-weight: bold;
		}		
	</style>
	
</head>
<body>
<noscript>자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
<div id="wrapper">
<form:form name="regist" commandName="regist" method="post" action="/backoffice/basicManage/centerUpdate.do">	   	  
<form:hidden path="centerId" id="centerId"/>
<form:hidden path="mode" id="mode" value="${regist.mode }"/>
<form:hidden path="pageIndex" />
<form:hidden path="pageSize" />
<form:hidden path="searchCondition" />
<form:hidden path="searchKeyword" />
<form:hidden path="restInfo" />
<form:hidden path="meetingroomInfo" />
<c:import url="/backoffice/inc/top_inc.do" />
<input type="hidden" id="hid_Edit">

<form:hidden path="restInfo" />
<form:hidden path="meetingroomInfo" />
<form:hidden path="centerInfo" />


<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span><spring:message code="menu.menu03" /><span>></span><strong><spring:message code="menu.menu03_3" /></strong>
                    </div>
                </div>
            </div>

            <div class="Swrap tableArea viewArea">
                <div class="view_contents">
                <table class="pop_table thStyle">
                <tbody>
                    <tr>
                        <th><spring:message code="page.center.centerId" /></th>
                        <td style="text-align:left">
                        ${regist.centerId }
                        </td>
                        <th><spring:message code="page.center.centerNm" /></th>
                        <td style="text-align:left">
                        <span class="lable">${regist.centerNm }</span>
                        <span class="type">
                        <form:input title="센터명" path="centerNm"  />
                        </span>
                        </td>
                        <th><spring:message code="page.center.adminApprovalYn" /></th>
                        <td style="text-align:left">
                            <input type="radio" name="adminApprovalYn" value="Y" <c:if test="${regist.adminApprovalYn == 'Y' }"> checked </c:if> /><label>사용</label>
							<input type="radio" name="adminApprovalYn" value="N" <c:if test="${regist.adminApprovalYn == 'N' }"> checked </c:if> /><label>사용안함</label>
                        </td>
                    </tr>
                    <tr id="centerInfo01">
                        <th><spring:message code="page.center.centerImg" /></th>
                        <td style="text-align:left" colspan="2">
                           <span class="lable">
                            <c:choose>
				                <c:when test="${regist.centerImg eq ''}">
				                  <img id="atchFileId" name="atchFileId" src="/images/no_image.png"  width="100" height="60"/>
				                </c:when>
				        		<c:otherwise>
				        		  <img src="<c:url value='/upload/${regist.centerImg}' />" alt="${regist.centerNm }" width="150px;" />
				        		</c:otherwise>					        							        
						    </c:choose>
						    </span>
						    <span class="type">
						    <input name="centerImg" id="centerImg" type="file"  size="20"/>
						    </span>
						    
						    
                        </td>
                        <th><spring:message code="page.center.centerSeatImg" /></th>
                        <td style="text-align:left" colspan="2">
                        <span class="lable">
                        <c:choose>
				                <c:when test="${regist.centerSeatImg eq ''}">
				                  <img id="atchFileId" name="atchFileId" src="/images/no_image.png"  width="100" height="60"/>
				                </c:when>
				        		<c:otherwise>
				        		  <img src="<c:url value='/upload/${regist.centerSeatImg}' />" alt="${regist.centerSeatImg }" width="150px;" />
				        		</c:otherwise>					        							        
						    </c:choose>
						</span>
						<span class="type">
						<input name="centerSeatImg" id="centerImg" type="file"  size="20"/>
						</span>
                        </td>
                    </tr>
                    <tr id="centerInfo02" >
                        <th><spring:message code="page.center.centerAddr" /></th>
                        <td colspan="5"  style="text-align:left">
                        <span class="lable">
                        ${regist.centerZipcode }<br />
                        ${regist.centerAddr1 } ${regist.centerAddr2 }
                        </span>
                        <span class="type">
                            <form:input title="우편번호" path="centerZipcode1"  id="centerZipcode1" name="centerZipcode1" size="3" maxlength="3" />
                            <label>-</label>
                            <form:input title="우편번호" path="centerZipcode2" id="centerZipcode2"  size="3"  maxlength="3"/>
                            <p class="margin-top10">
                                <form:input title="주소1" path="centerAddr1"  id="centerAddr1" size="60" />
                                <form:input title="주소1" path="centerAddr2"  id="centerAddr2" size="60" />
                            </p>
                        </span>
                        
                        
                        </td>
                    </tr>
                    <tr id="centerInfo03">
                        <th><spring:message code="page.center.centerTel" /></th>
                        <td style="text-align:left"> 
                        <span class="lable">${regist.centerTel }</span>
                        <span class="type">
                        <form:input title="연락처" path="centerTel"  id="centerTel" size="20" maxlength="20"/>
                        </span>
                        </td>
                        <th><spring:message code="page.center.centerFax" /></th>
                        <td style="text-align:left">
                        <span class="lable">${regist.centerFax }</span>
                        <span class="type">
                        <form:input title="연락처" path="centerFax"  id="centerFax" size="20" maxlength="20"/>
                        </span>
                        
                        </td>
                        <th><spring:message code="page.center.centerUrl" /></th>
                        <td style="text-align:left">
                        <span class="lable">${ regist.centerUrl}</span>
                        <span class="type">
                        <form:input title="연락처" path="centerUrl"  id="centerUrl" size="15" />
                        </span>
                        </td>
                    </tr>
                    <tr id="centerInfo04">
                        <th><spring:message code="page.center.groupNm" /></th>
                        <td style="text-align:left">
                          <span class="lable"> ${regist.partNm }</span>
                          <span class="type">
                           <form:select path="partId" id="partId" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectGroupCombo}" itemValue="partId" itemLabel="partNm"/>
							</form:select>
                          </span>
                        </td>
                        <th><spring:message code="page.center.centerUseYn" /></th>
                        <td style="text-align:left">
                            <input type="radio" name="centerUseYn" value="Y" <c:if test="${regist.centerUseYn == 'Y' }"> checked </c:if> /><label>사용</label>
							<input type="radio" name="centerUseYn" value="N" <c:if test="${regist.centerUseYn == 'N' }"> checked </c:if> /><label>사용안함</label>
                        </td>
                        <th><spring:message code="page.center.centerFloor" /></th>
                        <td style="text-align:left">
                        <span class="lable">
                           ${regist.centerFloorTxt }~${regist.centerFloorEndTxt }
                        </span>
                        <span class="type">
                            <form:select path="centerFloor" id="centerFloor" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectFloorCombo}" itemValue="code" itemLabel="codeNm"/>
							</form:select>~
							<form:select path="centerFloorEnd" id="centerFloorEnd" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectFloorCombo}" itemValue="code" itemLabel="codeNm"/>
							</form:select>
                        </span>
                        </td>
                    </tr>
                    <tr id="centerInfo05">
                        <th><spring:message code="page.center.centerInfo" /></th>
                        <td colspan="5" style="text-align:left">
                        <span class="lable">${regist.centerInfo }</span>
                        <span class="type">
                        <textarea name="ir3" id="ir3" style="width:1000px; height:50px; display:none;">${regist.centerInfo }</textarea>
                        </span>
                        </td>
                    </tr>
                </tbody>
            </table>
            </div>
            <div class="view_contents" id="agent_state" style="margin-top:20px;">
                 <span id="sp_agentInfo"></span>               
            </div>
            </div>
            <div class="footerBtn">
	            <a href="javascript:fn_Center();" class="redBtn" id="btnUpdate"><spring:message code="button.update" /></a>
	            <a href="javascript:fn_viewAgent();" id="btn_centerInfo" class="redBtn" id="btnUpdate">에이전트 보여주기</a>
	            <a href="javascript:del_form();" class="redBtn" id="btnDel"><spring:message code="button.delete" /></a>
	            <a href="javascript:fn_linkPage('/backoffice/basicManage/centerList.do', 'regist');" class="deepBtn"><spring:message code="button.list" /></a>
	        </div>
        </div>
    </div>
  
    
<c:import url="/backoffice/inc/bottom_inc.do" />    
</form:form>   	
</div>
<script type="text/javascript">
      var oEditors = [];
      google.load("visualization", "1", {packages:["corechart"]});
	  function del_form(){
		  if ($("#btnDel").text() == "취소"){
				$("#mode").val("Edt");
				$("#btnDel").text('삭제');
				fn_Center();
			}else{
				if (confirm("삭제 하시겠습니까?")== true){
					fn_uniDelJSON("/backoffice/basicManage/centerDelete.do"
							  , {centerId : $("#centerId").val()}
					          , "/backoffice/basicManage/centerList.do");
				}
			}
		    	  
	  }
	  $(document).ready(function() {
		  //모드에 따라 보여주기 
		   fn_centerInfo();
	  });
	  function fn_centerInfo(){
		  //alert("fn_centerInfo:" + $("#mode").val());
		  var tr_style = $("#mode").val() == "Edt"?"":"none";
		  var agent_style = $("#mode").val() == "Edt"?"none":"";
		  var btn_txt = $("#mode").val() == "Edt"  ? "에이전트 현황 보기" : "지점 정보 보기";
		  for (var i = 1; i < 6; i++){
			  $("#centerInfo0"+i).css("display",tr_style);
		  }
		  $("#agent_state").css("display",agent_style);
		  $("#btn_centerInfo").text(btn_txt);
		  
		  $(".lable").show();
		  $(".type").hide();
		  $("#sp_flooerInfo").hide();
		  $("#btnUpdate").text("수정");
		  $("#btnDel").text("삭제");
		  if ($("#mode").val() == "AGT"){
			  $("#mode").val("Pre");
		  }else {
			  $("#mode").val("Edt");
		  }
		  
	  }
	  function fn_viewAgent(){
		  //alert("fn_viewAgent:" + $("#mode").val());
		  if ($("#mode").val() != "Edt" ){
			  $("#mode").val("Edt");
		  }else {
			  
			  $("#mode").val("AGT");
			  var params = { centerId: $("#centerId").val() }; 
			  uniAjax("/backoffice/basicManage/AgentInfoCenterList.do", params, 
		     			function(result) {
				              
						       if (result.status == "LOGIN FAIL"){
						    	    alert(result.message);
		  							location.href="/backoffice/login.do";
		  					   }else if (result.status == "SUCCESS"){
		  						    //여기 부분 수정 어떻게 할지 추후 생각
		  						    var oldFloor = "";	
		  						    var Floor = "";
		  						    var rowCnt = 0;
		  						    didDetailHtm = "<table class='pop_table thStyle'><tbody>";
		  						    
		  						    for (var i=0; i<result.agentList.length; i++) {
	 									var obj = result.agentList[i];
	 									var floor = obj.agentFloor
	 									
	 									if (oldFloor != floor){
	 										if (parseInt(rowCnt) < 4 && parseInt(rowCnt) > 0 ){
	 											for (var a = rowCnt; a < 4; a++){
	 												didDetailHtm += "<td></td>";	
	 											}
	 										}
	 										didDetailHtm += "<tr><td colspan='4' sytle='text-align:left'>"+ floor +"층</td></tr><tr>";	
	 									}
	 									
	 									didDetailHtm += "<td>";
	 									didDetailHtm +=  "<table border='0' width='100%'><tr><th>단말기명</th><th>전송화면</th><th>단말기 상태</th></tr>";
	 									didDetailHtm +=  "<tr><td>"+ obj.agentNm +"</td><td>";
	 									didDetailHtm += "<a href='javascript:fn_agentPreView(&#39;"+obj.displaySeq +" &#39;,&#39;"+obj.displayTitle +" &#39;,&#39;"+obj.contentGubun +" &#39;)'>"+ obj.displayTitle +"</a></td>";
	 									didDetailHtm +=      "<td><a href='#' onclick='fn_agentState(&#39;"+obj.agentCode +" &#39;)' data-needpopup-show='#agentState' >"+ obj.agentStateInfo +"</a></td>";
	 									didDetailHtm += "</tr></table>";
	 									didDetailHtm += "</td>";
	 									oldFloor = obj.agentFloor;
	 									rowCnt = parseInt(rowCnt) + 1; 
	 									if (parseInt(rowCnt) > 3 ){
	 										rowCnt = 0;
	 										didDetailHtm += "</tr><tr>";	
	 									}
	 									
	 									
	 								}	
		  						  didDetailHtm += "</tr></table>";
		  						  $("#sp_agentInfo").html(didDetailHtm);
		  					   }
						    },
						    function(request){
							    alert("Error:" +request.status );	       						
						    }    		
		      );	
		  }
		  fn_centerInfo(); 
	  }
	  function fn_Center(){
		  
		  if ($("#mode").val() == "Edt" && $("#btnUpdate").text() == "수정"  ){
			  
			    $(".lable").hide();
				$(".type").show();
				$("#sp_flooerInfo").show();
				$("#btnUpdate").text("입력");
				$("#btnDel").text("취소");
				$("#mode").val("Edt");
				if ($("#hid_Edit").val() ==  ""){
					
		            nhn.husky.EZCreator.createInIFrame({
		            oAppRef: oEditors,
		            elPlaceHolder: "ir3",                        
		            sSkinURI: "/SE/SmartEditor2Skin.html",
		            htParams: { bUseToolbar: true,
		                fOnBeforeUnload: function () { },
		                //boolean 
		                fOnAppLoad: function () { }
		                //예제 코드
		            },
		            fCreator: "createSEditor2"
			        });
		            $("#hid_Edit").val("Vwd");
				}
			  
		  }else if ($("#mode").val() == "Edt" && $("#btnUpdate").text() == "입력" && $("#btnDel").text() == "취소"   ){
			  
			  if (any_empt_line_id('centerNm', '<spring:message code="page.center.alert01" />') == false) return;
	          var sHTML3 = oEditors.getById["ir3"].getIR();
			  $("#restInfo").val('');
			  $("#meetingroomInfo").val('');
			  $("#centerInfo").val(sHTML3);
			  document.regist.encoding = "multipart/form-data";
			  $("form[name=regist]").attr("action", "/backoffice/basicManage/centerUpdate.do").submit();
			  
		  }else {
			    $(".lable").show();
				$(".type").hide();
				$("#sp_flooerInfo").hide();
				$("#btnUpdate").text("수정");
				$("#btnDel").text("삭제");
				$("#mode").val("Pre");
		  }	
			  
	 }
	  //미리보기 기능 
	 function fn_DisplayInfo(code){
		  
	 }
</script>
</body>
</html>