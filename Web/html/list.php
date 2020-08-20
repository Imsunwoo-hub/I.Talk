<?

$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");



$page_size=10;


$page_list_size = 10;

if (!$no || $no < 0) $no=0;
$query = "select NoticeNum,NoticeClassification, NoticeTitle,NoticeDate,NoticeWriter from NoticeTest order by NoticeNum desc limit $no,$page_size";
$result = mysql_query($query, $conn);

$result_count=mysql_query("select count(*) from NoticeTest",$conn);
$result_row=mysql_fetch_row($result_count);
$total_row = $result_row[0];

if ($total_row <= 0) $total_row = 0;
$total_page = floor(($total_row - 1) / $page_size);


$current_page = floor($no/$page_size);


?>

<html>
<head>
<title>�� ���� �Խ���</title>
<style>
<!--
    td  { font-size : 9pt;   }
    A:link  { font : 9pt;  	color : black; 	text-decoration : none;	font-family : ����;	font-size : 9pt;  }
    A:visited  {   text-decoration : none; color : black;	font-size : 9pt;  }
    A:hover  { 	text-decoration : underline; color : black; font-size : 9pt;  }

-->
</style>
</head>

<body topmargin=0 leftmargin=0 text=#464646>
<center>
<BR>
<!-- �Խ��� Ÿ��Ʋ -->
<font size=2>��~ ���׸� ã���ô�~ ���׸�~~</a>
<BR>
<BR>

<!-- �Խù� ����Ʈ�� ���̱� ���� ���̺� -->
<table width=580 border=0  cellpadding=2 cellspacing=1 bgcolor=#777777>
<!-- ����Ʈ Ÿ��Ʋ �κ� -->
<tr height=20 bgcolor=#999999>
	<td width=30 align=center>
		<font color=white>��ȣ</font>
	</td>
	<td width=370  align=center>
		<font color=white>�� ��</font>
	</td>
	<td width=50 align=center>
		<font color=white>�۾���</font>
	</td>
	<td width=60 align=center>
		<font color=white>�� ¥</font>
	</td>
	<td width=40 align=center>
		<font  color=white>��ȸ��</font>
	</td>
</tr>
<!-- ����Ʈ Ÿ��Ʋ �� -->
<!-- ����Ʈ �κ� ���� -->
<?
while($row=mysql_fetch_array($result))
{

?>

<tr>

	<td height=20  bgcolor=white align=center>
		<a href=read.php?id=<?=$row[NoticeNum]?>&no=<?=$no?>><?=$row[NoticeNum]?></a>
	</td>

	<td height=20  bgcolor=white>&nbsp;
		<a href=read.php?id=<?=$row[NoticeNum]?>&no=<?=$no?>><?=strip_tags($row[NoticeTitle], '<b><i>');?></a>
	</td>

	<td align=center height=20 bgcolor=white>
		<font  color=black><?=$row[NoticeDate]?></font>
	</td>

	<td align=center height=20 bgcolor=white>
		<font  color=black><?=$row[NoticeContent]?></font>
	</td>

</tr>

<?
}
mysql_close($conn);
?>
</table>
<table border=0>
<tr>
<td width=600 height=20 align=center rowspan=4>
<font  color=gray>
&nbsp;
<?

$start_page = (int)($current_page / $page_list_size) * $page_list_size;

$end_page = $start_page + $page_list_size - 1;
if ($total_page < $end_page) $end_page = $total_page;

if ($start_page >= $page_list_size) {

	$prev_list = ($start_page - 1)*$page_size;
    echo  "<a href=\"$PHP_SELF?no=$prev_list\">��</a>\n";
}

for ($i=$start_page;$i <= $end_page;$i++) {

$page=$page_size*$i;
$page_num = $i+1;

	if ($no!=$page){
		echo "<a href=\"$PHP_SELF?no=$page\">";
     }

	echo " $page_num ";

	if ($no!=$page){
		echo "</a>";
    }

}

if($total_page > $end_page)
{

	$next_list = ($end_page + 1)* $page_size;
	echo "<a href=$PHP_SELF?no=$next_list>��</a><p>";
}
?>

</font>
</td>
</tr>
</table>

<a href=write.php>�۾���</a>
</center>
</body>
</html>
