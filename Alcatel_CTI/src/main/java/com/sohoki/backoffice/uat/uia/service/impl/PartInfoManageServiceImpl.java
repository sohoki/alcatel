package com.sohoki.backoffice.uat.uia.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import com.sohoki.backoffice.mapper.PartInfoManageMapper;
import com.sohoki.backoffice.uat.uia.service.PartInfo;
import com.sohoki.backoffice.uat.uia.service.PartInfoVO;
import com.sohoki.backoffice.uat.uia.service.PartInfoManageService;


@Service("PartInfoManageService")
public class PartInfoManageServiceImpl extends EgovAbstractServiceImpl implements PartInfoManageService {
	
	
	@Resource(name="PartInfoManageMapper")
	private PartInfoManageMapper partMapper;

	@Override
	public List<PartInfoVO> selectPartInfoPageInfoManageListByPagination(PartInfoVO serarch) throws Exception {
		return partMapper.selectPartInfoPageInfoManageListByPagination(serarch);
	}

	@Override
	public List<PartInfoVO> selectPartInfoCombo() throws Exception {
		return partMapper.selectPartInfoCombo();
	}

	@Override
	public int selectPartInfoPageInfoManageListToCnt_s(PartInfoVO serarch) throws Exception {
		return partMapper.selectPartInfoPageInfoManageListToCnt_s(serarch);
	}

	@Override
	public PartInfoVO selectPartInfoDetail(String partId) throws Exception {
		// TODO Auto-generated method stub
		return partMapper.selectPartInfoDetail(partId);
	}

	@Override
	public int updatePartInfoManage(PartInfo vo) throws Exception {
		// TODO Auto-generated method stub
		if (vo.getMode().equals("Ins")){
			return partMapper.insertPartInfoManage(vo);
		}else {
			return partMapper.updatePartInfoManage(vo);
		}
	}
	
	

}
