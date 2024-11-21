package board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.JSFunction;

@WebServlet("/delete.kosmo")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		if (session.getAttribute("UserId") == null) {
			//session영역에 인증에 관련된 속성이 있는지 확인 후 경고창 띄움
			JSFunction.alertLocation(resp, "로그인 후 이용해주세요", "/WebProject_LHS/login.jsp");

			return;
		}
		
		// 게시물 얻어오기 : 열람에서 사용한 메서드를 그대로 사용한다.
		String id = req.getParameter("id");
		FreeBoardDAO dao = new FreeBoardDAO();
		FreeBoardDTO dto = dao.selectView(id);
		
		// 게시물 삭제
		int result = dao.deletePost(id);
		dao.close();
		if(result == 1) {
		}
		//삭제가 완료되면 목록으로 이동
		JSFunction.alertLocation(resp, "삭제되었습니다.", "/WebProject_LHS/index.kosmo");
	}

}
