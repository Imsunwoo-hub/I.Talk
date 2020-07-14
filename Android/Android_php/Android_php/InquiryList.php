<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");


$sql = "select inquiry.inquiryNum, a.name, b.name, inquiryContent.comment, inquiryContent.date
from inquiry,user a, user b, inquiryContent
where inquiry.requesterid=a.id
and inquiry.responserid = b.id
and inquiry.inquiryNum=inquiryContent.inquiryNum
and inquiryContent.date in (SELECT MAX(date) from inquiryContent GROUP BY inquiryNum)";

$result = mysqli_query($conn, $sql);
$response = array();

while ($row = mysqli_fetch_array($result)) {
  array_push($response, array("inquiryNum" => $row[0], "user1" => $row[1], "user2" => $row[2], "lastComment" => $row[3], "lastCommentDateTime" => $row[4]));
}
echo json_encode(array("response"=>$response));
mysqli_close($conn);
?>
