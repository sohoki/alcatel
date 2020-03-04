package egovframework.let.sym.ccm.cde.web;

import java.util.List;
import java.util.Map;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.service.Globals;
import egovframework.let.sym.ccm.cde.service.CmmnDetailCode;
import egovframework.let.sym.ccm.cca.service.CmmnCode;
import egovframework.let.sym.ccm.cca.service.CmmnCodeVO;
import egovframework.let.sym.ccm.cca.service.EgovCcmCmmnCodeManageService;
import egovframework.let.sym.ccm.ccc.service.CmmnClCodeVO;
import egovframework.let.sym.ccm.ccc.service.EgovCcmCmmnClCodeManageService;
import egovframework.let.sym.ccm.cde.service.CmmnDetailCodeVO;
import egovframework.let.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;


@RestController
@RequestMapping("/backoffice/basicManage")
public class EgovCcmCmmnDetailCodeManageController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovCcmCmmnDetailCodeManageController.class);
	
	
	@Resource(name = "CmmnDetailCodeManageService")
    private EgovCcmCmmnDetailCodeManageService cmmnDetailCodeManageService;

	@Resource(name = "CmmnClCodeManageService")
    private EgovCcmCmmnClCodeManageService cmmnClCodeManageService;

	@Resource(name = "CmmnCodeManageService")
    private EgovCcmCmmnCodeManageService cmmnCodeManageService;

	@Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	
	private List<?> rightList(String codeId)throws Exception{
		List<CmmnDetailCode> codeDetail = (List<CmmnDetailCode>) cmmnDetailCodeManageService.selectCmmnDetailCodeList(codeId);
		return codeDetail;
	}
    @RequestMapping(value="codeDetailCodeDelete.do")
	public ModelAndView deleteCmmnDetailCode (@ModelAttribute("loginVO") AdminLoginVO loginVO,
			                                  @RequestBody CmmnDetailCode vo,
			                                  ModelMap modelMe,
			                                  HttpServletRequest request) throws Exception {
    	
    	
    	ModelAndView model = new 	ModelAndView("jsonView");
    	try{
    		
    		
    		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
            if(!isAuthenticated) {
            	modelMe.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
            	ModelAndView mav = new ModelAndView("/backoffice/login");
        		return mav;
            }
            
    		
    		int ret = cmmnDetailCodeManageService.deleteCmmnDetailCode(vo.getCode());
    		model.addObject("resutl", ret );   	
    	    model.addObject("cmDetailLst", (List<CmmnDetailCode>) cmmnDetailCodeManageService.selectCmmnDetailCodeList(vo.getCodeId()));
    	    int totalCnt = cmmnDetailCodeManageService.selectCmmnDetailCodeListTotCnt(vo.getCodeId());
    	    System.out.println("totCnt:" + totalCnt);
    		model.addObject("totCnt", totalCnt);
    		
    	}catch(Exception e){
    		System.out.println("error:" + e.toString());
    	}
    	return model;
	}

	/**
	* 공통상세코드 상세항목을 조회한다.
	* @param loginVO
	 * @param cmmnDetailCode
	 * @param model
	 * @return "cmm/sym/ccm/EgovCcmCmmnDetailCodeDetail"
	 * @throws Exception
	 */
	@RequestMapping(value="CcmCmmnDetailCodeDetail.do")	
 	public ModelMap selectCmmnDetailCodeDetail (@ModelAttribute("loginVO") AdminLoginVO loginVO
									 			, CmmnDetailCode cmmnDetailCode
									 			, ModelMap model)	throws Exception {
    	CmmnDetailCode vo = cmmnDetailCodeManageService.selectCmmnDetailCodeDetail(cmmnDetailCode);
		model.addAttribute("result", vo);
		return model;
	}
	
    /**
	 * 공통상세코드 목록을 조회한다.
     * @param loginVO
     * @param searchVO
     * @param model 
     * @throws Exception
     */
    @RequestMapping(value="CmmnDetailCodeList.do")
    public ModelAndView selectCmmnDetailCodeList ( @ModelAttribute("AdminLoginVO") AdminLoginVO loginVO,
    		                                       @RequestBody CmmnDetailCode vo, 
    		                                       HttpServletRequest request)  {
    	//나중에 권한 설정 값 넣기 
    	
    	
    	ModelAndView model = new 	ModelAndView("jsonView");
    	try{
    		
            List<CmmnDetailCode> codeDetail = (List<CmmnDetailCode>) cmmnDetailCodeManageService.selectCmmnDetailCodeList(vo.getCodeId());
            int totCnt = cmmnDetailCodeManageService.selectCmmnDetailCodeListTotCnt(vo.getCodeId());
            model.addObject("cmDetailLst", codeDetail);
            model.addObject("totalCnt", totCnt);
            return model;
    	}catch(Exception e){
    		return model;
    	}    	
	}
    
    @RequestMapping(value="CodeDetailUpdate.do")
	public ModelAndView updateCmmnDetailCode (@ModelAttribute("loginVO") AdminLoginVO loginVO
			                                  , @RequestBody CmmnDetailCode vo
			                                  , ModelMap modelMe
											  , BindingResult bindingResult ) throws Exception {
    	
    	
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
        	modelMe.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	ModelAndView mav = new ModelAndView("/backoffice/login");
    		return mav;
        }
    	vo.setLastUpdusrId(EgovUserDetailsHelper.getAuthenticatedUser().toString());
    	int ret  = cmmnDetailCodeManageService.updateCmmnDetailCode(vo);
    	
    	//신규 변경 작업  Ajax 변경
    	ModelAndView model = new 	ModelAndView("jsonView");
   	    List<CmmnDetailCode> codeDetail = (List<CmmnDetailCode>) cmmnDetailCodeManageService.selectCmmnDetailCodeList(vo.getCodeId());
        int totCnt = cmmnDetailCodeManageService.selectCmmnDetailCodeListTotCnt(vo.getCodeId());
        model.addObject("cmDetailLst", codeDetail);
        model.addObject("totalCnt", totCnt);
        model.addObject("result", ret);
		
    	return model;
    }
}
