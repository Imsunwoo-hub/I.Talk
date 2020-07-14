
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
$noticeNum= $_GET[no];
$noticeTitle= $_GET[id];

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
    <script src="main.js"></script>
    <script>
    function deleteCo(num)
    {
      var quest = confirm('공지사항을 삭제하시겠습니까?');
      if(quest) // 예를 선택하실 경우;;
      {
          location.href="deleteCommentWeb.php?num="+num;
        }
      else{
        return false;
      }
}
</script>
<script>
function ckC()
{
  var a =  document.getElementById('a');
  if(a.value=="") // 예를 선택하실 경우;;
  {
    alert('댓글을 입력하세요.');
    return false;
    }
  else{
    document.zz.submit();
  }
}
</script>

</head>
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
                </p>
            </div>
            <div id="account">
                <a href="http://tkdl2401.cafe24.com/html/php/logout.php" class="button white">LOGOUT</a>
            </div>
            <ul id="menu">
                <li class="active">
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
    <div id="container" class="notice_view">
        <div class="content_main">
            <div class="content_title">
                <h3>공지사항</h3>
            </div>

            <?php
            $conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

            $sql=" SELECT NoticeNum ,NoticeClassification, NoticeTitle,NoticeDate, NoticeWriterName, NoticeContent FROM Notice WHERE NoticeNum=$noticeNum ";
            $result = mysqli_query($conn,$sql);

            while ($row = mysqli_fetch_array($result)) {
            $nNum = $row[0];
            $noticeClassification = $row[1];
            $nTitle = $row[2];
            $noticeDate = $row[3];
            $noticeWriter = $row[4];
            $nContent = $row[5];
            }

            ?>

            <div class="content_subtext">
                <table class="contentwrite" border="1">

                    <tbody>
                        <tr>
                            <th>제목</th>
                            <td><?=$nTitle?></td>
                        </tr>
                        <tr>
                            <th>공지분류</th>
                            <td><?=$noticeClassification?></td>
                        </tr>
                        <tr>
                            <th width="50%">작성자</th>
                            <td><?=$noticeWriter?></td>
                            <th width="50%">작성일</th>
                            <td><?=$noticeDate?></td>
                        </tr>
                        <tr>
                          <th>공지내용</th>
                            <td class="textviewer"><pre  style="white-space:pre-line;"><?=$nContent?></td>

                        </tr>

                    </tbody>
                </table>



                <div class='comments' style='display: block;'>

                  <form name='zz' action="createCommentWeb.php" method="post" class="writecomment">
                      <input type="hidden" name="userid" value="<?=$userid?>">
                      <input type="hidden" name="username" value="<?=$username?>">
                      <input type="hidden" name="noticeNum" value="<?=$nNum?>">
                      <input type="text" id="a" name="text" maxlength="500" placeholder="댓글을 입력하세요." class="text">
                      <ul class="option">
                        <button style="background-color:white;" type="submit" onclick="ckC(); return false;"><li title="댓글 작성하기" value="댓글 작성하기" class="submit"></li></button>
                      </ul>
                  </form>



                    <article class="parent" style=" height:200px; overflow:auto" >


                      <?php
                      $conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");
                      $sql=" SELECT comment, commentWriterName, writtenDateTime, CommentNum FROM Comment WHERE NoticeNum=$nNum order by writtenDateTime ";
                      $result = mysqli_query($conn,$sql);
                      while ($row = mysqli_fetch_array($result)) {
                          $com = $row[0];
                          $cw = $row[1];
                          $cd = $row[2];
                          $cn=$_row[3]

                       ?>

                        <img src="images/profileimg.png" class="picture medium"><!--댓글작성자 사진-->
                        <h3 class="medium" width="50"><?=$cw?><?php if($username==$cw):
                        ?> <a style="color:red;" href="javascript:deleteCo(<?=$cn?>);"><span ><삭제></span></a>
                        <?php endif; ?></h3></td>
                        <hr>


                      <!--댓글 작성자 이름-->
                        <time title="" class="medium"><?=$com?></time>
                        <!--<ul class="status">
                                <li class="childcomment">대댓글</li>
                            </ul>-->

                        <p class="large" style="text-align:right;"><?=$cd?></p>

                        <?php
                      } ?>

                    </article>


                   <!--<article class="child">
                        <img src="images/profileimg.png" class="picture medium">
                        <h3 class="medium writer"></h3>
                        <time title="" class="medium"></time>
                        <hr>
                        <p class=""></p>
                    </article>

                    <form class="writecomment child">
                        <input type="text" name="text" maxlength="500" placeholder="대댓글을 입력하세요." class="text">
                        <ul class="option">
                            <li title="댓글 작성하기" value="댓글 작성하기" class="submit"></li>
                        </ul>
                    </form>-->


                </div>
                <div class="Btn">
                    <span class="">
                    <button type="button" id="backlist" onclick=" history.go(-1);">목록
                    </button>
                </span>
                </div>
             </div>
    </div>
    </div>
    </div>
    </body>
    </html>

    <?php endif; ?>
