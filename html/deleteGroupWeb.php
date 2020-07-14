<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$num = $_GET["groupnum"];

$sql = "delete from groupInformation where groupnum = '$num'";
$result = mysqli_query($conn,$sql);

if($result==true){
    echo "<script> alert('그룹삭제가 완료되었습니다.'); history.go(-1);</script>";
}
?>
