<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$userid = $_REQUEST["userid"];
$userpassword = $_REQUEST["userpassword"];


$sql = "select password, name, phonenum, email, identity from user where id = '$userid'";
$result = mysqli_query($conn,$sql);
$row = mysqli_fetch_array($result);

$username = $row[1];
$userphonenum = $row[2];
$useremail = $row[3];
$useridentity = $row[4];


if(mysqli_num_rows($result)==0){
  echo "<script>alert('아이디를 정확히 입력하시오.');  history.go(-1); </script>";
}
else{

  if($row[0] == $userpassword){
    session_start();
    $_SESSION["userid"] = $userid;
    $_SESSION["username"] = $username;
    $_SESSION["userphonenum"] = $userphonenum;
    $_SESSION["useremail"] = $useremail;
    $_SESSION["useridentity"] = $useridentity;

    echo ("<script>location.href='http://tkdl2401.cafe24.com/html/index.php';</script>");
  }
  else{
    echo "<script>alert('비밀번호가 틀렸습니다.'); history.go(-1); </script> ";
  }
}
mysqli_close($conn);
?>
