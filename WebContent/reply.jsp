<%@page import="dto.BbsDto"%>
<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Object ologin = session.getAttribute("login");
MemberDto mdto = null;

if(ologin == null){
%>	
<script type="text/javascript">
	alert("로그인을 다시해주십시오");
	location.href = "control?work=login";
</script>

<% 	
}else{
mdto = (MemberDto)ologin; 
} 

BbsDto bdto = (BbsDto)request.getAttribute("bdto");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글 페이지</title>


<style type="text/css">


.button {

    width:100px;
    height: 30px;

    background-color: #7ba6d1;

    border: 1px black solid;

    color:#fff;

    padding: 5px 0;

    text-align: center;

    text-decoration: none;

    display: inline-block;

    font-size: 15px;

    margin: 4px;

    cursor: pointer;

}


th.bor{
		border-collapse :collapse;
		border : 1px non;
		background-color: #7ba6d1;
		color : white;
}

.writebor{

width:600;  
background-color: #7ba6d1; 
padding:15px;
 border:1px black solid;  
}
td.bor{
		font-family: 한컴바탕;
		background-color: white;
		
}

</style>
</head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<body>

<div align="center">


<table class="writebor">
<col width="150"> <col width="150"> <col width="300">

<tr>

	<td colspan="3" bgcolor="#ffcc00" height="1"></td>
</tr>

<tr>

	<th class="bor">ID:&nbsp;&nbsp;<%=bdto.getId() %></th>
	<th class="bor">조회수:&nbsp;&nbsp;<%=bdto.getReadcount() %></th>
	<th class="bor">올린날짜:&nbsp;&nbsp;<%=bdto.getWdate() %></th>
</tr>


<tr>

	<td colspan="3" class="bor">제목:&nbsp;&nbsp;<%=bdto.getTitle() %></td>
</tr>


<tr>

	<td colspan="3" class="bor"><textarea rows="25" cols="90" readonly="readonly"><%=bdto.getContent() %></textarea> </td>
</tr>

</table>

</div>
<div align="center">


<button type="button" class="button" id="listBtn">게시판가기</button>
</div>

<div id="replyView" align="center">



<table  class="writebor">
<col width="400">

<tr>

	<th class="bor" id="id">ID:<%=mdto.getId() %></th>
</tr>

<tr>

	<td bgcolor="#ffcc00" height="1"></td>
</tr>

<tr>

	<td class="bor">제목:&nbsp;&nbsp;<input type="text" id="title" size="50"> </td>
</tr>

<tr>

	<td class="bor"><textarea rows="10" cols="60" id="content"></textarea></td>
</tr>
</table>
<button type="button" class="button" id="replyBtn">댓글입력</button>


</div>

<script type="text/javascript">

$(function() {
	

	$('#listBtn').click(function() {
		
		location.href = "control?work=list";
	});
	
	
	
	$('#replyBtn').click(function() {
		if($('#title').val().trim() == ""){
			
			alert("제목을 입력해주세요");
			$('#title').focus();
		}
		else if($('#content').val().trim() == ""){
			
			alert("내용을 입력해주세요");
			$('#content').focus();
		}
		else{
			
			let _id = "<%=mdto.getId()%>";
			let _seq = "<%=bdto.getSeq()%>"
			
			$.ajax({
				
				url: "control",
				type: "get",
				data: {work:"reply", id:_id, title:$('#title').val(), content:$('#content').val(), seq:_seq},
				success:function(data){
					
					if(data.trim() == "true"){
						
						alert("댓글을 입력했습니다.");
						location.href = "control?work=list";
					}
					else{
						
						alert("댓글입력을 실패했습니다.");
						
					}
					
				},
				error:function(){
					
					alert("전송에 실패했습니다.");
				}			
			});			
		}	
	});
	
});


</script>


</body>
</html>