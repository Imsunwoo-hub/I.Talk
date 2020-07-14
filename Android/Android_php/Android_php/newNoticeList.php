<?php

$con = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$result = mysqli_query($con, "SELECT NoticeNum, NoticeClassification, NoticeTitle, NoticeContent, NoticeWriterId, NoticeWriterName, NoticeDate, TopFixed FROM Notice ORDER BY TopFixed DESC, NoticeDate DESC");
$response = array();

while($row = mysqli_fetch_array($result)){
  array_push($response, array('NoticeNum'=> $row[0], 'NoticeClassification'=>$row[1], 'NoticeTitle'=> $row[2], 'NoticeContent'=> $row[3], 'NoticeWriterId'=> $row[4], 'NoticeWriterName'=>$row[5], 'NoticeDate'=> $row[6], 'TopFixed'=> $row[7]));
}
echo json_encode(array('response'=>$response));
mysqli_close($con);
?>