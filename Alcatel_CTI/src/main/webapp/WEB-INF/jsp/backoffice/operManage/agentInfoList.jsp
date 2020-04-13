<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ page import ="egovframework.com.cmm.AdminLoginVO" %>
<% AdminLoginVO loginVO = (AdminLoginVO)session.getAttribute("AdminLoginVO"); %>
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
<div id="wrapper">	
<form:form name="regist" commandName="regist" method="post" action="/backoffice/basicManage/AgentInfoList.do">
<c:import url="/backoffice/inc/top_inc.do" />
<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex }">
<input type="hidden" name="mode" id="mode" >
<input type="hidden" name="agentCode" id="agentCode" >


<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span>
                        <spring:message code="menu.menu02" /><span>
                        <span>></span>
                        <strong><spring:message code="menu.menu02_1" /><span></strong>
                    </div>
                </div>
            </div>

            <div class="Swrap Asearch">
                <div class="Atitle"><spring:message code="page.common.pageCnt" arguments="${totalCnt}"/></div>
                <section class="Bclear">
                    <form:select path="searchCenterid" id="searchCenterid" title="소속" onChange="javascript:fn_FlooerView('S', '')">
						     <option value=""><spring:message code="combobox.text" /></option>
	                         <form:options items="${selectCenterCombo}" itemValue="centerId" itemLabel="centerNm"/>
					</form:select>
					<select id="searchFloor" name="searchFloor"></select>
					 <% if (loginVO.getAdminLevel().equals("ROLE_ADMIN")){ %>
					<form:select path="searchPartid" id="searchPartid" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectGroupCombo}" itemValue="partId" itemLabel="partNm"/>
					</form:select>
					<% } %>
						    
                    <select name="searchCondition"  id="searchCondition">
								<option value=""><spring:message code="combobox.text" /></option>
								<option value="agentNm" <c:if test="${searchVO.searchCondition == 'agentNm' }"> selected="selected" </c:if>><spring:message code="page.agent.TitleNm" /></option>
								<option value="agentRemark" <c:if test="${searchVO.searchCondition == 'agentRemark' }"> selected="selected" </c:if>><spring:message code="page.agent.remark" /></option>
					</select>
					<input class="nameB" type="text" name="searchKeyword" id="searchKeyword" value="${searchVO.searchKeyword}">                    
                    <a href="javascript:search_form();"><span class="redBtn"><spring:message code="button.inquire" /></span></a>
                    <div class="rightB">
                        <a href="#" onclick="fn_excelPop()"><span class="deepBtn"><spring:message code="page.commo.excelUpload" /></a>
                        <a href="#" onclick="fn_AgentReste()"><span class="deepBtn"><spring:message code="page.agent.sendBtn1" /></a>
                        <a href="#" onclick="fn_agentPop('Ins','0')" data-needpopup-show="#agent_pop"><span class="deepBtn"><spring:message code="button.create" /></a>
                    </div>
                </section>
            </div>

            <div class="Swrap tableArea">
                <table class="margin-top30 backTable">
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="all_check" name="all_check" onClick="fn_allCheck()"></th>
                            <th><spring:message code="page.agent.Osversion" /></th>
                            <th><spring:message code="page.agent.Mac" /></th>
                            <th><spring:message code="page.agent.TitleNm" /></th>
                            <th><spring:message code="page.agent.CenterInfo" /></th>
                            <th><spring:message code="page.agent.floor" /></th>
                            <th><spring:message code="page.agentNownumber" /></th>
                            <th><spring:message code="page.agent.Telgubun" /></th>
                            <th><spring:message code="page.agent.state" /></th>
                            <th><spring:message code="common.UseYn.title" /></th>
                            <th><spring:message code="button.delete" /></th>
                        </tr>
                    </thead>
                    <tbody>
                     <c:forEach items="${resultList }" var="agentInfo" varStatus="status">
                        <tr>
                            <td>
                            <c:if test="${agentInfo.agentState == 'PHONE_STATE_2' }">
                            <input type="checkbox" name="agentInput"  value="<c:out value="${agentInfo.agentCode}"/>">
                            </c:if>
                            </td>
                            <td>${ agentInfo.agentOsversion}</td>
                            <td><a href="#" onclick="fn_agentPop('Edt','${ agentInfo.agentCode }')"  data-needpopup-show="#agent_pop" class="underline" >${ agentInfo.agentMac }</a></td>
                            <td><a href="#" onclick="fn_agentPop('Edt','${ agentInfo.agentCode }')"  data-needpopup-show="#agent_pop" class="underline" >${ agentInfo.agentNm }</a></td>
                            <td style="text-align:left">${agentInfo.centerNm }</td>
                            <td>${agentInfo.agentFloor }<spring:message code="page.agent.floorTxt" /></td>
                            <td>${agentInfo.agentNownumber }</td>
                            <td>${agentInfo.telChangeTxt }</td>
                            <td>
                            <c:choose>
                               <c:when test="${agentInfo.agentState == 'PHONE_STATE_2' }">
                                ${agentInfo.agentStateTxt }
                               </c:when>
                               <c:when test="${agentInfo.agentState == 'PHONE_STATE_1' }">
                                <a href="#" onclick="fn_agentChangePop('${ agentInfo.agentCode }')"  data-needpopup-show="#agent_telChange" class="underline" >
                                 ${agentInfo.agentStateTxt }
                                 </a>
                               </c:when>
                               <c:otherwise>
                               ${agentInfo.agentStateTxt }
                               </c:otherwise>
                            </c:choose>
                            
                            
                            </td>
                            <td>${agentInfo.agentUseyn }</td>
                            <td><a href="javascript:del_check('${ agentInfo.agentCode }','${ agentInfo.agentNm }');" class="grayBtn">
                                <spring:message code="button.delete" />
                                </a>
                            </td>
                        </tr>
                      </c:forEach>                        
                    </tbody>
                </table>
            </div>
            <div class="new_pagenum">
                <div class="new_pager">
                	<ol>
                		<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage"  />
                   </ol>
                </div>
            </div>
        </div>
    </div>
    
     <div id='agent_telChange' class="needpopup">  
         <div class="popHead">
            <h2>전화번호 변경</h2>
        </div>
        <div class="popCon">
	        <div class="pop_box100">
	                <div class="padding15">
	                    <p class="pop_tit">*<spring:message code="page.user.phoneNumber" /> <span class="join_id_comment joinSubTxt"></span></p>
	                    <form:input  path="agentNownumber" size="5" maxlength="10" id="agentNownumber"   />
	                </div>                
	            </div>
	        <div class="clear"></div>   
            
        </div>
        <div class="pop_footer">
            <span id="join_confirm_comment" class="join_pop_main_subTxt">내용을 모두 입력후  클릭해주세요.</span>
            <a href="javascript:fn_telChange();" class="redBtn" id="btnUpdate"><spring:message code="button.create" /></a>
        	<div class="clear"></div>
        </div>
     </div>
    <div id='agent_pop' class="needpopup">  
        <!-- popheader-->                        
        <div class="popHead">
            <h2>단말기 관리</h2>
        </div>
        <!-- pop contents-->   
        <div class="popCon">
            <!--// 팝업 필드박스-->
            <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.agent.TitleNm" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:input  path="agentNm" size="20" maxlength="100" id="agentNm"   />
                </div>                
            </div>
             <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.agent.Osversion" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:input  path="agentOsversion" size="20" maxlength="100" id="agentOsversion"  />
                </div>                
            </div>
            <!--팝업 필드박스 //-->
            <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.agent.SeatId" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:input  path="seatId" size="20"  maxlength="100" id="seatId"  />
                </div>                
            </div>
            <div class="pop_box50">
               <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.center.centerNm" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:select path="centerId" id="centerId" title="지점" onChange="javascript:fn_FlooerView('P', '')" style="width:200px;">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectCenterCombo}" itemValue="centerId" itemLabel="centerNm"/>
					</form:select>
					<select id='agentFloor' name='agentFloor' style="display:none;width:120px;"></select>
					
                </div>            
            </div>
             <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.agent.partInfo" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:select path="partId" id="partId" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectGroupCombo}" itemValue="partId" itemLabel="partNm"/>
					</form:select>
                </div>   
            </div>
            <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.agent.Mac" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:input  path="agentMac" size="10"  id="agentMac" />
                </div>                
            </div>
             <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="common.UseYn.title" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <input type="radio" name="agentUseyn" id="agentUseyn" value="Y" />
                    <label><spring:message code="button.use" /></label>
					<input type="radio" name="agentUseyn" id="agentUseyn" value="N" />
					<label><spring:message code="button.nouse" /></label>
                </div>                
            </div>
            <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.agent.NodeInfo" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:input  path="nodeInfo" size="10"  id="nodeInfo" />
                </div>                
            </div>
             <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.agent.Telgubun" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:select path="telChange" id="telChange" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectTelCombo}" itemValue="code" itemLabel="codeNm"/>
					</form:select>
                </div>                
            </div>
             
            <div class="clear"></div>   
            
        </div>
        <div class="pop_footer">
            <span id="join_confirm_comment" class="join_pop_main_subTxt">내용을 모두 입력후  클릭해주세요.</span>
            <a href="javascript:fn_CheckForm();" class="redBtn" id="btnUpdate"><spring:message code="button.update" /></a>
        	<div class="clear"></div>
        </div>
    </div>
<c:import url="/backoffice/inc/bottom_inc.do" />
</form:form>
</div>
<script type="text/javascript">

   $(document).ready(function() { 
			if ("${searchVO.searchFloor }" != ""){
				fn_FlooerView("S", "${searchVO.searchFloor }")
			}else{
				$("#searchFloor").hide();
			}
	});
   function fn_excelPop(){
	   window.open("/backoffice/operManage/excelUploadPop.do?gubun=Agent", "zip_code", "width=500, height=400, toolbar=no, menubar=no, scrollbars=no, resizable=auto" );	
   }
   function fn_agentChangePop(code){
	   $('#agentCode').val(code);
   }
   //전화기 입력 
   function fn_telChange(){
	   if (any_empt_line_id("agentNownumber", '<spring:message code="page.user.alert01" />') == false) return;
	   
	   uniAjax("/backoffice/operManage/agentInfoTelChange.do", { agentNownumber : $("#agentNownumber").val(), agentCode : $("#agentCode").val() }, 
      			function(result) {
 	    	            if (result.status == "SUCCESS"){
 	    	            	alert('<spring:message code="success.common.update" />');
 						}else{
 							alert("처리 도중 장애가 발생하였습니다." + result.message);
 						}
 				    },
 				    function(request){
 					    alert("Error:" +request.status );	       						
 				    }    		
       );
	   $('#agentNownumber').val("");
	   $('#agentCode').val("");
	   location.reload();
   }
   //초기화 
   function fn_AgentReste(){
		var reportSeq = ckeckboxValue('<spring:message code="common.radionCheck.msg" />',"agentInput");
    	if (reportSeq == false){
    		return;
    	}
    	if (confirm('<spring:message code="common.reset.msg" />')== true){
    		
    		uniAjax("/backoffice/operManage/agentInfoReset.do", { agentReset : reportSeq }, 
	       			function(result) {
	  	    	            if (result.status == "SUCCESS"){
	  	    	            	alert('<spring:message code="success.common.update" />');
	  						}else{
	  							alert("처리 도중 장애가 발생하였습니다." + result.message);
	  						}
	  				    },
	  				    function(request){
	  					    alert("Error:" +request.status );	       						
	  				    }    		
	        );
    		$('#agentNownumber').val("");
    		$('#agentCode').val("");
    		window.location.reload();
    	}
   }
   function fn_agentPop(mode, code){
		  $('#mode').val(mode);
		  $('#agentCode').val(code);
		  if (mode == "Edt"){
			  $("#btnUpdate").text('<spring:message code="button.update" />');	
			  url= "/backoffice/operManage/agentDetail.do";
			  var param = {
		                'mode' : $('#mode').val(),
		                'agentCode' : $('#agentCode').val()
		      };
			  uniAjax(url, param, 
	     			function(result) {
					       if (result.status == "LOGIN FAIL"){
					    	   alert(result.meesage);
	  						   location.href="/backoffice/login.do";
	  					   }else if (result.status == "SUCCESS"){
	  						    //여기 부분 수정 어떻게 할지 추후 생각
	  						    var obj = result.agentInfo;
	  						    
	  						   $("#agentNm").val(obj.agentNm);
	  						   $("#agentOsversion").val(obj.agentOsversion);
		  					   $("#agentRemark").val(obj.agentRemark);
		  					   $("#centerId").val(obj.centerId);
		  					   $("#partId").val(obj.partId);
		  					   $("#agentIp").val(obj.agentIp);
		  					   $("#agentMac").val(obj.agentMac);
		  					   
		  					   $("#loginId").val(obj.loginId);
		  					   $("#loginPwd").val(obj.loginPwd);
		  					   $("#seatId").val(obj.seatId);
		  					 
		  					   $("#agentBasicnumber").val(obj.agentBasicnumber);
		  					   $("#nodeInfo").val(obj.nodeInfo);
		  					   $("#telChange").val(obj.telChange);
		  					 
		  					   $('input:radio[name=agentUseyn]:input[value=' + obj.agentUseyn + ']').attr("checked", true);	 
		  					   //fn_SearchNumber("Edt");
		  					   fn_FlooerView("P", obj.agentFloor);
		  					   //$("#agentIp").val(obj.agentIp);
	 		            	   
	  					   }else {
	  						 alert("Error:" +result.message );	       	
	  					   }
					    },
					    function(request){
						    alert("Error:" +request.status );	       						
					    }    		
	          );
		  }else {
			//  $("#agentBasicnumber").attr("display", "none");
			//  $("#agentBasicnumber").empty();
			  $("#btnUpdate").text('<spring:message code="button.create" />');
		  }
   }
   function del_check(code){	
    	fn_uniDelJSON("/backoffice/operManage/agentInfoDelete.do"
				  , {agentCode : code}
		          , "/backoffice/operManage/agentList.do");	
   }
   function fn_FlooerView(searhGubun, floorInfo){
		if ($("#centerId").val()  != "" || $("#searchCenterid").val()  != ""){
			var params = (searhGubun =="P") ? { centerId : $("#centerId").val() }: { centerId : $("#searchCenterid").val() };
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
		  	    	            	if (searhGubun == "P"){
		  	    	            		fn_optionControl("agentFloor", floorInfo, startFloor, endFloor);
		  	    	            	}else{
		  	    	            		fn_optionControl("searchFloor", floorInfo, startFloor, endFloor);
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
   function fn_CheckForm(){
	   if (any_empt_line_id("agentNm", '<spring:message code="page.agent.alert1" />') == false) return;
	   if (any_empt_line_id("agentMac", '<spring:message code="page.agent.alert5" />') == false) return;
	   
	   var params = {  
			           agentNm : $("#agentNm").val(),
			           agentOsversion : $("#agentOsversion").val(),
					   centerId : $("#centerId").val(),
					   partId : fn_emptyReplace($("#partId").val() ,"0") ,
					   agentBasicnumber : $("#agentBasicnumber").val(),
					   agentIp : $("#agentIp").val(),
					   agentMac : $("#agentMac").val(),
					   agentUseyn :  fn_emptyReplace($('input[name="agentUseyn"]:checked').val(),"Y"),
					   mode : $("#mode").val(),
					   loginId : $("#loginId").val(),
					   loginPwd : $("#loginPwd").val(),
					   agentFloor : $("#agentFloor").val(),
					   seatId : $("#seatId").val(),
					   agentCode : $("#agentCode").val(),
					   nodeInfo : $("#nodeInfo").val(),
					   telChange : $("#telChange").val()
			        };
	            uniAjax("/backoffice/operManage/agentInfoUpdate.do", params, 
     			function(result) {
				    	if (result.status == "SUCCESS"){
				    		location.reload();
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
   function linkPage(pageNo) {
		$(":hidden[name=pageIndex]").val(pageNo);	
		$('#displaySeq').val("0");
		$("form[name=regist]").attr("action","/backoffice/operManage/AgentInfoList.do").submit();
   }
   function fn_viewAgent(agentCode){
		  $('#mode').val("Edt");
		  $('#agentCode').val(agentCode);
		  $('#displaySeq').val("0");
		  $("form[name=regist]").attr("action", "/backoffice/operManage/agentInfoView.do").submit();
   }
   function search_form(){	
		  $(":hidden[name=pageIndex]").val("1");	
		  $('#displaySeq').val("0");
		  $("form[name=regist]").attr("action", "/backoffice/operManage/AgentInfoList.do").submit();		  
   }
</script>
</body>
</html>