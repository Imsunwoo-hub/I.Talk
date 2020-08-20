<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$num = $_GET["num"];

$sql = "delete from Notice where noticeNum = '$num'";
$result = mysqli_query($conn,$sql);

if($result==true){
    echo "<script> alert('공지사항이 삭제되었습니다.'); history.go(-1);</script>";
}
?>
