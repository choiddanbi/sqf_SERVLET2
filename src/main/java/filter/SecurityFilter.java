package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.User;

//마이페이지
//@WebFilter({ "/mypage", "/mypage/password" })
@WebFilter(filterName = "securityFilter")
public class SecurityFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Http < Servlet
		// tomcat -> httprequest -> servlet servletrequest(업캐스팅) -> filter httpservletrequest (다운캐스팅) -> jsp
		// tomcat에서 httprequest상태로 들어옴 -> 그대로 servlet한테 감 -> servlet에서 servletrequest(업캐스팅)해서 filter로 감 
		// -> filter httpservletrequest (다운캐스팅) -> jsp
		// 다운캐스팅 
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		
		// session 에 로그인 이력이 없을 경우
		User user = (User) session.getAttribute("authentication"); 
		if(user == null) {
			StringBuilder responseBody = new StringBuilder();
			responseBody.append("<script>");
			responseBody.append("alert('잘못된 접근입니다.\\n로그인 후에 이용바랍니다.');");
			responseBody.append("location.href='/ssa/login';");
			responseBody.append("</script>");
			
			response.setContentType("text/html");
			response.getWriter().println(responseBody.toString());
			return;
		}
		
				
		chain.doFilter(request, response); //servlet 호출
	}



}
