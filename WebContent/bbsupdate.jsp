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

BbsDto bdto =  (BbsDto)request.getAttribute("bdto");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 수정 페이지</title>

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
background-color: #a1c0de; 
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


<form action="control" method="get">

<input type="hidden" name="work" value="update">
<input type="hidden" name="seq" value="<%= bdto.getSeq()%>"> 


<table class="writebor">

<col width="150"> <col width="150"> <col width="300">

<tr>

	<th class="bor">ID:&nbsp;&nbsp;<%=bdto.getId() %></th>
	<th class="bor">조회수:&nbsp;&nbsp;<%=bdto.getReadcount() %></th>
	<th class="bor">올린날짜:&nbsp;&nbsp;<%=bdto.getWdate() %></th>
</tr>

<tr>

	<td colspan="3" class="bor" height="4"></td>
</tr>

<tr>

	<td colspan="3" class="bor">제목:&nbsp;&nbsp; <input type="text" value="<%=bdto.getTitle() %>" name="title" size="77">   </td>
</tr>

<tr>

	<td colspan="3" class="bor" height="4"></td>
</tr>

<tr>

	<td colspan="3" class="bor"><textarea rows="25" cols="90" name="content"><%=bdto.getContent() %></textarea> </td>
</tr>

</table>

<input type="submit" value="수정완료" class="button">
</form>


</div>

</body>
</html>