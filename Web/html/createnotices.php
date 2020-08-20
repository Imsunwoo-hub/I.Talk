

<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");
session_start();

	$noticeTitle = $_POST['noticeTitle'];
	$noticeClassification=$_POST['noticeClassification'];
  $noticeContent= $_POST['noticeContent'];
	$noticeWriterId = $_SESSION['userid'];
	$noticeWriterName =$_SESSION['username'];
	$noticeDate = date('Y-m-d-A H:i');




  if(!$conn){
      echo 'not connet';
  }

  $sql = "INSERT INTO Notice(NoticeTitle, NoticeClassification, NoticeContent, NoticeWriterId, NoticeWriterName ,NoticeDate) VALUES('$noticeTitle','$noticeClassification', '$noticeContent','$noticeWriterId','$noticeWriterName','$noticeDate')";

	if($noticeTitle == '' ||  $noticeContent == '' ){
	    echo "<script> alert('내용을 모두 입력해주시오.'); history.go(-1);</script>";
	}
	else{
  if(mysqli_query($conn,$sql)){
      echo "<script> alert('공지가 올라갔습니다'); window.location.href='http://tkdl2401.cafe24.com/html/index.php' </script>";
  }
else{
      echo "<script> alert('사이트에 문제가 발생했습니다.');</script>";
  }
}
  mysqli_close($conn);
  ?>
