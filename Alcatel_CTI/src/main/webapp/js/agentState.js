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
 						    //여기 부분 수정 어떻게 할지 추후 생각
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
			//alert(json_data);
			
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