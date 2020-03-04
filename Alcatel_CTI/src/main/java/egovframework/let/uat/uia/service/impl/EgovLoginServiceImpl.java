package egovframework.let.uat.uia.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.mapper.LoginUsrManageMapper;
import egovframework.com.cmm.AdminLoginVO;
import egovframework.let.uat.uia.service.EgovLoginService;

@Service("loginService")
public class EgovLoginServiceImpl extends EgovAbstractServiceImpl implements EgovLoginService {

	@Resource(name="LoginLogManagerMapper")
    private LoginUsrManageMapper loginMapper;
	
	@Override
	public AdminLoginVO actionLogin(AdminLoginVO vo) throws Exception {
		// TODO Auto-generated method stub
		AdminLoginVO loginVO = loginMapper.selectactionLogin(vo); 
		
		
    	// 3. 결과를 리턴한다.
    	if (loginVO != null && !loginVO.getAdminId().equals("") ) {  
    		return loginVO;
    	} else {    		
    		loginVO = new AdminLoginVO();
    	}
    	return loginVO;
	}

}
