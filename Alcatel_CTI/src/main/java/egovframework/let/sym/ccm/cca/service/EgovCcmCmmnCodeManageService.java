package egovframework.let.sym.ccm.cca.service;

import java.util.List;


public interface EgovCcmCmmnCodeManageService {

	
	/**
	 * 공통코드를 삭제한다.
	 * @param cmmnCode
	 * @throws Exception
	 */
	int deleteCmmnCode(String codeId) throws Exception;

	/**
	 * 공통코드를 등록한다.
	 * @param cmmnCode
	 * @throws Exception
	 */
	int insertCmmnCode(CmmnCode cmmnCode) throws Exception;
	     
	/**
	 * 공통코드 상세항목을 조회한다.
	 * @param cmmnCode
	 * @return CmmnCode(공통코드)
	 * @throws Exception
	 */
	CmmnCode selectCmmnCodeDetail(String codeId) throws Exception;

	/**
	 * 공통코드 목록을 조회한다.
	 * @param searchVO
	 * @return List(공통코드 목록)
	 * @throws Exception
	 */
	List<?> selectCmmnCodeList(CmmnCodeVO searchVO) throws Exception;

    /**
	 * 공통코드 총 갯수를 조회한다.
     * @param searchVO
     * @return int(공통코드 총 갯수)
     */
    int selectCmmnCodeListTotCnt(CmmnCodeVO searchVO) throws Exception;

	/**
	 * 공통코드를 수정한다.
	 * @param cmmnCode
	 * @throws Exception
	 */
	int updateCmmnCode(CmmnCode cmmnCode) throws Exception;

	
	int selectIDCheck(String codeId) throws Exception;
}
