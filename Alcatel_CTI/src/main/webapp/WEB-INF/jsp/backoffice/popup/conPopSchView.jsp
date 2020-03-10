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
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <title><spring:message code="URL.TITLE" /></title>
    <link href="/css/reset.css" rel="stylesheet" />
    <link href="/css/global.css" rel="stylesheet" />
    <link href="/css/page.css" rel="stylesheet" />
    <script src="/js/popup.js"></script>
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
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
<form:form name="regist" commandName="regist" method="post" action="/backoffice/contentManage/conPopSchView.do">
<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex }">
<input type="hidden" name="mode" id="mode" >
<input type="hidden" name="displaySeq" id="displaySeq" >
<input type="hidden" name="conschCode" id="conschCode" >

<div class="pop_container">
        <!--//팝업 타이틀-->
        <div class="pop_header">
            <div class="pop_contents">
                <h2><spring:message code="menu.menu01_4" /></h2>
            </div>
        </div>
        <div class="pop_contents">
              <div class="Swrap Asearch">
                <div class="Atitle"><spring:message code="page.common.pageCnt" arguments="${totalCnt}"/></div>
            </div>

            <div class="Swrap tableArea">
                <table class="margin-top30 backTable">
                    <thead>
                        <tr>
                            <th><spring:message code="common.Number.title" /></th>
                            <th><spring:message code="page.display.title" /></th>
                            <th><spring:message code="page.display.agentCnt" /></th>
                            <th><spring:message code="page.display.contentCnt" /></th>
                            <th><spring:message code="page.display.playTotalCnt" /></th>
                            <th><spring:message code="common.UseYn.title" /></th>
                            <th><spring:message code="page.consch.conschSend" /></th>
                        </tr>
                    </thead>
                    <tbody>
                     <c:forEach items="${resultList }" var="displayInfo" varStatus="status">
                        <tr>
                            <td><c:out value="${ totalCnt - (((searchVO.pageIndex - 1) * searchVO.pageSize) + status.count) + 1}"/></td>
                            <td><a href="javascript:fn_ViewDis('Edt','${ displayInfo.displaySeq }')" class="underline" >${ displayInfo.displayTitle }</a></td>
                            <td><a href="javascript:fn_agentList('${ displayInfo.displaySeq }')">${displayInfo.conschAgentcnt }</a></td>
                            <td>${displayInfo.displayPagecnt }</td>
                            <td>${displayInfo.displayTotaltime }초</td>
                            <td>${displayInfo.conschUseyn }</td>
                            <td><a href="#" onclick="fn_sendSchedue('${ displayInfo.conschCode }')" class="grayBtn"><spring:message code="page.consch.conschSend" /></a></td>
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
        <div class="footerBtn">
            <a href="javascript:self.close()" class="deepBtn">닫기</a>
        </div>
        <!--팝업 버튼//-->
</div>

</form:form>
<script src="/js/needpopup.js"></script> 
<script src="/js/jquery-ui.js"></script>
<script type="text/javascript">
function fn_sendSchedue(conschCode){
	   uniAjax("/backoffice/contentManage/scheduleReset.do", {'conschCode':conschCode}, 
	   			function(result) {
						if (result.status == "SUCCESS"){
							alert(result.message);
						}else if (result.status == "LOGIN FAIL"){
							alert(result.message);
							location.href="/backoffice/login.do";
						}else {
							alert(result.message);
						}
				    },
				    function(request){
					    alert("Error:" +request.status );	       						
				    }    		
	   	   );
}
</script>
</body>
</html>