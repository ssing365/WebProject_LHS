package auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updateUser.kosmo")
public class updateUserController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String userName = req.getParameter("user_name");
		String bio = req.getParameter("bio");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		String userId = req.getParameter("user_id");
		
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();
		
		dto.setName(userName);
		dto.setBio(bio);
		dto.setEmail(email);
		dto.setPhone(phone);
		dto.setAddress(address);
		dto.setUserid(userId);
		
		boolean isUpdated = dao.updateUser(dto);
		
		if(isUpdated) {
			// DB 업데이트 성공 시, 세션에 갱신된 정보를 다시 저장
            req.getSession().setAttribute("user", dto);
            resp.sendRedirect("myPage.kosmo");
		} else {
			req.setAttribute("updateMsg", "수정에 실패했습니다.");
			req.getRequestDispatcher("myPage.jsp").forward(req, resp);
		}
	}
}
