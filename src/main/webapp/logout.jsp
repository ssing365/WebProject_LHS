<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
/*
로그아웃처리1 : session영역의 속성명을 지정해서 삭제한다.
*/
session.removeAttribute("UserId");
session.removeAttribute("UserName");

/*
로그아웃처리2 : session영역 전체의 속성을 한꺼번에 삭제한다.
	만약 회원인증 이외의 데이터가 있다면 조심해야한다.
*/
session.invalidate();

//로그아웃 처리 후 로그인 페이지로 이동한다.
response.sendRedirect("login.jsp");
%>