package com.sohoki.backoffice.sym.cnt.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import egovframework.let.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;
import com.sohoki.backoffice.sym.cnt.service.CenterInfo;
import com.sohoki.backoffice.sym.cnt.service.CenterInfoVO;
import com.sohoki.backoffice.sym.cnt.service.CenterInfoService;
import com.sohoki.backoffice.uat.uia.service.PartInfoManageService;
import com.sohoki.backoffice.sts.cnt.web.FileUpladController;



@Controller
@RequestMapping("/backoffice/basicManage")
public class CenterInfoManageController {

	
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(CenterInfoManageController.class);
	 
	 
	 @Autowired
	 private DefaultBeanValidator beanValidator;
	 
	 
	 @Resource(name="egovMessageSource")
	 protected EgovMessageSource egovMessageSource;
		
	 @Resource(name = "propertiesService")
	 protected EgovPropertyService propertiesService;
	 	
	 @Resource(name="PartInfoManageService")
	 private PartInfoManageService partService;
	 
	 @Resource(name="CenterInfoService")
	 private CenterInfoService centerService;
	 
	 @Resource(name="CmmnDetailCodeManageService")
	 private EgovCcmCmmnDetailCodeManageService detailService;
	 
	 
	 FileUpladController uploadFile = new FileUpladController();
	 
	 
	 @RequestMapping(value="centerList.do")
	 public String  selectCenterInfoManageListByPagination(@ModelAttribute("loginVO") AdminLoginVO loginVO
															  , @ModelAttribute("searchVO") CenterInfoVO searchVO
															  , HttpServletRequest request
															  , BindingResult bindingResult						
															  , ModelMap model) throws Exception {
			
			try{
				 Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
			     if(!isAuthenticated) {
			    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			    		return "/backoffice/login";
			     }else {
			    	//System.out.println(EgovUserDetailsHelper.getAuthenticatedUser().toString());
			    	HttpSession httpSession = request.getSession(true);
			    	loginVO = (AdminLoginVO)httpSession.getAttribute("AdminLoginVO");
			    	
			    	searchVO.setAdminLevel(loginVO.getAdminLevel());
			    	searchVO.setPartId(loginVO.getPartId());
			    	
			    	LOGGER.debug("partId:" + loginVO.getPartId());
			     }
			     //LOGGER.debug("loginVO:" + loginVO.getAdminId());
			     if(  searchVO.getPageUnit() > 0  ){    	   
			    	searchVO.setPageUnit(searchVO.getPageUnit());
				 }else {
					searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
				 }
				 searchVO.setPageSize(propertiesService.getInt("pageSize"));
				
			     PaginationInfo paginationInfo = new PaginationInfo();
				 paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
				 paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
				 paginationInfo.setPageSize(searchVO.getPageSize());
	
				 searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
				 searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
				  //수정
				 searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		         
				  List<CenterInfoVO> list =  centerService.selectCenterInfoPageInfoManageListByPagination(searchVO);
			      model.addAttribute("resultList",  list);
			      model.addAttribute("regist", searchVO);
			      int totCnt = list.size() > 0 ? Integer.valueOf(list.get(0).getTotalRecordCount()) : 0;        
				  paginationInfo.setTotalRecordCount(totCnt);
			      model.addAttribute("paginationInfo", paginationInfo);
			      model.addAttribute("totalCnt", totCnt);
				
			}catch( Exception e){
				LOGGER.error("ERROR:" + e.toString());
			}
		   //공용 확인 하기 
		   //page em 으로 넘어가는 확인 필요 
		  return "/backoffice/basicManage/centerList";	
		}
		//센터 정보 상세
		@RequestMapping (value="centerDetail.do")
		public String selectCenterInfoManageDetail(@ModelAttribute("loginVO") AdminLoginVO loginVO
                                                   , @ModelAttribute("CenterInfoVO")  CenterInfoVO vo
                                                   , HttpServletRequest request
                                    			   , BindingResult bindingResult
												   , ModelMap model ) throws Exception{	
			
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		    if(!isAuthenticated) {
		    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		    		return "/backoffice/login";
		    }
		     
			model.addAttribute("regist", vo);
			model.addAttribute("selectFloorCombo", detailService.selectCmmnDetailCombo("CenterFloor") );
			model.addAttribute("selectFloorEndCombo", detailService.selectCmmnDetailCombo("CenterFloor") );
			
			
			model.addAttribute("selectGroupCombo", partService.selectPartInfoCombo());
			//model.addAttribute("", )
			if (!vo.getMode().equals("Ins")){			
		     	model.addAttribute("regist", centerService.selectCenterInfoDetail(vo.getCenterId()));	     	
			}		
			return "/backoffice/basicManage/centerDetail";
		}
		@RequestMapping (value="centerView.do")
		public String selectCenterInfoManageView(@ModelAttribute("loginVO") AdminLoginVO loginVO
                                                   , @ModelAttribute("CenterInfoVO")  CenterInfoVO vo
                                                   , HttpServletRequest request
                                    			   , BindingResult bindingResult
												   , ModelMap model ) throws Exception{	
			
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		    if(!isAuthenticated) {
		    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		    		return "/backoffice/login";
		    }		
			model.addAttribute("regist", vo);
			CenterInfoVO info = centerService.selectCenterInfoDetail(vo.getCenterId());
			info.setMode(vo.getMode());
			
			model.addAttribute("selectFloorCombo", detailService.selectCmmnDetailCombo("CenterFloor") );
			//model.addAttribute("selectFloorEndCombo", detailService.selectCmmnDetailCombo("CenterFloor") );
			model.addAttribute("selectGroupCombo", partService.selectPartInfoCombo());
			
		    model.addAttribute("regist", info);	     	
					
			return "/backoffice/basicManage/centerView";
		}
		@RequestMapping(value="centerInfo.do")
		public ModelAndView selectCenterInfo (@ModelAttribute("loginVO") AdminLoginVO loginVO
                                                   , @RequestBody  CenterInfo vo
                                                   , HttpServletRequest request
                                    			   , BindingResult bindingResult ) throws Exception{	
			
			ModelAndView model = new ModelAndView("jsonView");
			try{
				//String centerId = request.getParameter("centerId") != null ? request.getParameter("centerId") : "";
				
				Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
			    if(!isAuthenticated) {
			    	model.addObject("status", "LOGIN FAIL");
			    	model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
			    	return model;
			    }	
			    model.addObject("status", "SUCCESS");
			    model.addObject("regist", centerService.selectCenterInfoDetail(vo.getCenterId()));
			    
			}catch (Exception e){
				model.addObject("message", egovMessageSource.getMessage("fail.request.msg"));	
				model.addObject("status", "FAIL");
				
			}
			
		    return model;
		}
		@RequestMapping (value="centerUpdate.do")
		public String updateCenterInfoManage(HttpServletRequest request,  MultipartRequest mRequest,
														 @ModelAttribute("AdminLoginVO") AdminLoginVO loginVO,
														 @ModelAttribute("CenterInfoVO") CenterInfoVO vo					
														 , BindingResult result
														 ,ModelMap model) throws Exception{
			
			
			LOGGER.debug("01");
			
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		    if(!isAuthenticated) {
		    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		    		return "/backoffice/login";
		    }
		    vo.setCenterUpdateId(EgovUserDetailsHelper.getAuthenticatedUser().toString());
			model.addAttribute("regist", vo);
			String meesage = "";
			String url = "redirect:/backoffice/basicManage/centerList.do";
			
			
	    	String realFolder = propertiesService.getString("Globals.fileStorePath") ;
	     	 
	     	vo.setCenterZipcode( vo.getCenterZipcode1() +   vo.getCenterZipcode2()  );     	     			     	
	        vo.setCenterImg( uploadFile.uploadFileNm(mRequest.getFiles("centerImg"), realFolder));
			vo.setCenterSeatImg( uploadFile.uploadFileNm(mRequest.getFiles("centerSeatImg"), realFolder));
			vo.setCenterUpdateId(loginVO.getAdminId() );
			
			
			
			try{
				
				int ret  = centerService.updateCenterInfoManage(vo);
				if (ret >0){
					model.addAttribute("status", Globals.STATUS_SUCCESS);
					meesage = vo.getMode().equals("Ins") ? "success.common.insert" : "success.common.update";
					model.addAttribute("message", egovMessageSource.getMessage(meesage));
							
				}else {
					throw new Exception();
				}
				
			}catch (Exception e){
				LOGGER.debug("error:"+ e.toString());
				model.addAttribute("status", Globals.STATUS_FAIL);
				model.addAttribute("message", egovMessageSource.getMessage("fail.common.insert"));	
			}finally {	
				return url;	
			}					
			
		}		
}
