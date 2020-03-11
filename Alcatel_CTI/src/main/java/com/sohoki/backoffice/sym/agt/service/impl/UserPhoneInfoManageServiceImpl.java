package com.sohoki.backoffice.sym.agt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sohoki.backoffice.sym.agt.service.UserPhoneInfo;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoManageService;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import com.sohoki.backoffice.mapper.UserPhoneInfoManageMapper;

@Service("UserPhoneInfoManageService")
public class UserPhoneInfoManageServiceImpl extends EgovAbstractServiceImpl implements UserPhoneInfoManageService {

	
	@Resource(name="UserPhoneInfoManageMapper")
	private UserPhoneInfoManageMapper phoneInfo;

	@Override
	public List<UserPhoneInfoVO> selectUserPhoneInfoManageListByPagination(UserPhoneInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return phoneInfo.selectUserPhoneInfoManageListByPagination(searchVO);
	}

	@Override
	public List<UserPhoneInfoVO> selectUserPhoneInfoManageListByCombo(UserPhoneInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return phoneInfo.selectUserPhoneInfoManageListByCombo(searchVO);
	}

	@Override
	public UserPhoneInfoVO selectUsserPhoneInfoDetail(String phoneNumber)
			throws Exception {
		// TODO Auto-generated method stub
		return phoneInfo.selectUsserPhoneInfoDetail(phoneNumber);
	}

	@Override
	public int selectUserPhoneInfoManageListByCnt(UserPhoneInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return phoneInfo.selectUserPhoneInfoManageListByCnt(searchVO);
	}

	@Override
	public int updateUserPhoneInfo(UserPhoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		int ret = 0;
		if (vo.getMode().equals("Ins")){
			ret = phoneInfo.insertUserPhoneInfo(vo);
		}else{
			ret = phoneInfo.updateUserPhoneInfo(vo);
		}
		return ret;
	}

	@Override
	public int deleteUserPhoneInfo(String phoneNumber) throws Exception {
		// TODO Auto-generated method stub
		return phoneInfo.deleteUserPhoneInfo(phoneNumber);
	};
	
	
	
}
