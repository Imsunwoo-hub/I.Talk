<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");


$userid = $_POST['userid'];
$inquiryNum = $_POST['inquiryNum'];
$comment = $_POST['comment'];

$sql = "insert into inquiryContent(inquiryNum, sender, comment, date) values($inquiryNum, '$userid', '$comment', NOW())";
$result =  mysqli_query($conn, $sql);

$response = array();
$response["success"] = false;

if($result==true){
  $response["success"] = true;
}

echo json_encode($response);
?>
