<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$noticeNum = $_GET["noticeNum"];

$result = mysqli_query($conn, "SELECT * FROM Comment WHERE NoticeNum=$noticeNum ORDER BY CommentNum ASC");
$response = array();

while($row = mysqli_fetch_array($result)) {
  array_push($response, array('Type'=> $row[0], 'CommentNum'=> $row[1], 'NoticeNum'=> $row[2], 'Comment'=> $row[3], 'CommentWriterId'=> $row[4], 'CommentWriterName'=> $row[5], 'WrittenDateTime'=> $row[6], 'RepliedCommentNum'=> $row[7]));
}

echo json_encode(array('response'=>$response));
mysqli_close($conn);
?>