<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

session_start();
$userid = $_SESSION['userid'];
$newpw =$_POST['newpw'];
$newpwc=$_POST['newpwc'];
$userpassword =$_POST['userpassword'];


$sql = "SELECT password FROM user where id='$userid'";

$result = mysqli_query($conn,$sql);

$row = mysqli_fetch_array($result);

if($newpw == '' || $newpwc == '' || $userpassword == '' ){
    echo "<script> alert('정보를 모두 입력해주시오.'); history.go(-1);</script>";
}
else{
if($row[0] == $userpassword){  // 여기까지는 비슷합니다.
  if($newpw == $newpwc){
        $sql = "UPDATE user SET password='$newpw' where id='$userid'";
        $result = mysqli_query($conn,$sql);
          if ($result == true){
              session_destroy();
              echo "<script> alert('비밀번호 변경이 완료되었습니다. 다시 로그인해주세요'); window.location.href='http://tkdl2401.cafe24.com/html/login.html' </script>";
            }
    }else{
        echo "<script> alert('변경할 비밀번호가 일치하지 않습니다.'); history.go(-1);</script>";
    }
  }
     else{
        echo "<script> alert('비밀번호를 확인해 주시기 바랍니다.'); history.go(-1);</script>";
    }
  }

mysqli_close($conn);
?>
