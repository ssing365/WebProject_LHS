package board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.JSFunction;

@WebServlet("/write.kosmo")
public class WriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.getAttribute("UserId") == null){
			JSFunction.alertLocation(resp, "로그인 후 이용해주세요.", "/WebProject_LHS/login.jsp");
			//Java코드가 더이상 실행되지 않도록 차단
			return;
		}
		//로그인이 완료된 상태라면 쓰기 페이지를 포워드한다.
		req.getRequestDispatcher("/Write.jsp").forward(req, resp);
	}
	
	//글쓰기 처리
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		//로그인 확인 : 세션은 일정 시간이 지나면 자동 해제되므로 확인이 필요
		HttpSession session = req.getSession();
		if(session.getAttribute("UserId")==null) {
			JSFunction.alertLocation(resp, "로그인이 만료되었습니다.",
					"/WebProject_LHS/login.jsp");
			return;
		}
		
		//폼값을 DTO에 저장
		FreeBoardDTO dto = new FreeBoardDTO();
		//작성자 아이디는 session영역에 저장된 데이터를 이용한다.
		dto.setMember_id(session.getAttribute("UserId").toString());
		//제목과 내용등은 사용자가 전송한 폼값을 받은 후 저장한다.
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		dto.setCategory("free");
		
		//DAO를 통해 DB에 게시 내용 저장(insert 쿼리문 실행)
		FreeBoardDAO dao = new FreeBoardDAO();
		//입력에 성공하면 1, 실패하면 0이 반환된다. (insert는 하나가 추가되거나 아니거나기 때문)
		int result = dao.insertWrite(dto);
		
		dao.close();
		
		//성공 or 실패?
		if(result ==1) { //글쓰기 성공
			//게시판 목록 이동
			resp.sendRedirect("/WebProject_LHS/index.kosmo");
		}
		else { //글쓰기 실패
			//글쓰기 페이지로 다시 돌아간다.
			JSFunction.alertLocation(resp, "글쓰기에 실패했습니다.", "/WebProject_LHS/Write.jsp");
		}
	}
}
