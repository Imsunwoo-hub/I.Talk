<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$userid = $_GET['id'];
$groupname = $_GET['groupname'];
$groupMember = $_GET['memberid'];

$groupMember = explode(',' , $groupMember);
$count = count($groupMember);

$sql1 = "insert into groupInformation(groupname, groupadministrator) values('$groupname', '$userid')";
$temp =  mysqli_query($conn, $sql1);


if($temp==true){
  for($i=0; $i<$count; $i++){
    $sql2 = "insert into groupmember values((select groupnum from groupInformation where groupname = '$groupname'), '$groupMember[$i]')";
    $result = mysqli_query($conn, $sql2);
  }
}
  if($result==true){
    echo "<script> alert('그룹생성이 완료되었습니다.'); window.close();</script>";
  }

?>
