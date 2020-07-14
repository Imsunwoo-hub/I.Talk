<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$sql = "SELECT groupnum, groupname, groupadministrator
        FROM  groupInformation
        ORDER BY groupname";
$result = mysqli_query($conn, $sql);
$response = array();

while ($row = mysqli_fetch_array($result)) {
  array_push($response, array("groupnum" => $row[0], "groupname" => $row[1], "groupadministrator" => $row[2]));
}
echo json_encode(array("response"=>$response));
mysqli_close($conn);
?>
