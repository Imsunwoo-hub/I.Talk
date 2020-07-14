<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$groups = $_POST['groups'];
$groups = explode(',' , $groups);
$count = count($groups);

$response = array();
$response["success"] = false;

for($i=0; $i<$count; $i++){
  $sql = "delete from groupInformation where groupnum = $groups[$i]";
  $result = mysqli_query($conn, $sql);
  if($result==true){
    $response["success"] = true;
  }
}
  echo json_encode($response);
 ?>
