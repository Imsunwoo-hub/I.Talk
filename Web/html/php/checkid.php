<?php
include_once "db.php";


$idch = $_POST['userid'];

if(!$conn){
    echo "not connect DB";
}

$sql = "SELECT * FROM user WHERE id='".$idch."'";
$result = mysqli_query($conn,$sql);
$count = mysqli_num_rows($result);

if($idch == ''){
    ?>
<div>아이디를 입력하세요.</div>
<?php
}else{

    if($count == 0){
    ?>
    <div style="color:green" class="use">
    사용가능한 아이디입니다.
    <input type="hidden" value="1" name="use"/>
    </div>
    <?php
    }else{
    ?>
    <div style="color:red" class="use">
    아이디가 존재합니다.
    <input type="hidden" value="0" name="use"/>
    </div>
    <?php
    }
}
    ?>
