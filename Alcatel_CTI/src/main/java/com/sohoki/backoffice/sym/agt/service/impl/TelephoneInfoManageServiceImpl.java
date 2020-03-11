package com.sohoki.backoffice.sym.agt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sohoki.backoffice.sym.agt.service.TelephoneInfo;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoManageService;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import com.sohoki.backoffice.mapper.TelephoneInfoManageMapper;

@Service("TelephoneInfoManageService")
public class TelephoneInfoManageServiceImpl extends EgovAbstractServiceImpl implements TelephoneInfoManageService{

	
	@Resource(name="TelephoneInfoManageMapper")
	private TelephoneInfoManageMapper agentMapper;

	@Override
	public List<TelephoneInfoVO> selectAgentPageInfoManageListByPagination(TelephoneInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentPageInfoManageListByPagination(searchVO);
	}

	@Override
	public TelephoneInfoVO selectAgentPageInfoManageDetail(String agentCode)
			throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentPageInfoManageDetail(agentCode);
	}

	@Override
	public TelephoneInfoVO selectAgentPageInfoManageView(TelephoneInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentPageInfoManageView(searchVO);
	}

	@Override
	public TelephoneInfoVO selectAgentUrlCheck(TelephoneInfo searchVO) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentUrlCheck(searchVO);
	}

	@Override
	public int selectAgentExist(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentExist(agentCode);
	}

	@Override
	public String selectDisplayCheck(TelephoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectDisplayCheck(vo);
	}

	@Override
	public int selectAgentPageInfoManageListTotCnt_S(TelephoneInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentPageInfoManageListTotCnt_S(searchVO);
	}

	@Override
	public int updateAgentPageInfoManage(TelephoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		int ret = 0;
		if (vo.getMode().equals("Ins")){
			ret = agentMapper.insertAgentPageInfoManage(vo);
		}else {
			ret = agentMapper.updateAgentPageInfoManage(vo);
		}
		return ret;
	}

	@Override
	public int updateAgentUrlRec(TelephoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentUrlRec(vo);
	}

	@Override
	public int updateAgentIpMac(TelephoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentIpMac(vo);
	}

	@Override
	public int updateAgentStateUpdate(String displaySeq) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentStateUpdate(displaySeq);
	}

	@Override
	public int updateDisplayUpdate(TelephoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateDisplayUpdate(vo);
	}

	@Override
	public int deleteAgentPageInfoManage(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.deleteAgentPageInfoManage(agentCode);
	}

	@Override
	public List<TelephoneInfoVO> selectAgentCenterPageList(TelephoneInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentCenterPageList(searchVO);
	}

	@Override
	public List<TelephoneInfoVO> selectAgentNowStateInnfo(String centerId)
			throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentNowStateInnfo(centerId);
	}

	@Override
	public int updateAgentState() throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentState();
	}
}
