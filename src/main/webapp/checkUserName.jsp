<%@page import="auth.MemberDAO"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String userName = request.getParameter("userName");
    MemberDAO dao = new MemberDAO();
    boolean isNameTaken = dao.isUserNameTaken(userName);

    if (isNameTaken) {
        out.print("<div class='error'><br>사용할 수 없는 닉네임입니다.</div>");
    } else {
        out.print("<div class='success'><br>사용 가능한 닉네임입니다.</div>");
    }
%>