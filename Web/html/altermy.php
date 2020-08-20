
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
                            <li class="active">
                                <a href="altermy.php">개인정보 변경</a>
                            </li>
                            <li>
                                <a href="alterpw.php">비밀번호 변경</a>
                            </li>
                            <li>
                                <a href="userdrop.php">회원 탈퇴</a>
                            </li>
                        </ol>
                    </div>
                </aside>
                <div class="wrap setting">
                    <form action="altermys.php" method="post" class="myinfoset">
                        <h2>개인정보 변경</h2>
                        <p>
                            <label>이름 : </label>
                            <?=$username?>
                        </p>
                        <p>
                            <label>학번 : </label>
                            <?=$userid?>
                        </p>
                        <p>
                            <label>기본 전화번호 : </label> <?=$userphonenum?><br><br>
                            <label>변경할 전화번호 : </label>
                            <input type="text" name="newphone" id='newphone' value="" maxlength="15" class="text">
                        </p>
                        <p>
                            <label>기본 이메일 : </label><?=$useremail?><br><br>
                            <label>변경할 이메일 : </label>
                            <input type="text" name="newemail" id='newemail' value="" maxlength="30" class="text">
                        </p>
                        <p class="submit">
                            <input type="submit" value="수정하기">
                        </p>
                    </form>
                 </div>
</body>
</html>
<?php endif; ?>
