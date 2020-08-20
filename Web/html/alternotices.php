<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$num=$_POST["num"];
$ntit=$_POST["noticeTitle"];
$ncls=$_POST["noticeClassification"];
$ncon=$_POST["noticeContent"];

$tit=$_POST["tit"];
$cls=$_POST["cls"];
$con=$_POST["con"];

if(empty($ntit)){
  $ntit= $tit;
}
if(empty($ncls)){
  $ncls= $cls;
}
if(empty($ncon)){
  $ncon= $con;
}



$sql = "update Notice set noticeTitle = '$ntit', noticeClassification= '$ncls', noticeContent='$ncon' where noticeNum=$num";
$result = mysqli_query($conn,$sql);

if($result==true){
  echo "<script> alert('수정이 완료되었습니다.'); history.go(-2);</script>";
}
?>
