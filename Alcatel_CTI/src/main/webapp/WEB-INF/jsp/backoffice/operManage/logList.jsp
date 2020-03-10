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
    
    <!-- TOAST UI Grid -->
	<script src="/js/underscore.js"></script>
	<script src="/js/backbone.js"></script>
	<script src="/ti/tui-code-snippet.js"></script>
	<script src="/ti/tui-pagination.js"></script>
	<script src="/ti/tui-date-picker.js"></script>
	<script src="/ti/tui-grid.js"></script>
	<link rel="stylesheet" type="text/css" href="/ti/css/tui-grid.css" />
    <!-- TOAST UI Grid End-->
</head>
</head>
<body>
<div id="wrapper">	
<form:form name="regist" commandName="regist" method="post" action="/backoffice/contentManage/SchInfoList.do">
<c:import url="/backoffice/inc/top_inc.do" />
<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex }">
<input type="hidden" name="mode" id="mode" >
<input type="hidden" name="schCode" id="schCode" >

<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span>
                        <spring:message code="menu.menu02" />
                        <span>></span>
                        <strong><spring:message code="menu.menu02_3" /></strong>
                    </div>
                </div>
            </div>

            <div class="Swrap Asearch">
                <div class="Atitle"><spring:message code="page.common.pageCnt" arguments="${totalCnt}"/></div>
                <section class="Bclear">
                    
						    
                    <select name="searchCondition"  id="searchCondition">
								<option value=""><spring:message code="combobox.text" /></option>
								<option value="schTitle" <c:if test="${searchVO.searchCondition == 'schTitle' }"> selected="selected" </c:if>>스케줄명</option>
								<option value=schMessage <c:if test="${searchVO.searchCondition == 'schMessage' }"> selected="selected" </c:if>>메세지내용</option>
					</select>
					<input class="nameB" type="text" name="searchKeyword" id="searchKeyword" value="${searchVO.searchKeyword}">                  
                    <a href="javascript:search_form();"><span class="redBtn">
                    <spring:message code="button.inquire" />
                    </span></a>
                    
                </section>
            </div>
            
            
            
        </div>
    </div>

<c:import url="/backoffice/inc/bottom_inc.do" />
</form:form>
</div>
  
</body>
</html>