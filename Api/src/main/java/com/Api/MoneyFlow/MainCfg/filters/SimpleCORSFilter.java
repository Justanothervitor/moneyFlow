package com.Api.MoneyFlow.MainCfg.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(SimpleCORSFilter.class);
	
	public SimpleCORSFilter() {
		logger.info("Initializing CORS Filter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		res.setHeader("Acess-Control-Allow-Origin", req.getHeader("Origin"));
		String auth = req.getHeader("Authorization");
		if(StringUtils.hasText(auth)&&(auth.startsWith("Bearer"))) {
			res.setHeader("Acess-Control-Allow-Credentials", "true");
			res.setHeader("Acess-Control-Allow-Methods", "OPTIONS,POST,GET,OPTIONS,DELETE");
			res.setHeader("Acess-Control-Max-Age", "3600");
			res.setHeader("Acess-Control-Allow-Headers", "Authorization,Content-Type,Accept, X-Requested-With,remeber-me");
		}else {
			res.setHeader("Acess-Control-Allow-Credentials", "true");
			res.setHeader("Acess-Control-Allow-Methods", "OPTIONS,POST");
			res.setHeader("Acess-Control-Max-Age", "3600");
			res.setHeader("Acess-Control-Allow-Headers", "Authorization,Content-Type,Accept, X-Requested-With,remeber-me");
				
		}
		
		chain.doFilter(req,res);

	}
	
	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}

}
