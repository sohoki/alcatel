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
                        <spring:message code="menu.menu01" /><span>
                        <span>></span>
                        <strong><spring:message code="menu.menu01_3" /><span></strong>
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
					<form:select path="searchDisplayseq" id="searchDisplayseq" title="소속">
						     <option value=""><spring:message code="combobox.text" /></option>
	                         <form:options items="${selectCodeDM}" itemValue="displaySeq" itemLabel="displayTitle"/>
					</form:select>
					
					<form:select path="searchAgentgubun" id="searchAgentgubun" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectAgentContentGubun}" itemValue="code" itemLabel="codeNm"/>
					</form:select>
						    
                    <select name="searchCondition"  id="searchCondition">
								<option value=""><spring:message code="combobox.text" /></option>
								<option value="agentNm" <c:if test="${searchVO.searchCondition == 'agentNm' }"> selected="selected" </c:if>><spring:message code="page.agent.TitleNm" /></option>
								<option value="disSeq" <c:if test="${searchVO.searchCondition == 'disSeq' }"> selected="selected" </c:if>><spring:message code="page.agent.disSeq" /></option>
								<option value="agentRemark" <c:if test="${searchVO.searchCondition == 'agentRemark' }"> selected="selected" </c:if>><spring:message code="page.agent.remark" /></option>
					</select>
					<input class="nameB" type="text" name="searchKeyword" id="searchKeyword" value="${searchVO.searchKeyword}">                    
                    <a href="javascript:search_form();"><span class="redBtn"><spring:message code="button.inquire" /></span></a>
                    <div class="rightB">
                        <a href="javascript:fn_SendMsg('SP_REDOWN', 'agentCode', 'M')" class="grayBtn"><spring:message code="page.agent.sendBtn2" /></a>
						<a href="javascript:fn_SendMsg('SP_AGENTREBOOT', 'agentCode', 'M')" class="grayBtn"><spring:message code="page.agent.sendBtn1" /></a>
                        <a href="#" onclick="fn_agentPop('Ins','0')" data-needpopup-show="#agent_pop"><span class="deepBtn"><spring:message code="button.create" /></a>
                    </div>
                </section>
            </div>

            <div class="Swrap tableArea">
                <table class="margin-top30 backTable">
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="all_check" name="all_check" onClick="fn_allCheck()"></th>
                            <th><spring:message code="page.agent.osType" /></th>
                            <th><spring:message code="page.agent.contentGubun" /></th>
                            <th><spring:message code="page.agent.state" /></th>
                            <th><spring:message code="page.agent.TitleNm" /></th>
                            <th><spring:message code="page.agent.floor" /></th>
                            <th><spring:message code="page.agent.remark" /></th>
                            <th><spring:message code="page.agent.displayNm" /></th>
                            <th><spring:message code="page.agent.totalTime" /></th>
                            <th><spring:message code="page.agent.startTime" /></th>
                            <th><spring:message code="common.UseYn.title" /></th>
                            <th><spring:message code="button.preview" /></th>
                            <th><spring:message code="button.delete" /></th>
                        </tr>
                    </thead>
                    <tbody>
                     <c:forEach items="${resultList }" var="agentInfo" varStatus="status">
                        <tr>
                            <td><input type="checkbox" name="agentInput" value="<c:out value="${agentInfo.agentCode}"/>"></td>
                            <td>
                            <c:choose>
							   <c:when test="${agentInfo.agentGubun eq '안드로이드' }">
									<img src="/images/android_icon.png" width="16px" height="16px" />
							   </c:when>
							   <c:when test="${agentInfo.agentGubun eq '윈도우' }">
									<img src="/images/windows_icon.png" width="16px" height="16px" />
							   </c:when>
							   <c:otherwise>
							   		<img src="/images/ios_icon.png" width="16px" height="16px" />
							   </c:otherwise>
							</c:choose>${ agentInfo.agentGubun}
                            </td>
                            <td><a href="#" onclick="fn_viewAgent('${ agentInfo.agentCode }')">${ agentInfo.contentGubun}</a></td>
                            <td>
	                            <c:choose>
								   <c:when test="${agentInfo.agentStateInfo eq 'NORMAL' }">
										<img src="/images/on_icon.png" width="16px" height="16px" />
								   </c:when>
								   <c:when test="${agentInfo.agentStateInfo eq 'CAUTION' }">
										<img src="/images/caution_icon.png" width="16px" height="16px" />
								   </c:when>
								   <c:otherwise>
								   		<img src="/images/danger_icon.png" width="16px" height="16px" />
								   </c:otherwise>
								</c:choose>
	                            <a href="#" onclick="fn_agentState('${ agentInfo.agentCode }')" data-needpopup-show="#agentState" class="underline" >
	                            <spring:message code="page.agent.state" />
	                            </a>
                            </td>
                            <td><a href="#" onclick="fn_agentPop('Edt','${ agentInfo.agentCode }')"  data-needpopup-show="#agent_pop" class="underline" >${ agentInfo.agentNm }</a></td>
                            <td>${agentInfo.agentFloor }<spring:message code="page.agent.floorTxt" /></td>
                            <td style="text-align:left">${agentInfo.agentRemark }</td>
                            <td>${agentInfo.displayTitle }</td>
                            <td>${agentInfo.displayTotalTime }<spring:message code="page.agent.minute" /> </td>
                            <td>${agentInfo.agentStarttime }</td>
                            <td>${agentInfo.agentUseYn }</td>
                            <td><a href="javascript:fn_agentPreView('${ agentInfo.displaySeq }','${ agentInfo.displayTitle }','${ agentInfo.contentGubun }')" class="grayBtn"><spring:message code="button.preview" /></a></td>                            
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
    <div id='agent_pop' class="needpopup">  
        <!-- popheader-->                        
        <div class="popHead">
            <h2>콘텐츠  등록</h2>
        </div>
        <!-- pop contents-->   
        <div class="popCon">
            <!--// 팝업 필드박스-->
            <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.agent.TitleNm" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:input  path="agentNm" size="20" maxlength="100" id="agentNm"   value="${regist.agentNm }" />
                </div>                
            </div>
             <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.agent.osType" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:select path="agentGubun" id="agentGubun" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectAgentGubun}" itemValue="code" itemLabel="codeNm"/>
					</form:select>
                </div>                
            </div>
            <!--팝업 필드박스 //-->
            <div class="pop_box100">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.agent.remark" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:input  path="agentRemark" size="80" maxlength="120" id="agentRemark"   value="${regist.agentRemark }" />
                </div>                
            </div>
            <!--// 팝업 필드박스-->
            <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.center.centerNm" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:select path="centerId" id="centerId" title="소속" onChange="javascript:fn_FlooerView('P', '')">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectCenterCombo}" itemValue="centerId" itemLabel="centerNm"/>
					</form:select>
					<select id='agentFloor' name='agentFloor' style="display:none;"></select>
					
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
                    <p class="pop_tit">*<spring:message code="page.agent.startTime" />~<spring:message code="page.agent.endTime" /> <span class="join_id_comment joinSubTxt"></span></p>
                   <form:input  path="agentStarttime" size="10" maxlength="5" id="agentStarttime"   value="${regist.agentStarttime }" />~
                   <form:input  path="agentEndtime" size="10" maxlength="5" id="agentEndtime"   value="${regist.agentEndtime }" />
                </div>   
            </div>
            <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.agent.contentGubun" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:select path="agentContentgubun" id="agentContentgubun" title="소속" onChange="fn_displayView('')">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectAgentContentGubun}" itemValue="code" itemLabel="codeNm"/>
					</form:select>
                </div>                
            </div>
            <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.agent.displayNm" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:select path="displaySeq" id="displaySeq" title="소속" style="display:none">
		                 <option value=""><spring:message code="combobox.text" /></option>
			             <form:options items="${selectCodeDM}" itemValue="displaySeq" itemLabel="displayTitle"/>
					</form:select>
                </div>   
            </div>
            
             <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="common.UseYn.title" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <input type="radio" name="agentUseYn" id="agentUseYn" value="Y" <c:if test="${regist.agentUseYn == 'Y' }"> checked </c:if> />
                    <label><spring:message code="button.use" /></label>
					<input type="radio" name="agentUseYn" id="agentUseYn" value="N" <c:if test="${regist.agentUseYn == 'N' }"> checked </c:if> />
					<label><spring:message code="button.nouse" /></label>
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
   function fn_agentPop(mode, code){
		  $('#mode').val(mode);
		  $('#agentCode').val(code);
		  if (mode == "Edt"){
			  $("#btnUpdate").text('<spring:message code="button.update" />');	
			  url= "/backoffice/basicManage/agentInfoDetail.do";
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
	  						    $("#agentGubun").val(obj.agentGubun);
		  						$("#agentRemark").val(obj.agentRemark);
		  						$("#centerId").val(obj.centerId);
		  						$("#partId").val(obj.partId);
		  						$("#agentStarttime").val(obj.agentStarttime);
		  						$("#agentEndtime").val(obj.agentEndtime);
		  						$("#agentContentgubun").val(obj.agentContentgubun);
	 		            	    $('input:radio[name=agentUseYn]:input[value=' + obj.agentUseYn + ']').attr("checked", true);	 
	 		            	    fn_FlooerView("P", obj.agentFloor );
	 		            	    
	 		            	    fn_displayView(obj.displaySeq);
	 		            	    
	  					   }
					    },
					    function(request){
						    alert("Error:" +request.status );	       						
					    }    		
	          );
		  }else {
			  $("#btnUpdate").text('<spring:message code="button.create" />');
		  }
   }
   function del_check(code){	
    	fn_uniDelJSON("/backoffice/basicManage/agentInfoDelete.do"
				  , {agentCode : code}
		          , "/backoffice/contentManage/AgentInfoList.do");	
   }

   function fn_displayView(selectVal){
	   uniAjax("/backoffice/contentManage/displayCombo.do?agentContentgubun="+$("#agentContentgubun").val() , {'agentContentgubun': $("#agentContentgubun").val()}, 
    			function(result) {
				    	if (result.status == "SUCCESS"){
				    		if (result.disList.length> 0){
				    			
				    			$("#displaySeq").show();
				    			$("#displaySeq option").remove();
				    			$("#displaySeq").append("<option value=''>선택</option>");
				    			for (var i = 0; i <= result.disList.length; i ++){
				    				var obj = result.disList[i];
				    				if (obj.displaySeq == selectVal){
				    					$("#displaySeq").append("<option value='"+obj.displaySeq+"' selected>"+obj.displayTitle+"</option>");
				    				}else {
				    					$("#displaySeq").append("<option value='"+obj.displaySeq+"'>"+obj.displayTitle+"</option>");
				    				}
				    				
				    		    }  
				    			
				    		    if (selectval != ""){
				    		    	console.log("selectval:" + selectval);
				    		    	$("#displaySeq").val(selectval);
				    		    }
				    		    	
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
	   if (any_empt_line_id("agentRemark", '<spring:message code="page.agent.alert2" />') == false) return;
	   
	   var params = {  agentNm : $("#agentNm").val(),
					   agentGubun : $("#agentGubun").val(),
					   agentRemark : $("#agentRemark").val(),
					   centerId : $("#centerId").val(),
					   partId : $("#partId").val(),
					   agentStarttime : $("#agentStarttime").val(),
					   agentEndtime : $("#agentEndtime").val(),
					   displaySeq : $("#displaySeq").val(),
					   agentUseYn :  fn_emptyReplace($('input[name="agentUseYn"]:checked').val(),"Y"),
					   agentContentgubun : $("#agentContentgubun").val(),
					   mode : $("#mode").val(),
					   agentFloor : $("#agentFloor").val(),
					   agentCode : $("#agentCode").val()
			        };
	            uniAjax("/backoffice/basicManage/agentInfoUpdate.do", params, 
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
		$("form[name=regist]").attr("action","/backoffice/basicManage/AgentInfoList.do").submit();
   }
   function fn_viewAgent(agentCode){
		  $('#mode').val("Edt");
		  $('#agentCode').val(agentCode);
		  $('#displaySeq').val("0");
		  $("form[name=regist]").attr("action", "/backoffice/basicManage/agentInfoView.do").submit();
   }
   function search_form(){	
		  $(":hidden[name=pageIndex]").val("1");	
		  $('#displaySeq').val("0");
		  $("form[name=regist]").attr("action", "/backoffice/basicManage/AgentInfoList.do").submit();		  
   }
</script>
</body>
</html>