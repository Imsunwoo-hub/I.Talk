<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$inquirys = $_POST['inquirys'];
$inquirys = explode(',' , $inquirys);
$count = count($inquirys);


$response = array();
$response["success"] = false;

for($i=0; $i<$count; $i++){
  $sql = "delete from inquiry where inquiryNum = $inquirys[$i]";
  $result = mysqli_query($conn, $sql);
  if($result==true){
    $response["success"] = true;
  } else{
      $response["success"] = false;
  }
  }
  echo json_encode($response);
 ?>
