   function fn_optionControl(selectId, selectval, startFloor, endFloor){
	    $("#"+selectId).show();
 		$("#"+selectId+" option").remove();
 		$("#"+selectId).append("<option value=''>선택</option>");
 		for (var i = startFloor; i <= endFloor; i ++){
 			$("#"+selectId).append("<option value='"+ i +"'>"+i+"층</option>");
 	    }   
 	    if (selectval != "")
 	    	$("#"+selectId).val(selectval);
 	    
   }
   google.load("visualization", "1", {packages:["corechart"]});
   function fn_agentState(agentCode){
	     var param = {
	                'searchDay' : today_get(),
	                'agentCode' : agentCode
	       };
		   uniAjax("/backoffice/basicManage/agentStateAjax.do", param, 
  			function(result) {
				       if (result.status == "LOGIN FAIL"){
				    	   alert(result.meesage);
						   location.href="/backoffice/login.do";
					   }else if (result.status == "SUCCESS"){
						    //여기 부분 수정 어떻게 할FV지 추후 생각
						    google.setOnLoadCallback(drawChart(result.agentState));
					   }
				    },
				    function(request){
					    alert("Error:" +request.status );	       						
				    }    		
       ); 
		    
 }
 function drawChart(json_data) {   
		try{
			var data = new google.visualization.DataTable();
			data.addColumn('string', '시간');
			data.addColumn('number', '카운터수');
			data.addRows(JSON.parse(json_data));
			var options = { title: '시간별 접속 현황', fontSize: '12', fontName: '굴림체', 
					        hAxis: {title: '시간', titleTextStyle: {color: 'red', fontName: '굴림체'}} , 
					        vAxis: {title: '접속 카운트', titleTextStyle: {color: 'blue', fontName: '굴림체'}}
			};      
			var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
			chart.draw(data, options);   
		}catch( e){
			alert(e);
		}
} 
 function fn_SendMsg(code, arrayName, ckGubun){
	 var del_atch = "";
	 if (ckGubun == "S"){
		 del_atch = $("#"+arrayName).val();
	 }else {
	    var cnt = $("input[name="+arrayName+"]:checkbox:checked").length;
	   	if (cnt< 1){
	   		alert("하나 이상의 체크를 선택 하셔야 합니다");
	   		return;
	   	}else {
	   		for (var i= 0; i < document.getElementsByName(arrayName).length; i++){
	   			if (document.getElementsByName(arrayName)[i].checked == true){
	   				del_atch = del_atch+","+document.getElementsByName("agentInput")[i].value;	
	   			}    			
	   		}
	   		del_atch = del_atch.substring(1);
	   	}
	 }
	 apiExecute(
	        "POST", "/backoffice/basicManage/restartAgentInfo.do", { xmlProceNm : code, RestartDidInfo : del_atch},
			null,				
			function(result) {							
				if (result != null) {	         							
					if (result == "O"){
						alert("요청이 정삭적으로  저리 되었습니다.");
					}else {
						alert("처리 도중  문제가 생겼습니다.");
					}
				}
			},null, null
	);
	 
}
//장비 통신 이력 
function fn_msgView(code){
	   
	   if (code != $("#popGubun").val()){
		   $("#agentPageIndex").val("1");
		   $("#popGubun").val(code);
	   }
	   var url = "";
	   switch ($("#popGubun").val()){
	        case "S" :
	        	url = "/backoffice/operManage/pop_sendLst.do" ;
	        	break;
	        case "M" :
	        	url = "/backoffice/pop_msgLst.do" ;
	        	break;
	        case "C" :
	        	url = "/backoffice/contentManage/agentContentList.do" ;
	        	break;
	   }
		var params = { agentCode : $("#agentCode").val(), 
				       pageIndex : fn_emptyReplace($("#agentPageIndex").val(),"1"), 
				       searchKeyword : fn_emptyReplace($("#agentSearchCondition").val(),""),
				       searchCondition : fn_emptyReplace($("#agentSearchKeyword").val(),"")
		             };
		uniAjax(url, params, 
  			function(result) {
				       if (result.status == "LOGIN FAIL"){
				    	    alert(result.meesage);
							location.href="/backoffice/login.do";
					   }else if (result.status == "SUCCESS"){
						    //테이블 정리 하기
							$("#tb_agentOrderList > tbody").empty(); 
							$("#tb_agentMsgList > tbody").empty();
						     if ($("#popGubun").val() == "S"){
						    	fn_sendHistory(result.megLst, result.paginationInfo);
						    	$("#tb_agentOrderList").show();
						    	$("#tb_agentMsgList").hide();
						    	$("#popTitle").html("<h2>단말기 통신이력</h2>");
						     }else if ($("#popGubun").val() == "C"){
							    	fn_contentHistory(result.contentList, result.paginationInfo);
							 }else{
						    	fn_msgHistory(result.msgLst, result.paginationInfo);
						    	$("#tb_agentOrderList").hide(); 
						    	$("#tb_agentMsgList").show();
						    	$("#popTitle").html("<h2>메세지 통신이력</h2>");
						     }
						    //페이징 처리 하기
						    var pageObj = result.paginationInfo;						
								
					   }
				    },
				    function(request){
					    alert("Error:" +request.status );	       						
				    }    		
   );
}
function ajaxPageChange(code){
	 $("#agentPageIndex").val(code);
	 fn_msgView($("#popGubun").val());		 
}
function ajaxContentPageChange(code){
	 $("#tb_agentOrderList").hide(); 
	 $("#tb_agentMsgList").hide();
	 $("#tb_agentOrderList > tbody").empty(); 
	 $("#tb_agentMsgList > tbody").empty();
	 $("#agentPageIndex").val(code);
	 fn_msgView($("#popGubun").val());		 
}
function fn_contentHistory(msgList, pageObj){
    var data = msgList;
    $("#tb_ContentInfo > tbody").empty();
    if (data.length > 0 ){
    	for (var i = 0; i < data.length; i++ ){
			setHtml = "<tr><td>"+ data[i].conschTitle +"</td>"
			        + "<td>"+ data[i].conschStartday+"~"+ data[i].conschEndday +"</td>"
			        + "<td style='text-align:center'>"+ data[i].displayTitle +"</td>"
			        + "<td style='text-align:center'>"+ data[i].sendResult +"</td>"
			        + "<td style='text-align:center'>"+ data[i].sendDate +"</td>"
			        + "<td style='text-align:center'>"+ data[i].receivedFileResult +"</td>"
			        + "<td style='text-align:center'>"+ data[i].receivedFileDate +"</td>";
			$("#tb_ContentInfo >  tbody:last").append(setHtml);	
		}
    	var pageHtml = ajaxPaging(pageObj.currentPageNo, pageObj.firstPageNo, pageObj.recordCountPerPage, 
                pageObj.firstPageNoOnPageList, pageObj.lastPageNoOnPageList, 
                pageObj.totalRecordCount, pageObj.pageSize, "ajaxContentPageChange");
        $("#contentPaging").html(pageHtml+"<div class='clear'></div>");
    }
    
}
function fn_sendHistory(msgList, pageObj){
	    var data = msgList;
	    if (data.length > 0 ){
	    	for (var i = 0; i < data.length; i++ ){
				setHtml = "<tr><td>"+ data[i].agentNm +"</td>"
				        + "<td>"+ data[i].processRemark +"</td>"
				        + "<td>"+ data[i].sendRegdate +"</td>"
				        + "<td>"+ data[i].sendResult +"</td>"
				        + "<td>"+ data[i].agentPlaytime +"</td>"
				        + "<td>"+ data[i].errorMessage +"</td>";
				$("#tb_agentOrderList >  tbody:last").append(setHtml);	
			}
	    	var pageHtml = ajaxPaging(pageObj.currentPageNo, pageObj.firstPageNo, pageObj.recordCountPerPage, 
	                pageObj.firstPageNoOnPageList, pageObj.lastPageNoOnPageList, 
	                pageObj.totalRecordCount, pageObj.pageSize, "ajaxPageChange");
	        $("#schedulePaging").html(pageHtml+"<div class='clear'></div>");
	    }
	    
}

function fn_msgHistory(msgList, pageObj){ 
	var data = msgList;
	if (data.length > 0 ){
    	for (var i = 0; i < data.length; i++ ){
			setHtml = "<tr><td>"+ data[i].schMessage +"</td>"
			        + "<td>"+ data[i].schFonttype +"</td>"
			        + "<td>"+ data[i].msgSend +"</td>"
			        + "<td>"+ data[i].msgSendRegdate +"</td>"
			        + "<td>"+ data[i].msgRecCheck +"</td>"
			        + "<td>"+ data[i].msgRecUpdate +"</td>";
			$("#tb_agentMsgList >  tbody:last").append(setHtml);	
		}
    	var pageHtml = ajaxPaging(pageObj.currentPageNo, pageObj.firstPageNo, pageObj.recordCountPerPage, 
                pageObj.firstPageNoOnPageList, pageObj.lastPageNoOnPageList, 
                pageObj.totalRecordCount, pageObj.pageSize, "ajaxPageChange");
        $("#schedulePaging").html(pageHtml+"<div class='clear'></div>");
    }
}