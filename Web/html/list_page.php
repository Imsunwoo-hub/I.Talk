
<div id='page_1'>
<?
//총게시물                 $totals
//한페이지 나타날 게시글수 $view_total
$rr=ceil($totals/$view_total);


//이전 페이지 구하기
 $before= $_page-1; //현재 페이지수 에서 -1을 준다.
 if($before<1)($before=1);

//다음 페이지 구하기
$next= $_page + 1;
if($next>$rr)($next=$rr);


//그룹페이지 구성//
//(처음)
if($_page%10){$goto=$_page-$_page%10+1; //한 그룹당 10개 페이지를 지정 '10'을 넘기면 1을 증가.
     }else if($goto=$_page -9); // '10'배수가 아니면 -'9'


//그룹페이지 구성 (끝)//
$last= $goto + 10;

//이전페이지 그룹 출력
$before_group= $goto -1;
if($before_group<1)($before_group=1);
if($_page !=1) echo ("<a href=$PHP_SELF?_page=$before_group$href>◀</a>&nbsp;"); //이전 페이지 그룹출력





//페이지 번호 출력
for($e=$goto; $e<$last; $e++){ //현재페이지가 전체페이지 보다 작으면 페이지를 증가
	if($e>$rr) break;
	if($e==$_page) echo ("<strong>$e</strong>");
	               else{
					   echo ("&nbsp; <a href=$PHP_SELF?_page=$e$href>[$e]</a>&nbsp;");
				   }
}

//다음페이지 출력
$next_group= $last;
if($next_group > $rr)($next_group=$rr);
if($_page !=$rr) echo ("&nbsp; <a href=$PHP_SELF?_page=$next_group$href>▶</a>");




?>
</div>
