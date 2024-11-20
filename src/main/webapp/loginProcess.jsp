<%@page import="utils.CookieManager"%>
<%@page import="auth.MemberDAO"%>
<%@page import="auth.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
String userId = request.getParameter("user_id");
String userPwd = request.getParameter("user_pw");
String save_check = request.getParameter("save_check");

MemberDAO dao = new MemberDAO();
MemberDTO memberDTO = dao.getMemberDTO(userId, userPwd);
dao.close();

//만약 DTO객체에 아이디가 저장되어 있다면 로그인에 성공한 것으로 판단
if (memberDTO.getId() != null) {
	
	if (save_check != null && save_check.equals("Y")) {
		//로그인에 성공하고 아이디 저장에 체크한 상태라면 쿠키를 생성한다.
		CookieManager.makeCookie(response, "loginId", userId, 86400);
		//쿠키에는 로그인 아이디를 저장하고, 유효시간은 하루로 설정한다.

	} else {
		//로그인에 성공했지만 체크를 해제한 상태라면 쿠키를 삭제한다.
		CookieManager.deleteCookie(response, "loginId");
	}
	
	//세션 영역에 아이디와 이름을 저장한다.
	session.setAttribute("UserId", memberDTO.getUserid());
	session.setAttribute("UserName", memberDTO.getName());
	session.setAttribute("Bio", memberDTO.getBio());
	session.setAttribute("Email", memberDTO.getEmail());
	session.setAttribute("Phone", memberDTO.getPhone());
	session.setAttribute("Address", memberDTO.getAddress());
	session.setAttribute("ProfileImage", memberDTO.getProfile_image());
	
	/*세션 영역에 저장된 속성값은 페이지를 이동하더라도 유지되므로 로그인페이지로 이동
	그리고 웹브라우저를 완전히 닫을때까지 저장된 정보는 유지된다.*/
	response.sendRedirect("index.kosmo");
} else {
	/*
	로그인에 실패한 경우에는 request영역에 에러메시지를 저장한 후 로그인페이지로 포워드한다.
	request영역은 포워드한 페이지까지 데이터를 공유한다.*/
	
	request.setAttribute("LoginErrMsg", "아이디 또는 비밀번호를 확인해주세요.");
	request.getRequestDispatcher("login.jsp").forward(request, response);
}
%>