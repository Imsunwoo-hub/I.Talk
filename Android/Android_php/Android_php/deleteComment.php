<?php
$con = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$commentNum = $_POST['CommentNum'];

$sql = "DELETE FROM Comment WHERE CommentNum='$commentNum'";
$result = mysqli_query($con, $sql);

$response = array();
$response['success'] = false;

if($result==true){
  $response['success'] = true;
}
echo json_encode($response);
?>