function toggleEditMode() {
	const viewFields = document.querySelectorAll('.view-field');
	const editFields = document.querySelectorAll('.edit-field');
	const editButton = document.getElementById('edit-btn');
	const saveButton = document.getElementById('save-btn');
	const cancelButton = document.getElementById('cancel-btn');

	// view-field는 숨기고 edit-field는 보이게 설정
	viewFields.forEach(field => field.style.display = 'none');
	editFields.forEach(field => field.style.display = 'block');

	editButton.style.display = editButton.style.display === 'none' ? 'inline-block' : 'none';
	saveButton.style.display = saveButton.style.display === 'none' ? 'inline-block' : 'none';
	cancelButton.style.display = cancelButton.style.display === 'none' ? 'inline-block' : 'none';
}

function toggleViewMode() {
	const viewFields = document.querySelectorAll('.view-field');
	const editFields = document.querySelectorAll('.edit-field');
	const editButton = document.getElementById('edit-btn');
	const saveButton = document.getElementById('save-btn');
	const cancelButton = document.getElementById('cancel-btn');

	// view-field는 숨기고 edit-field는 보이게 설정
	viewFields.forEach(field => field.style.display = 'block');
	editFields.forEach(field => field.style.display = 'none');

	editButton.style.display = editButton.style.display === 'none' ? 'inline-block' : 'none';
	saveButton.style.display = saveButton.style.display === 'none' ? 'inline-block' : 'none';
	cancelButton.style.display = cancelButton.style.display === 'none' ? 'inline-block' : 'none';
}

function checkUserEmail() {
	const inputEmail = document.getElementById("inputEmail").value;
	const userEmailCheckMessage = document.getElementById("userEmailCheckMessage");

	const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	if (!emailRegex.test(inputEmail)) {
		userEmailCheckMessage.innerHTML = "<div class='error'>이메일 형식이 올바르지 않습니다.</div>";
	} else {
		userEmailCheckMessage.innerHTML = "<div class='success'>사용 가능한 이메일입니다.</div>";
	}

}

function checkUserName() {
	const inputUserName = document.getElementById("inputUserName").value;
	const userNameCheckMessage = document.getElementById("userNameCheckMessage");

	const nameRegex = /^[a-zA-Z가-힣0-9]{2,16}$/;
	if (!nameRegex.test(inputUserName)) {
		userNameCheckMessage.innerHTML = "<div class='error'>2~16자 사이의 영문, 한글, 숫자만 입력 가능합니다.</div>";
	} else {
		userNameCheckMessage.innerHTML = "<div class='success'>사용 가능한 닉네임입니다.</div>"
	}
}

function validateForm(form) {
	
	    // 이메일 검증
	    const userEmailCheckMessage = document.getElementById("userEmailCheckMessage").innerHTML;
	    if (userEmailCheckMessage.includes("이메일 형식이 올바르지 않습니다.")) {
	        alert("이메일을 확인해주세요.");
	        form.inputEmail.focus();
	        return false;
	    }

	    // 닉네임 검증
	    const userNameCheckMessage = document.getElementById("userNameCheckMessage").innerHTML;
	    if (userNameCheckMessage.includes("4~20자 사이의 영문, 한글, 숫자만 입력 가능합니다.")) {
	        alert("닉네임을 확인해주세요.");
	        form.inputUserName.focus();
	        return false;
	    }

	    // 모든 검증이 통과했을 때 폼 제출
	    return true;
	}
	
function saveProfile(){
	const form = document.getElementById('profileForm');
	if (validateForm(form)){
		form.submit();
	}
}