<?
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

session_start();
$type = $_POST['type'];
$commentNum = $_POST["commentNum"];
$noticeNum = $_POST['noticeNum'];
$comment = $_POST["comment"];
$commentWriterName = $_POST['userid'];
$writtenDateTime = date('Y-m-d-A H:i');

if(!$conn){
    echo 'not connet';
}

$sql = "INSERT INTO comment VALUES('$type', '$commentNum', '$noticeNum', '$comment', '$commentWriterName', '$writtenDateTime')";


if(mysqli_query($conn,$sql)){

    echo "<script> alert('댓글 작성 완료.');</script>";
}

mysqli_close($conn);
?>
