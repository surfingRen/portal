package ren.maichu.portal.api;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import io.swagger.annotations.Api;
import ren.maichu.portal.model.ReturnVO;

@Service
@Path("/testMethod")
@Api(value = "/testMethod")
public class MethodTestResource {

	@Path("/post/application/json")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnVO user(User user) {
		return new ReturnVO(ReturnVO.CODE_SUCCESS, ReturnVO.MSG_SUCCESS, user);
	}

	@Path("/put/{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnVO put(@PathParam("id") String id, @FormParam("formParam") String formParam,
			@QueryParam("queryParam") String queryParam) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("id", id);
		m.put("formParam", formParam);
		m.put("queryParam", queryParam);
		return new ReturnVO(ReturnVO.CODE_FAIL, ReturnVO.MSG_FAIL, m);
	}

	@Path("/get/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnVO get(@PathParam("id") String id, @FormParam("formParam") String formParam,
			@QueryParam("queryParam") String queryParam) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("id", id);
		m.put("formParam", formParam);
		m.put("queryParam", queryParam);
		return new ReturnVO(ReturnVO.CODE_FAIL, ReturnVO.MSG_FAIL, m);
	}

	@Path("/post/{id}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnVO post(@PathParam("id") String id, @FormParam("formParam") String formParam,
			@QueryParam("queryParam") String queryParam) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("id", id);
		m.put("formParam", formParam);
		m.put("queryParam", queryParam);
		return new ReturnVO(ReturnVO.CODE_FAIL, ReturnVO.MSG_FAIL, m);
	}

	@Path("/delete/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnVO delete(@PathParam("id") String id, @FormParam("formParam") String formParam,
			@QueryParam("queryParam") String queryParam) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("id", id);
		m.put("formParam", formParam);
		m.put("queryParam", queryParam);
		return new ReturnVO(ReturnVO.CODE_FAIL, ReturnVO.MSG_FAIL, m);
	}

}
