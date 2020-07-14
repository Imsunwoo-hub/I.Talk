<?php
$con = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$noticeNum = $_POST['NoticeNum'];
$noticeClassification = $_POST['NoticeClassification'];
$noticeTitle = $_POST['NoticeTitle'];
$noticeContent = $_POST['NoticeContent'];
$noticeWriterId = $_POST['NoticeWriterId'];
$noticeWriterName = $_POST['NoticeWriterName'];
$noticeDate = $_POST['NoticeDate'];
$noticeTopFixed = $_POST['NoticeTopFixed'];


$sql = "UPDATE Notice SET NoticeTitle='$noticeTitle', NoticeClassification='$noticeClassification', NoticeContent='$noticeContent', NoticeWriterId='$noticeWriterId', NoticeWriterName='$noticeWriterName', NoticeDate='$noticeDate', TopFixed='$noticeTopFixed' WHERE NoticeNum='$noticeNum'";
$result = mysqli_query($con, $sql);

$response = array();
$response['success'] = false;

if($result==true){
  $response['success'] = true;
}

echo json_encode($response);
?>