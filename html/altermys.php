<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

session_start();
$userid = $_SESSION['userid'];
$userphonenum = $_SESSION['userphonenum'];
$useremail = $_SESSION['useremail'];

$newphone =$_POST['newphone'];
$newemail=$_POST['newemail'];



if($newphone == '' && $newemail == ''  ){
    echo "<script> alert('수정할 정보를 입력해주시오.'); history.go(-1);</script>";
}
else{
if(empty($newphone)){
  $sql = "UPDATE user SET email='$newemail' where id='$userid'";

  $result = mysqli_query($conn,$sql);
  $_SESSION['useremail']=$newemail;
    echo "<script> alert('이메일 변경이 완료되었습니다.'); history.go(-1);</script>";
}
else if(empty($newemail)){
  $sql = "UPDATE user SET phonenum='$newphone' where id='$userid'";

  $result = mysqli_query($conn,$sql);
  $_SESSION['userphonenum']=$newphone;
    echo "<script> alert('전화번호 변경이 완료되었습니다.'); history.go(-1);</script>";
}

else{
  $sql = "UPDATE user SET email='$newemail',phonenum='$newphone' where id='$userid'";

  $result = mysqli_query($conn,$sql);

  $_SESSION['useremail']=$newemail;
  $_SESSION['userphonenum']=$newphone;
    echo "<script> alert('개인정보 변경이 완료되었습니다.');
    window.location.href='http://tkdl2401.cafe24.com/html/altermy.php';</script>";
}
}
mysqli_close($conn);
?>
