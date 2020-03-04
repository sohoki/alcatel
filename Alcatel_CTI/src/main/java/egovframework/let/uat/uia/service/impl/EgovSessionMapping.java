package egovframework.let.uat.uia.service.impl;


import java.sql.ResultSet;
import java.sql.SQLException;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.AdminLoginVO;
import egovframework.rte.fdl.security.userdetails.EgovUserDetails;
import egovframework.rte.fdl.security.userdetails.jdbc.EgovUsersByUsernameMapping;

import javax.sql.DataSource;


public class EgovSessionMapping extends EgovUsersByUsernameMapping  {
	
	
	/**
	 * 사용자정보를 테이블에서 조회하여 EgovUsersByUsernameMapping 에 매핑한다.
	 * @param ds DataSource
	 * @param usersByUsernameQuery String
	 */
	public EgovSessionMapping(DataSource ds, String usersByUsernameQuery) {
        super(ds, usersByUsernameQuery);
    }

	/**
	 * mapRow Override
	 * @param rs ResultSet 결과
	 * @param rownum row num
	 * @return Object EgovUserDetails
	 * @exception SQLException
	 */
	@Override
	protected EgovUserDetails mapRow(ResultSet rs, int rownum) throws SQLException {
    	//logger.debug("## EgovUsersByUsernameMapping mapRow ##");
    	
        String strUserId    = rs.getString("ADMIN_ID");        
        String strPassWord  = rs.getString("ADMIN_PASSWORD");        
        boolean strEnabled  = true;
        String strUserNm    = rs.getString("ADMIN_NAME");        
        String strUserTel    = rs.getString("ADMIN_TEL");        
        String strUserEmail = rs.getString("ADMIN_EMAIL");        
        String strAdminLevel  = rs.getString("ADMIN_LEVEL");     
        
        String strLockYn = rs.getString("LOCK_YN");        
        String strUseYn = rs.getString("USE_YN");
        String strEmpNo = rs.getString("EMP_NO");
        String strPartId = rs.getString("PART_ID");
        
        
        // 세션 항목 설정
        AdminLoginVO loginVO = new AdminLoginVO();
        loginVO.setAdminId(strUserId);
        loginVO.setAdminPassword(strPassWord);
        loginVO.setAdminName(strUserNm);
        loginVO.setAdminTel(strUserTel);
        loginVO.setAdminEmail(strUserEmail);
        loginVO.setAdminLevel(strAdminLevel);
        loginVO.setPartId(strPartId); 
        
        loginVO.setLockYn(strLockYn);
        loginVO.setUseYn(strUseYn);
        loginVO.setEmpNo(strEmpNo);
        loginVO.setPartId(strPartId);
        

        return new EgovUserDetails(strUserId, strPassWord, strEnabled, loginVO);
    }

}
