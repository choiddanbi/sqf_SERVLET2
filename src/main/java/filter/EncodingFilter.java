package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;


@WebFilter(filterName = "encodingFilter")
public class EncodingFilter extends HttpFilter implements Filter {
      
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 전처리 ( chain을 기준 )
		System.out.println("전처리");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response); // 다음 필터 또는 servlet 호출 = forward랑 비슷..
		// 후처리
		System.out.println("후처리");
	}
}
