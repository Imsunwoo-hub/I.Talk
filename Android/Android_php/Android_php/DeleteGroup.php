<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$groupnum = $_POST['groupnum'];

$sql = "delete from groupInformation where groupnum = $groupnum";
$result = mysqli_query($conn, $sql);

$response = array();
$response['success'] = false;

if($result==true){
  $response["success"] = true;
}
echo json_encode($response);
?>
