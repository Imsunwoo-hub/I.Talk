<?php

$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$userid = $_POST["userid"];
$userpassword = $_POST["userpassword"];

$response = array();
$response["success"] = false;

if(!empty($userpassword)){
  $sql = "update user set password ='$userpassword' where id ='$userid'";
  $result = mysqli_query($conn, $sql);
  if($result==true){
    $response["success"] = true;
  }
}
echo json_encode($response);
?>
