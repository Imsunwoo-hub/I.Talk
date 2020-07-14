<?php

$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$userid = $_POST["userid"];
$userphonenum = $_POST["userphonenum"];
$newUserphonenum = $_POST["newUserphonenum"];
$useremail = $_POST["useremail"];
$newUseremail = $_POST["newUseremail"];



if(empty($newUserphonenum)){
  $sql = "update user set email = '$newUseremail' where id = '$userid'";
  $newUserphonenum = $userphonenum;
}
else if(empty($newUseremail)){
  $sql = "update user set phonenum = '$newUserphonenum' where id = '$userid'";
  $newUseremail = $useremail;
}
else{
  $sql = "update user set phonenum = '$newUserphonenum', email = '$newUseremail' WHERE id = '$userid' ";

}

$result = mysqli_query($conn, $sql);
$response = array();
$response["success"] = false;

if($result==true){
  $response["success"] = true;
  $response["userid"] = $userid;
  $response["userphonenum"] = $newUserphonenum;
  $response["useremail"] = $newUseremail;
}
echo json_encode($response);

?>
