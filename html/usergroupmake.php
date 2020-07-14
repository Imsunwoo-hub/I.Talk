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
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>학생그룹생성</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/managerusergroupmake.css" />
    <script src="main.js"></script>
    <script>
    var check1 = false;
    function CheckAll1(){
      var chk = document.getElementsByName("check-tab1");
      if(check1 == false){
        check1 = true;
        for(var i=0; i<chk.length;i++){
          chk[i].checked = true;
        }
      }else{
        check1 = false;
        for(var i=0; i<chk.length;i++){
          chk[i].checked = false;
        }
      }
    }
</script>
<script>
var check2 = false;
function CheckAll2(){
  var chk = document.getElementsByName("check-tab2");
  if(check2 == false){
    check2 = true;
    for(var i=0; i<chk.length;i++){
      chk[i].checked = true;
    }
  }else{
    check2 = false;
    for(var i=0; i<chk.length;i++){
      chk[i].checked = false;
    }
  }
}
</script>

<script>
  function selectDelRow(userid) {
  var groupname = document.getElementById("groupname");
  var chk = document.getElementsByName("check-tab1");
  var len = chk.length;
  var checkRow = '';
  var checkCnt = 0;
  var checkLast = '';
  var rowid = '';
  var cnt = 0;

  for(var i=0; i<len; i++){
    if(chk[i].checked == true){
      checkCnt++;
      checkLast = i;
    }
  }

  for(var i=0; i<len; i++){
    if(chk[i].checked == true){
      checkRow = chk[i].value;

      if(checkCnt == 1){
        rowid = checkRow;
      }else{
        if(i==0){
          rowid += checkRow;
        }else{
          rowid += ","+checkRow;
        }

        }
      }
      cnt++;
      checkRow = '';
    }
  if (groupname.value==""){
    alert('그룹명을 입력해주세요.');
    return false;
  }

  if(checkCnt!=0 && groupname.value!=""){
    location.href="createGroupWeb.php?id="+userid+"&memberid="+rowid+"&groupname="+groupname.value;
    return true;
}
if(checkCnt==0){
  alert('맴버를 지정해주세요.');
  return false;
}
}
</script>





</head>
<body>
<header>
    <div class="wrap">
        <h2>그룹생성</h2>
    </div>
</header>
<section>
  <div id="container" class="groupmakebox" >
    <div class="groupname">
        <h4>그룹이름 :
        <input id="groupname" type="text" maxlength="15" required></h4>
    </div>
    <hr>

    <div class="box_1">
      <?php
      $search_text = $_GET['search'];
      $search_grade= $_GET['grade'];
      $where = "id";

        if($search_text){
          if($search_grade==0){
              $where= "id like '%$search_text%' or name like '%$search_text%' ";}
          else{
              $where= "id like '%$search_text%' or name like '%$search_text%' and grade = $search_grade ";}
        }
        else{
          if($search_grade==0){
              $where= "id";}
          else{
            $where= "grade = $search_grade";
          }
        }

      ?>
      <!--
        <form action="<?=$PHP_SELE?>" id="searchUser" class="search">
            <select name="grade">
                <option value="0">전체</option>
                <option value="1">1학년</option>
                <option value="2">2학년</option>
                <option value="3">3학년</option>
                <option value="4">4학년</option>
            </select>
            <input name="search" type="text" placeholder="학번 OR 이름 검색" class="text">
            <input type="submit" value="검색" id = "a">
        </form>-->
            <div class="list" style=" height:330px; overflow:auto">
                <table id="table1" border="1" >
                    <thead>
                        <tr>
                           <th><input type="checkbox" id="allCheck" onclick="CheckAll1()"/></th>
                           <th>학년</th>
                           <th>전공</th>
                           <th>아이디(학번)</th>
                           <th>이름</th>
                        </tr>
                     </thead>
                    <tbody>
                          <?php
                          $conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

                          $sql = "select grade,major,id, name from user where $where  order by grade, id, name";
                          $result = mysqli_query($conn,$sql);

                          while ($row = mysqli_fetch_array($result)) {
                            $usergrade = $row[0];
                            $usermajor = $row[1];
                            $id = $row[2];
                            $username = $row[3];
                          ?>
                            <tr>
                                <td><input type="checkbox" name="check-tab1" value='<?=$id?>' ></td>
                                <td><?=$usergrade?></td>
                                <td><?=$usermajor?></td>
                                <td><?=$id?></td>
                                <td><?=$username?></td>
                            </tr>
                            <?php
                          }
                            ?>
                            </tbody>

                    </table>
                </div>
        </div>
<!--
        <div class="tab tab-btn">
                <button onclick="tab1_To_tab2();">추가</button>
                <button onclick="tab2_To_tab1();">삭제</button>
        </div>

        <div class="box_2">
          <form id="searchUser" class="search">
              <select name="grade">
                  <option value="1">전체</option>
                  <option>1학년</option>
                  <option>2학년</option>
                  <option>3학년</option>
                  <option>4학년</option>
              </select>
              <input type="text" placeholder="학번 OR 이름 검색" class="text">
              <input type="submit" value="검색">
          </form>

          <div class="list" style="height:330px; overflow:auto">
              <table id="table2" border="1">
                  <thead>
                      <tr>
                          <th><input type="checkbox" id="allCheck" onclick="CheckAll2()"/></th>
                          <th>학년</th>
                          <th>전공</th>
                          <th>아이디(학번)</th>
                          <th>이름</th>
                      </tr>
                  </thead>

                    </table>
                  </div>
                </div>


    </div>
  -->
    <div class="groupsave">
        <input type="button" value="저장" class="button" onclick="selectDelRow(<?=$userid?>)">
        <input type="button" onclick="window.close()" value="취소" class="button">
    </div>
    </section>

    <script>

           function tab1_To_tab2()
           {
               var table1 = document.getElementById("table1"),
                   table2 = document.getElementById("table2"),
                   checkboxes = document.getElementsByName("check-tab1");
           console.log("Val1 = " + checkboxes.length);
                for(var i = 0; i < checkboxes.length; i++)
                    if(checkboxes[i].checked)
                       {
                           // create new row and cells
                           var newRow = table2.insertRow(table2.length),
                               cell1 = newRow.insertCell(0),
                               cell2 = newRow.insertCell(1),
                               cell3 = newRow.insertCell(2),
                               cell4 = newRow.insertCell(3);
                               cell5 = newRow.insertCell(4);
                           // add values to the cells
                           cell1.innerHTML = "<input type='checkbox' name='check-tab2' value='<?=$id?>' checked>";
                           cell2.innerHTML = table1.rows[i+1].cells[1].innerHTML;
                           cell3.innerHTML = table1.rows[i+1].cells[2].innerHTML;
                           cell4.innerHTML = table1.rows[i+1].cells[3].innerHTML;
                           cell5.innerHTML = table1.rows[i+1].cells[4].innerHTML;


                           // remove the transfered rows from the first table [table1]
                           var index = table1.rows[i+1].rowIndex;
                           table1.deleteRow(index);
                           // we have deleted some rows so the checkboxes.length have changed
                           // so we have to decrement the value of i
                           i--;
                          console.log(checkboxes.length);
                       }
           }


           function tab2_To_tab1()
           {
               var table1 = document.getElementById("table1"),
                   table2 = document.getElementById("table2"),
                   checkboxes = document.getElementsByName("check-tab2");
           console.log("Val1 = " + checkboxes.length);
                for(var i = 0; i < checkboxes.length; i++)
                    if(checkboxes[i].checked)
                       {
                           // create new row and cells
                           var newRow = table1.insertRow(table1.length),
                               cell1 = newRow.insertCell(0),
                               cell2 = newRow.insertCell(1),
                               cell3 = newRow.insertCell(2),
                               cell4 = newRow.insertCell(3);
                               cell5 = newRow.insertCell(4);
                           // add values to the cells
                           cell1.innerHTML = "<input type='checkbox' name='check-tab1'>";
                           cell2.innerHTML = table2.rows[i+1].cells[1].innerHTML;
                           cell3.innerHTML = table2.rows[i+1].cells[2].innerHTML;
                           cell4.innerHTML = table2.rows[i+1].cells[3].innerHTML;
                           cell5.innerHTML = table1.rows[i+1].cells[4].innerHTML;


                           // remove the transfered rows from the second table [table2]
                           var index = table2.rows[i+1].rowIndex;
                           table2.deleteRow(index);
                           // we have deleted some rows so the checkboxes.length have changed
                           // so we have to decrement the value of i
                           i--;
                          console.log(checkboxes.length);
                       }
           }
       </script>





</body>
</html>
