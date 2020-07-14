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
    <link rel="stylesheet" type="text/css" media="screen" href="css/common.managerpartial.css?ver=1" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/manager.user.css?ver=1" />
    <script src="main.js"></script>

    <script>
    function deleteUser(userid)
    {
      var quest = confirm(userid+ ' 회원을 탙퇴시키겠습니까?');
      if(quest) // 예를 선택하실 경우;;
      {
          location.href="deleteUserId.php?id="+userid;
        }
      else{
        return false;
      }
}
</script>

    <script language="javascript">
        function showuserinfoadjust() {
            window.open( "유저정보", 'left='+(screen.availWidth-400)/2+', top='+(screen.availHeight-300)/2+', width=400, height=300 ');
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


                <?php if ($useridentity == "교직원" || $useridentity=="교수"): ?>
                <li class="active">
                      <img src="images/usersetting.png">
                      <a href="usersetting.php">학생관리</a>
                </li>
                <?php endif; ?>
            </ul>
        </div>
    </nav>
    <div id="container" class="usersetting">
        <aside>
            <div class="title">
                <h1>학생관리</h1>
            </div>
            <div class="menu">
                <ol>
                    <li class="active">
                        <a href="usersetting.php">학생조회</a>
                    </li>
                    <li>
                        <a href="usergroup.php">그룹관리</a>
                    </li>
                </ol>
            </div>
        </aside>
        <div class="location_wrap">
            <div class="user_Search">

              <?php
              $search_text = $_GET['search_text'];
              $search_major = $_GET['major_Select'];
              $search_grade = $_GET['grade_Select'];
              $where = "id";

              if($search_text){
                if($search_major==0){
                  if($search_grade==0){ $where =  "(id like '%$search_text%' or name like '%$search_text%')";}
                  else { $where = "(id like '%$search_text%' or name like '%$search_text%' and grade=$search_grade )"; }
                }
                else{
                  if($search_grade==0){
                    if($search_major==1){$where = "(id like '%$search_text%' or name like '%$search_text%' and major ='IT융합부')"; }
                    else if($search_major==2){$where = "(id like '%$search_text%' or name like '%$search_text%' and major ='컴퓨터공학')"; }
                    else if($search_major==3){$where = "(id like '%$search_text%' or name like '%$search_text%' and major ='전자공학')"; }
                  }
                  else{
                    if($search_major==1){ $where = "(id like '%$search_text%' or name like '%$search_text%' and major = 'IT융합부' and grade = $search_grade )";}
                    else if($search_major==2){ $where = "(id like '%$search_text%' or name like '%$search_text%' and major = '컴퓨터공학' and grade = $search_grade )";}
                    else if($search_major==3){ $where = "(id like '%$search_text%' or name like '%$search_text%' and major = '전자공학' and grade = $search_grade )";}
                  }
                }
              }

              else{
                if($search_major==0){
                  if($search_grade==0){ $where = "id"; }
                  else{ $where= "grade= $search_grade"; }
                    }
                else {
                  if( $search_grade==0){
                    if($search_major==1){$where = "(major = 'IT융합부')";}
                    else if($search_major==2){$where = "(major = '컴퓨터공학')";}
                    else if($search_major==3){$where = "(major = '전자공학')";}
                  }
                    else{
                      if($search_major==1){  $where="major = 'IT융합부' and grade=$search_grade";}
                      else if($search_major==2){  $where="major = '컴퓨터공학' and grade=$search_grade";}
                      else if($search_major==3){  $where="major = '전자공학' and grade=$search_grade";}
                    }
                }
              }
              ?>

              <form action="<?=$PHP_SELE?>" class="search">
                <select name="major_Select">
                    <option value="0" selected>학과선택</option>
                    <option value="1">IT융합공학부</option>
                    <option value="2">컴퓨터공학전공</option>
                    <option value="3">전자공학전공</option>
                </select>

                <select name="grade_Select">
                  <option value="0" selected>학년선택</option>
                  <option value="1">1학년</option>
                  <option value="2">2학년</option>
                  <option value="3">3학년</option>
                  <option value="4">4학년</option>
                </select>
            </div>
                <input type="text" name="search_text" placeholder="아이디(학번) 또는 이름을 검색하세요." class="text" >
                <input type="submit" value="search" class="button">
            </form>
        </div>

            <div class="wrap setting">
            <div class="list">
                <table border="1">
                  <?php
                  $_page = $_GET['_page'];
                  $view_total=15;
                  if(!$_page)($_page=1);
                  $page=($_page-1)*$view_total;

                  $sql="SELECT COUNT(*) FROM user where $where ";
                  $result=mysqli_query($conn,$sql);
                  $row=mysqli_fetch_array($result);
                  $totals=$row[0];
                  ?>
                <thead>
                    <tr>
                    <th width="150">아이디(학번)</th>
                    <th>이름</th>
                    <th>학과</th>
                    <th>학년</th>
                    <th>상태</th>
                    <th width="80">수정</th>
                    <th width="80">삭제</th>
                </tr>
                </thead>
                <tbody>
                  <?php
                  $conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

                  $sql = "select id, name, major, grade, identity, state, phonenum, email from user where $where order by grade, name limit $page, $view_total";
                  $result = mysqli_query($conn,$sql);

                  while ($row = mysqli_fetch_array($result)) {
                  $userid = $row[0];
                  $username = $row[1];
                  $usermajor = $row[2];
                  $useregrade = $row[3];
                  $useridentity = $row[4];
                  $userstate = $row[5];
                  $phonenum = $row[6];
                  $useremail = $row[7];
                  ?>
                  <tr>
                    <form method="post" action="userinfoadjust.php">
                    <td><input type="hidden" name="id" value="<?=$userid?>"><?=$userid?></td>
                    <td><input type="hidden" name="name" value="<?=$username?>"><?=$username?></td>
                    <td><input type="hidden" name="major" value="<?=$usermajor?>"><?=$usermajor?></td>
                    <td><input type="hidden" name="grade" value="<?=$useregrade?>"><?=$useregrade?></td>
                    <td><input type="hidden" name="state" value="<?=$userstate?>"><?=$userstate?></td>
                    <input type="hidden" name="phonenum" value="<?=$userphonenum?>">
                    <input type="hidden" name="email" value="<?=$useremail?>">
                    <td><input type="submit" value="수정"></td>
                    <td><input type="button" value="삭제" onclick="deleteUser(<?=$userid?>);"></td>
                    </form>
                  </tr>
                <?php
              }
                ?>
                </tbody>
                </table>

            </div>
            <div align='center'height='100%' colspan='5' ><?include('list_page.php');?></div>
        </div>

    </body>
</html>
<?php endif; ?>
