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
    function deleteGroup(num)
    {
      var quest = confirm('그룹을 삭제하시겠습니까?');
      if(quest) // 예를 선택하실 경우;;
      {
          location.href="deleteGroupWeb.php?groupnum="+num;
        }
      else{
        return false;
      }
}
</script>

    <script language="javascript">


    function showusergroupmake() {
        window.open("usergroupmake.php", "그룹생성", 'left='+(screen.availWidth-1100)/2+', top='+(screen.availHeight-700)/2+', width=1100, height=650 ');
    }
    function showusergrouplist() {
        window.open("usergrouplist.php", "그룹멤버보기", 'left='+(screen.availWidth-500)/2+', top='+(screen.availHeight-700)/2+', width=500, height=650 ');
    }
    function showusergroupadjust() {
        window.open("usergroupadjust.php", "그룹수정", 'left='+(screen.availWidth-1100)/2+', top='+(screen.availHeight-700)/2+', width=1100, height=650 ');
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
                    <li>
                        <a href="usersetting.php">학생조회</a>
                    </li>
                    <li class="active">
                        <a href="usergroup.php">그룹관리</a>
                    </li>
                </ol>
            </div>
        </aside>
        <div class="wrap setting">
          <?php
          $search_text = $_GET['search'];
          $where = "groupnum";

            if($search_text){ $where= "groupname like '%$search_text%'";}

          ?>


            <form action="<?=$PHP_SELE?>" class="usergroup">
                <h2>그룹관리
                <input type="button" value="+" onclick="showusergroupmake();"/>
            </h2>
            </form>
            <div class="GroupList">
            <form class="searchGroup">
               <input name="search" type="text" placeholder="그룹명을 검색하세요." class="text" ><input id="s" type="submit" value="검색">
            </form>


            <div class="ListView" style="width:100%; height:300px; overflow:auto">
                <table border="1">

                    <?php
                    $_page = $_GET['_page'];
                    $view_total=15;
                    if(!$_page)($_page=1);
                    $page=($_page-1)*$view_total;

                    $sql="SELECT COUNT(*) FROM groupInformation where $where ";
                    $result=mysqli_query($conn,$sql);
                    $row=mysqli_fetch_array($result);
                    $totals=$row[0];
                    ?>
                    <thead>
                      <tr>
                          <th colspan="2" style="text-align:center;">그룹 이름</th>
                          <th style="text-align:center;">수정</th><!--그룹수정하기-->
                          <th style="text-align:center;">삭제</th><!--그룹삭제하기-->
                      </tr>

                    </thead>

                    <tbody>

                      <?php
                      $conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

                      $sql = "select groupnum, groupname, groupadministrator from groupInformation where groupadministrator='$userid' and $where order by groupname limit $page, $view_total";
                      $result = mysqli_query($conn,$sql);

                      while ($row = mysqli_fetch_array($result)) {
                      $groupnum = $row[0];
                      $groupname = $row[1];
                      $groupmaker = $row[2];
                      ?>
                        <tr>
                            <td width="10%"><img src="images/groupimage.png"></td>
                            <td width="40%"><?=$groupname?></td>
                            <td width="25%"><input type="button" value="그룹수정" onclick="showusergroupadjust();"></td><!--그룹수정하기-->
                            <td width="25%"><input type="button" value="삭제" onclick="deleteGroup(<?=$groupnum?>);"></td><!--그룹삭제하기-->
                        </tr>
                        <?php
                      }
                        ?>
                </tbody>
                </table>
            </div>
            <div align='center'height='100%' colspan='5' ><?include('list_page.php');?></div>
            </div>
        </div>
        </body>
        </html>
        <?php endif; ?>
