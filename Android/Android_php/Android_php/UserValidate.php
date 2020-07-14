<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$userid = $_POST["userid"];

$sql = "select * from user where id = '$userid'";
$result = mysqli_query($conn, $sql);

$response = array();
$response["success"] = true;

while (mysqli_fetch_array($result)) {
  $response["success"] = false;
  $response["userid"] = $userid;
}
echo json_encode($response);

?>
