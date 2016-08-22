package ren.maichu.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * apache shiro 多角色filter实现<br>
 * ex: <br>
 * roleOrFilter<br>
 * /api/app/** = authc, roleOrFilter["admin,publisher"]
 */
public class ShiroRolesAuthorizationFilter extends AuthorizationFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ShiroRolesAuthorizationFilter.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("ServletRequest, ServletResponse, Object - start"); //$NON-NLS-1$
		}

		Subject subject = getSubject(req, resp);
		String[] rolesArray = (String[]) mappedValue;

		if (rolesArray == null || rolesArray.length == 0) { // 没有角色限制，有权限访问

			if (logger.isDebugEnabled()) {
				logger.debug("ServletRequest, ServletResponse, Object - end"); //$NON-NLS-1$
			}
			return true;
		}
		for (int i = 0; i < rolesArray.length; i++) {
			if (subject.hasRole(rolesArray[i])) { // 若当前用户是rolesArray中的任何一个，则有权限访问

				if (logger.isDebugEnabled()) {
					logger.debug("ServletRequest, ServletResponse, Object - end"); //$NON-NLS-1$
				}
				return true;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ServletRequest, ServletResponse, Object - end"); //$NON-NLS-1$
		}
		return false;
	}
}