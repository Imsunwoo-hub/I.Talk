<?php
$con = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$type = $_POST["Type"];
$noticeNum = $_POST["NoticeNum"];
$comment = $_POST["Comment"];
$commentWriterId = $_POST['CommentWriterId'];
$commentWriterName = $_POST['CommentWriterName'];
$writtenDateTime = $_POST['WrittenDateTime'];
$repliedCommentNum = $_POST['RepliedCommentNum'];


if(!strcmp($type, "comment")) {
    $sql = "INSERT INTO Comment(Type, NoticeNum, Comment, CommentWriterId, CommentWriterName, WrittenDateTime, RepliedCommentNum) VALUES('$type','$noticeNum','$comment','$commentWriterId','$commentWriterName','$writtenDateTime', null)";
}
else {
    $sql = "INSERT INTO Comment(Type, NoticeNum, Comment, CommentWriterId, CommentWriterName, WrittenDateTime, RepliedCommentNum) VALUES('$type','$noticeNum','$comment','$commentWriterId','$commentWriterName','$writtenDateTime', '$repliedCommentNum')";
}

$result = mysqli_query($con, $sql);

$response = array();
$response['success'] = false;

if($result==true){
  $response['success'] = true;
}

echo json_encode($response);
?>