<%@page import="auth.MemberDAO"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String uId = request.getParameter("userId");
    MemberDAO dao = new MemberDAO();
    boolean isTaken = dao.isUsernameTaken(uId);

    if (isTaken || uId.length() < 6 ) {
        out.print("<div class='error'><br>사용할 수 없는 아이디입니다.</div>");
    } else {
        out.print("<div class='success'><br>사용 가능한 아이디입니다.</div>");
    }
%>