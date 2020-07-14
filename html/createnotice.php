
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
    <title>ITALK</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/common.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/common.partial.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/notice.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/create.css" />

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

                <?php if ($useridentity == "교직원"|| $useridentity == "교수"): ?>
                <li>
                      <img src="images/usersetting.png">
                      <a href="usersetting.php">학생관리</a>
                </li>
                <?php endif; ?>
            </ul>
        </div>
    </nav>
    <div id="container" class="createnotice">
        <div class="content_main">
            <div class="content_title">
                <h3>공지사항 글쓰기</h3>
            </div>
            <form name="noticeform" class="Noform" method=POST action=createnotices.php>
              <div class="content_subtext">
                <table class="contentwrite" border="1">
                    <tbody>
                            <tr>
                            <th>
                                <label for="writeTitle">제목</label>
                            </th>
                            <td>
                                <input type="text" name="noticeTitle" id="noticeTitle"class="writeTitle" placeholder="제목을 입력하세요.">
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <label>공지분류</label>
                            </th>
                            <td>
                                <input type="radio" class="noticeType" name="noticeClassification" value="일반">일반
                                <input type="radio" class="noticeType" name="noticeClassification" value="학사">학사
                                <input type="radio" class="noticeType" name="noticeClassification" value="강의">강의
                                <input type="radio" class="noticeType" name="noticeClassification" value="장학">장학
                                <input type="radio" class="noticeType" name="noticeClassification" value="취업">취업
                                <input type="radio" class="noticeType" name="noticeClassification" value="학생회">학생회
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <label>공지대상</label>
                            </th>
                            <td>
                                <input type="button" value="선택하기" class="targetselect" onclick="showNoticetargetselect();"/>
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <label>첨부파일</label>
                            </th>
                            <td id="uproadfile">
                                <input type="file" value="파일업로드" class="uproadfile" name="uploadfile" id="uploadfile">
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <label>내용</label>
                            </th>
                            <td>
                                <textarea cols="10" placeholder="내용을 입력하세요" name="noticeContent" id="noticeContent"></textarea>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="Btn">
                    <span class="">
                        <button type="submit" id="saveBtn">저장
                        </button>
                    </span>
                    <span class="">
                        <button type="button" onclick="history.back(-1)" id="cancelBtn">취소
                        </button>
                    </span>
                </div>
            </div>
          </form>


        </div>
    </div>
    </body>
</html>
<?php endif; ?>
