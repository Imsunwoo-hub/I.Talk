<?php

$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$userid = $_POST["userid"];
$newUserid = $_POST["newuserid"];
$username = $_POST["username"];
$newUsername = $_POST["newusername"];
$usermajor = $_POST["usermajor"];
$usergrade = $_POST["usergrade"];
$userstate = $_POST["userstate"];

if(empty($usergrade)){
if(empty($newUserid) && empty($newUsername)){
  $sql = "update user set major = '$usermajor' where id = '$userid'";
  $newUserid = $userid;
  $newUsername = $username;
}
else if(empty($newUserid)){
  $sql = "update user set name = '$newUsername', major = '$usermajor' where id = '$userid'";
  $newUserid = $userid;
}
else if(empty($newUsername)){
    $sql = "update user set id = '$newUserid', major = '$usermajor' where id = '$userid'";
    $newUsername = $username;
}
else{
  $sql = "update user set id = '$newUserid', name = '$newUsername', major = '$usermajor' where id = '$userid'";
}

} else{
  if(empty($newUserid) && empty($newUsername)){
    $sql = "update user set major = '$usermajor', grade = '$usergrade', state = '$userstate' where id = '$userid'";
    $newUserid = $userid;
    $newUsername = $username;
  }
  else if(empty($newUserid)){
    $sql = "update user set name = '$newUsername', major = '$usermajor', grade = '$usergrade', state = '$userstate' where id = '$userid'";
    $newUserid = $userid;
  }
  else if(empty($newUsername)){
      $sql = "update user set id = '$newUserid', major = '$usermajor', grade = '$usergrade', state = '$userstate' where id = '$userid'";
      $newUsername = $username;
  }
  else{
    $sql = "update user set id = '$newUserid', name = '$newUsername', major = '$usermajor', grade = '$usergrade', state = '$userstate' where id = '$userid'";
  }

}


$result = mysqli_query($conn, $sql);
$response = array();
$response["success"] = false;

if($result==true){
  $response["success"] = true;
  $response["userid"] = $newUserid;
  $response["username"] = $newUsername;
  $response["usermajor"] = $usermajor;
  $response["usergrade"] = $usergrade;
  $response["userstate"] = $userstate;
}
echo json_encode($response);
?>
