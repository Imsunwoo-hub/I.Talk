
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
    <script src="http://tkdl2401.cafe24.com/html/js/alterpw.js"></script>
<body>
        <nav>
                <div class="wrap">
                    <div id="logo">
                        <a href="index.html">
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
                            <li class="active">
                                <a href="alterpw.php">비밀번호 변경</a>
                            </li>
                            <li>
                                <a href="userdrop.php">회원 탈퇴</a>
                            </li>
                        </ol>
                    </div>
                </aside>
                <div class="wrap setting">
                    <form action="alterpws.php" method="post" class="infosetpassword">
                        <h2>비밀번호 변경</h2>
                        <p>
                            <label>현재 비밀번호</label>
                            <input type="password" id="userpassword" name="userpassword" class="text">
                        </p>
                        <p>
                            <label>변경할 비밀번호</label>
                            <input type="password" name="newpw" id="newpw"  class="text">
                        </p>
                        <p>
                            <label>비밀번호 확인</label>
                            <input type="password" name="newpwc" id="newpwc" maxlength="20" class="text" onkeyup="check_pw(this.value)">
                        </p>


                        <p class="submit">
                            <input type="submit" value="비밀번호 변경">
                        </p>
                    </form>
                </div>
            </div>
</body>
</html>
<?php endif; ?>
