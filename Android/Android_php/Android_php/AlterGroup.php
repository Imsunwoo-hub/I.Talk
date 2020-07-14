<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$userid = $_POST['userid'];
$groupNum = $_POST['groupNum'];
$groupName = $_POST['groupName'];
$groupMember = $_POST['groupMember'];
$groupMember = explode(',' , $groupMember);
$count = count($groupMember);

$response = array();
$response["success"] = false;

if(empty($groupName)){
  $sql1 = "delete from groupmember where groupnum = $groupNum";
  $temp =  mysqli_query($conn, $sql1);
  if($temp==true){
    for($i=0; $i<$count; $i++){
      $sql2 = "insert into groupmember values($groupNum, '$groupMember[$i]')";
      $result = mysqli_query($conn, $sql2);
      if($result==true){
        $response["success"] = true;
        }
      }
  }
}
else {
  $sql1 = "update groupInformation set groupname = '$groupName' where groupnum = $groupNum";
  $temp1 =  mysqli_query($conn, $sql1);
  if($temp1==true){
    $sql2 = "delete from groupmember where groupnum = $groupNum";
    $temp2 =  mysqli_query($conn, $sql2);
    if($temp2==true){
      for($i=0; $i<$count; $i++){
        $sql3 = "insert into groupmember values($groupNum, '$groupMember[$i]')";
        $result = mysqli_query($conn, $sql3);
        if($result==true){
          $response["success"] = true;
        }
      }
    }
  }
}
echo json_encode($response);
?>
