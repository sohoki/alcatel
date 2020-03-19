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
    <script src="/js/popup.js"></script>
</head>
<body>
<div id="wrapper">	
<noscript>자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
<form:form name="regist" commandName="regist" method="post" action="/backoffice/sub/operManage/tranUpdate.do">
	<form:hidden path="tranSeq" id="tranSeq" />		
	<input type="hidden" id="mode" name="mode" value="${ registView.mode}">
	<input type="hidden" id="pageUnit" name="pageUnit" value="${ registView.pageUnit}">
	<input type="hidden" id="pageIndex" name="pageIndex" value="${ registView.pageIndex}">
	<input type="hidden" id="searchCondition" name="searchCondition" value="${ registView.searchCondition}">
	<input type="hidden" id="searchKeyword" name="searchKeyword" value="${ registView.searchKeyword}">
	
	<c:import url="/backoffice/inc/top_inc.do" />
	<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span>
                        <spring:message code="menu.menu02" />
                        <span>></span>
                        <strong><spring:message code="menu.menu02_2" /></strong>
                    </div>
                </div>
            </div>
            <div class="Swrap tableArea viewArea">
                <div class="view_contents">
                <table>
					<tbody>
						<tr id="tr_TranView">
						   <th><spring:message code="page.tran.MessageCk" /> </th>
						   <td colspan="3" sytle="text-align:right;">
						   <a href="javascript:open_JsonView('I','${regist.tranSeq  }' )" class="deepBtn">
						   <spring:message code="page.tran.ViewSendJson" />
						   </a>
						   <a href="javascript:open_JsonView('O','${regist.tranSeq  }' )" class="deepBtn">
						   <spring:message code="page.tran.ViewRecJson" />
						   </a>
						   <!-- 전문 보기 -->
						   <span id="sp_MessageView"></span>
						   </td>
						</tr>
						<tr>
							<th><spring:message code="page.tran.processId" /></th>
							<td style="text-align:left"><span class="lable"><c:out value="${regist.tranProcessName}" /></span>
							<span class="type">
							<form:input path="tranProcessName" id="tranProcessName" title="전문설명" size="25" value="${regist.tranProcessName }"/>
							 <a href="javascript:processCheck();" class="grayBtn" id="btn_UniCheck"><spring:message code="common.unicheck" /></a>
							</span>
							</td>
							<th><spring:message code="page.tran.sendGubun" /></th>		
							<td style="text-align:left">
							<span class="lable"><c:out value="${regist.sendGubunTxt}" /></span>
							<span class="type">
							<form:select path="sendGubun" id="sendGubun" title="전문구분">
							        <option value><spring:message code="combobox.text" /></option>
			                        <form:options items="${selectSendgubun}" itemValue="code" itemLabel="codeNm"/>
							</form:select>
							</span>
							</td>				
						</tr>
						<tr>
						    <th><spring:message code="page.tran.processJob" /></th>		
							<td style="text-align:left" colspan="3">
							<span class="lable"><c:out value="${regist.processRemark}" /></span>
							<span class="type">
							<form:input path="processRemark" id="processRemark" title="전문설명"  style="width:550px;"  value="${regist.processRemark }"/>
							</span>
							</td>	
						</tr>
						<tr>
							<th><spring:message code="page.tran.inParam" /></th>
							<td style="text-align:left">
							<span class="lable"><c:out value="${regist.tranInputParam}" /></span>
							<span class="type">
							<textarea name="tranInputParam" id="tranInputParam" style="width:250px; height:50px;">${regist.tranInputParam }</textarea>
							</span>
							</td>
							<th><spring:message code="page.tran.otParam" /></th>		
							<td style="text-align:left">
							<span class="lable"><c:out value="${regist.tranOutputParam}" /></span>
							<span class="type">
							<textarea name="tranOutputParam" id="tranOutputParam" style="width:250px; height:50px;">${regist.tranOutputParam }</textarea>
							</span>
							</td>				
						</tr>	
						<tr>
							<th><spring:message code="page.tran.inParamExample" /></th>
							<td style="text-align:left">
							<span class="lable"><c:out value="${regist.tranInputParamSample}" /></span>
							<span class="type">
							<textarea name="tranInputParamSample" id="tranInputParamSample" style="width:250px; height:50px;">${regist.tranInputParamSample }</textarea>
							</span>
							</td>
							<th><spring:message code="page.tran.inParamExp" /></th>		
							<td style="text-align:left">
							<span class="lable"><c:out value="${regist.tranExplain}" /></span>
							<span class="type">
							<textarea name="tranExplain" id="tranExplain" style="width:250px; height:50px;">${regist.tranExplain }</textarea>
							</span>
							</td>				
						</tr>	
						<tr>
							<th><spring:message code="page.tran.sendGubun" /></th>
							<td style="text-align:left">
							<span class="lable"><c:out value="${regist.codeNm}" /></span>
							<span class="type">
							<form:select path="workGubun" id="workGubun" title="전문구분">
							        <option value><spring:message code="combobox.text" /></option>
			                        <form:options items="${selectWorkgubun}" itemValue="code" itemLabel="codeNm"/>
							</form:select>
							</span>
							</td>
							<th><spring:message code="page.tran.result" /></th>		
							<td style="text-align:left">
							<span class="lable"><c:out value="${regist.resultCodeExample}" /></span>
							<span class="type">
							<form:input path="resultCodeExample" id="resultCodeExample" title="전문설명" style="width:250px;" value="${regist.resultCodeExample }"/>
							</span>
							</td>				
						</tr>
						<tr>
							<th><spring:message code="page.tran.ListCheck" /></th>
							<td style="text-align:left">
							<span class="lable"><c:out value="${regist.etc1}" /></span>
							<span class="type">
							<form:input path="etc1" id="etc1" title="전문설명" size="15" value="${regist.etc1 }"/>
							</span>
							</td>
							<th><spring:message code="page.tran.confirm" /></th>		
							<td style="text-align:left">
							<span class="lable">
							<c:choose>
							   <c:when test="${regist.testOk eq 'Y' }"><spring:message code="button.confirm" /></c:when>
							   <c:otherwise><spring:message code="button.undefine" /></c:otherwise>
							 </c:choose>
							 </span>
							 <span class="type">
							 <input type="radio" name="testOk" value="Y" <c:if test="${regist.testOk == 'Y' }"> checked </c:if> /><spring:message code="button.confirm" />
							 <input type="radio" name="testOk" value="N" <c:if test="${regist.testOk == 'N' }"> checked </c:if> /><spring:message code="button.undefine" />
							 </span>
							</td>			
						</tr>																										
					</tbody>
				</table>
                 </div>
            </div>
            <div class="footerBtn">
	            
	            <a href="javascript:fn_Edit();" class="redBtn" id="btnUpdate"><spring:message code="button.update" /></a>
	            <a href="javascript:dn_delCheck();" class="redBtn" id="btnDel"><spring:message code="button.delete" /></a>
	            <a href="javascript:pageList()" class="deepBtn"><spring:message code="button.list" /></a>
	        </div>
        </div>
    </div>
	<c:import url="/backoffice/inc/bottom_inc.do" />
</form:form>
</div>
<script type="text/javascript">
       $(document).ready(function () {
    	   
    	   fn_Edit();
	   });
       function pageList(){
    	   $("form[name=regist]").attr("action", "/backoffice/operManage/tranList.do").submit();
       }
       function dn_delCheck(){
    	   if ($("#btnDel").text() == "<spring:message code='button.delete' />"){
	   			$("#mode").val("Edt");
	   			fn_Edit();
	   		}else{
	   			fn_uniDelJSON("/backoffice/operManage/tranDel.do"
	   					  , {tranSeq : $("#tranSeq").val()}
	   			          , "/backoffice/operManage/tranlList.do");
	   		}
       }
       function fn_Edit(){
    	   
    	$("#sp_MessageView").html("");  
    	
   		if ($("#btnUpdate").text() == "<spring:message code='button.create' />" && ( $("#mode").val() == "Edt" || $("#mode").val() == "Ins"   )){
   			   
   		       if (any_empt_line_id("tranProcessName", '<spring:message code="page.tran.alert01" />') == false) return;
   		       if (any_empt_line_id("tranInputParam", '<spring:message code="page.tran.alert02" />') == false) return;
   		       if (any_empt_line_id("tranOutputParam", '<spring:message code="page.tran.alert03" />') == false) return;
   		       
   			   var params = $("#regist").serializeObject(); 
   			   uniAjax("/backoffice/operManage/tranUpdate.do", params, 
   		     			function(result) {
   				               alert(result.message);
   						       if (result.status == "LOGIN FAIL"){
   		  							location.href="/backoffice/login.do";
   		  					   }else if (result.status == "SUCCESS"){
   		  						    //여기 부분 수정 어떻게 할지 추후 생각
   		  						    if (result.regist.tranSeq != ""){
   		  						    	//alert("result.regist.tranSeq:" + result.regist.tranSeq);
	   		  						    $("#tranSeq").val(result.regist.tranSeq);
		   								$("#mode").val("Viw");
		   								$("form[name=regist]").attr("action", "/backoffice/operManage/tranView.do").submit();
   		  						    }
   		  					   }
   						    },
   						    function(request){
   							    alert("Error:" +request.status );	       						
   						    }    		
   		      );	
   		}
   		//alert($("#mode").val());
   		
   		if ($("#mode").val() == "Edt" || $("#mode").val() == "Viw" ){
   			$(".lable").show();
   			$(".type").hide();
   			$("#tr_TranView").show();
   			$("#btnUpdate").text("<spring:message code='button.update' />");
   			$("#btnDel").text("<spring:message code='button.delete' />");
   			$("#mode").val("Pre");
   			$("#btn_UniCheck").hide();
   		}else if ($("#mode").val() == "Ins") {
   			$(".lable").hide();
   			$(".type").show();
   			$("#tr_TranView").hide();
   			$("#btnUpdate").text("<spring:message code='button.create' />");
   			$("#btnDel").hide();
   			$("#btn_UniCheck").show();
   		} else {
   			$("#btn_UniCheck").hide();
   			$(".lable").hide();
   			$(".type").show();
   			$("#tr_TranView").hide();
   			$("#btnUpdate").text("<spring:message code='button.create' />");
   			$("#btnDel").text("<spring:message code='button.reset' />");
   			$("#mode").val("Edt");
   		}
   	}
    function open_JsonView(code, code1){
   	      var url = code == "I" ? "/backoffice/operManage/jsonAuthView.do?tranSeq="+code1 : "/backoffice/operManage/jsonAuthReq.do?tranSeq="+code1;
  		  uniAjax(url, null, 
	     			function(result) {
			               
					       if (result.status == "LOGIN FAIL"){
					    	   alert(result.meesage);
	  						   location.href="/backoffice/login.do";
	  					   }else if (result.status == "SUCCESS"){
	  						    //여기 부분 수정 어떻게 할지 추후 생각
	  						   $("#sp_MessageView").html(result.json_res);
	  					   }
					    },
					    function(request){
						    alert("Error:" +request.status );	       						
					    }    		
	      );	
  		         	   
    }
    //전문 체크
    function processCheck(){
 	   fn_uniCheck(   {inTable : "tb_sendmessageinfo",
 		               inCheckName : "TRAN_PROCESS_NAME",
 		               inCondition : "TRAN_PROCESS_NAME=["+$("#tranProcessName").val()+"["
 		              }, "tranProcessName", "idCheck", "<spring:message code='page.tran.confirm' />"
 		            );
    }
    </script>
</body>
</html>