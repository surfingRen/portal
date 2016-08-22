package ren.maichu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Contact;
import io.swagger.models.Info;

public class ResteasyApplication extends Application {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ResteasyApplication.class);

	private String version;

	private String resourcePackage;

	private String description;

	private String context;

	private String endpoint;

	private String title;

	private String email;

	private String contactName;

	private String consulClusterAddr;

	private boolean isSwagger;

	private boolean isConsul;

	public ResteasyApplication() {
		Properties prop = new Properties();
		InputStream in = ResteasyApplication.class.getClassLoader().getResourceAsStream("apimgr.properties");

		try {
			prop.load(in);

			isSwagger = Boolean.valueOf(prop.getProperty("config.isSwagger"));
			isConsul = Boolean.valueOf(prop.getProperty("config.isConsul"));

			version = prop.getProperty("swagger.version").trim();
			resourcePackage = prop.getProperty("swagger.resourcePackage").trim();
			description = prop.getProperty("swagger.description").trim();
			endpoint = prop.getProperty("swagger.endpoint").trim();
			context = prop.getProperty("swagger.context").trim();
			title = prop.getProperty("swagger.title").trim();
			email = prop.getProperty("swagger.contact.email").trim();
			contactName = prop.getProperty("swagger.contact.name").trim();

			if (isSwagger) {
				initSwagger(prop, in);
			}
			if (isConsul) {
				register2consul(prop, in);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		}

		// register2consul();

	}

	@SuppressWarnings("unused")
	private void register2consul(Properties prop, InputStream in) {
		if (logger.isDebugEnabled()) {
			logger.debug("<no args> - start"); //$NON-NLS-1$
		}

		consulClusterAddr = prop.getProperty("consul.cluster.addr");
		HashMap<String, Object> buildResult = consulBuild(0);
		Consul consul = (Consul) buildResult.get("consul");
		if (null == consul) {
			logger.info("none nodes are avaliable!");
			return;
		}
		String ip = (String) buildResult.get("ip");
		int port = Integer.parseInt((String) buildResult.get("port"));

		// on
		// localhost
		String serviceName = prop.getProperty("swagger.title") + "-" + prop.getProperty("swagger.version") + "-"
				+ prop.getProperty("consul.nodeId");
		String serviceId = serviceName.hashCode() + "";
		AgentClient agentClient = consul.agentClient();

		try {
			String provider = prop.getProperty("swagger.endpoint");
			String str = "http://" + provider + context + "/consul/healthyCheck";
			agentClient.register(port, URI.create(str).toURL(), 10, serviceName, serviceId);
			agentClient.pass(serviceId); // check in with Consul, serviceId
											// required
			// only. client will prepend "service:"
			// for service level checks.
		} catch (MalformedURLException | NotRegisteredException e) {
			e.printStackTrace();

		} // registers with a TTL of 3 seconds

		try {
			agentClient.pass(serviceId);
		} catch (NotRegisteredException e) {
			e.printStackTrace();

		}

		if (logger.isDebugEnabled()) {
			logger.debug("<no args> - end"); //$NON-NLS-1$
		}
	}

	private void initSwagger(Properties prop, InputStream in) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("<no args> - start"); //$NON-NLS-1$
		}

		BeanConfig beanConfig = new BeanConfig();
		Info info = new Info();
		try {
			/* beanConfig.setVersion(version); */
			beanConfig.setResourcePackage(resourcePackage);
			beanConfig.setScan(true);
			beanConfig.setBasePath(context);
			beanConfig.setHost(endpoint);
			// beanConfig.setContact(phoneNumber);
			info.setTitle(title);
			info.setDescription(description);
			info.setVersion(version);
			Contact contact = new Contact();
			contact.setEmail(email);
			contact.setName(contactName);
			info.setContact(contact);
			beanConfig.setInfo(info);
		} catch (Exception e) {
			e.printStackTrace();

			beanConfig.setVersion("1.0.0");
			beanConfig.setResourcePackage("com");
			beanConfig.setScan(true);
			beanConfig.setBasePath("/");
			info.setTitle("testAPI");
			beanConfig.setInfo(info);
			throw new Exception(e.getCause());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("<no args> - end"); //$NON-NLS-1$
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Class<?>> getClasses() {
		if (logger.isDebugEnabled()) {
			logger.debug("<no args> - start"); //$NON-NLS-1$
		}

		@SuppressWarnings("rawtypes")
		Set<Class<?>> resources = new HashSet();

		// Swagger Resources
		resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
		resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

		if (logger.isDebugEnabled()) {
			logger.debug("<no args> - end"); //$NON-NLS-1$
		}
		return resources;
	}

	private HashMap<String, Object> consulBuild(int index) {
		HashMap<String, Object> result = new HashMap<>();
		Consul consul = null;
		boolean flag = true;
		String ip;
		String port;
		String[] address;
		if (StringUtils.isNotBlank(this.consulClusterAddr)) {
			address = consulClusterAddr.split(";");
			while (flag) {
				if (index < address.length) {
					ip = address[index].substring(0, address[index].indexOf(":"));
					port = address[index].substring(address[index].indexOf(":") + 1);
					if (!"".equals(port) && !"".equals(ip)) {
						try {
							consul = Consul.builder().withHostAndPort(HostAndPort.fromParts(ip, Integer.parseInt(port)))
									.build();
							consul.agentClient().ping();// 检测是否联通
							flag = false;
							result.put("consul", consul);
							result.put("ip", ip);
							result.put("port", port);
							return result;
						} catch (RuntimeException e) {
							e.printStackTrace();
							index = index + 1;
							consulBuild(index);
						}

					}
				} else {
					result.put("consul", null);
					return null;
				}
			}
		} else {
			result.put("consul", null);
			return null;
		}
		result.put("consul", null);
		return null;
	}
}