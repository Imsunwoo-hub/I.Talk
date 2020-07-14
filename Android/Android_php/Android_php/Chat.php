<?php

$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$inquiryNum = $_GET["inquiryNum"];

$result = mysqli_query($conn, "select user.name, comment, date from user, inquiryContent where user.id = inquiryContent.sender
and inquiryNum = $inquiryNum order by date");
$response = array();

while($row = mysqli_fetch_array($result)){
  array_push($response, array('inquiryNum'=>$inquiryNum, 'sender'=> $row[0], 'comment'=> $row[1], 'commentDateTime'=> $row[2] ));
}

echo json_encode(array('response'=>$response));
mysqli_close($conn);

?>
