package auth;

import common.DBConnPool;

public class MemberDAO extends DBConnPool{
	public MemberDTO getMemberDTO(String uid, String upass) {
		//회원인증을 위한 쿼리문 실행후, 회원정보를 저장하기 위한 인스턴스 dto 생성
		MemberDTO dto = new MemberDTO();
		/*
		로그인폼에서 입력한 아이디, 패스워드를 통해 인파라미터를 설정할수 있도록 쿼리문을 작성*/
		String query = "SELECT * FROM members WHERE username = ? AND password=?";
		
		try {
			//쿼리문 실행을 위한 prepared 인스턴스 생성
			psmt = con.prepareStatement(query);
			//쿼리문의 인파라미터 설정(아이디와 비번)
			psmt.setString(1, uid);
			psmt.setString(2, upass);
			//쿼리문 실행 및 결과는 ResultSet으로 반환받는다.
			rs = psmt.executeQuery();
			
			//반환된 ResultSet객체에 정보가 저장되어있으면 조건문 실행
			if(rs.next()) {
				//회원정보가 있다면 DTO객체에 저장
				dto.setId(rs.getString("id"));
				dto.setUsername(rs.getString("username"));
				dto.setPassword(rs.getString("password"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setPhone(rs.getString("phone"));
				dto.setAddress(rs.getString("address"));
				dto.setBio(rs.getString("bio"));
				dto.setProfile_image(rs.getString("profile_image"));
				dto.setCreated_at(rs.getDate("created_at"));
				dto.setUpdated_at(rs.getDate("updated_at"));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//회원정보를 저장한 DTO객체 반환
		return dto;
	}
}
