<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$userid = $_POST["userid"];
$userpassword = $POST["userpassword"];

$sql = "select * from user where id = '$userid' and password = '$userpassword'";

$result = mysqli_query($conn, $sql);


$response = array();
$response["success"] = false;

if($result==true){
  $response["success"] = true;
}

 echo json_encode($response);
?>
