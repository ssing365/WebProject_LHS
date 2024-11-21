<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>마이페이지 - Spoiler Page</title>
<link rel="stylesheet" href="css/myPage.css" />
</head>
<body>
<button id="homeBtn" onclick ="location.href='index.kosmo'">홈으로</button>
	<div class="container">
		<!-- 프로필 이미지 -->
		<div class="profile-image"
			style="<c:choose>
						<c:when test='${not empty ProfileImage}'>
							background-image:url('img/profile/${ProfileImage}');
						</c:when>
						<c:otherwise>
							background-image:url('img/undraw_profile.svg');
						</c:otherwise>
					</c:choose>"
             onclick="location.href='프로필 변경 URL 또는 컨트롤러'">
		</div>
		
		<form id="profileForm"
		 action="updateUser.kosmo" method="post" onsubmit="return validateForm(this);">
			<!-- 닉네임 -->
			<div class="view-field" id="name">${user.name}</div>
	        <div class="edit-field">
	            <input type="text" value="${user.name}"
	            	name="user_name" id="inputUserName"
	                minlength="4" maxlength="20" required
	                oninput="checkUserName()" />
	            <div id="userNameCheckMessage"></div>
	        </div>
	
			<!-- 회원아이디 -->
			<div class="view-field" id="userid">${user.userid}</div>
			<div>
				<input style="display:none" type="text" name="user_id" value="${user.userid}"/>
			</div>
	
			<!-- Bio (내용 없을 경우 표시하지 않음) -->
			<c:if test="${not empty user.bio}">
				<div class="view-field" id="bio">${user.bio}</div>
			</c:if>
			<div class="edit-field">
				<div class="editDiv">소개</div>
	            <textarea name="bio" rows="4" id="edit-bio">${user.bio}</textarea>
	        </div>
	
			<!-- 이메일, 전화번호, 주소 (각 항목 내용이 없으면 표시하지 않음) -->
			<c:if test="${not empty user.email}">
				<div class="view-field" id="infoDiv">
					<span>이메일:</span> <span id="info"> ${user.email}</span>
				</div>
			</c:if>
				<div class="edit-field">
					<div class="editDiv">이메일</div>
		            <input type="text" value="${user.email}" 
		            	name="email" id="inputEmail"
		            	required 
		            	oninput="checkUserEmail()"/>
		            <div id="userEmailCheckMessage"></div>
		        </div>
		        
			<c:if test="${not empty user.phone}">
				<div class="view-field" id="infoDiv">
					<span>연락처:</span> <span id="info"> ${user.phone}</span>
				</div>
			</c:if>
				<div class="edit-field">
					<div class="editDiv">연락처</div>
		            <input name="phone" type="text" id="edit-info" value="${user.phone}" />
		        </div>
		        
			<c:if test="${not empty Address}">
				<div class="view-field" id="infoDiv">
					<span>주소:</span> <span id="info"> ${user.address}</span>
				</div>
			</c:if>
				<div class="edit-field">
					<div class="editDiv">주소</div>
		            <input name="address" type="text" id="edit-info" value="${user.address}" />
		        </div>

		</form>
		<!-- 정보 수정 버튼 -->
			<div>
			<button class="edit-btn" id="edit-btn" onclick="toggleEditMode()">
				정보수정</button>
			<button class="edit-btn" id="save-btn" style="display:none;" onclick="saveProfile()">
				저장</button>
			<button class="edit-btn" id="cancel-btn" style="display:none;" onclick="toggleViewMode()">
				취소</button>
			</div>
	</div>
</body>

<script src="js/myPage.js"></script>
</html>
