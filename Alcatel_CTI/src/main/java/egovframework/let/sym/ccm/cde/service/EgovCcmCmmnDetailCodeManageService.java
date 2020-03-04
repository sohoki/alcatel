package egovframework.let.sym.ccm.cde.service;

import java.util.List;


public interface EgovCcmCmmnDetailCodeManageService {

	/**
	 * 공통상세코드를 삭제한다.
	 * @param cmmnDetailCode
	 * @throws Exception
	 */
	int deleteCmmnDetailCode(String  code) throws Exception;

	
	/**
	 * 공통상세코드 상세항목을 조회한다.
	 * @param cmmnDetailCode
	 * @return CmmnDetailCode(공통상세코드)
	 * @throws Exception
	 */
	List<CmmnDetailCode> selectCmmnDetailCombo (String code) throws Exception;
	
	
	CmmnDetailCode selectCmmnDetailCodeDetail(CmmnDetailCode vo) throws Exception;

	/**
	 * 공통상세코드 목록을 조회한다.
	 * @param searchVO
	 * @return List(공통상세코드 목록)
	 * @throws Exception
	 */
	
	CmmnDetailCode selectCmmnDetail(String code) throws Exception;
	
	
	List<?> selectCmmnDetailCodeList(String codeId) throws Exception;

    /**
	 * 공통상세코드 총 갯수를 조회한다.
     * @param searchVO
     * @return int(공통상세코드 총 갯수)
     */
    int selectCmmnDetailCodeListTotCnt(String codeId) throws Exception;

	/**
	 * 공통상세코드를 수정한다.
	 * @param cmmnDetailCode
	 * @throws Exception
	 */
    int updateCmmnDetailCode(CmmnDetailCode cmmnDetailCode) throws Exception;
    int selectCmmnDetailCodeIdCheck (String code) throws Exception;
    //신규 추가 
    int deleteCmmnDetailCodeId (String codeId) throws Exception;
}
