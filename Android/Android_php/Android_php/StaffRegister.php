<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$userid = $_POST["userid"];
$userpassword = $_POST["userpassword"];
$username = $_POST['username'];
$userphonenum = $_POST['userphonenum'];
$useremail = $_POST['useremail'];
$useridentity = $_POST['useridentity'];
$usermajor = $_POST['usermajor'];
//$usergrade = $_POST['usergrade'];
//$userestate = $_POST['userstate'];

$sql = "insert into user values('$userid', '$userpassword', '$username', '$userphonenum', '$useremail', '$useridentity', '$usermajor', null, null)";
$result = mysqli_query($conn, $sql);

$response = array();
$response['success'] = false;

if($result==true){
  $response['success'] = true;
}

echo json_encode($response);

?>
