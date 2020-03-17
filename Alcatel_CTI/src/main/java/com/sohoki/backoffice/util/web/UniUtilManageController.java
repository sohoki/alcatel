package com.sohoki.backoffice.util.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.sohoki.backoffice.util.service.UniUtilInfo;
import com.sohoki.backoffice.util.service.UniUtilManageService;

@RestController
public class UniUtilManageController {


    private static final Logger LOGGER = LoggerFactory.getLogger(UniUtilManageController.class);
	
	@Resource(name="UniUtilManageService")
	private UniUtilManageService utilService;
	
	
	@RequestMapping(value="/backoffice/util/UniCheck.do")
	@ResponseBody
	public String selectUniCheck(@ModelAttribute("vo") UniUtilInfo vo
								 , HttpServletRequest request
								 , BindingResult bindingResult						
								 , ModelMap model)throws Exception{
		
		
		try{
			
			LOGGER.debug("vo:" + vo.getInTable()+","+vo.getInCheckName()+","+vo.getInCondition());
			int ret = utilService.selectIdDoubleCheck(vo);
			LOGGER.debug("ret:"+ret);
			if (ret > 0){
				return "E";
			}else {
				return "O";
			}
		}catch(Exception e){
			LOGGER.debug("selectUniCheck error:" + e.toString());
			return "F";
		}
	}
	@RequestMapping(value="/backoffice/util/UnideleteParam.do")
	@ResponseBody
	public String uniDeleteParam(HttpServletRequest request) throws Exception {
		
      try{
			
			String tableId =  request.getParameter("tableId");
			String conField =  request.getParameter("conField");
			String value =  request.getParameter("value");
			
    	    UniUtilInfo utilInfo = new UniUtilInfo();
  		    utilInfo.setInTable(tableId);
  		    utilInfo.setInCondition(conField+"=["+value+"[");
			
			int ret = utilService.deleteUniStatement(utilInfo);
			
			LOGGER.debug("ret:"+ret);
			if (ret > 0){
				return "O";
			}else {
				return "F";
			}
		}catch(Exception e){
			LOGGER.debug("uniDelete error:" + e.toString());
			return "F";
		}
	}
	@RequestMapping(value="/backoffice/util/Unidelete.do")
	@ResponseBody
	public String uniDelete(@ModelAttribute("vo") UniUtilInfo vo
							, HttpServletRequest request
							, BindingResult bindingResult						
							, ModelMap model)throws Exception{
		try{
			
			int ret = utilService.deleteUniStatement(vo);
			
			LOGGER.debug("ret:"+ret);
			if (ret > 0){
				return "O";
			}else {
				return "F";
			}
		}catch(Exception e){
			LOGGER.debug("uniDelete error:" + e.toString());
			return "F";
		}
	}
	public int callUniDelete(UniUtilInfo vo)throws Exception{
		//임시 조치
		vo.setOtCnt(-1);
		utilService.deleteUniStatement(vo);
		int otCnt =vo.getOtCnt();
		LOGGER.debug("otCnt:" + otCnt);
		return 1;
	}
}
