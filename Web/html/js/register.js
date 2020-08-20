$(document).ready(function(){

    $('#userid').blur(function(){

            $.ajax({ // ajax실행부분
                type: "post",
                url : "php/checkid.php",
                data : {
                userid : $('#userid').val()
                },
                success : function s(a){ $('#idch').html(a); },
                error : function error(){ alert('시스템 문제발생');}
            });
    });

});


function check_pw(val){

    var du = document.userinput;
    var ogpw = du.userpassword.value;
    var same = "<span style='color:green;'>비밀번호가 일치합니다.</span>";
    var diff = "<span style='color:red;'>비밀번호가 일치하지 않습니다.</span>";

    if(ogpw == val){
        document.getElementById("check").innerHTML = same;
    }else if(ogpw != val){
        document.getElementById("check").innerHTML = diff;
    }
}



function blank_up(){

    var du = document.userinput;
    var pattern1 = /[0-9]/;
    var pattern2 = /[a-zA-Z]/;
    var pattern3 = /[~!@\#$%<>^&*]/;
    var pw_msg = "";


      if(!du.userid.value){
        alert("아이디를 입력하시오");
        du.userid.focus();
        return false;
    }

    if(du.use.value == '0'){
      alert("아이디 중복을 확인해주세요.");
      du.userid.focus();
      return false;
  }
  if(!du.userpassword.value){
      alert("패스워드를 입력하시오");
      du.userpassword.focus();
      return false;
  }

  if(du.userpassword.value != du.userpasswordc.value){
      alert("패스워드를 정확하게 입력해주세요.");
      du.userpasswordc.focus();
      return false;
  }
  if(!pattern1.test(pw)||!pattern2.test(pw)||!pattern3.test(pw)||pw.length<8||pw.length>50){
      alert("영문+숫자+특수기호 8자리 이상으로 구성하여야 합니다.");
      return false;
    }

  if(!du.username.value){
      alert("이름을 입력하시오");
      du.username.focus();
      return false;
  }

    if(!du.userphonenum.value){
        alert("전화번호를 입력하시오");
        du.userphonenum.focus();
        return false;
    }

    if(!du.useremail.value){
        alert("이메일을 입력하시오");
        du.useremail.focus();
        return false;
    }



    if(!du.usergrade.value){
        alert("학년을 선택하시오");
        du.usergrade.focus();
        return false;
    }

}
