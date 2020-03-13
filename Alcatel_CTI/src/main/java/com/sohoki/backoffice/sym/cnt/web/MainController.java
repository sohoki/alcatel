package com.sohoki.backoffice.sym.cnt.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import egovframework.let.uat.uia.service.EgovLoginService;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import egovframework.rte.fdl.property.EgovPropertyService;


@RestController
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	
	
  
	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** TRACE */
    @Resource(name="leaveaTrace")
    LeaveaTrace leaveaTrace;
	
	// include 파일 정리 
	@RequestMapping(value="/backoffice/inc/top_inc.do")
	public ModelAndView nhisTop() throws Exception{	
		ModelAndView mav = new ModelAndView("/backoffice/inc/top_inc");
		return mav;
	}
	@RequestMapping(value="/backoffice/inc/bottom_inc.do")	
	public ModelAndView nhisBottom() throws Exception{				
		ModelAndView mav = new ModelAndView("/backoffice/inc/bottom_inc");
		return mav;
	}
}
