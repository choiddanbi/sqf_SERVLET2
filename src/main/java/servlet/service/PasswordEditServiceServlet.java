package servlet.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import dao.UserDao;
import entity.User;

@WebServlet("/api/mypage/password/edit")
public class PasswordEditServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("authentication");
		String password = req.getParameter("password");
		String checkPassword = req.getParameter("checkPassword");
		
		// input 의 pw 끼리 확인
		if(!password.equals(checkPassword)) {
			responsePasswordEditError(resp, "비밀번호가 일치하지 않습니다. \\n다시 입력하세요.");
			return;
		}
		
		// 바꾸려는 pw 와 세션의 pw가 일치하면
		if(BCrypt.checkpw(password, user.getPassword())) {
			responsePasswordEditError(resp, "기존 비밀번호와 동일한 비밀번호는 사용 할 수 없습니다. \\n다시 입력하세요.");
			return;
		}
		
		User modifyUser = User.builder()
				.userId(user.getUserId())
				.password(BCrypt.hashpw(password, BCrypt.gensalt())) // input 의 pw를 암호화해서 던져줌
				.build();
		UserDao.updatePassword(modifyUser); // db 에 업데이트
		user.setPassword(modifyUser.getPassword()); // 세션에도 변경된 pw정보 업데이트 + 강제 로그아웃하지 않을 때!

		
		StringBuilder responseBody = new StringBuilder();
		responseBody.append("<script>");
		responseBody.append("alert('비밀번호 변경 완료!!\\n다시 로그인하세요.');");
		responseBody.append("location.href='/ssa/logout';");
		responseBody.append("</script>");
		
		resp.setContentType("text/html");
		resp.getWriter().println(responseBody.toString());		
	}

	
	private void responsePasswordEditError(HttpServletResponse resp, String msg) throws IOException {
		StringBuilder responsebody = new StringBuilder();
		responsebody.append("<script>");
		responsebody.append("alert('");
		responsebody.append(msg);
		responsebody.append("');");
		responsebody.append("history.back();");
		responsebody.append("</script>");

		resp.setContentType("text/html");
		resp.getWriter().println(responsebody.toString());
	}
	
	
}
