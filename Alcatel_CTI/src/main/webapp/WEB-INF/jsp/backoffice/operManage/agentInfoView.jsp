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
    <title><spring:message code="URL.TITLE" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">    
    <link href="/css/reset.css" rel="stylesheet" />
    <link href="/css/global.css" rel="stylesheet" />
    <link href="/css/page.css" rel="stylesheet" />
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/cms_display.js"></script>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="/js/cms_agent.js"></script>
    <script src="/js/popup.js"></script>
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
<div id="wrapper">	
<form:form name="regist" commandName="regist" method="post" action="/backoffice/contentManage/displayList.do">
<c:import url="/backoffice/inc/top_inc.do" />
<input type="hidden" name="pageIndex" id="pageIndex" value="${regist.pageIndex }">
<input type="hidden" name="mode" id="mode" value="${regist.mode }">
<form:hidden path="pageSize" />
<form:hidden path="searchCondition"/>
<form:hidden path="searchKeyword" />
<form:hidden path="agentCode" />


<input type="hidden" name="agentPageIndex" id="agentPageIndex">
<input type="hidden" name="agentSearchCondition" id="agentSearchCondition">
<input type="hidden" name="agentSearchKeyword" id="agentSearchKeyword">
<input type="hidden" name="popGubun" id="popGubun">


<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span>
                        <spring:message code="menu.menu01" /><span>
                        <span>></span>
                        <strong><spring:message code="menu.menu01_3" /><span></strong>
                    </div>
                </div>
            </div>
            <div class="Swrap tableArea viewArea">
                <div class="view_contents">
                <table class="pop_table thStyle">
	                <tbody>
	                    <tr>
	                        <th><spring:message code="page.agent.agentCode" /></th>
	                        <td>${regist.agentCode }</td>
	                        <th><spring:message code="page.agent.IP" /></th>
	                        <td style="text-align:left">${regist.agentIp }
	                        </td>	
	                        <th><spring:message code="page.agent.Mac" /></th>
	                        <td style="text-align:left">${regist.agentMac }</td>
	                    </tr>
	                    <tr>
	                        <th><spring:message code="page.agent.TitleNm" /></th>
	                        <td>
	                        <span class="lable">${regist.agentNm }</span>
	                        <span class="type">
	                        <form:input  path="agentNm" size="20" maxlength="100" id="agentNm"   value="${regist.agentNm }" />
	                        </span>
	                        </td>
	                        <th><spring:message code="page.agent.remark" /></th>
	                        <td colspan="3">
	                        <span class="lable">${regist.agentRemark }</span>
	                        <span class="type">
	                        <form:input  path="agentRemark" size="80" maxlength="120" id="agentRemark"   value="${regist.agentRemark }" />
	                        </span>
	                        </td>	                        
	                    </tr>
	                    <tr>
	                        <th><spring:message code="page.center.centerNm" /></th>
	                        <td>
	                        <span class="lable">${regist.centerNm }</span>
	                        <span class="type">
	                        <form:select path="centerId" id="centerId" title="소속" onChange="javascript:fn_FlooerView()">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectCenterCombo}" itemValue="centerId" itemLabel="centerNm"/>
							</form:select>
							</span>
	                        </td>
	                        <th><spring:message code="page.agent.floor" /></th>
	                        <td style="text-align:left">
	                        <span class="lable">${regist.agentFloor }</span>
	                        <span id="sp_flooerInfo"></span>
	                        </td>
	                        <th><spring:message code="page.group.groupNm" /></th>
	                        <td style="text-align:left">
	                        <span class="lable">${regist.partNm }</span>
	                        <span class="type">
	                        <form:select path="partId" id="partId" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectGroupCombo}" itemValue="partId" itemLabel="partNm"/>
							</form:select>
							</span>
	                        </td>	
	                    </tr>
	                    <tr>
	                        <th><spring:message code="page.agent.osType" /></th>
	                        <td>
	                        <span class="lable">${regist.osType }</span>
	                        <span class="type">
	                        <form:select path="agentGubun" id="agentGubun" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectAgentGubun}" itemValue="code" itemLabel="codeNm"/>
							</form:select>
							</span>
	                        </td>
	                        <th><spring:message code="page.agent.startTime" /></th>
	                        <td>
	                        <span class="lable">${regist.agentStarttime }</span>
	                        <span class="type">
	                        <form:input  path="agentStarttime" size="10" maxlength="5" id="agentStarttime"   value="${regist.agentStarttime }" />
	                        </span>
	                        </td>
	                        <th><spring:message code="page.agent.endTime" /></th>
	                        <td>
	                        <span class="lable">${regist.agentEndtime }</span>
	                        <span class="type">
	                        <form:input  path="agentEndtime" size="10" maxlength="5" id="agentStarttime"   value="${regist.agentEndtime }" />
	                        </span>
	                        </td>
	                    </tr>
	                    <tr>
	                        <th><spring:message code="page.agent.displayNm" /></th>
	                        <td style="text-align:left">
	                        <span class="lable">${regist.displayTitle }</span>
	                        <span class="type">
	                        <form:select path="displaySeq" id="displaySeq" title="소속">
										     <option value=""><spring:message code="combobox.text" /></option>
					                         <form:options items="${selectCodeDM}" itemValue="displaySeq" itemLabel="displayTitle"/>
							</form:select>
							</span>
	                        </td>
	                        <th><spring:message code="page.agent.contentGubun" /></th>
	                        <td style="text-align:left">
	                        <span class="lable">${regist.contentGubun }</span>
	                        <span class="type">
		                        <form:select path="agentContentgubun" id="agentContentgubun" title="소속">
										     <option value=""><spring:message code="combobox.text" /></option>
					                         <form:options items="${selectAgentContentGubun}" itemValue="code" itemLabel="codeNm"/>
								</form:select>
							</span>
	                        </td>	
	                        <th><spring:message code="common.UseYn.title" /></th>
	                        <td style="text-align:left">
	                        <input type="radio" name="agentUseYn" value="Y" <c:if test="${regist.agentUseYn == 'Y' }"> checked </c:if> />
	                        <label><spring:message code="button.use" /></label>
							<input type="radio" name="agentUseYn" value="N" <c:if test="${regist.agentUseYn == 'N' }"> checked </c:if> />
							<label><spring:message code="button.notUsed" /></label>	
	                        </td>
	                    </tr>
	                    <tr>
	                       <th><spring:message code="page.agent.state" /></th>
	                       <td>
	                        <c:choose>
							   <c:when test="${regist.agentStateInfo eq 'NORMAL' }">
									<img src="/images/on_icon.png" width="16px" height="16px" />
							   </c:when>
							   <c:when test="${regist.agentStateInfo eq 'CAUTION' }">
									<img src="/images/caution_icon.png" width="16px" height="16px" />
							   </c:when>
							   <c:otherwise>
							   		<img src="/images/danger_icon.png" width="16px" height="16px" />
							   </c:otherwise>
							</c:choose>
	                        <a href="#" onclick="fn_agentState('${ regist.agentCode }')" data-needpopup-show="#agentState" class="underline" >
                            ${ regist.agentStateInfo }</a>
	                       </td>
	                       <td colspan='4' style='text-align:right;'>     
	                           <a href="javascript:fn_agentPreView('${ regist.agentCode }','${ regist.displayTitle }','${ regist.contentGubun }')" class="grayBtn">
		                       <spring:message code="button.preview" />
		                       </a>
		                       <a onclick="fn_SendMsg('SP_AGENTREBOOT', 'agentCode', 'S')" class="grayBtn" id="btn_sendBtn01"><spring:message code="page.agent.sendBtn2" /></a>
						       <a onclick="fn_SendMsg('SP_REDOWN', 'agentCode', 'S')" class="grayBtn" id="btn_sendBtn02"><spring:message code="page.agent.sendBtn1" /></a>
		                       <a href="#" onclick="fn_msgView('S')" data-needpopup-show="#agentMessageState" class="deepBtn" id="btn_sendBtn03">
	                           <spring:message code="page.msg.button" />
	                           </a>
	                          <a href="#" onclick="fn_msgView('M')" data-needpopup-show="#agentMessageState" class="deepBtn">
	                          <spring:message code="page.agent.sendBtn3" />
	                          </a>
	                       </td>
	                    </tr>	                    
	                </tbody>
	            </table>
	            </div>
            </div>
            <div>
                <div class="view_contents">
                <table class="pop_table thStyle" id="tb_ContentInfo" >
                    <thead>
                      <tr>
                        <th>콘텐츠명</th>
                        <th style='width:150px;'>시작일~종료일</th>
                        <th style='width:120px;'>전송화면</th>
                        <th style='width:100px;'>전송여부</th>
                        <th>전송시간</th>
                        <th style='width:100px;'>적용여부</th>
                        <th>적용시간</th>
                      </tr>
                    </thead>
                    <tbody></tbody>
                </table>
                <div class="pageFooter" id="contentPaging">
					<div class="clear"></div>	
			    </div>
                </div>
            </div>
            <div class="footerBtn">
	            <a href="javascript:fn_Edit();" class="redBtn" id="btnUpdate"><spring:message code="button.update" /></a>
	            <a href="javascript:fn_Del();" class="redBtn" id="btnDel"><spring:message code="button.delete" /></a>
	            <a href="javascript:linkPage()" class="deepBtn"><spring:message code="button.list" /></a>
	        </div>
        </div>
    </div>
    <div id="agentMessageState" class="needpopup">
        <div class="popHead" id="popTitle">
            <h2>단말기 통신이력</h2>
        </div>
        <table class="margin-top30 backTable" id="tb_agentOrderList">
			<thead>
				<tr>
					<th><spring:message code="page.msg.agentNm" /></th>				  	
				  	<th><spring:message code="page.msg.processRemark" /></th>
				  	<th><spring:message code="page.msg.sendRegDate" /></th>
				  	<th><spring:message code="page.msg.sendResult" /></th>
				  	<th><spring:message code="page.msg.didPlayTime" /></th>	
				  	<th><spring:message code="page.msg.message" /></th>				  	
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<table class="margin-top30 backTable" id="tb_agentMsgList">
			<thead>
				<tr>
					<th><spring:message code="page.schMessage.schMessage" /></th>
				  	<th><spring:message code="page.schMessage.fontSize" /></th>
				  	<th><spring:message code="page.schMessage.msgSend" /></th>
				  	<th><spring:message code="page.schMessage.msgSendRegdate" /></th>	
				  	<th><spring:message code="page.schMessage.msgRecCheck" /></th>	
				  	<th><spring:message code="page.schMessage.msgRecUpdate" /></th>				  	
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<div class="pageFooter" id="schedulePaging">
			<div class="clear"></div>	
	    </div>
    </div>
<c:import url="/backoffice/inc/bottom_inc.do" />
</form:form>
	</div>
<script type="text/javascript">
	function linkPage() {
		$("form[name=regist]").attr("action", "/backoffice/basicManage/AgentInfoList.do").submit();
	}	
	$(document).ready(function() {     		
		$("#mode").val("Edt");
		fn_Edit();		
		
		if ("${regist.agentContentgubun }" == "AGENT_CONTENT_2"){
			$("#tb_ContentInfo").show();
			$("#btn_sendBtn01").show();
			$("#btn_sendBtn02").show();
			$("#btn_sendBtn03").show();
			fn_msgView("C");	
		}else {
			$("#tb_ContentInfo").hide();
			$("#btn_sendBtn01").hide();
			$("#btn_sendBtn02").hide();
			$("#btn_sendBtn03").hide();
		}
		

	});	
	
	function ajaxPageChange(code){
		 $("#agentPageIndex").val(code);
		 fn_msgView($("#popGubun").val());		 
	}
	function fn_ContentList(){
		var params = { agentCode : $("#agentCode").val(), 
				       pageIndex : fn_emptyReplace($("#agentPageIndex").val(),"1"), 
				       searchKeyword : fn_emptyReplace($("#agentSearchCondition").val(),""),
				       searchCondition : fn_emptyReplace($("#agentSearchKeyword").val(),"")
		             };
		uniAjax(url, params, 
  			function(result) {
				       if (result.status == "LOGIN FAIL"){
				    	    alert(result.meesage);
							location.href="/backoffice/login.do";
					   }else if (result.status == "SUCCESS"){
						    //테이블 정리 하기
							$("#tb_ContentInfo > tbody").empty(); 
						    
						    //페이징 처리 하기
						    var pageObj = result.paginationInfo;						
							var pageHtml = ajaxPaging(pageObj.currentPageNo, pageObj.firstPageNo, pageObj.recordCountPerPage, 
										                  pageObj.firstPageNoOnPageList, pageObj.lastPageNoOnPageList, 
										                  pageObj.totalRecordCount, pageObj.pageSize, "ajaxPageChange");
							$("#schedulePaging").html(pageHtml+"<div class='clear'></div>");	
					   }
				    },
				    function(request){
					    alert("Error:" +request.status );	       						
				    }    		
   );
	}
	function fn_Edit(){
		if ($("#mode").val() == "Edt" && $("#btnUpdate").text() == "입력"){
			   if (any_empt_line_id("agentNm", '<spring:message code="page.agent.alert1" />') == false) return;
			   if (any_empt_line_id("agentRemark", '<spring:message code="page.agent.alert2" />') == false) return;
			   $("#agentPageIndex").remove();
			   $("#agentSearchCondition").remove();
			   $("#agentSearchKeyword").remove();
			   $("#popGubun").remove();
			   
			   
			   var params = $("#regist").serializeObject(); 
			   uniAjax("/backoffice/basicManage/agentInfoUpdate.do", params, 
		     			function(result) {
				               alert(result.meesage);
						       if (result.status == "LOGIN FAIL"){
		  							location.href="/backoffice/login.do";
		  					   }else if (result.status == "SUCCESS"){
		  						    //여기 부분 수정 어떻게 할지 추후 생각
		  						    //$("form[name=regist]").attr("action", "/backoffice/basicManage/AgentInfoList.do").submit();
		  						    location.reload();
		  					   }
						    },
						    function(request){
							    alert("Error:" +request.status );	       						
						    }    		
		      );	
		}
		if ($("#mode").val() == "Edt"){
			$(".lable").show();
			$(".type").hide();
			$("#sp_flooerInfo").hide();
			$("#btnUpdate").text("수정");
			$("#btnDel").text("삭제");
			$("#mode").val("Pre");
		}else {
			
			$(".lable").hide();
			$(".type").show();
			$("#sp_flooerInfo").show();
			$("#btnUpdate").text("입력");
			$("#btnDel").text("취소");
			$("#mode").val("Edt");
			fn_FlooerView();
		}
	}
    function fn_FlooerView(){
		
		if ($("#centerId").val()  != ""){
			var params = { centerId : $("#centerId").val() };
			
			uniAjax("/backoffice/basicManage/centerInfo.do", params, 
		       			function(result) {
		  	    	            if (result.status == "SUCCESS"){
		  	    	            	if (result.regist.centerFloorTxt != "" && result.regist.centerFloorEndTxt != ""){
		  	    	            		var startFloor = result.regist.centerFloorTxt.replace(/층/gi,"");
		  	    	            		var endFloor = result.regist.centerFloorEndTxt.replace(/층/gi,"");
		  	    	            	}else if (result.regist.centerFloorTxt != "" && result.regist.centerFloorEndTxt == ""){
		  	    	            		var startFloor = result.regist.centerFloorTxt.replace(/층/gi,"");
		  	    	            		var endFloor = result.regist.centerFloorTxt.replace(/층/gi,"");
		  	    	            	}
		  	    	            	var comboHtml = "<select id='agentFloor' name='agentFloor'>";
	  	    	            	    for (var i = startFloor; i <= endFloor; i ++){
	  	    	            	    	comboHtml += "<option value='"+ i +"'>"+i+"층</option>";
	  	    	            	    }         
	  	    	            	    comboHtml += "</select>"
	  	    	            	    $("#sp_flooerInfo").html(comboHtml);
	  	    	            	    
	  	    	            	    if ('${regist.agentFloor}' != "0"){
	  	    	            	    	$("#agentFloor").val('${regist.agentFloor}');
	  	    	            	    }
		  	    	            	    
		  						}else if (result.status == "LOGIN FAIL"){
		  							location.href="/backoffice/login.do";
		  						}else{
		  							alert(result.message);
		  						}
		  				    },
		  				    function(request){
		  					    alert("Error:" +request.status );	       						
		  				    }    		
		    );
		}
		 
	}
	function fn_Del(){
		if ($("#btnDel").text() == "취소"){
			$("#mode").val("Edt");
			fn_Edit();
		}else{
			fn_uniDelJSON("/backoffice/basicManage/agentInfoDelete.do"
					  , {agentCode : $("#agentCode").val()}
			          , "/backoffice/contentManage/AgentInfoList.do");
		}
    		
	}
</script>  
</body>
</html>