<?php
$con = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$noticeClassification = $_POST["NoticeClassification"];
$noticeTitle = $_POST["NoticeTitle"];
$noticeContent = $_POST["NoticeContent"];
$noticeWriterId = $_POST['NoticeWriterId'];
$noticeWriterName = $_POST['NoticeWriterName'];
$noticeDate = $_POST['NoticeDate'];
$noticeTopFixed = $_POST['NoticeTopFixed'];

$sql = "INSERT INTO Notice(NoticeClassification, NoticeTitle, NoticeContent, NoticeWriterId, NoticeWriterName, NoticeDate, TopFixed) VALUES('$noticeClassification', '$noticeTitle', '$noticeContent','$noticeWriterId', '$noticeWriterName', '$noticeDate','$noticeTopFixed')";

$result = mysqli_query($con, $sql);

$response = array();
$response['success'] = false;

if($result==true){
  $response['success'] = true;
}

echo json_encode($response);
?>