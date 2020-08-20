
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
    <link rel="stylesheet" type="text/css" media="screen" href="css/common.css?ver=1" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/common.partial.css?ver=1" />

    <link rel="stylesheet" type="text/css" media="screen" href="css/notice.css?ver=1" />

    <script>
    function deleteNotice(num)
    {
      var quest = confirm('공지사항을 삭제하시겠습니까?');
      if(quest) // 예를 선택하실 경우;;
      {
          location.href="deleteNoticeWeb.php?num="+num;
        }
      else{
        return false;
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
            <div id="container" class="firstpage">
                <div class="leftside">
                    <div class="profile">
                        <form class="logged">
                            <img src="images/profileimg.png" class="picture">
                            <p class="userid"><?=$userid?></p>
                            <p class="username"><?=$username?></p>
                            <div class="button">
                                <a href="my.php">MYPAGE</a>
                            </div>
                        </form>
                    </div>
                    <div class="menus">
                        <div class="linkbanner">
                            <a href="https://www.shinhan.ac.kr/" target="_blank"> <!--&nbsp;&nbsp;신한대학교 홈페이지-->
                                <img src="images/qq.png" width="100%" height="100px" >
                            </a>
                        </div>
                    </div>
                    <div class="menus">
                        <div class="linkbanner">
                            <a href="http://stins.shinhan.ac.kr/irj/portal" target="_blank">
                                <img src="images/ee.png" width="100%" height="100px">
                            </a>
                        </div>
                    </div>
                    <div class="menus">
                        <div class="linkbanner">
                            <a href="https://itc.shinhan.ac.kr/itc/" target="_blank"><!--&nbsp;IT융합공학부 홈페이지-->
                                <img src="images/rr.png" width="100%" height="100px" >
                            </a>
                        </div>
                    </div>
                </div>
                <div class="wrap noticetitle">
                    <h1>공지사항</h1>
                </div>
                <div class="wrap notice">

                  <?php
                  $search_text = $_GET['serchText'];
                  $search_mode = $_GET['AAA'];
                  $search_type = $_GET['BBB'];
                  $where = "NoticeNum";

                  if($search_text){
                    if($search_type==0){
                      if($search_mode==1) $tmp="noticetitle";
                      if($search_mode==2) $tmp="NoticeWriterName";
                      if($search_mode==3) $tmp="noticecontent";
                      $where = "($tmp like '%$search_text%')";
                    } else {
                      if($search_mode==1){ $tmp="noticetitle";
                        if($search_type==1){$where = "($tmp like '%$search_text%' and NoticeClassification = '일반' )";}
                        if($search_type==2){$where = "($tmp like '%$search_text%' and NoticeClassification = '학사' )";}
                        if($search_type==3){$where = "($tmp like '%$search_text%' and NoticeClassification = '강의' )";}
                        if($search_type==4){$where = "($tmp like '%$search_text%' and NoticeClassification = '장학' )";}
                        if($search_type==5){$where = "($tmp like '%$search_text%' and NoticeClassification = '취업' )";}
                        if($search_type==6){$where = "($tmp like '%$search_text%' and NoticeClassification = '학생회' )";}
                        if($search_type==7){$where = "($tmp like '%$search_text%' and NoticeClassification = '기타' )";}
                      }

                      if($search_mode==2){ $tmp="a.NoticeWriterName";
                        if($search_type==1){$where = "($tmp like '%$search_text%' and NoticeClassification = '일반' )";}
                        if($search_type==2){$where = "($tmp like '%$search_text%' and NoticeClassification = '학사' )";}
                        if($search_type==3){$where = "($tmp like '%$search_text%' and NoticeClassification = '강의' )";}
                        if($search_type==4){$where = "($tmp like '%$search_text%' and NoticeClassification = '장학' )";}
                        if($search_type==5){$where = "($tmp like '%$search_text%' and NoticeClassification = '취업' )";}
                        if($search_type==6){$where = "($tmp like '%$search_text%' and NoticeClassification = '학생회' )";}
                        if($search_type==7){$where = "($tmp like '%$search_text%' and NoticeClassification = '기타' )";}
                    }

                      if($search_mode==3){ $tmp="noticecontent";
                        if($search_type==1){$where = "($tmp like '%$search_text%' and NoticeClassification = '일반' )";}
                        if($search_type==2){$where = "($tmp like '%$search_text%' and NoticeClassification = '학사' )";}
                        if($search_type==3){$where = "($tmp like '%$search_text%' and NoticeClassification = '강의' )";}
                        if($search_type==4){$where = "($tmp like '%$search_text%' and NoticeClassification = '장학' )";}
                        if($search_type==5){$where = "($tmp like '%$search_text%' and NoticeClassification = '취업' )";}
                        if($search_type==6){$where = "($tmp like '%$search_text%' and NoticeClassification = '학생회' )";}
                        if($search_type==7){$where = "($tmp like '%$search_text%' and NoticeClassification = '기타' )";}
                        }
                    }
                  } else{
                    if($search_type==0){$where="NoticeNum";}
                    else{
                      if($search_type==1){$where = "NoticeClassification = '일반'";}
                      if($search_type==2){$where = "NoticeClassification = '학사'";}
                      if($search_type==3){$where = "NoticeClassification = '강의'";}
                      if($search_type==4){$where = "NoticeClassification = '장학'";}
                      if($search_type==5){$where = "NoticeClassification = '취업'";}
                      if($search_type==6){$where = "NoticeClassification = '학생회'";}
                      if($search_type==7){$where = "NoticeClassification = '기타'";}
                    }
                  }

                  ?>

                    <form action="<?=$PHP_SELE?>"  class="searchNotice">
                            <select name="BBB">
                                <option value="0">전체</option>
                                <option value="1">일반</option>
                                <option value="2">학사</option>
                                <option value="3">강의</option>
                                <option value="4">장학</option>
                                <option value="5">취업</option>
                                <option value="6">학생회</option>
                                <option value="7">기타</option>
                            </select>

                            <select name="AAA">
                                <option value="1">제목</option>
                                <option value="2">작성자</option>
                                <option value="3">내용</option>
                            </select>
                        <input type="text" name="serchText" placeholder="검색" class="text">
                        <input type="submit" value="search" class="button">
                        <?php if ($useridentity == "교직원" || $useridentity == "교수"): ?>

                        <button type="button" onclick="location.href='createnotice.php'" class="createNotice">글쓰기</button>
                        <?php endif; ?>
                    </form>
                    <div class="noticecontent">
                        <table border="1">

                          <?php
                          $_page = $_GET['_page'];
                          $view_total=15;
                          if(!$_page)($_page=1);
                          $page=($_page-1)*$view_total;

                          $sql="SELECT COUNT(*) FROM Notice where $where";
                          $result=mysqli_query($conn,$sql);
                          $row=mysqli_fetch_array($result);
                          $totals=$row[0];
                          ?>
                          <thead>
                            <tr>
                                <td width="15%">분류</td>
                                <td>제목</td>
                                <td width="15%">등록일</td>
                                <td width="10%">작성자</td>

                            </tr>
                          </thead>


                          <?php
                          $conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

                          $sql = "SELECT noticeNum, NoticeClassification, NoticeTitle,NoticeDate, NoticeWriterName, NoticeContent  FROM  Notice where  $where ORDER BY NoticeDate desc Limit $page, $view_total";
                          $result = mysqli_query($conn,$sql);
                          //$row=$result->fetch_assoc();
                          $cnt=1;

                          while ($row = mysqli_fetch_array($result)) {
                          $noticeNum = $row[0];
                          $noticeClassification = $row[1];
                          $noticeTitle = $row[2];
                          $noticeDate = $row[3];
                          $noticeWriter = $row[4];
                          $NoticeContent =$row[5];
                          ?>
                          <tr>
                            <td><?=$noticeClassification?></td>
                            <td><a style="color:black;" href='./noticeview.php?no=<?=$noticeNum?>&id=<?=$noticeTitle?>'><?=$noticeTitle?></a><?php if($username==$noticeWriter):
                            ?><a href="alterNoticeWeb.php?num=<?=$noticeNum?>&tit=<?=$noticeTitle?>&cls=<?=$noticeClassification?>&con=<?=$NoticeContent?>"><span style="color:blue; "><수정></span></a>
                            <a style="color:red;" href="javascript:deleteNotice(<?=$noticeNum?>);"><span ><삭제></span></a>
                          <?php endif; ?></td>
                            <td><?=$noticeDate?></td>
                            <td><?=$noticeWriter?></td>

                          </tr>
                          <?php
                          $cnt++;
                        }?>

                        </table>


                        </div>
                        <div align='center'height='100%' colspan='5' ><?include('list_page.php');?></div>
                    </div>

                </div>
            </div>


</body>
</html>
<?php endif; ?>
