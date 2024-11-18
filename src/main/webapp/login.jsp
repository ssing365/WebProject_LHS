<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="icon" href="img/logo/clapper.png"/> 
    <title>Spoiler Page - 로그인</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body class="bg-gradient-primary">
<% 
if (session.getAttribute("UserId") != null) {
    response.sendRedirect("index.kosmo");
    return;
}
%>


	<script>
	/* 
	로그인 폼의 입력값을 서버로 전송하기 전에 검증하기 위해 정의한 함수.
	입력값이 빈값인지 확인하여 경고창 출력.*/
	function validateForm(form){
		/* 
		매개변수로 전달된 form태그의 DOM을 통해 하위태그인 input에 접근할 수 있다.
		접근시에는 name속성값을 사용하고, value는 입력된 값을 가리키게된다.
		*/
		if(!form.user_id.value){
			//입력된 값이 없다면 경고창을 띄우고..
			alert("아이디를 입력하세요.");
			//입력을 위해 포커스를 이동하고..
			form.user_id.focus();
			//submit이벤트핸들러쪽으로 false를 반환한다. -> 서버로 전송은 취소(중단)된다.
			return false;
		}
		if(form.user_pw.value == ""){
			alert("패스워드를 입력하세요.");
			form.user_pw.focus(); //포커스 이동 개좋은데
			return false;
		}
		
	}
	</script>

    <div class="container">

        <!-- Outer Row -->
        <div class="row justify-content-center">

            <div class="col-xl-10 col-lg-12 col-md-9">

                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <!-- Nested Row within Card Body -->
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-block bg-login-image">
                            	<div style=" position:absolute; top: 50%; left: 50%; transform: translate(-30%, -50%);
                            	    width: 400px; font-size: large; color:red;">
	                            	<%=request.getAttribute("LoginErrMsg") == null ? 
											"" : request.getAttribute("LoginErrMsg")%>
                            	</div>
                            </div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4">Welcome Back to Spoiler Page!</h1> 
                                        
                                    </div>
                                    <form class="user" action="loginProcess.jsp" method="post" onsubmit="return validateForm(this);">
                                        <div class="form-group">
                                            <input type="text" class="form-control form-control-user"
                                                id="exampleInputEmail" name="user_id" placeholder="아이디">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class="form-control form-control-user"
                                                id="exampleInputPassword" name="user_pw" placeholder="비밀번호">
                                        </div>
                                        <div class="form-group">
                                            <div class="custom-control custom-checkbox small">
                                                <input type="checkbox" class="custom-control-input" id="customCheck">
                                                <label class="custom-control-label" for="customCheck">아이디저장</label>
                                            </div>
                                        </div>
                                        <input type="submit" class="btn btn-primary btn-user btn-block" value="로그인">
                                        </input>
                                    </form>
                                    <hr>
                                    <div class="text-center">
                                        <a class="small" href="forgot-password.kosmo">비밀번호를 잊으셨나요?</a>
                                    </div>
                                    <div class="text-center">
                                        <a class="small" href="register.kosmo">계정이 없으신가요? 회원가입하기</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>

    <!-- Font Awesome  -->
    <script src="https://kit.fontawesome.com/d1e91e0615.js" crossorigin="anonymous"></script>
    
    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>

</body>

</html>