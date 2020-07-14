<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$userid = $_POST['userid'];
$groupName = $_POST['groupName'];
$groupMember = $_POST['groupMember'];
$groupMember = explode(',' , $groupMember);
$count = count($groupMember);

$sql1 = "insert into groupInformation(groupname, groupadministrator) values('$groupName', '$userid')";
$temp =  mysqli_query($conn, $sql1);

$response = array();
$response["success"] = false;

if($temp==true){
  for($i=0; $i<$count; $i++){
    $sql2 = "insert into groupmember values((select groupnum from groupInformation where groupname = '$groupName'), '$groupMember[$i]')";
    $result = mysqli_query($conn, $sql2);
    if($result==true){
      $response["success"] = true;
      }
    }
}
 echo json_encode($response);
?>
