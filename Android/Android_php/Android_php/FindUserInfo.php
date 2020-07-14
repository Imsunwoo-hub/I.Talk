<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$userid = $_POST["userid"];


$sql = mysqli_prepare($conn, "select id, name, phonenum, email,grade, major, state from user where id = ?");
mysqli_stmt_bind_param($sql, "s", $userid);
mysqli_stmt_execute($sql);

mysqli_stmt_store_result($sql);
mysqli_stmt_bind_result($sql, $userid, $username, $userphonenum, $useremail,$usergrade, $usermajor, $userstate);



$response = array();
$response["success"] = false;

while (mysqli_stmt_fetch($sql)) {
  $response["success"] = true;
  $response["userid"] = $userid;
  $response["username"] = $username;
  $response["userphonenum"] = $userphonenum;
  $response["useremail"] = $useremail;
  $response["usergrade"] = $usergrade;
  $response["usermajor"] = $usermajor;
  $response["userstate"] = $userstate;
}
 echo json_encode($response);
?>
