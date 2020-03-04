package egovframework.let.sym.ccm.ccc.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import egovframework.com.mapper.EgovCmmnClCodeManageMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import egovframework.let.sym.ccm.ccc.service.CmmnClCode;
import egovframework.let.sym.ccm.ccc.service.CmmnClCodeVO;
import egovframework.let.sym.ccm.ccc.service.EgovCcmCmmnClCodeManageService;

@Service("CmmnClCodeManageService")
public class EgovCcmCmmnClCodeManageServiceImpl extends EgovAbstractServiceImpl implements EgovCcmCmmnClCodeManageService {

	@Resource(name="CmmnClCodeManageMapper")
	EgovCmmnClCodeManageMapper cmmnClCodeManageMapper;

	/**
	 * 공통분류코드를 삭제한다.
	 */
	@Override
	public int deleteCmmnClCode(CmmnClCode cmmnClCode) throws Exception {
		return cmmnClCodeManageMapper.deleteCmmnClCode(cmmnClCode.getClCode());				
	}

	/**
	 * 공통분류코드를 등록한다.
	 */
	@Override
	public int insertCmmnClCode(CmmnClCode cmmnClCode) throws Exception {
		return cmmnClCodeManageMapper.insertCmmnClCode(cmmnClCode);    	
	}

	/**
	 * 공통분류코드 상세항목을 조회한다.
	 */
	@Override
	public CmmnClCode selectCmmnClCodeDetail(CmmnClCode cmmnClCode) throws Exception {    	
    	return cmmnClCodeManageMapper.selectCmmnClCodeDetail(cmmnClCode.getClCode());
	}

	/**
	 * 공통분류코드 목록을 조회한다.
	 */
	@Override
	public List<?> selectCmmnClCodeList(CmmnClCodeVO searchVO) throws Exception {
		return cmmnClCodeManageMapper.selectCmmnClCodeListByPagination(searchVO);        
	}

	/**
	 * 공통분류코드 총 갯수를 조회한다.
	 */
	@Override
	public int selectCmmnClCodeListTotCnt(CmmnClCodeVO searchVO) throws Exception {
		return cmmnClCodeManageMapper.selectCmmnClCodeListTotCnt(searchVO);
	}

	/**
	 * 공통분류코드를 수정한다.
	 */
	@Override
	public int updateCmmnClCode(CmmnClCode cmmnClCode) throws Exception {
		return cmmnClCodeManageMapper.updateCmmnClCode(cmmnClCode) ;		
	}

	
	
}
