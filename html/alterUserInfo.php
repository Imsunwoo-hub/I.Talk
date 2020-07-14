<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$newid=$_POST["newid"];
$newname=$_POST["newname"];
$newmajor=$_POST["newmajor"];
$newgrade=$_POST["newgrade"];
$newphonenum=$_POST["newphonenum"];
$newemail=$_POST["newemail"];

$id=$_POST["id"];
$name=$_POST["name"];
$major=$_POST["major"];
$grade=$_POST["grade"];
$phonenum=$_POST["phonenum"];
$email=$_POST["email"];

if(empty($newid)){
  $newid = $id;
}
if(empty($newname)){
  $newname = $name;
}
if(empty($newmajor)){
  $newmajor = $major;
}
if(empty($newgrade)){
  $newgrade = $grade;
}
if(empty($newphonenum)){
  $newphonenum = $phonenum;
}
if(empty($newemail)){
  $newemail = $email;
}
$sql = "update user set id = '$newid', name= '$newname', major='$newmajor', grade='$newgrade', phonenum='$newphonenum', email='$newemail' where id='$id'";
$result = mysqli_query($conn,$sql);

if($result==true){
  echo "<script> alert('수정이 완료되었습니다.'); history.go(-2);</script>";
}
?>
