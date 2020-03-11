package com.sohoki.backoffice.sym.cnt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import com.sohoki.backoffice.sym.cnt.service.CenterInfo;
import com.sohoki.backoffice.sym.cnt.service.CenterInfoVO;
import com.sohoki.backoffice.sym.cnt.service.CenterInfoService;
import com.sohoki.backoffice.mapper.CenterInfoManageMapper;

@Service("CenterInfoService")
public class CenterInfoServiceImpl extends EgovAbstractServiceImpl implements  CenterInfoService {

	
	@Resource(name="CenterInfoManageMapper")
	private CenterInfoManageMapper centerMapper;

	@Override
	public List<CenterInfoVO> selectCenterInfoPageInfoManageListByPagination(
			CenterInfoVO serarch) throws Exception {
		// TODO Auto-generated method stub
		return centerMapper.selectCenterInfoManageListByPagination(serarch);
	}

	@Override
	public List<CenterInfoVO> selectCenterCombo() throws Exception {
		// TODO Auto-generated method stub
		return centerMapper.selectCenterInfoManageCombo();
	}

	@Override
	public int selectCenterInfoPageInfoManageListToCnt_s(CenterInfoVO serarch)
			throws Exception {
		// TODO Auto-generated method stub
		return centerMapper.selectCenterInfoManageListTotCnt_S(serarch);
	}

	@Override
	public CenterInfoVO selectCenterInfoDetail(String centerId)
			throws Exception {
		// TODO Auto-generated method stub
		return centerMapper.selectCenterInfoManageDetail(centerId);
	}

	@Override
	public int updateCenterInfoManage(CenterInfo vo) throws Exception {
		// TODO Auto-generated method stub
		int ret = 0;
		if (vo.getMode().equals("Ins")){
			
			ret = centerMapper.insertCenterInfoManage(vo);
		}else {
			ret = centerMapper.updateCenterInfoManage(vo);
		}
		return ret;
	}
	
	
	
}
