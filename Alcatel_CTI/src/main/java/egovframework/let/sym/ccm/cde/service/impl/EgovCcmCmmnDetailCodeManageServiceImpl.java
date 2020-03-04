package egovframework.let.sym.ccm.cde.service.impl;

import java.util.List;
import egovframework.com.mapper.EgovCmmnDetailCodeManageMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import egovframework.let.sym.ccm.cde.service.CmmnDetailCode;
import egovframework.let.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;

@Service("CmmnDetailCodeManageService")
public class EgovCcmCmmnDetailCodeManageServiceImpl extends EgovAbstractServiceImpl implements EgovCcmCmmnDetailCodeManageService {

	
	@Resource(name="CmmnDetailCodeManageMapper")
    private EgovCmmnDetailCodeManageMapper CmmnDetailCodeManageMapper;
	/**
	 * 공통상세코드를 삭제한다.
	 */
	@Override
	public int deleteCmmnDetailCode(String code) throws Exception {
		 return CmmnDetailCodeManageMapper.deleteCmmnDetailCode(code);		
	}
	
	/**
	 * 공통상세코드 상세항목을 조회한다.
	 */
	@Override
	public CmmnDetailCode selectCmmnDetailCodeDetail(CmmnDetailCode vo) throws Exception {
    	return CmmnDetailCodeManageMapper.selectCmmnDetailCodeDetail(vo.getCode());    	
	}

	/**
	 * 공통상세코드 목록을 조회한다.
	 */
	@Override
	public List<?> selectCmmnDetailCodeList(String codeId) throws Exception {
        return CmmnDetailCodeManageMapper.selectCmmnDetailCodeListByPagination(codeId);
	}
	/**
	 * 공통상세코드 총 갯수를 조회한다.
	 */
	@Override
	public int selectCmmnDetailCodeListTotCnt(String codeId) throws Exception {
        return CmmnDetailCodeManageMapper.selectCmmnDetailCodeListTotCnt(codeId);
	}
	
	@Override
	public List<CmmnDetailCode> selectCmmnDetailCombo(String code) throws Exception {
		// TODO Auto-generated method stub
		return CmmnDetailCodeManageMapper.selectCmmnDetailCombo(code);
	}
	

	/**
	 * 공통상세코드를 수정한다.
	 */
	@Override
	public int updateCmmnDetailCode(CmmnDetailCode vo) throws Exception {
		if (vo.getMode().equals("Ins")){
			return CmmnDetailCodeManageMapper.insertCmmnDetailCode(vo);
		}else {
			return CmmnDetailCodeManageMapper.updateCmmnDetailCode(vo);
		}
	}

	@Override
	public int selectCmmnDetailCodeIdCheck(String code) throws Exception {
		// TODO Auto-generated method stub
		return CmmnDetailCodeManageMapper.selectCmmnDetailCodeIdCheck(code);
	}

	@Override
	public CmmnDetailCode selectCmmnDetail(String code) throws Exception {
		// TODO Auto-generated method stub
		return CmmnDetailCodeManageMapper.selectCmmnDetail(code);
	}

	@Override
	public int deleteCmmnDetailCodeId(String codeId) throws Exception {
		// TODO Auto-generated method stub
		return CmmnDetailCodeManageMapper.deleteCmmnDetailCodeId(codeId);
	}
	

}
