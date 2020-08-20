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
<?

$conn = mysqli_connect("localhost", "tkdl2401", "tkdl2401@", "tkdl2401");



// �� ���� ��������
$result=mysql_query("select NoticeNum,NoticeClassification, NoticeTitle,NoticeDate,NoticeWriter from NoticeTest where id=$noticeNum", $conn);
$row=mysql_fetch_array($result);

?>

<table width=450 border=0  cellpadding=2 cellspacing=1 bgcolor=#777777>
<tr>
	<td height=20 colspan=4 align=center bgcolor=#999999>
		<font color=white><B><?=strip_tags($row[NoticeTitle], '<b><i>');?></B></font>
	</td>
</tr>
<tr>
	<td width=50 height=20 align=center bgcolor=#EEEEEE>�۾���</td><td width=240 bgcolor=white><?=$row[NoticeWriter]?></td>

</tr>
<tr>
	<td width=50 height=20 align=center bgcolor=#EEEEEE>��&nbsp;&nbsp;&nbsp;¥</td><td width=240 bgcolor=white><?=$row[NoticeDate]?></td>

</tr>
<tr>
	<td bgcolor=white colspan=4>
		<font color=black>
			<pre><?=strip_tags($row[NoticeContent], '<b><i><img><a>');?></pre>
		</font>
	</td>
</tr>
<!-- ��Ÿ ��ư �� -->
<tr>
	<td colspan=4 bgcolor=#999999>
		<table width=100%>
			<tr>
				<td width=200 align=left height=20>
					<a href=list.php?no=<?=$no?>><font color=white>[���Ϻ���]</font></a>
					<a href=write.php><font color=white>[�۾���]</font></a>
					<a href=edit.php?id=<?=$noticeNum?>><font color=white>[����]</font></a>
					<a href=predel.php?id=<?=$noticeNum?>><font color=white>[����]</font></a>
				</td>

<!-- ��Ÿ ��ư �� -->

<!-- ���� ���� ǥ�� -->
				<td align=right>
				<?

				//  ���� �ۺ��� id ���� ū �� �� ���� ���� ���� �����´�. �� �ٷ� ���� ��
				$query=mysql_query("select id from testboard where id > $id limit 1", $conn);
				$prev_id=mysql_fetch_array($query);

					if ($prev_id[id]) // ���� ���� ���� ����
					{
						 echo "<a href=read.php?id=$prev_id[id]><font color=white>[����]</font></a>";
					}
					else
					{
						echo "[����]";
					}

				$query=mysql_query("select id from testboard where id < $id order by id desc limit 1", $conn);
				$next_id=mysql_fetch_array($query);

					if ($next_id[id])
					{
						 echo "<a href=read.php?id=$next_id[id]><font color=white>[����]</font></a>";
					}
					else
					{
						echo "[����]";
					}

				?>
				</td>
			</tr>
		</table>
</b></font>
</td>
</tr>
</table>

<?
mysql_close($conn);
?>

</center>
</body>
</html>
