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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
	<title>다산콜센터</title>
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/popup.css">
    <script src="/js/popup.js"></script>
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    

    
</head>
<body>
<form:form name="regist" commandName="regist" method="post" action="/backoffice/popup/empSearchList.do">	   	   
<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex }">
<input type="hidden" name="code" id="code" value="${searchVO.code }">
     <div class="pop_container">
		<!--//팝업 타이틀-->
		<div class="pop_header">
			<div class="pop_contents">
				<h2>직원조회</h2>	
			</div>			
		</div>
		<!--팝업타이틀//-->
		<!--//팝업 내용-->
		<div class="pop_contents">
            
            <select name="searchCondition"  id="searchCondition">
								<option value>선택</option>
								<option value="empNo" <c:if test="${searchVO.searchCondition == 'empNo' }"> selected="selected" </c:if>>사번</option>
								<option value="empNm" <c:if test="${searchVO.searchCondition == 'empNm' }"> selected="selected" </c:if>>이름</option>
			</select>
			<input type="text" name="searchKeyword" id="searchKeyword" value="${searchVO.searchKeyword}" size="15">
            <a href="javascript:search_confirm();"><span class="redBtn">조회</span></a>
            <p class="searchP">이름을 선택해 주세요.</p>
			<table class="pop_table thStyle searchT">
				<tbody class="search">
					<tr>
						<th>부서명</th>
						<th>직책</th>
						<th>이름</th>
						<th>사번</th>
					</tr>
					<c:forEach items="${resultList }" var="userinfo" varStatus="status">
					<tr class="click">
						<td><a href="javascript:userInfoJ('${userinfo.orgName }','${userinfo.posGrdNm }','${userinfo.empNm }','${userinfo.empNo },'${userinfo.email }','${userinfo.mobTelNo }','${userinfo.orgId }')">${userinfo.orgName }</a></td>
						<td>${userinfo.posGrdNm }</td>
                        <td><a href="javascript:userInfoJ('${userinfo.orgName }','${userinfo.posGrdNm }','${userinfo.empNm }','${userinfo.empNo }','${userinfo.email }','${userinfo.mobTelNo }','${userinfo.orgId }')">${userinfo.empNm }</a></td>
                        <td>${userinfo.empNo }</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!--팝업 내용//-->
        <!--//팝업 버튼-->
        <div class="pagenum">
                <div class="pager">
                   <ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage"  />
                </div>
        </div>
        <div class="footerBtn">
            <a href="javascrfipt:self.closer();" class="deepBtn">닫기</a>
        </div>
        <!--팝업 버튼//-->
	</div>
</form:form>
<script type="text/javascript">
   function userInfoJ(code, code1, code2, code3, code4, code5, code6){
	   if ($("#code").val()==""){
		   var deptNm = code1+"("+code+")";		   
		   $("#deptName", opener.document).val(deptNm); 
		   $("#adminName", opener.document).val(code2); 
		   $("#adminTel", opener.document).val(code5); 
		   $("#adminEmail", opener.document).val(code4); 
		   $("#empNo", opener.document).val(code3); 
		   $("#deptId", opener.document).val(code6);
	   }else if ($("#code").val()=="hly") { 
		   $("#orgName", opener.document).val(code1); 
		   $("#empNm", opener.document).val(code2); 
		   $("#empNo", opener.document).val(code3); 
		   $("#orgId", opener.document).val(code6);
	   }
	   else {
		   
		   $("#deptName", opener.document).val(code1); 
		   $("#empNm", opener.document).val(code2); 
		   $("#userId", opener.document).val(code3); 
		   $("#deptId", opener.document).val(code6);
	   }
	   self.close();
   }
   function search_confirm(){
	   if (any_empt_line_id("searchCondition", "검색방법을 선택 하지 않았습니다.") == false) return;	
	   if (any_empt_line_id("searchKeyword", "검색어를 입력 하지 않았습니다.") == false) return;	
	   $("form[name=regist]").attr("action", "/backoffice/popup/empSearchList.do").submit();
	   return;
   }
</script>
</body>
</html>