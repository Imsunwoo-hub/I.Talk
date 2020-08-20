
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

    <script>
      function ck() {
        var ti = document.getElementById("noticeTitle");
        var cls = document.getElementsByName("noticeClassification");
        var con = document.getElementById("noticeContent");

        var l = null;

        for(var i=0;i<cls.length;i++){
			       if(cls[i].checked == true){
				           l = cls[i].value;
			}
		}

        if((!ti.value) && l==null && (!con.value)){
          alert("수정할 정보 입력해주세요.");
          return false;
        }
        else{
          document.noticeform.submit()
        }
      }
      </script>

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
                <h3>공지사항 수정</h3>
            </div>
            <form name="noticeform" class="Noform" method="POST" action="alternotices.php">
              <div class="content_subtext">
                <table class="contentwrite" border="1">
                    <?php
                      $num=$_GET['num'];
                      $tit=$_GET['tit'];
                      $cls=$_GET['cls'];
                      $con=$_GET['con'];
                     ?>

                    <tbody>
                            <tr>
                            <th>
                                <label for="writeTitle">제목</label>

                            </th>
                            <td>
                                <input type="text" name="noticeTitle" id="noticeTitle" class="writeTitle" placeholder="제목을 입력하세요.">
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <label>공지분류</label>
                            </th>
                            <td>
                                <input type="radio" id="cls" class="noticeType" name="noticeClassification" value="일반">일반
                                <input type="radio" id="cls" class="noticeType" name="noticeClassification" value="학사">학사
                                <input type="radio" id="cls" class="noticeType" name="noticeClassification" value="강의">강의
                                <input type="radio" id="cls" class="noticeType" name="noticeClassification" value="장학">장학
                                <input type="radio" id="cls" class="noticeType" name="noticeClassification" value="취업">취업
                                <input type="radio" id="cls" class="noticeType" name="noticeClassification" value="학생회">학생회
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <label>공지대상</label>
                            </th>
                            <td>
                                <input type="button" value="선택하기" class="targetselect" onclick="showNoticetargetselect();"/>
                                <input type="hidden" name = "num" value="<?=$num?>">
                                <input type="hidden" name = "tit" value="<?=$tit?>">
                                <input type="hidden" name = "cls" value="<?=$cls?>">
                                <input type="hidden" name = "con" value="<?=$con?>">
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
                        <button type="submit" id="saveBtn" onclick="ck(); return false; ">수정
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
