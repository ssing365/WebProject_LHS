package auth;

import common.DBConnPool;

public class MemberDAO extends DBConnPool{
	public MemberDTO getMemberDTO(String uid, String upass) {
		//회원인증을 위한 쿼리문 실행후, 회원정보를 저장하기 위한 인스턴스 dto 생성
		MemberDTO dto = new MemberDTO();
		/*
		로그인폼에서 입력한 아이디, 패스워드를 통해 인파라미터를 설정할수 있도록 쿼리문을 작성*/
		String query = "SELECT * FROM members WHERE userid = ? AND password=?";
		
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
				dto.setUserid(rs.getString("userid"));
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
	
    public boolean isUserIdTaken(String uid) {
        String query = "SELECT COUNT(*) FROM members WHERE userid = ?";
        try {
        	psmt = con.prepareStatement(query);
        	psmt.setString(1, uid);
        	rs = psmt.executeQuery();
        		
        	if (rs.next()) {
                	return rs.getInt(1) > 0;
        		}
    	} catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isUserNameTaken(String uName) {
        String query = "SELECT COUNT(*) FROM members WHERE name = ?";
        try {
        	psmt = con.prepareStatement(query);
        	psmt.setString(1, uName);
        	rs = psmt.executeQuery();
        		
        	if (rs.next()) {
                	return rs.getInt(1) > 0;
        		}
    	} catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean registerMember(MemberDTO dto) {
    	String query = "INSERT INTO members (id, userid, password, name, email) VALUES (member_seq.NEXTVAL, ?, ?, ?, ?)";
    	try {
    		psmt = con.prepareStatement(query);
    		psmt.setString(1, dto.getUserid());
    		psmt.setString(2, dto.getPassword());
    		psmt.setString(3, dto.getName());
    		psmt.setString(4, dto.getEmail());
    		
    		return psmt.executeUpdate() == 1;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public boolean updateUser(MemberDTO dto) {
    	String query = "update MEMBERS set name=?, "
    			+ " bio=?, email=?, phone=?, address=? "
    			+ " where userid = ?";
    	try {
    		psmt = con.prepareStatement(query);
    		psmt.setString(1, dto.getName());
    		psmt.setString(2, dto.getBio());
    		psmt.setString(3, dto.getEmail());
    		psmt.setString(4, dto.getPhone());
    		psmt.setString(5, dto.getAddress());
    		psmt.setString(6, dto.getUserid());
    		
    		return psmt.executeUpdate() > 0;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public MemberDTO getUserById(String userId) {
    	MemberDTO user = null;
    	String query = "SELECT * FROM members WHERE userid = ?";
    	try {
    		psmt = con.prepareStatement(query);
    		psmt.setString(1, userId);
    		rs = psmt.executeQuery();
        
	        if (rs.next()) {
	            // DB에서 가져온 값을 MemberDTO 객체에 설정
	            user = new MemberDTO();
	            user.setUserid(rs.getString("userid"));
	            user.setName(rs.getString("name"));
	            user.setBio(rs.getString("bio"));
	            user.setEmail(rs.getString("email"));
	            user.setPhone(rs.getString("phone"));
	            user.setAddress(rs.getString("address"));
	        	}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return user;
	    }
}