package com.sohoki.backoffice.sts.error.service.impl;

import java.util.List;

import com.sohoki.backoffice.sts.error.service.ErrorInfo;
import com.sohoki.backoffice.sts.error.service.ErrorInfoVO;
import com.sohoki.backoffice.sts.error.service.ErrorInfoManageService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import com.sohoki.backoffice.mapper.ErrorInfoManageMapper;

@Service("ErrorInfoManageService")
public class ErrorInfoManageServiceImpl extends EgovAbstractServiceImpl implements ErrorInfoManageService {

	@Resource(name="ErrorInfoManageMapper")
	private ErrorInfoManageMapper errorMapper;
	
	

	@Override
	public List<ErrorInfoVO> selectErrorMessage(ErrorInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return errorMapper.selectErrorMessage(searchVO);
	}

	@Override
	public int insertErrorMessage(String errorMessage) throws Exception {
		// TODO Auto-generated method stub
		
		ErrorInfo vo = new ErrorInfo();
		String[] errorInfp = errorMessage.split(":");
		int ret = 0;
		if (errorInfp.length>1){
			vo.setErrorType(errorInfp[0]);
			vo.setErrorMessage(errorInfp[1]);
			ret = errorMapper.insertErrorMessage(vo);
		}else {
			System.out.println("not string");
		}
		return ret;
	}

	@Override
	public int deleteErrorMessage(String errorSeq) throws Exception {
		// TODO Auto-generated method stub
		return errorMapper.deleteErrorMessage(errorSeq);
	}

	@Override
	public int selectErrorMessageCnt(ErrorInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return errorMapper.selectErrorMessageCnt(searchVO);
	}
	
	
	
}
