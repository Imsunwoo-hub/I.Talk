<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");


$sql = "select id,name,grade,major,identity, phonenum from user order by grade, name";
$result = mysqli_query($conn, $sql);
$response = array();

while ($row = mysqli_fetch_array($result)) {
  array_push($response, array("userid" => $row[0], "username" => $row[1], "usergrade" => $row[2], "usermajor" => $row[3], "useridentity" => $row[4], "userphonenum"=>$row[5]));
}
echo json_encode(array("response"=>$response));
mysqli_close($conn);
?>
