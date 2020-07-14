<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$userid = $_POST["userid"];
$userpassword = $_POST["userpassword"];

$sql = mysqli_prepare($conn, "select id, name, identity, major, grade from user where id = ? and password = ?");
mysqli_stmt_bind_param($sql, "ss", $userid, $userpassword);
mysqli_stmt_execute($sql);

mysqli_stmt_store_result($sql);
mysqli_stmt_bind_result($sql, $userid, $username, $useridentity, $usermajor, $usergrade);



$response = array();
$response["success"] = false;

while (mysqli_stmt_fetch($sql)) {
  $response["success"] = true;
  $response["userid"] = $userid;
  $response["username"] = $username;
  $response["useridentity"] = $useridentity;
  $response["usermajor"] = $usermajor;
  $response["usergrade"] = $usergrade;
}
 echo json_encode($response);
?>
