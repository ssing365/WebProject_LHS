package auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/myPage.kosmo")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String userId = (String) req.getSession().getAttribute("UserId");
		
		MemberDAO dao = new MemberDAO();
        MemberDTO user = dao.getUserById(userId); 
        
        // 세션에 사용자 정보 저장
        req.getSession().setAttribute("user", user);

        // myPage.jsp로 이동
        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
	}

}
