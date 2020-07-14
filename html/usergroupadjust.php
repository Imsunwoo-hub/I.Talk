<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>학생그룹수정</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="css/managerusergroupmake.css" />
</head>
<body>
<header>
    <div class="wrap">
        <h2>그룹수정</h2>
    </div>
</header>
<section>
    <div id="container" class="groupmakebox" >
        <div class="groupname">
            <h4>그룹이름 :
            <input type="text" maxlength="15"></h4>
        </div>
        <hr>

        <div class="box_1">
            <form id="searchUser" class="search">
                <select name="grade">
                    <option value="1">전체</option>
                    <option>1학년</option>
                    <option>2학년</option>
                    <option>3학년</option>
                    <option>4학년</option>
                </select>
                <input type="text" placeholder="학번 OR 이름 검색" class="text">
            </form>        
                <div class="list">
                    <table border="1" >
                        <thead>
                            <tr>
                               <th><input type="checkbox" id="allCheck"/></th>
                               <th>학년</th>
                               <th>전공</th>
                               <th>아이디(학번)</th>
                               <th>이름</th>
                            </tr>
                         </thead>
                        <tbody>
                            <tr>
                                <td><input type="checkbox"/></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        <!--
        <div class="tab tab-btn">
            <button onclick="tab1_To_tab2();">추가</button>
            <button onclick="tab2_To_tab1();">삭제</button>
        </div>
    
        <div class="box_2">
            <form id="searchUser" class="search">
                <select name="grade">
                    <option value="1">전체</option>
                    <option>1학년</option>
                    <option>2학년</option>
                    <option>3학년</option>
                    <option>4학년</option>
                </select>
                <input type="text" placeholder="학번 OR 이름 검색" class="text">
            </form> 
                <div class="list">
                    <table border="2">
                        <thead>
                            <tr>
                                <th><input type="checkbox" id="allCheck"/></th>
                                <th>학년</th>
                                <th>전공</th>
                                <th>아이디(학번)</th>
                                <th>이름</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><input type="checkbox"/></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </tbody>
                        </table>
                  </div>
        </form>
    </div>-->
    
    </section>
            
    <div class="groupsave">
        <input type="submit" value="저장" class="button">
        <input type="button" value="취소" class="button">
    </div>
       
    

</body>
</html>