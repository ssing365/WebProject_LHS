package auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register.kosmo")
public class RegisterController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String userId = req.getParameter("user_id");
		String userPwd = req.getParameter("user_pw");
		String userEmail = req.getParameter("user_email");
		String userName = req.getParameter("user_name");
		
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();
		
		dto.setUserid(userId);
		dto.setPassword(userPwd);
		dto.setEmail(userEmail);
		dto.setName(userName);
		
		boolean isRegistered = dao.registerMember(dto);

        if (isRegistered) {
            resp.sendRedirect("registerSuccess.jsp");
        } else {
            req.setAttribute("RegisterErrMsg", "회원가입에 실패했습니다.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
	}
}
