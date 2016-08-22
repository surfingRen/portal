package ren.maichu.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BathPathJSServlet extends HttpServlet {

	private static final long serialVersionUID = -1464168938509076363L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getContextPath();

		String basePath = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + path + "/";

		resp.getWriter().print("window.BATHPATH='" + basePath + "'");

	}

}
