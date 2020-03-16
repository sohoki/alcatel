package com.sohoki.backoffice.uat.uia.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import egovframework.let.uat.uia.service.AdminInfoVO;
import com.sohoki.backoffice.uat.uia.service.PartInfo;
import com.sohoki.backoffice.uat.uia.service.PartInfoVO;
import com.sohoki.backoffice.uat.uia.service.PartInfoManageService;
import com.sohoki.backoffice.util.service.UniUtilManageService;


@RestController
@RequestMapping("/backoffice/basicManage/")
public class PartInfoManageController {

	
 private static final Logger LOGGER = LoggerFactory.getLogger(PartInfoManageController.class);
	 
    @Autowired
	 protected EgovMessageSource egovMessageSource;
	 @Autowired
	 protected EgovPropertyService propertiesService;
	 @Autowired
	 private PartInfoManageService partService;
	
	 
	 
	 @RequestMapping(value="partList.do")
	 public ModelAndView selectPartManagerList(@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
										 , @ModelAttribute("searchVO") PartInfoVO searchVO
										 , HttpServletRequest request
										 , BindingResult bindingResult
										 , ModelMap model) throws Exception {  
		 
		   ModelAndView mav = new ModelAndView("/backoffice/basicManage/partList");
		   try{
			   			   
			   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));          
		       searchVO.setPageSize(propertiesService.getInt("pageSize"));   
		       
		       model.addAttribute("regist", searchVO);
		       //** pageing *//       
		   	   PaginationInfo paginationInfo = new PaginationInfo();
			   paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			   
			   paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			   paginationInfo.setPageSize(searchVO.getPageSize());
			   searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			   searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			   searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			   
			   if (searchVO.getPartUseyn() == null){ searchVO.setPartUseyn("");}
			   
		       model.addAttribute("resultList",   partService.selectPartInfoPageInfoManageListByPagination(searchVO) );      
		       int totCnt = partService.selectPartInfoPageInfoManageListToCnt_s(searchVO) ;       
			   paginationInfo.setTotalRecordCount(totCnt);
		       model.addAttribute("paginationInfo", paginationInfo);
		       model.addAttribute("totalCnt", totCnt);
		       model.addAttribute("selectGroupCombo", partService.selectPartInfoCombo());
		       
		   }catch (Exception e){
			   System.out.println("e:" + e.toString());
		   }
		   return mav;
	}
	@RequestMapping(value="partDetail.do")
	public PartInfoVO partInfoDetail (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
									  , @RequestBody PartInfoVO partInfo
									  , HttpServletRequest request
									  , BindingResult bindingResult
									  , ModelMap model) throws Exception {  
		PartInfoVO info = new  PartInfoVO();
		 try{ info = partService.selectPartInfoDetail(partInfo.getPartId());
		 }catch(Exception e){
			 LOGGER.error("groupInfoDetail : error" + e.toString());
		 }
		 return info;
	}
	@RequestMapping(value="partUpdate.do", method=RequestMethod.POST)
	public int partInfoUpdate (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
										 , @RequestBody PartInfo partInfo
										 , HttpServletRequest request
										 , BindingResult bindingResult
										 , ModelMap model) throws Exception {  
		
		
		int ret  = 0;
		String meesage = "";
		try{
			LOGGER.debug("partInfo.getMode():" + partInfo.getMode());
			
			if (partInfo.getMode().equals("Ins")){
				meesage = "sucess.common.insert";
			}else {
				meesage = "sucess.common.update";
			}	
			ret = ret = partService.updatePartInfoManage(partInfo);			
			if (ret >0){
				model.addAttribute("status", Globals.STATUS_SUCCESS);
			}else {
				throw new Exception();
			}
		}catch(Exception e){
		    LOGGER.error("groupInfoUpdate : error: " + e.toString());
		    model.addAttribute("status", Globals.STATUS_FAIL);
		}
		return ret;
	}
}
