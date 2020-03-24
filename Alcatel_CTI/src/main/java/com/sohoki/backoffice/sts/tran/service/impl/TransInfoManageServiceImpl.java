package com.sohoki.backoffice.sts.tran.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sohoki.backoffice.mapper.TranInfoManagerMapper;
import com.sohoki.backoffice.sts.tran.service.TransInfoManageService;
import com.sohoki.backoffice.sts.tran.service.TransInfo;
import com.sohoki.backoffice.sts.tran.service.TransInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;


@Service("TransInfoManageService")
public class TransInfoManageServiceImpl extends EgovAbstractServiceImpl implements TransInfoManageService{

	@Resource(name="TranInfoManagerMapper")
	private TranInfoManagerMapper tranMapper;

	@Override
	public List<TransInfoVO> selectTranInfoManageListByPagination(
			TransInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return tranMapper.selectTranInfoManageListByPagination(searchVO);
	}

	@Override
	public TransInfoVO selectTranInfoManageDetail(String tranSeq)
			throws Exception {
		// TODO Auto-generated method stub
		return tranMapper.selectTranInfoManageDetail(tranSeq);
	}

	@Override
	public int selectTranProcessCount(String tranProcessName) throws Exception {
		// TODO Auto-generated method stub
		return tranMapper.selectTranProcessCount(tranProcessName);
	}

	@Override
	public int selectTrannfoManageListTotCnt_S(TransInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return tranMapper.selectTrannfoManageListTotCnt_S(searchVO);
	}

	@Override
	public int updateTraninfoManage(TransInfo VO) throws Exception {
		// TODO Auto-generated method stub
		int ret = (VO.getMode().equals("Ins")) ? tranMapper.insertTranInfoManage(VO) : tranMapper.updateTraninfoManage(VO) ;
		return ret;
	}

	@Override
	public TransInfoVO selectTranProcessInfo(String tranProcessName)
			throws Exception {
		// TODO Auto-generated method stub
		return tranMapper.selectTranProcessInfo(tranProcessName);
	}
	
	
}
