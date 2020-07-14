<?php

$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$result = mysqli_query($conn, "select * from notice order by date desc");
$response = array();

while($row = mysqli_fetch_array($result)){
  array_push($response, array('title'=> $row[0], 'content'=> $row[1], 'writer'=> $row[2], 'date'=> $row[3]));
}

echo json_encode(array('response'=>$response));
mysqli_close($conn);

?>
>$row[1], 'NoticeTitle'=> $row[2], 'NoticeContent'=> $row[3], 'NoticeWriter'=> $row[4], 'NoticeDate'=> $row[5], 'TopFixed'=> $row[6]));
}
echo json_encode(array('response'=>$response));
mysqli_close($con);
?>