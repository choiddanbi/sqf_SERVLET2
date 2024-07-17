package servlet.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import dao.UserDao;
import entity.User;

@WebServlet("/api/register")
public class RegisterServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		
		//아이디 중복 체크 
		if(UserDao.findUserByUsername(username) != null) {
			StringBuilder responsebody = new StringBuilder();
			responsebody.append("<script>");
			responsebody.append("alert('이미 존재하는 사용자이름입니다.');");
			responsebody.append("history.back();");
			responsebody.append("</script>");


			resp.setContentType("text/html");
			resp.getWriter().println(responsebody.toString());
			return;
		}
		
		User user = User.builder()
				.username(username)
				.password(BCrypt.hashpw(password, BCrypt.gensalt()))
				.name(name)
				.email(email)
				.build();
		UserDao.save(user);
		StringBuilder responsebody = new StringBuilder();
		responsebody.append("<script>");
		responsebody.append("alert('회원가입에 성공하였습니다. 로그인 후 이용바랍니다.');");
		responsebody.append("location.replace('/ssa/login');");
		responsebody.append("</script>");

		
		resp.setContentType("text/html");
		resp.getWriter().println(responsebody.toString());
		return;
		
	}

}
