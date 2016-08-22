package ren.maichu.utils.shiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.InitializingBean;

public class JdbcRealm extends org.apache.shiro.realm.jdbc.JdbcRealm implements InitializingBean {
	/**
	* Logger for this class
	*/
	private static final Logger logger = LoggerFactory.getLogger(JdbcRealm.class);

    public static final String AUTHENTICATION_QUERY = "select pwd from t_caf_apimgr_user where user_code = ?";

    public static final String USER_ROLES_QUERY = "select r.role_name from t_caf_apimgr_role_user ur,t_caf_apimgr_user u,t_caf_apimgr_role r  where ur.user_id = u.user_id and ur.role_id = r.role_id and u.user_code = ?";

    public static final String PERMISSIONS_QUERY = "select permission_name from T_CAF_APIMGR_PERMISSION p,t_caf_apimgr_role r where r.role_id = p.role_id and r.role_name  = ?";


    public JdbcRealm() {
        super();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("<no args> - start"); //$NON-NLS-1$
		}

        //override
        setAuthenticationQuery(AUTHENTICATION_QUERY);
        setUserRolesQuery(USER_ROLES_QUERY);
        setPermissionsQuery(PERMISSIONS_QUERY);

		if (logger.isDebugEnabled()) {
			logger.debug("<no args> - end"); //$NON-NLS-1$
		}
    }
}
