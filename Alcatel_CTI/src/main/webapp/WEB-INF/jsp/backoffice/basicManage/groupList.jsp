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
<form:form name="regist" commandName="regist" method="post" action="/backoffice/basicManage/groupList.do">	   	  
<c:import url="/backoffice/inc/top_inc.do" />	
 
<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex }">
<input type="hidden" id="mode">
<input type="hidden" id="groupId">
<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span><spring:message code="menu.menu03" /><span>></span><strong><spring:message code="menu.menu03_2" /></strong>
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
                        <a href="#"  data-needpopup-show="#groupPop" onclick="javascript:fn_View('Ins','0');" ><span class="deepBtn"><spring:message code="button.create" /></span></a>
                    </div>
                </section>
            </div>

            <div class="Swrap tableArea">
                <table class="margin-top30 backTable">
                    <thead>
                        <tr>
                            <th><spring:message code="page.group.groupId" /></th>
                            <th><spring:message code="page.group.groupNm" /></th>
                            <th><spring:message code="page.group.groupCreaetTime" /></th>
                            <th><spring:message code="page.group.groupUseYn" /></th>
                            <th><spring:message code="page.group.parentGroupId" /></th>
                            <th><spring:message code="button.delete" /></th>
                        </tr>
                    </thead>
                    <tbody>
                       <c:forEach items="${resultList }" var="groupInfo" varStatus="status">
                        <tr>
                            <td>${ groupInfo.groupId }</td>
                            <td><a href="#" onclick="javascript:fn_View('Edt','${ groupInfo.groupId }')" class="underline" data-needpopup-show="#groupPop">${ groupInfo.groupNm }</a></td>
                            <td>${ groupInfo.groupCreateDe }</td>
                            <td>${ groupInfo.useYn }</td>
                            <td>${ groupInfo.parentGroupId }</td>
                            <td><a href="javascript:del_check('${ groupInfo.groupId }');" class="grayBtn"><spring:message code="button.delete" /></a></td>
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
    
     <div id='groupPop' class="needpopup">
        <div class="user_top">
            <p>부서 관리</p>
        </div>
        <div class="user_info">
            <table class="user_noti">
                <tbody>
                    <tr>
                        <th>부서명</th>
                        <td><input type="text" id="groupNm" name="groupNm"></td>
                    </tr>
                    <tr>
                        <th>사용유무</th>
                        <td>
                           <input type="radio" name="useYn" value="Y" />사용
						   <input type="radio" name="useYn" value="N" />사용안함	
                        </td>
                    </tr>
                    <tr>
                        <th>상위부서코드</th>
                        <td><form:select path="parentGroupId" id="parentGroupId" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectGroupCombo}" itemValue="groupId" itemLabel="groupNm"/>
							</form:select>
					   </td>
                    </tr>
                    <tr>
                        <th>정렬순서</th>
                        <td><form:select path="groupOrder" id="groupOrder" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <c:forEach var="item" varStatus="i" begin="1" end="10" step="1">
							       <option value="${item}">
							        <c:if test="${item < 10}">0</c:if><c:out value="${item}" />
							       </option>
							     </c:forEach>
							</form:select>
					   </td>
                    </tr>
                    <tr> 
                      <td colspan="2"><a href="javascript:fn_checkForm();"><spring:message code="button.create" /></span></a></td>
                    </tr>
                </tbody>
            </table>
            <div class="clear"></div>
        </div>
    </div>
    <div id='okLogin' class="needpopup close_black">
        <div class="check_pop">
            <p><span id="sp_message"></span></p>
        </div>
    </div>
<c:import url="/backoffice/inc/bottom_inc.do" />    
</form:form>    
<script src="/js/needpopup.min.js" type="text/javascript"></script> 	
<script type="text/javascript">
	function linkPage(pageNo) {
		$(":hidden[name=pageIndex]").val(pageNo);		
		$("form[name=regist]").attr("action","/backoffice/basicManage/groupList.do").submit();
	}
	$(document).ready(function() {   
		if ("${status}" != ""){
			if ("${status}" == "SUCCESS") {
				alert('<spring:message code="success.common.update" />');	
			}else{
				alert('<spring:message code="fail.common.msg" />');	
			}						
		}		
		
	});	
	function del_check(code){
		fn_uniDel("/sohoki/util/UnideleteParam.do"
				  , {tableId: "TB_GROUPINFO", conField: "GROUP_ID", value : code }
		          , "/backoffice/basicManage/groupList.do");				
	}  
	
	function fn_View(mode, groupId){
	   $("#mode").val(mode);
	   if (mode=="Edt"){
		   $("#groupId").val(groupId);
		   var param = {
	                'groupId' : groupId,
	                'mode' : mode
	            };
           $.ajax({
	            url: "/backoffice/basicManage/groupDetail.do",
	            type : "POST",
	            contentType : "application/json; charset=utf-8",
	            data : JSON.stringify(param),
	            success : function(obj) {
	            	/* var obj = JSON.stringify(response); */
	            	$("#groupNm").val(obj.groupNm); 
	            	$("#parentGroupId").val(obj.parentGroupId);
	            	$("#groupOrder").val(obj.groupOrder);
	            	$("#groupNm").val(obj.groupNm);
	            	$('input:radio[name=useYn]:input[value=' + obj.useYn + ']').attr("checked", true);	            	
	            },
	            error: function (jqXHR, textStatus, errorThrown){
		            alert(textStatus + ":" + errorThrown);
	            }
           });
	   }	
	}
	function fn_checkForm(){
		if (any_empt_line_id('groupNm', '<spring:message code="page.group.alert01" />') == false) return;
		if (any_empt_line_id('parentGroupId', '<spring:message code="page.group.alert02" />') == false) return;
		var param = {
                'groupId' : $("#groupId").val(),
                'groupNm' : $("#groupNm").val(),
                'useYn' :  $('input[name="useYn"]:checked').val(),
                'parentGroupId' : $("#parentGroupId").val(),
                'groupOrder' : $("#groupOrder").val(),
                'mode' : $("#mode").val()
            };
            $.ajax({
                type : 'POST',
                url : '/backoffice/basicManage/groupUpdate.do',
                contentType : "application/json; charset=utf-8",
                data : JSON.stringify(param),
                success : function(response) {
                   if (response > 0 ){
                	   alert("정상적으로 등록 되었습니다");
                   }else {
                	   alert("등록중 문제가 발생 하였습니다");
                   }
                   location.reload();
                },
                error : function(e) {
                    alert("ERROR : " + e.statusText);
                }
            });
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