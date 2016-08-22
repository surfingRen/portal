package ren.maichu.portal.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ren.maichu.portal.model.ReturnVO;

/**
 * APISTORE相关REST服务
 */
@Service
@Path("/system")
@Api(value = "/system")
public class SystemResource {

	Logger logger = LoggerFactory.getLogger(SystemResource.class);

	@Autowired
	JdbcTemplate template;

	@Path("/time")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取服务器时间", notes = "获取服务器时间")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok", response = ReturnVO.class) })
	public ReturnVO time(@Context HttpServletRequest request) {
		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("now", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		ReturnVO returnReturnVO = new ReturnVO(ReturnVO.CODE_SUCCESS, ReturnVO.MSG_SUCCESS, content);
		return returnReturnVO;
	}

	@Path("/ip")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取ip", notes = "获取客户端ip")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok", response = ReturnVO.class) })
	public ReturnVO ip(@Context HttpServletRequest request) {
		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("ip", getIpAddr(request));
		ReturnVO returnReturnVO = new ReturnVO(ReturnVO.CODE_SUCCESS, ReturnVO.MSG_SUCCESS, content);
		return returnReturnVO;
	}

	private String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = request.getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
}
