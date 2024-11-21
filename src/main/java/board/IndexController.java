package board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auth.MemberDAO;
import auth.MemberDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/index.kosmo")
public class IndexController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	//게시판에서 목록은 특정 메뉴를 클릭해서 진입하므로 get방식의 요청임
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
				throws ServletException, IOException {
			/* 우측 상단 회원 정보 출력용*/
			String userId = (String) req.getSession().getAttribute("UserId");
			MemberDAO memberDAO = new MemberDAO();
			MemberDTO user = memberDAO.getUserById(userId);
			req.getSession().setAttribute("user", user);
			
			/* 게시판 출력용 */
			//DAO 인스턴스 생성. 생성과 동시에 DBCP를 통해 오라클에 연결된다.
			FreeBoardDAO dao = new FreeBoardDAO();
			
			//검색어 관련 파라미터 저장을 위해 Map 생성. 뷰에 전달할 매개변수 저장용 맵 생성
			Map<String, Object> map = new HashMap<String, Object>();
			
			//검색을 위해 검색어를 입력했다면 파라미터로 전달된 값을 Map에 저장
			String searchField = req.getParameter("searchField");
			String searchWord = req.getParameter("searchWord");
			if(searchWord != null) {
				map.put("searchField", searchField);
				map.put("searchWord", searchWord);
			}
			
			//게시물의 개수 카운트를 위한 메서드 호출
			int totalCount = dao.selectCount(map);

			//결과를 Map에 저장
			map.put("totalCount", totalCount);
			
			//목록에 출력할 레코드를 인출하기 위한 메서드 호출
			List<FreeBoardDTO> boardLists = dao.selectList(map);
			dao.close(); //DB연결해제
			
			//전달할 데이터를 request영역에 저장 후 List.jsp(View)로 포워드
			req.setAttribute("boardLists", boardLists);
			req.setAttribute("map", map);
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
			/*
			 * request영역은 포워드 된 페이지까지 데이터를 공유할 수 있으므로 
			 * 서블릿에서 처리한 내용은 request영역을 통해 JSP쪽으로 공유된다.
			 */
		}
}
