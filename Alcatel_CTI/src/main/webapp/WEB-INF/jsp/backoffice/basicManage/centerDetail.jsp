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
</head>
<body>
<div id="wrapper">
<noscript>자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
<form:form name="regist" commandName="regist" method="post" action="/backoffice/basicManage/centerUpdate.do">	   	  
	

<form:hidden path="centerId" id="centerId"/>
<form:hidden path="mode" id="mode"/>
<form:hidden path="pageIndex" />
<form:hidden path="pageSize" />
<form:hidden path="searchCondition" />
<form:hidden path="searchKeyword" />

<form:hidden path="restInfo" />
<form:hidden path="meetingroomInfo" />
<form:hidden path="centerInfo" />

<c:import url="/backoffice/inc/top_inc.do" />

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
                        <td style="text-align:left"><span id=sp_CenterInfo></span></td>
                        <th><spring:message code="page.center.centerNm" /></th>
                        <td style="text-align:left"><form:input title="센터명" path="centerNm"  /></td>
                    </tr>
                    <tr>
                        <th>주소</th>
                        <td colspan="3"  style="text-align:left">
                            <form:input title="우편번호" path="centerZipcode1"  id="centerZipcode1" size="3" maxlength="3" />
                            <label>-</label>
                            <form:input title="우편번호" path="centerZipcode2" id="centerZipcode2"   size="3"  maxlength="3"/>
                            <p class="margin-top10">
                                <form:input title="주소1" path="centerAddr1"  id="centerAddr1" size="60" />
                                <form:input title="주소1" path="centerAddr2"  id="centerAddr2" size="60" />
                            </p>
                        </td>
                    </tr>
                    <tr>
                        <th>센터 연락처</th>
                        <td style="text-align:left">
                            <form:input title="연락처" path="centerTel"  id="centerTel" size="20" maxlength="20"/>
                        </td>
                        <th>센터 FAX</th>
                        <td style="text-align:left">
                            <form:input title="연락처" path="centerFax"  id="centerFax" size="20" maxlength="20"/>
                        </td>
                    </tr>
                    <tr>
                        <th>센터전경사진</th>
                        <td style="text-align:left">
                            <input name="centerImg" id="centerImg" type="file"  size="20"/>
                        </td>
                        <th>센터내부이미지</th>
                        <td style="text-align:left"><input name="centerSeatImg" id="centerImg" type="file"  size="20"/></td>
                    </tr>
                    <tr>
                        <th>센터URL</th>
                        <td style="text-align:left"><form:input title="연락처" path="centerUrl"  id="centerUrl" size="15" /></td>
                        <th>사용여부</th>
                        <td style="text-align:left">
                            <input type="radio" name="centerUseYn" value="Y" <c:if test="${regist.centerUseYn == 'Y' }"> checked </c:if> /><label>사용</label>
							<input type="radio" name="centerUseYn" value="N" <c:if test="${regist.centerUseYn == 'N' }"> checked </c:if> /><label>사용안함</label>
                        </td>
                    </tr>
                    <tr>
                        <th>관리자승인여부</th>
                        <td style="text-align:left">
                            <input type="radio" name="adminApprovalYn" value="Y" <c:if test="${regist.adminApprovalYn == 'Y' }"> checked </c:if> /><label>사용</label>
							<input type="radio" name="adminApprovalYn" value="N" <c:if test="${regist.adminApprovalYn == 'N' }"> checked </c:if> /><label>사용안함</label>
                        </td>
                        <th>괸리 부서</th>
                        <td style="text-align:left">
                            <form:select path="partId" id="partId" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectGroupCombo}" itemValue="partId" itemLabel="partNm"/>
							</form:select>
                        </td>
                    </tr>
                    <tr>
                        <th>지점 층수</th>
                        <td colspan="3" style="text-align:left">
                           <form:select path="centerFloor" id="centerFloor" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectFloorCombo}" itemValue="code" itemLabel="codeNm"/>
							</form:select>
							
							 <form:select path="centerFloorEnd" id="centerFloorEnd" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectFloorEndCombo}" itemValue="code" itemLabel="codeNm"/>
							</form:select>
                        </td>
                    </tr>
                    <!-- 
                    <tr>
                        <th>휴게공간</th>
                        <td colspan="3" style="text-align:left">
                        <textarea name="ir1" id="ir1" style="width:1000px; height:50px; display:none;">${regist.restInfo }</textarea>
                        </td>
                    </tr>
                    <tr>
                        <th>회의실정보</th>
                        <td colspan="3" style="text-align:left">
                        <textarea name="ir2" id="ir2" style="width:1000px; height:50px; display:none;">${regist.meetingroomInfo }</textarea>
                        </td>
                    </tr>
                     -->
                    <tr>
                        <th>센터정보</th>
                        <td colspan="3" style="text-align:left">
                            <textarea name="ir3" id="ir3" style="width:1000px; height:50px; display:none;">${regist.centerInfo }</textarea>
                        </td>
                    </tr>
                </tbody>
            </table>
            </div>
            </div>
            <div class="footerBtn">
	            <a href="javascript:check_form();" class="redBtn" id="btnUpdate"><spring:message code="button.update" /></a>
	            <c:if test="${regist.mode != 'Ins' }">
	            <a href="javascript:del_form();" class="redBtn"><spring:message code="button.delete" /></a>
	            </c:if>
	            <a href="fn_linkPage('/backoffice/basicManage/centerList.do', 'regist');" class="deepBtn"><spring:message code="button.list" /></a>
	        </div>
        </div>
    </div>
<c:import url="/backoffice/inc/bottom_inc.do" />    
</form:form>    
<script type="text/javascript">
        var oEditors = [];
        /* nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            elPlaceHolder: "ir1",                        
            sSkinURI: "/SE/SmartEditor2Skin.html",
            htParams: { bUseToolbar: true,
                fOnBeforeUnload: function () { },
                //boolean 
                fOnAppLoad: function () { }
                //예제 코드
            },
            fCreator: "createSEditor2"
        });
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            elPlaceHolder: "ir2",                        
            sSkinURI: "/SE/SmartEditor2Skin.html",
            htParams: { bUseToolbar: true,
                fOnBeforeUnload: function () { },
                //boolean 
                fOnAppLoad: function () { }
                //예제 코드
            },
            fCreator: "createSEditor2"
        }); */
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
  </script>  	
  <script type="text/javascript">
		$(document).ready(function() {
			if ($("#mode").val() == "Ins"){   	  		    	
		 		$("#btnUpdate").text('<spring:message code="button.create" />');
		 		$("#sp_CenterInfo").html('<spring:message code="page.center.message" />');
		    }	else {		    	
		    	$("#btnUpdate").text('<spring:message code="button.update" />');
		    	$("#sp_CenterInfo").html($("#centerId").val());
		    }
	  });	  
	  function check_form(){
		  if (any_empt_line_id('centerNm', '<spring:message code="page.center.alert01" />') == false) return;
          /*  var sHTML = oEditors.getById["ir1"].getIR();
		  $("#restInfo").val(sHTML);
		  var sHTML2 = oEditors.getById["ir2"].getIR();
		  $("#meetingroomInfo").val(sHTML2); */
		  var sHTML3 = oEditors.getById["ir3"].getIR();
		  $("#restInfo").val('');
		  $("#meetingroomInfo").val('');
		  //alert(sHTML3);
		  $("#centerInfo").val(sHTML3);
		  document.regist.encoding = "multipart/form-data";
		  $("form[name=regist]").attr("action", "/backoffice/basicManage/centerUpdate.do").submit();
	  }
	  //센터 삭제 
	  function del_form(){
		  if (confirm('<spring:message code="common.delete.msg" />')== true){
			  document.regist.action = "/backoffice/basicManage/centerDeletel.do";
			  document.regist.encoding = "application/x-www-form-urlencoded"; 
			  document.regist.submit();  			  
		  }		  	  
	  }
	  function zipcode(){		  
	      var url = "/backoffice/popup/zipPop.do";	      
          window.open(url,"우편번호", 'width=600,height=550,top=100,left=650,scrollbars=auto')	   
	  }	  
	</script> 
  
</body>
</html>