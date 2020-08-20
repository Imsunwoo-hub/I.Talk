<?php
include_once "db.php";
session_start(); ob_start();

$userid = $_POST["userid"];
$userpassword = $_POST["userpassword"];
$username = $_POST['username'];
$userphonenum = $_POST['userphonenum'];
$useremail = $_POST['useremail'];
$useridentity = $_POST['identity'];
$usermajor = $_POST['majorselect'];
$usergrade = $_POST['grade'];
$userstate = $_POST['userstate'];

if(!$conn){
    echo 'not connet';
}

$sql = "insert into user values('$userid', '$userpassword', '$username', '$userphonenum', '$useremail', '$useridentity', '$usermajor', '$usergrade', '$userstate')";

$userpassword = md5($userpassword);

if(mysqli_query($conn,$sql)){
    echo "<script> alert('회원가입을 축하드립니다.'); window.location.href='http://tkdl2401.cafe24.com/html/login.html' </script>";
}else{
    echo "<script> alert('사이트에 문제가 발생했습니다.');</script>";
}

mysqli_close($conn);
?>
