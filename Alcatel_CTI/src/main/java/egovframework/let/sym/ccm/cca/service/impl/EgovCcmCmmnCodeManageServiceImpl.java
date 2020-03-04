package egovframework.let.sym.ccm.cca.service.impl;

import java.util.List;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.mapper.EgovCmmnCodeManageMapper;
import egovframework.let.sym.ccm.cca.service.CmmnCode;
import egovframework.let.sym.ccm.cca.service.CmmnCodeVO;
import egovframework.let.sym.ccm.cca.service.EgovCcmCmmnCodeManageService;
import egovframework.let.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;

@Service("CmmnCodeManageService")
public class EgovCcmCmmnCodeManageServiceImpl extends EgovAbstractServiceImpl implements EgovCcmCmmnCodeManageService {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovCcmCmmnCodeManageServiceImpl.class);
	
	
	@Resource(name="CmmnCodeManageMapper")
	private EgovCmmnCodeManageMapper codeMapper;
	
	@Resource (name="CmmnDetailCodeManageService")
	private EgovCcmCmmnDetailCodeManageService detailService;
	
	
	@Override
	public int deleteCmmnCode(String codeId) throws Exception {
		
		int ret = 0;
		try{
			ret = detailService.deleteCmmnDetailCodeId(codeId);
			ret = codeMapper.deleteCmmnCode(codeId);
			
		}catch ( Exception e1){
			LOGGER.error("deleteCmmnCode error:" + e1.toString() );
		}
		return ret;
	}

	@Override
	public int insertCmmnCode(CmmnCode cmmnCode) throws Exception {
		// TODO Auto-generated method stub
		return codeMapper.insertCmmnCode(cmmnCode);
	}

	@Override
	public CmmnCode selectCmmnCodeDetail(String codeId) throws Exception {
		// TODO Auto-generated method stub
		return codeMapper.selectCmmnCodeDetail(codeId);
	}

	@Override
	public List<?> selectCmmnCodeList(CmmnCodeVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return codeMapper.selectCmmnCodeList(searchVO.getCodeId());
	}

	@Override
	public int selectCmmnCodeListTotCnt(CmmnCodeVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return codeMapper.selectCmmnCodeListTotCnt(searchVO);
	}

	@Override
	public int updateCmmnCode(CmmnCode cmmnCode) throws Exception {
		// TODO Auto-generated method stub
		return codeMapper.updateCmmnCode(cmmnCode);
	}

	@Override
	public int selectIDCheck(String codeId) throws Exception {
		// TODO Auto-generated method stub
		return codeMapper.selectIDCheck(codeId);
	}

}
