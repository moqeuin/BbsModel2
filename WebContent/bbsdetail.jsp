<%@page import="dto.BbsDto"%>
<%@page import="java.util.List"%>
<%@page import="dto.MemberDto"%>
<%@page import="dao.BbsDao"%>
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
<title>게시물 상세정보</title>


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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
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
<%
//접속한 id와 게시물을 쓴 id가 같을 경우 수정,삭제 권한을 획득
if(mdto.getId().equals(bdto.getId()) ){
	
%>	

<button type="button" class="button" id="updateBtn">수정</button>
<button type="button" class="button" id="deleteBtn">삭제</button>

<%
}
%>

<button type="button" class="button" id="replyBtn">댓글달기</button>
<button type="button" class="button" id="listBtn">게시판가기</button>
</div>



<script type="text/javascript">
$(function() {
	
	
	$('#updateBtn').click(function() {
		
		location.href = "control?work=updateView&seq=" + <%= bdto.getSeq()%>;
	
	});
	
	$('#deleteBtn').click(function() {
		
		location.href = "control?work=delete&seq=" + <%= bdto.getSeq()%>;	
	});
	
	$('#listBtn').click(function() {
		
		location.href = "control?work=list";
	})
	
	$('#replyBtn').click(function() {
		
		location.href = "control?work=replyView&seq=" + <%= bdto.getSeq()%>;
	})	
});



</script>
</body>
</html>