
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
    <title>I.Talk</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/common.css?ver=1" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/common.partial.css?ver=1" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/talk_common.css?ver=1" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/talk_chatting.css?ver=1" />



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
                <li class="active">
                    <img src="images/inquiry.png">
                    <a href="talk.php">문의</a>
                </li>

                <?php if ($useridentity == "교직원"|| $useridentity == "교수"): ?>
                <li>
                      <img src="images/usersetting.png">
                      <a href="usersetting.php">학생관리</a>
                </li>
                <?php endif; ?>
            </ul>
        </div>
    </nav>
    <div id="container">
        <div id="sub">
            <div class="section_left">
                <div class="section_left_header">
                    <ul>
                        <li class="active">
                            <!--대화상대선택-->
                            <a href="#">
                            <img src="images/chatting_1.png">
                            </a>
                        </li>
                        <li>
                            <!--채팅방선택-->
                            <a href="#">
                            <img src="images/chatting_2.png">
                            </a>
                        </li>
                    </ul>

                </div>
                <div class="section_left_bottom">
                    <div class="search_area">
                        <input type="text" size="10" placeholder="상대방을 검색하세요" list="d">
                    </div>
                    <datalist id="d"  >
                      <?php
                      $conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

                      $sql = "select id, name from user  order by id";
                      $result = mysqli_query($conn,$sql);

                        while ($row = mysqli_fetch_array($result)) {
                          $id = $row[0];
                          $name = $row[1];
                          ?>
                          <option value="<?=$id?> <?=$name?>"></option>
                          <?php
                        }
                          ?>
                    </datalist>

                </div>
                </div>
                </div>

                <div id="content_main">
                    <!--선택된 대상 또는 채팅방이 없을 때-->
                    <div class="chatting_empty">
                        <i class="chatting_bg_default"></i>
                        <h3>대화내역이 없습니다.</h3>
                        <p>
                        <span>
                        대화상대 또는 채팅방을 선택하세요!
                        </span>
                         </p>
                    </div>
                </div>
            </div>

</body>
</html>
<?php endif; ?>
