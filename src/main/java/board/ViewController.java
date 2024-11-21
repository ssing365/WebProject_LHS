package board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/view.kosmo")
public class ViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 게시물 불러오기
		FreeBoardDAO dao = new FreeBoardDAO();
		//파라미터로 전달된 일련번호 받기
		String id = req.getParameter("id");
		//조회수 1 증가
		dao.updateViews(id);
		//일련번호에 해당하는 게시물 인출
		FreeBoardDTO dto = dao.selectView(id);
		dao.close();
		
		//줄바꿈 처리 : 웹브라우저에서 출력할 때는 <br>태그로 변경해야한다.
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
		
		//게시물(dto)저장 후 뷰로 포워드
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("View.jsp").forward(req, resp);
	}
}
