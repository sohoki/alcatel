package com.sohoki.backoffice.sym.agt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sohoki.backoffice.sym.agt.service.TelephoneInfo;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoManageService;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import com.sohoki.backoffice.mapper.TelephoneInfoManageMapper;

@Service("TelephoneInfoManageService")
public class TelephoneInfoManageServiceImpl extends EgovAbstractServiceImpl implements TelephoneInfoManageService{

	
	@Resource(name="TelephoneInfoManageMapper")
	private TelephoneInfoManageMapper agentMapper;

	
    @Resource(name = "egovAgentIdGnrService")
    private EgovIdGnrService idgenService;

    
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
	public int selectAgentExist(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentExist(agentCode);
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
			vo.setAgentCode(idgenService.getNextStringId());
			ret = agentMapper.insertAgentInfoManage(vo);
		}else {
			ret = agentMapper.updateAgentPageInfoManage(vo);
		}
		return ret;
	}

	@Override
	public int updateAgentIpMac(TelephoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentIpMac(vo);
	}


	@Override
	public int deleteAgentPageInfoManage(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.deleteAgentPageInfoManage(agentCode);
	}

	@Override
	public int updateAgentChangeNumber(TelephoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentChangeNumber(vo);
	}

	@Override
	public int updateAgentStateChange(TelephoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentStateChange(vo);
	}
}
