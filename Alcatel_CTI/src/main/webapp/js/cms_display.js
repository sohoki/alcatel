    function fn_disGubun(){
    	if ($("#displayGubun").val() == "DispalyGubun_2"){
    		$("#divNext").show();
    		$("#divWH").show();
    	}else {
    		$("#divNext").hide();
    		$("#divWH").hide();
    	}
    }
    function fn_ViewDis(mode, displaySeq){
		  $('#mode').val(mode);
		  $('#displaySeq').val(displaySeq);
		 
		  if (mode == "Ins"){
			   fn_disGubun();
			   fn_ComboView()
			   $("#displayTitle").val(""); 
			   $("#displayRemark").val("");
			   $("#displayGubun").val("");
			   $("#displayNextSeq").val(""); 
			   $("#displayWidth").val(""); 
			   $("#displayHeight").val(""); 
			   $("#adminLevel").val(""); 
			   $("#partId").val(""); 
			   $("input:radio[name='displayUseYn']:radio[value='Y']").prop('checked', true); 
		  }else {
		      $("#displayNextseq").remove();
			  $("form[name=regist]").attr("action", "/backoffice/contentManage/displayView.do").submit();
		  }
		  		  
	}
    function fn_checkForm(){
    	if (any_empt_line_id('displayTitle', '<spring:message code="page.display.alert01" />') == false) return;
    	if (any_empt_line_id('displayGubun', '<spring:message code="page.display.alert04" />') == false) return;
    	
    	var params = { mode:$("#mode").val(), 
    			       displaySeq : $("#displaySeq").val(),
    			       displayTitle : $("#displayTitle").val(),
    			       displayRemark : $("#displayRemark").val(),
    			       displayGubun : $("#displayGubun").val(),
    			       displayNextSeq : $("#displayNextSeq").val(),
    			       displayWidth : fn_emptyReplace($("#displayWidth").val(),"0"),
    			       displayHeight : fn_emptyReplace($("#displayHeight").val(),"0"),
    			       displayUseYn : $('input[name="displayUseYn"]:checked').val(),
    			       adminLevel : $("#adminLevel").val(),
    			       partId : fn_emptyReplace($("#partId").val(), "0")
    	              };
    	
  	    uniAjax("/backoffice/contentManage/displayBasicUpdate.do", params, 
       			function(result) {
  	    	            alert(result.message);
  				    	if (result.status == "SUCCESS"){
  							$("#displaySeq").val(result.regist.displaySeq);
  							$("#displayNextseq").remove();
  							$("#mode").val("Edt");
  							$("form[name=regist]").attr("action", "/backoffice/contentManage/displayView.do").submit();
  						}
  				    },
  				    function(request){
  					    alert("Error:" +request.status );	       						
  				    }    		
        );
    }
    function fn_agentPreView(displaySeq, displayTitle, disGubun){
	    //콘텐츠 인지 아니지에 따라 생성 파일 변경 
	   if (disGubun == "DispalyGubun_2"){
		   apiExecute(
	    			"POST", 
	    			"/backoffice/conManage/preViewCheck.do",
	    			{
	    				conSeq : $("#conSeq").val()
	    			},
	    			null,
	    			function(result) {
	    				if (result != null) {    					
	    					//시간 변경값 가지고 오기    					
	    					if (result != "F"){
	    						var url = "/backoffice/conManage/contentPreview.do?conSeq="+$("#conSeq").val();	      	
	    				  	    window.open(url,"파일 업로드", "width="+ PageWidth +",height="+PageHeight+",top=50,left=50,scrollbars=auto")	;      						
	    					}else {
	    						alert("이미지 중 타임값을 넣지 않은 콘텐츠가 있습니다.");
	    					}
	    				}
	    			},
	    			null,
	    			null
	    		);	
	   }else {
		   window.open("/backoffice/contentManage/disPreview.do?displaySeq="+displaySeq, displayTitle, "width="+screen.availWidth+",height="+screen.availHeight);
	   }
    }