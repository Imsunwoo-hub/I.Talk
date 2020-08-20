<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$id = $_GET["id"];

$sql = "delete from user where id = '$id'";
$result = mysqli_query($conn,$sql);

if($result==true){
    echo "<script> alert('회원탈퇴가 완료되었습니다.'); history.go(-1);</script>";
}
?>
