package board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.JSFunction;

@WebServlet("/edit.kosmo")
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// 로그인확인
		HttpSession session = req.getSession();
		if (session.getAttribute("UserId") == null) {
			// session영역에 인증에 관련된 속성이 있는지 확인 후 경고창 띄움
			JSFunction.alertLocation(resp, "로그인 후 이용해주세요",
					"/WebProject_LHS/login.jsp");
			return;
		}
		
		// 게시물 얻어오기 : 열람에서 사용한 메서드를 그대로 사용한다.
		String id = req.getParameter("id");
		FreeBoardDAO dao = new FreeBoardDAO();
		FreeBoardDTO dto = dao.selectView(id);
		
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/Edit.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// 로그인확인
		HttpSession session = req.getSession();
		if (session.getAttribute("UserId") == null) {
			// session영역에 인증에 관련된 속성이 있는지 확인 후 경고창 띄움
			JSFunction.alertLocation(resp, "로그인 후 이용해주세요",
					"/WebProject_LHS/login.jsp");
			return;
			
		}
		
		//수정 내용을 매개변수에서 얻어옴
		String idx = req.getParameter("idx"); //수정할 게시물의 일련번호
		
		//DTO에 저장
		FreeBoardDTO dto = new FreeBoardDTO();
		//파일을 제외한 나머지 폼값을 먼저 저장
		dto.setId(idx);
		//특히 아이디는 session에 저장된 내용으로 추가
		dto.setMember_id(session.getAttribute("UserId").toString());
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		dto.setCategory("free");
		
		//DB에 수정 내용 반영
		FreeBoardDAO dao  = new FreeBoardDAO();
		int result = dao.updatePost(dto);
		dao.close();
		
		//성공 or 실패
		if(result ==1) {
			//수정에 성공하면 열람(View.jsp)페이지로 이동해 수정된 내용 확인
			resp.sendRedirect("/WebProject_LHS/view.kosmo?id="+idx);
			
		}else {
			JSFunction.alertLocation(resp, "수정에 실패했습니다.", 
					"/WebProject_LHS/view.kosmo?id="+idx);
		}
	}
}
