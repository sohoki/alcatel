package com.sohoki.backoffice.sts.error.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.sohoki.backoffice.mapper.ErrorInfoManageMapper;
import com.sohoki.backoffice.sts.error.service.ErrorInfoManageService;
import com.sohoki.backoffice.util.service.UniUtilManageService;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.let.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;
import egovframework.rte.fdl.property.EgovPropertyService;



@RestController
@RequestMapping("/backoffice/operManage")
public class ErrorInfoManageController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorInfoManageController.class);
	

	 @Resource(name="CmmnDetailCodeManageService")
	 private EgovCcmCmmnDetailCodeManageService detailService;
	 
	 @Resource(name="UniUtilManageService")
	 private UniUtilManageService utilService;
		
	 @Autowired
	 protected ErrorInfoManageService errorService;
	 
	 @Autowired
	 protected EgovPropertyService propertiesService;
		
	 @Autowired
	 protected EgovMessageSource egovMessageSource;
		
	 @Autowired
	 private DefaultBeanValidator beanValidator;
   
	
	
}
