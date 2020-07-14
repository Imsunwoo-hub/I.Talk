<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$num = $_POST["noticeNum"];
$userid = $_POST["userid"];
$username = $_POST["username"];
$comment = $_POST["text"];


$sql = "insert into Comment (Type, NoticeNum, Comment, CommentWriterId, CommentWriterName, WrittenDateTime) values('comment', $num, '$comment', '$userid', '$username', now()) ";
$result = mysqli_query($conn,$sql);

if($result==true){
    echo "<script> history.go(-1);</script>";
}
?>
