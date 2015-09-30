package com.newroad.fulltext.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.newroad.fulltext.service.SearchHttpClient;
import com.newroad.util.auth.TokenUtil;

/**
 * @info : 客户端请求时 search进行token认证
 * @author: tangzj1
 * @data : 2013-9-11
 * @since : 1.5
 */
public class TokenAuthFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(TokenAuthFilter.class);
	/**
	 * ThreadLocal points to the current thread
	 */
	private static ThreadLocal<JSONObject> current = new ThreadLocal<JSONObject>();

	private SearchHttpClient restClient;

	/**
	 * URL 匹配
	 */
	private String urlRegx;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		urlRegx = filterConfig.getInitParameter("urlRegx");

		ServletContext context = filterConfig.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(context);
		restClient = (SearchHttpClient) ac.getBean("restClient");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String url = req.getRequestURI();

		// 不符合规则的请求url
		if (!url.matches(urlRegx)) {
			chain.doFilter(request, response);
			return;
		}
		String token = req.getHeader(TokenUtil.TOKEN);
//		String uid = req.getHeader(FullTextSearchConstants.USERID);

		if (StringUtils.isBlank(token))
			token = req.getParameter(TokenUtil.TOKEN);

		if (StringUtils.isBlank(token)) {
			Cookie[] cookies = ((HttpServletRequest) request).getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if ("token".equals(cookie.getName())) {
						token = URLDecoder.decode(cookie.getValue(), "UTF-8");
						token = token.replace("\"", "");
					}
				}
			}
		}

		if (StringUtils.isBlank(token)) {
			out(res,
					"{returnCode:401, returnMessage:'No AuthToken in Http head!'}");
			logger.warn("No AuthToken in Http head!");
			return;
		}

		// Need to change app type according to client request.
		String app = "supernote";
		logger.info("token=" + token + ";app=" + app);
		JSONObject session = restClient.checkAuth(token, app);
		if (session == null) {
			out(res,
					"{returnCode:403, returnMessage:'The AuthToken is fail or expired!'}");
			logger.warn("The AuthToken[" + token + "] is fail or expired!");
			return;
		}

		// 保存当前线程副本
		current.set(session);
		try {
			chain.doFilter(request, response);
		} finally {
			current.remove();
		}
	}

	void out(HttpServletResponse res, String json) {
		res.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = res.getWriter();
			out.write(json);
			out.close();
		} catch (Exception e) {
		    logger.error("token filter response print out error!", e);
		}
	}

	@Override
	public void destroy() {
	}

	public static JSONObject getCurrent() {
		JSONObject session = current.get();
		if (session == null)
			return null;
		return session;
	}
}
