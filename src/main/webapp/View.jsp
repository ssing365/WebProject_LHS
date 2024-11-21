<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>파일 첨부형 게시판 - 상세 보기(View)</h2>

	<table border="1" width="90%">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="*" />
		</colgroup>
		<tr>
			<td>번호</td>
			<td>${dto.id }</td>
			<td>작성자</td>
			<td>${dto.name }</td>
		</tr>
		<tr>
			<td>작성일</td>
			<td>${dto.postdate }</td>
			<td>조회수</td>
			<td>${dto.views}</td>
		</tr>
		<tr>
			<td>제목</td>
			<td colspan="3">${dto.title }</td>
		</tr>
		<tr>
			<td>내용</td>
			<td colspan="3" height="100">
				${dto.content }
			</td>
		</tr>
		
		<c:if test="${sessionScope.UserId eq dto.member_id}">
			<script>
		            function deleteConfirm(id){
		            	let c = confirm("게시물을 삭제하시겠습니까?")
		            	if(c){
		            		location.href = "/WebProject_LHS/delete.kosmo?id="+id;
		            	}
		            } 
		            </script>
			<button type="button"
				onclick="location.href='/WebProject_LHS/edit.kosmo?id=${param.id}'">
				수정</button>
			<button type="button" onclick="deleteConfirm(${param.id});">
				삭제</button>
		</c:if>

</body>
</html>