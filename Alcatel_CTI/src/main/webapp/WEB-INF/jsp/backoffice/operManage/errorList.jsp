<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ page import ="egovframework.com.cmm.AdminLoginVO" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title><spring:message code="URL.TITLE" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">    
    <link href="/css/reset.css" rel="stylesheet" />
    <link href="/css/global.css" rel="stylesheet" />
    <link href="/css/page.css" rel="stylesheet" />
    <link href="/css/calendar.css" rel="stylesheet" />
    <link href="/css/jquery-ui.css" rel="stylesheet" />
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script src="/js/popup.js"></script>
    <script type="text/javascript" src="/js/cms_agent.js"></script>
   
    <script type="text/javascript" src="/js/jquery-ui.js"></script>
	
</head>
<body>
<div id="wrapper">	
<form:form name="regist" commandName="regist" method="post" action="/backoffice/basicManage/errorList.do">
<c:import url="/backoffice/inc/top_inc.do" />
<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex }">
<input type="hidden" name="mode" id="mode" >
<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span>
                        <strong><spring:message code="menu.menu02_3" /><span></strong>
                    </div>
                </div>
            </div>

            <div class="Swrap Asearch">
                <div class="Atitle"><spring:message code="page.common.pageCnt" arguments="${totalCnt}"/></div>
                <section class="Bclear">
                    <form:select path="searchErrorgubun" id="searchErrorgubun" title="소속" >
						     <option value=""><spring:message code="combobox.text" /></option>
	                         <form:options items="${selectErrorgubun}" itemValue="code" itemLabel="codeNm"/>
					</form:select>
					<input class="nameB" type="text" name="searchStartDay" id="searchStartDay"  class="date-picker-input-type-text" value="${searchVO.searchStartDay}">  
					<input class="nameB" type="text" name="searchEndDay" id="searchEndDay"  class="date-picker-input-type-text" value="${searchVO.searchEndDay}">  
					<select name="searchCondition"  id="searchCondition">
								<option value=""><spring:message code="combobox.text" /></option>
								<option value=errorMessage <c:if test="${searchVO.searchCondition == 'errorMessage' }"> selected="selected" </c:if>><spring:message code="page.err.errMessage" /></option>
								<option value="errorType" <c:if test="${searchVO.searchCondition == 'errorType' }"> selected="selected" </c:if>><spring:message code="page.err.gubun" /></option>
					</select>
					<input class="nameB" type="text" name="searchKeyword" id="searchKeyword" value="${searchVO.searchKeyword}">                    
                    <a href="javascript:search_form();"><span class="redBtn"><spring:message code="button.inquire" /></span></a>
                </section>
            </div>

            <div class="Swrap tableArea">
                <table class="margin-top30 backTable">
                    <thead>
                        <tr>
                            <th><spring:message code="common.Number.title" /></th>
                            <th><spring:message code="page.err.gubun" /></th>
                            <th><spring:message code="page.err.errMessage" /></th>
                            <th><spring:message code="page.err.errRegdate" /></th>
                        </tr>
                    </thead>
                    <tbody>
                     <c:forEach items="${resultList }" var="errorInfo" varStatus="status">
                        <tr>
                           <td><c:out value="${ totalCnt - (((searchVO.pageIndex - 1) * searchVO.pageSize) + status.count) + 1}"/></td>
                            <td>${ errorInfo.codeNm}</td>
                            <td style="text-align:left">${errorInfo.errorMessage }</td>
                            <td>${agentInfo.errorRegdate }</td>
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
<c:import url="/backoffice/inc/bottom_inc.do" />
</form:form>
</div>
<script type="text/javascript">

	$(function () {
	    var clareCalendar = {
	        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
	        weekHeader: 'Wk',
	        dateFormat: 'yymmdd', //형식(20120303)
	        autoSize: false, //오토리사이즈(body등 상위태그의 설정에 따른다)
	        changeMonth: true, //월변경가능
	        changeYear: true, //년변경가능
	        showMonthAfterYear: true, //년 뒤에 월 표시
	        buttonImageOnly: true, //이미지표시
	        buttonText: '달력선택', //버튼 텍스트 표시
			buttonImage: '/images/invisible_image.png', //이미지주소
	        //showOn: "both", //엘리먼트와 이미지 동시 사용(both,button)
	        yearRange: '1970:2030' //1990년부터 2020년까지
	    };
	    
	    $("#searchStartDay").datepicker(clareCalendar);
	    $("#searchEndDay").datepicker(clareCalendar);
	    $("img.ui-datepicker-trigger").attr("style", "margin-left:3px; vertical-align:middle; cursor:pointer;"); //이미지버튼 style적용
	    $("#ui-datepicker-div").hide(); //자동으로 생성되는 div객체 숨김
	});
   function linkPage(pageNo) {
		$(":hidden[name=pageIndex]").val(pageNo);	
		$("form[name=regist]").attr("action","/backoffice/operManage/errorList.do").submit();
   }
   function search_form(){	
		  $(":hidden[name=pageIndex]").val("1");	
		  $("form[name=regist]").attr("action", "/backoffice/operManage/errorList.do").submit();		  
   }
</script>
</body>
</html>