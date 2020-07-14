<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$chatNum = $_POST['chatNum'];

$sql = "delete from inquiry where inquiryNum = $chatNum";
$result = mysqli_query($conn, $sql);

$response = array();
$response['success'] = false;

if($result==true){
  $response["success"] = true;
}
echo json_encode($response);
?>
