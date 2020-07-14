<?php
$con = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$noticeNum = $_POST['NoticeNum'];

$sql = "DELETE FROM Notice WHERE NoticeNum='$noticeNum'";
$result = mysqli_query($con, $sql);

$response = array();
$response['success'] = false;

if($result==true){
  $response['success'] = true;
}
echo json_encode($response);
?>