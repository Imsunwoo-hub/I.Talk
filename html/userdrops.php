<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

session_start();
$userid = $_SESSION['userid'];
$userpassword =$_POST['userpassword'];


$sql = "SELECT password FROM user where id='$userid'";

$result = mysqli_query($conn,$sql);

$row = mysqli_fetch_array($result);

if($userpassword == '' ){
    echo "<script> alert('비밀번호를 입력해주시오.'); history.go(-1);</script>";
}
else{
if($row[0] == $userpassword){
        $sql = "DELETE FROM user WHERE id='$userid'";
        $result = mysqli_query($conn,$sql);
          if ($result == true){
              session_destroy();
              echo "<script> alert('회원탈퇴가 완료되었습니다.'); window.location.href='http://tkdl2401.cafe24.com/html/login.html' </script>";
            }
    }else{
        echo "<script> alert('비밀번호가 일치하지 않습니다.'); history.go(-1);</script>";
    }
  }


mysqli_close($conn);
?>
