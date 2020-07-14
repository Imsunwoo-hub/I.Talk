
<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

session_start();
$userid = $_SESSION['userid'];
$username = $_SESSION['username'];
$userphonenum = $_SESSION['userphonenum'];
$useremail = $_SESSION['useremail'];
$useridentity = $_SESSION['useridentity'];

if(!$conn){
    echo 'not connected DB';
}

?>
<!DOCTYPE html>
<?php if (empty($userid)): ?>
  echo "<script>alert('로그인해주세요.');  window.location.href="http://tkdl2401.cafe24.com/html/login.html"; </script>";
<?php endif; ?>
<?php if (!empty($userid)): ?>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ITALK 마이페이지</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/common.css?ver=1" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/common.partial.css?ver=1" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/container.mypage.css?ver=1" />


</head>
<body>
        <nav>
                <div class="wrap">
                    <div id="logo">
                        <a href="index.php">
                            <img src="images/simbolwhite.png">
                        </a>
                        <p>
                          <a href="index.php">
                            <span class="namepage">I.TALK</span>
                          </a>
                        </p>
                    </div>
                    <div id="account">
                        <a href="http://tkdl2401.cafe24.com/html/php/logout.php" class="button white">LOGOUT</a>
                    </div>
                    <ul id="menu">
                        <li>
                            <img src="images/notice.png">
                            <a href="index.php">공지</a>
                        </li>
                        <li>
                            <img src="images/inquiry.png">
                            <a href="talk.php">문의</a>
                        </li>

                        <?php if ($useridentity == "교직원" || $useridentity == "교수"): ?>
                        <li>
                              <img src="images/usersetting.png">
                              <a href="usersetting.php">학생관리</a>
                        </li>
                        <?php endif; ?>
                    </ul>
                </div>
            </nav>
            <div id="container" class="mypage">
                <aside>
                    <div class="title">
                        <h1>MYPAGE</h1>
                    </div>
                    <div class="menu">
                        <ol>
                            <li>
                                <a href="my.php">사용자 정보</a>
                            </li>
                            <li>
                                <a href="altermy.php">개인정보 변경</a>
                            </li>
                            <li>
                                <a href="alterpw.php">비밀번호 변경</a>
                            </li>
                            <li  class="active">
                                <a href="userdrop.php">회원 탈퇴</a>
                            </li>
                        </ol>
                    </div>
                </aside>
                <div class="wrap setting">
                    <form  action="userdrops.php" method="post" class="myinforemoneuser">
                        <input type="hidden" name="" value="">
                        <h2>회원 탈퇴</h2>
                        <p>
                            <label>현재 비밀번호</label>
                            <input type="password" id="userpassword"name="userpassword" maxlength="30" class="text">
                        </p>
                        <p>
                            <label>비밀번호 확인</label>
                            <input type="password" id="userpassword"name="userpassword" maxlength="30" class="text">
                        </p>
                        <p class="submit">
                            <span class="caution">
                                사용하고 계신 아이디는 탈퇴한 경우 재사용 및 복구가 불가능합니다.<br>
                                탈퇴한 아이디는 본인과 타인 모두 재사용 및 복구가 불가하오니 신중하게 선택하시기 바랍니다.<br>
                            </span>
                            <input type="submit" value="회원 탈퇴">
                        </p>

                    </form>
                </div>
            </div>
</body>
</html>
<?php endif; ?>
