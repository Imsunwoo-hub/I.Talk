<?php

$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$user1 = $_GET["user1"];
$user2 = $_GET["user2"];

$result1 = mysqli_query($conn, "select inquiryNum from inquiry where (requesterid = '$user1' and responserid='$user2') or (requesterid = '$user2' and responserid='$user1')");

if(mysqli_num_rows($result1)==0) {
    $sql = "insert into inquiry(requesterid, responserid) values('$user1','$user2')";
     $result2= mysqli_query($conn, $sql);
     if($result2==true){
      $result3 = mysqli_query($conn, "select inquiryNum from inquiry where (requesterid = '$user1' and responserid='$user2')");
      if($result3==true){
        $result4 = mysqli_query($conn, "insert into inquiryContent(inquiryNum, sender, comment,date) VALUES ((select inquiryNum from inquiry where (requesterid = '$user1' and responserid='$user2')),'$user1','createChatComment', NOW())");
        if($result4==true){
        $result5 = mysqli_query($conn, "select inquiryNum, user.name, comment, date from user, inquiryContent where user.id = inquiryContent.sender
        and inquiryNum = (select inquiryNum from inquiry where (requesterid = '$user1' and responserid='$user2')) order by date");
        if($result5==true){
        $response = array();
        while($row = mysqli_fetch_array($result5)){
          array_push($response, array('inquiryNum'=>$row[0], 'sender'=> $row[1], 'comment'=> $row[2], 'commentDateTime'=> $row[3] ));
      }
    }
  }
  }
}
}
else{
  $result2= mysqli_query($conn, "select inquiryNum, user.name, comment, date from user, inquiryContent where user.id = inquiryContent.sender
  and inquiryNum = (select inquiryNum from inquiry where (requesterid = '$user1' and responserid='$user2') or (requesterid = '$user2' and responserid='$user1'))
    order by date");

  $response = array();

  while($row = mysqli_fetch_array($result2)){
    array_push($response, array('inquiryNum'=>$row[0], 'sender'=> $row[1], 'comment'=> $row[2], 'commentDateTime'=> $row[3] ));
  }
}

echo json_encode(array('response'=>$response));
mysqli_close($conn);
?>
