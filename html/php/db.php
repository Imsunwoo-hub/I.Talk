<?php

$host = 'localhost';
$username = 'tkdl2401';
$userpass = 'tkdl2401@';
$dbname = 'tkdl2401';

$conn = mysqli_connect($host,$username,$userpass,$dbname);

if(mysqli_connect_errno($conn)){
    echo "DB 연결 실패".mysqli_connect_error();
}else{

}
?>
