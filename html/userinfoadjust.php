<!DOCTYPE html>
<?php
$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");

$id = $_POST["id"];
$name = $_POST["name"];
$major = $_POST["major"];
$grade = $_POST["grade"];
$state = $_POST["state"];
$phonenum = $_POST["phonenum"];
$email = $_POST["email"];
?>

<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>유저정보수정</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/user.css" />

    <script>
      function showuserinfoadjust() {
        var id = document.getElementById("id");
        var name = document.getElementById("name");
        var major = document.getElementById("major");
        var grade = document.getElementById("grade");
        var phonenum = document.getElementById("phonenum");
        var email = document.getElementById("email");

        if(!id.value && !name.value && !major.value && !grade.value && !phonenum.value && !email.value){
          alert("수정할 정보를 입력해주세요.");
          return false;
        }
        else{
          document.f.submit();
        }
      }
      </script>
</head>
<body>
<header>
    <div class="wrap">
        <h2>정보수정</h2>
    </div>
</header>
<div id="container">
    <table border="1">
      <form name="f" action="alterUserInfo.php" method="post">
        <tr>
            <td width="25%">아이디(학번) : </td>
            <td width="25%"><?=$id?><input type="hidden" name="id" value="<?=$id?>"> </td>
            <td width="50%"><input type="text" id="id" name="newid" maxlength="10" placeholder="변경할 아이디"></td>
        </tr>
        <tr>
            <td width="25%">이름 : </td>
            <td width="25%"><?=$name?><input type="hidden" name="name" value="<?=$name?>"> </td>
            <td width="50%"><input type="text" id="name" name="newname" maxlength="10" placeholder="변경할 이름"></td>
        </tr>
        <tr>
            <td width="25%">학과 : </td>
            <td width="25%"><?=$major?><input type="hidden" name="major" value="<?=$major?>"></td>
            <td width="50%"><input type="text" id="major" name="newmajor" maxlength="10" placeholder="변경할 전공"></td>
        </tr>
        <tr>
            <td width="25%">학년 : </td>
            <td width="25%"><?=$grade?><input type="hidden" name="grade" value="<?=$grade?>"></td>
            <td><input type="text" name="newgrade" id="grade" maxlength="1"  placeholder="변경할 학년" ></td>
        </tr>
        <tr>
            <td width="25%">연락처 : </td>
            <td width="25%"><?=$phonenum?><input type="hidden" name="phonenum" value="<?=$phonenum?>"></td>
            <td width="50%"><input type="tel" id="phonenum" name="newphonenum" maxlength="11" placeholder="변경할 전화번호"></td>
        </tr>
        <tr>
            <td width="25%">이메일 : </td>
            <td width="25%"><?=$email?><input type="hidden" name="email" value="<?=$email?>"></td>
            <td width="50%"><input type="email" id="email" name="newemail"  maxlength="20"  placeholder="변경할 전화번호"></td>
        </tr>
    </table>
      <div class="user_info_save">
        <input type="submit" value="저장" onclick="showuserinfoadjust(); return false;" >
      </form>
    </div>
</div>


</body>
</html>
