
<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$sql = "select user.id, user.name, user.grade, user.major, user.identity, user.state, groupmember.groupnum from user, groupmember where user.id = groupmember.groupmemberid order by groupmember.groupnum";
$result = mysqli_query($conn, $sql);
$response = array();

while ($row = mysqli_fetch_array($result)) {
  array_push($response, array("userid" => $row[0], "username" => $row[1], "usergrade" => $row[2], "usermajor" => $row[3], "useridentity" => $row[4], "userstate" => $row[5], "groupNum" => $row[6]));
}
echo json_encode(array("response"=>$response));
?>
