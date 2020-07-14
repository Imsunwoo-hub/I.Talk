<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$num = $_GET["num"];

$sql = "delete from Comment where commentNum = $num";
$result = mysqli_query($conn,$sql);

if($result==true){
    echo "<script> alert('댓글이 삭제되었습니다.'); history.go(-1);</script>";
}
?>
