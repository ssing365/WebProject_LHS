package board;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;

public class BoardDAO extends DBConnPool{
	
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
				/*
				 * count()함수는 조건에 상관없이 항상 결과가 인출되므로
				 * if문같은 조건절없이 바로 next함수를 실행할 수 있다.
				 * 결과가 인출되지 않는(select)함수 즉 null이 반환되는 함수에서 
				 * next를 바로 실행하면 nullpointerexception이 떨어지므로
				 * while(rs.next())이나 if(rs.next())처럼 조건절을 함께 써야한다. 
				 */
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
		public List<BoardDTO> selectList(Map<String, Object>map){
			//오라클에서 인출한 레코드를 저장하기 위한 List생성
			//게시판은 순서를 지켜야하므로 set안되고 list로
			List<BoardDTO> board = new Vector<BoardDTO>();
			
			//레코드 인출을 위한 쿼리문 작성
			String query = "SELECT * FROM board ";
			//검색어 입력 여부에 따라 where절은 조건부로 추가됨
			if(map.get("searchWord")!=null) {
				query += " WHERE " + map.get("searchField")
				+" LIKE '%" + map.get("searchWord") + "%' ";
			}
			//일련번호의 내림차순으로 정렬한 후 인출한다.
			query += " ORDER BY id DESC";
			//게시판은 항상 최근에 작성한 게시물이 상단에 노출되어야한다.
			
			try {
				//PrepareStatement 인스턴스 생성
				psmt = con.prepareStatement(query);
				//쿼리문 실행 및 결과 반환(ResultSet)
				rs = psmt.executeQuery();
				//ResultSet의 크기만큼(인출된 레코드 개수만큼) 반복하여 setter로 DTO에 저장
				while(rs.next()) {
					//하나의 레코드를 저장하기 위해 DTO인스턴스 생성
					BoardDTO dto = new BoardDTO();
					System.out.println("가져옴");
					
					/*
					 * ResultSet 인스턴스에서 데이터를 추출할 때 멤버변수의 타입에 따라
					 * getString(), getInt(), getDate()로 구분하여 호출한다.
					 * 이 데이터를 DTO의 setter를 이용해 저장한다.
					 */
					dto.setId(rs.getString(1));
					dto.setMember_id(rs.getString(2));
					dto.setCategory(rs.getString(3));
					dto.setTitle(rs.getString(4));
					dto.setContent(rs.getString(5));
					dto.setViews(rs.getInt(6));
					dto.setLikes(rs.getInt(7));
					dto.setPostdate(rs.getDate(8));
					
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

}
