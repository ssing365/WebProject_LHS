let debounceTimer;

	// 디바운싱된 아이디 체크 함수
	function debounceCheckUserId() {
	     clearTimeout(debounceTimer); // 기존 타이머를 초기화
	     debounceTimer = setTimeout(checkUserId, 500); // 500ms 후에 checkUserId 호출
	 }
	
	function checkUserId() {
	    const userId = document.getElementById("inputUserId").value;
	
	 	// 6~12자 사이의 영문, 숫자만 가능하도록 regex 추가
	    const idRegex = /^[a-zA-Z0-9]{6,12}$/;

	    // 입력값이 정규식에 맞는지 체크
	    if (!idRegex.test(userId)) {
	        userIdCheckMessage.innerHTML = "<div class='error'><br>아이디는 6~12자 사이의 영문과 숫자만 사용할 수 있습니다.</div>";
	        return;
	    }
	    
	    // AJAX 요청을 보냅니다
	    const xhr = new XMLHttpRequest();
	    xhr.open("POST", "checkUserId.jsp", true);
	    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState === 4 && xhr.status === 200) {
	            // 서버에서 반환된 메시지를 화면에 표시
	            document.getElementById("userIdCheckMessage").innerHTML = xhr.responseText;
	        }
	    };
	
	    xhr.send("userId=" + encodeURIComponent(userId));
	}
	
	function checkUserPwd() {
	    const userPwd = document.getElementById("inputPassword").value;
	    const userPwdConfirm = document.getElementById("inputRepeatPassword").value;
	    const userPwdCheckMessage = document.getElementById("userPwdCheckMessage");
	    
	 	// 8~20자 사이의 영문, 숫자만 가능하도록 regex 추가
	    const pwdRegex = /^[a-zA-Z0-9]{8,20}$/;

	    // 입력값이 정규식에 맞는지 체크
	    if (!pwdRegex.test(userPwd)) {
	    	userPwdCheckMessage.innerHTML = "<span class='error'><br>비밀번호는 8~20자 사이의 영문과 숫자만 사용할 수 있습니다.</span>";
	        return;
	    }
	    
	    if(userPwd == userPwdConfirm){
	    	userPwdCheckMessage.innerHTML = "<span class='success'>사용 가능한 비밀번호입니다.</span>"
	    } else{
	    	userPwdCheckMessage.innerHTML = "<span class='error'>비밀번호가 동일하지 않습니다.</span>"
	    }
	    
	}
	
	function checkUserEmail() {
	    const inputEmail = document.getElementById("inputEmail").value;
	    const userEmailCheckMessage = document.getElementById("userEmailCheckMessage");
	    
	    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(inputEmail)) {
        	userEmailCheckMessage.innerHTML = "<div class='error'><br>이메일 형식이 올바르지 않습니다.</div>";
        } else {
        	userEmailCheckMessage.innerHTML = "<div class='success'><br>사용 가능한 이메일입니다.</div>";
        }
	    	    
	}
	
	function checkUserName() {
        const inputUserName = document.getElementById("inputUserName").value;
        const userNameCheckMessage = document.getElementById("userNameCheckMessage");

        const nameRegex = /^[a-zA-Z가-힣0-9]{4,20}$/;
        if (!nameRegex.test(inputUserName)) {
        	userNameCheckMessage.innerHTML= "<div class='error'><br>4~20자 사이의 영문, 한글, 숫자만 입력 가능합니다.</div>";
        } else {
        	userNameCheckMessage.innerHTML = "<div class='success'><br>사용 가능한 닉네임입니다.</div>"
        }
    }
	
	function validateForm(form) {
	    const userId = document.getElementById("inputUserId").value;
	    const userPwd = document.getElementById("inputPassword").value;
	    const userPwdConfirm = document.getElementById("inputRepeatPassword").value;

	    // 아이디 검증
	    const userIdCheckMessage = document.getElementById("userIdCheckMessage").innerHTML;
	    if (userIdCheckMessage.includes("아이디는 6~12자 사이의 영문과 숫자만 사용할 수 있습니다.") || userId.length < 6 || userId.length > 12) {
	        alert("아이디를 확인해주세요.");
	        form.inputUserId.focus();
	        return false;
	    }

	    // 비밀번호 검증
	    const userPwdCheckMessage = document.getElementById("userPwdCheckMessage").innerHTML;
	    if (userPwdCheckMessage.includes("비밀번호는 8~20자 사이의 영문과 숫자만 사용할 수 있습니다.") || userPwd.length < 8 || userPwd.length > 20) {
	        alert("비밀번호를 확인해주세요.");
	        form.inputPassword.focus();
	        return false;
	    }

	    // 비밀번호 확인 검증
	    if (userPwd !== userPwdConfirm) {
	        alert("비밀번호가 일치하지 않습니다.");
	        form.inputRepeatPassword.focus();
	        return false;
	    }

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