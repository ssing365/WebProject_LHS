<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>마이페이지 - Spoiler Page</title>
<style>
body {
	font-family: Arial, sans-serif;
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	margin: 0;
	background-color: #f5f5f5;
}

.container {
	width: 500px;
	padding: 100px 20px;
	background-color: white;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	text-align: center;
}

.profile-image {
	width: 250px;
	height: 250px;
	border-radius: 50%;
	background-size: cover;
	background-position: center;
	margin: 0 auto 15px;
	cursor: pointer;
}

#name {
	font-size: 1.5em;
    font-weight: bold;
    margin-bottom: 8px;
}

#userid {
	font-size: 1.1em;
	color: #888;
	margin-bottom: 10px;
}

#bio {
	font-size: 1.0em;
	margin-bottom: 15px;
}

#infoDiv{
	margin-bottom: 5px;
}

#info {
	font-size: 1.0em;
	color: #555;
}

.edit-btn {
	width: 25%;
    padding: 10px;
    background-color: #EFF2F5;
    color: #25292E;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 0.9em;
    margin-top: 20px;
}

.edit-btn:hover {
	background-color: #EAEDF0;
}

.edit-field {
    display: none;
    margin-bottom: 8px;
}

</style>
</head>
<body>
	<div class="container">
		<!-- 프로필 이미지 -->
		<div class="profile-image"
			style="<c:choose>
						<c:when test='${not empty ProfileImage}'>
							background-image:url('img/profile/${ProfileImage}');
						</c:when>
						<c:otherwise>
							background-image:url('img/profile/default_profile.jpg');
						</c:otherwise>
					</c:choose>"
             onclick="location.href='프로필 변경 URL 또는 컨트롤러'">
		</div>

		<!-- 닉네임 -->
		<div class="view-field" id="name">${UserName}</div>
        <div class="edit-field">
            <input type="text" id="edit-name" value="${UserName}" />
        </div>

		<!-- 회원아이디 -->
		<div class="view-field" id="userid">${UserId}</div>

		<!-- Bio (내용 없을 경우 표시하지 않음) -->
		<c:if test="${not empty Bio}">
			<div class="view-field" id="bio">${Bio}</div>
		</c:if>
		<div class="edit-field">
            <input type="text" id="edit-bio" value="${Bio}" />
        </div>

		<!-- 이메일, 전화번호, 주소 (각 항목 내용이 없으면 표시하지 않음) -->
		<c:if test="${not empty Email}">
			<div class="view-field" id="infoDiv">
				<span>이메일:</span> <span id="info"> ${Email}</span>
			</div>
		</c:if>
			<div class="edit-field">
	            <input type="text" id="edit-email" value="${Email}" />
	        </div>
	        
		<c:if test="${not empty Phone}">
			<div class="view-field" id="infoDiv">
				<span>전화번호:</span> <span id="info"> ${Phone}</span>
			</div>
		</c:if>
			<div class="edit-field">
	            <input type="text" id="edit-phone" value="${Phone}" />
	        </div>
	        
		<c:if test="${not empty Address}">
			<div class="view-field" id="infoDiv">
				<span>주소:</span> <span id="info"> ${Address}</span>
			</div>
		</c:if>
			<div class="edit-field">
	            <input type="text" id="edit-address" value="${Address}" />
	        </div>

		<!-- 정보 수정 버튼 -->
		<div>
		<button class="edit-btn" id="edit-btn" onclick="toggleEditMode()">
			정보수정</button>
		<button class="save-btn" id="save-btn" style="display:none;" onclick="saveProfile()">
			저장</button>
		</div>
	</div>
</body>
<script>
function toggleEditMode() {
    const viewFields = document.querySelectorAll('.view-field');
    const editFields = document.querySelectorAll('.edit-field');
    const editButton = document.getElementById('edit-btn');
    const saveButton = document.getElementById('save-btn');

 	// view-field는 숨기고 edit-field는 보이게 설정
    viewFields.forEach(field => field.style.display = 'none');
    editFields.forEach(field => field.style.display = 'block');

    editButton.style.display = editButton.style.display === 'none' ? 'inline-block' : 'none';
    saveButton.style.display = saveButton.style.display === 'none' ? 'inline-block' : 'none';
}
</script>
</html>
