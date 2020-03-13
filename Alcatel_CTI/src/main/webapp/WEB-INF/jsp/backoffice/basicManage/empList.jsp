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
    <script src="/js/popup.js"></script>    
    
    <link rel="stylesheet" href="/css/needpopup.min.css" />
</head>
<body>
<noscript>자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
<form:form name="regist" commandName="regist" method="post" action="/backoffice/basicManage/empList.do">	   	  
<c:import url="/backoffice/inc/top_inc.do" />	
 
<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex }">

<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span><spring:message code="menu.menu03" />
                        <span>></span>
                        <strong><spring:message code="menu.menu03_1" /></strong>
                    </div>
                </div>
            </div>

            <div class="Swrap Asearch">
                <div class="Atitle"><spring:message code="page.common.pageCnt" arguments="${totalCnt}"/></div>
                <section class="Bclear">
                   <select name="searchCondition"  id="searchCondition">
								<option value=""><spring:message code="combobox.text" /></option>
								<option value="adminId" <c:if test="${searchVO.searchCondition == 'adminId' }"> selected="selected" </c:if>>아이디</option>
								<option value="adminName" <c:if test="${searchVO.searchCondition == 'adminName' }"> selected="selected" </c:if>>이름</option>
							</select>	
                    <input class="nameB" type="text" name="searchKeyword" id="searchKeyword" placeholder="이름" value="${searchVO.searchKeyword }">
                    <a href="javascript:search_form()"><span class="redBtn"><spring:message code="button.inquire" /></span></a>
                    <div class="rightB">
                        <a href="javascript:fn_MBER('Ins','0')" data-needpopup-show="#userPop" ><span class="deepBtn"><spring:message code="button.create" /></span></a>
                    </div>
                </section>
            </div>

            <div class="Swrap tableArea">
                <table class="margin-top30 backTable">
                    <thead>
                        <tr>
                            <th><spring:message code="common.Number.title" /></th>
                            <th><spring:message code="page.emp.title" /></th>
                            <th><spring:message code="page.emp.usrid" /></th>
                            <th><spring:message code="page.emp.usertel" /></th>
                            <th><spring:message code="page.emp.userEmail" /></th>
                            <th><spring:message code="page.emp.passLock" /></th>
                            <th><spring:message code="page.emp.userReg" /></th>
                            <th><spring:message code="button.use" /></th>
                            <th><spring:message code="button.delete" /></th>
                        </tr>
                    </thead>
                    <tbody>
                       <c:forEach items="${resultList }" var="userinfo" varStatus="status">
                        <tr>
                            <td><c:out value="${(searchVO.pageIndex - 1) * searchVO.pageSize + status.count}"/></td>
                            <td><a href="#" onclick="fn_MBER('Edt','${ userinfo.adminId }')" class="underline" data-needpopup-show="#userPop">${ userinfo.adminName }(${ userinfo.empNo })</a></td>
                            <td>${ userinfo.adminId }</td>
                            <td>${ userinfo.adminTel }</td>
                            <td>${ userinfo.adminEmail }</td>
                            <td>${ userinfo.lockYn }</td>
                            <td>${ userinfo.regDate }</td>
                            <td>${ userinfo.useYn }</td>
                            <td><a href="javascript:del_check('${ userinfo.adminId }');" class="grayBtn"><spring:message code="button.delete" /></a></td>
                        </tr>
                        </c:forEach>                       
                    </tbody>
                </table>
            </div>
            <div class="new_pagenum">
               <div class='new_pager'>
                  <ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage"  />
               </div> 
            </div>
        </div>
    </div>
<c:import url="/backoffice/inc/bottom_inc.do" />    
</form:form>    
<div id='userPop' class="needpopup">
        <div class="user_top">
            <p><span id="sp_title" /></p>
        </div>
        <div class="user_info">
            <table class="pop_table">
                <tbody>
                    <tr>
                        <th><spring:message code="page.emp.usrid" /></th>
                        <td style="text-align:left">
                        <input type="text" id="adminId" name="adminId" />
                        <span id="uniCheck" ></span>
                        </td>
                        <th><spring:message code="page.emp.title" /></th>
                        <td style="text-align:left">
                        <input type="text" id="adminName" name="adminName" />
                        (<input type="text" id="empNo" name="empNo" size="10" maxlength="20" />)
                        </td>
                    </tr>
                    <tr>
                        <th><spring:message code="page.emp.usertel" /></th>
                        <td style="text-align:left">
                        <input type="text" id="adminTel" name="adminTel" size="25" maxlength="25"/>
                        </td>
                        <th><spring:message code="page.emp.userEmail" /></th>
                        <td style="text-align:left">
                        <input type="text" id="adminEmail" name="adminEmail" size="40" maxlength="120"/>
                        </td>
                    </tr>
                    <tr>
                        <th><spring:message code="page.emp.password" /></th>
                        <td style="text-align:left">
                        <input type="password" id="adminPassword" name="adminPassword" size="20" maxlength="20" title='<spring:message code="page.emp.password" />'/>
                        </td>
                        <th><spring:message code="page.emp.passwordCk" /></th>
                        <td style="text-align:left">
                        <input name="adminPassword2" id="adminPassword2" title='<spring:message code="page.emp.passwordCk" />' type="password" size="20" maxlength="20" /></td>
                    </tr>
                    <tr>
                        <th><spring:message code="page.emp.userReg" /></th>
                        <td style="text-align:left"><span id="reg_date"></span></td>
                        <th><spring:message code="common.UseYn.title" /></th>
                        <td style="text-align:left">
                            <input type="radio" name="useYn" value="Y" <c:if test="${regist.useYn == 'Y' }"> checked </c:if> /><spring:message code="button.use" />
							<input type="radio" name="useYn" value="N" <c:if test="${regist.useYn == 'N' }"> checked </c:if> /><spring:message code="button.nouse" />					
                        </td>
                    </tr>
                    <tr>
                        <th><spring:message code="page.emp.userAuth" /> </th>
                        <td style="text-align:left">
	                        <select id="adminLevel" onClick="javascript:fn_ComboView()">
	                        </select>					
                        </td>
                        <th><spring:message code="page.emp.part" /></th>
                        <td style="text-align:left">
                            <select id="partId" name="partId">
	                        </select>	
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="clear"></div>
        </div>
    </div>
	
     <script src="/js/needpopup.min.js" ></script> 
	 <script type="text/javascript">
		function linkPage(pageNo) {
			$(":hidden[name=pageIndex]").val(pageNo);		
			$("form[name=regist]").attr("action","/backoffice/basicManage/empList.do").submit();
		}
		function fn_MBER(code, code1){
			  location.href = "/backoffice/basicManage/managerCheck.do?mode="+code+"&adminId="+code1;
		}
		function del_check(code){
			fn_uniDelJSON("/backoffice/basicManage/managerDelete.do"
					  , {adminId : code}
			          , "/backoffice/basicManage/empList.do");		
		}  
	    needPopup.config.custom = {
	            'removerPlace': 'outside',
	            'closeOnOutside': false,
	            onShow: function() {
	                console.log('needPopup is shown');
	            },
	            onHide: function() {
	                console.log('needPopup is hidden');
	            }
	    };
	    needPopup.init();
    </script>
</body>
</html>