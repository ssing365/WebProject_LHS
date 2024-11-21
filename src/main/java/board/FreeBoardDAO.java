package board;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;

public class FreeBoardDAO extends DBConnPool{
	
	//게시물의 개수를 카운트하기 위한 메서드
		public int selectCount(Map<String, Object> map) {
			int totalCount = 0;
			//오라클의 그룹함수는 count()를 사용해서 쿼리문 작성
			//select count(*) from mvcboard; 
			String query = "SELECT COUNT(*) FROM board";
			//매개변수로 전달된 검색어가 있는 경우에만 where절을 동적으로 추가
			/*
			 *  게시물의 일련번호를 내림차순으로 정렬하여 인출하기
			--검색어가 있는 경우 like 조건을 추가한 후 개수 파악
			select count(*) from mvcboard where title like '%자료%'; --5개
			select count(*) from mvcboard where title like '%겨울%'; --0개
			*/
			if(map.get("searchWord")!=null) {
				query += " WHERE " + map.get("searchField") + " "
						+ " LIKE '% " + map.get("searchWord") + "% '";
			}
			try {
				//Statement 인스턴스 생성(정적 쿼리문 실행)
				stmt = con.createStatement();
				//쿼리문을 실행한 후 결과를 ResultSet으로 반환받는다.
				rs = stmt.executeQuery(query);
				rs.next();
				totalCount = rs.getInt(1);
			}
			catch(Exception e) {
				System.out.println("게시물 카운트 중 예외 발생");
				e.printStackTrace();
			}
			return totalCount;
		}
		
		//게시판 목록에 출력할 레코드를 인출하기 위한 메서드 정의
		public List<FreeBoardDTO> selectList(Map<String, Object>map){
			//오라클에서 인출한 레코드를 저장하기 위한 List생성
			//게시판은 순서를 지켜야하므로 set안되고 list로
			List<FreeBoardDTO> board = new Vector<FreeBoardDTO>();
			
			//레코드 인출을 위한 쿼리문 작성
			String query = "SELECT Bo.*, Me.name FROM board Bo "
					+ " inner join members Me on Bo.member_id = Me.userid";
			//검색어 입력 여부에 따라 where절은 조건부로 추가됨
			if(map.get("searchWord")!=null) {
				query += " WHERE " + map.get("searchField")
				+" LIKE '%" + map.get("searchWord") + "%' ";
			}
			//일련번호의 내림차순으로 정렬한 후 인출한다.
			query += " ORDER BY Bo.id DESC";
			//게시판은 항상 최근에 작성한 게시물이 상단에 노출되어야한다.
			
			try {
				//PrepareStatement 인스턴스 생성
				psmt = con.prepareStatement(query);
				//쿼리문 실행 및 결과 반환(ResultSet)
				rs = psmt.executeQuery();
				//ResultSet의 크기만큼(인출된 레코드 개수만큼) 반복하여 setter로 DTO에 저장
				while(rs.next()) {
					//하나의 레코드를 저장하기 위해 DTO인스턴스 생성
					FreeBoardDTO dto = new FreeBoardDTO();
					
					dto.setId(rs.getString(1));
					dto.setMember_id(rs.getString(2));
					dto.setCategory(rs.getString(3));
					dto.setTitle(rs.getString(4));
					dto.setContent(rs.getString(5));
					dto.setViews(rs.getInt(6));
					dto.setLikes(rs.getInt(7));
					dto.setPostdate(rs.getDate(8));
					dto.setName(rs.getString("NAME"));
					
					//레코드가 저장된 DTO를 List에 개수만큼 추가한다.
					board.add(dto);
				}
			}
			catch (Exception e){
				System.out.println("게시물 카운트 중 예외 발생");
				e.printStackTrace();
			}
			//마지막으로 인출한 레코드를 저장한 List를 반환한다.
			return board;
		}
		
		//조회수 증가 메서드
		public void updateViews(String id) {
			String query = "UPDATE board SET "
					+ " views=views+1 "
					+ " WHERE id = ? ";
			
			try {
				psmt = con.prepareStatement(query);
				psmt.setString(1, id);
				psmt.executeQuery();
			} catch (Exception e) {
				System.out.println("게시물 조회수 증가 중 예외 발생");
				e.printStackTrace();
			}
		}
		
		//게시물 열람 메서드
		public FreeBoardDTO selectView(String id) {
			//인출한 레코드를 저장하기 위해 DTO 생성
			FreeBoardDTO dto = new FreeBoardDTO();
			//내부조인을 이용해 쿼리문 작성. member테이블의 name컬럼까지 포함.
			String query = " SELECT Bo.*, Me.name FROM board Bo "
					+ " INNER JOIN members Me ON Bo.member_id=Me.userid "
					+ " WHERE Bo.id=? "; 
			try {
				psmt = con.prepareStatement(query);//쿼리문 준비
				psmt.setString(1, id); //인파라미터 설정
				rs = psmt.executeQuery(); //쿼리문 실행
				
				//하나의 게시물을 인출하므로 if문으로 조건에 맞는 게시물이 있는지 확인
				if(rs.next()) {
					//결과를 DTO객체에 저장
					dto.setId(rs.getString("ID"));
					dto.setMember_id(rs.getString("MEMBER_ID"));
					dto.setCategory(rs.getString("CATEGORY"));
					dto.setTitle(rs.getString("TITLE"));
					dto.setContent(rs.getString("CONTENT"));
					dto.setViews(rs.getInt("VIEWS"));
					dto.setLikes(rs.getInt("LIKES"));
					dto.setPostdate(rs.getDate("CREATED_AT"));
					dto.setName(rs.getString("NAME"));
				}
			}
			catch(Exception e){
				System.out.println("게시물 상세보기 중 예외 발생");
				e.printStackTrace();
			}
			return dto;
		}
		
		//글쓰기 처리를 위한 메서드
		public int insertWrite(FreeBoardDTO dto) {
			int result = 0;
			try {
				String query = 
						"INSERT INTO Board ( "
						+ " id, member_id, category, title, content) "
						+ " VALUES ( "
						+ " board_seq.NEXTVAL,?,?,?,?)";
				//쿼리문을 인수로 preparedStatement 인스턴스 생성
				psmt = con.prepareStatement(query);
				
				psmt.setString(1, dto.getMember_id());
				psmt.setString(2, dto.getCategory());
				psmt.setString(3, dto.getTitle());
				psmt.setString(4, dto.getContent());
				result = psmt.executeUpdate();
				
			}catch(Exception e) {
				System.out.println(" 게시물 입력 중 예외 발생 ");
				e.printStackTrace();
			}
			return result;
		}
		
		//지정한 일련번호의 게시물을 삭제한다.
		public int deletePost(String id) {
			int result = 0;
			try {
				String query = "delete from board where id = ?";
				psmt = con.prepareStatement(query);
				psmt.setString(1, id);
				result = psmt.executeUpdate();
				
			} catch (Exception e) {
				System.out.println("게시물 삭제 중 예외 발생");
				e.printStackTrace();
			}
			return result;
		}
		
		public int updatePost(FreeBoardDTO dto) {
			int result = 0;
			try {
				//쿼리문 템플릿 준비
				String query = "update board set title=?, "
						+ " content=? "
						+ " where id=? and member_id=?";
					
				//쿼리문 준비
				psmt = con.prepareStatement(query);
				psmt.setString(1, dto.getTitle());
				psmt.setString(2, dto.getContent());
				psmt.setString(3, dto.getId());
				psmt.setString(4, dto.getMember_id());
				
				//쿼리문 실행
				result = psmt.executeUpdate();
			}
			catch(Exception e) {
				System.out.println("게시물 수정 중 예외 발생");
				e.printStackTrace();
			}
			return result;
		}
}
