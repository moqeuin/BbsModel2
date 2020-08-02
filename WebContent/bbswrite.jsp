<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
// 세션에서 로그인한 사용자의 정보를 가져옴
Object ologin = session.getAttribute("login");
MemberDto mdto = null;

// 세션이 만료될 경우 로그인화면으로 전환
if(ologin == null){	
%>	
<script type="text/javascript">
	alert("로그인을 다시해주십시오");
	location.href = "control?work=login";
</script>
<% 	
//세션이 만료되지 않았으면 사용자 정보 저장
}else{
mdto = (MemberDto)ologin; 
}
%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성 페이지</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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

.writebor{

width:300;  
background-color:#7ba6d1; 
padding:15px;
 border:1px black solid;  
}
</style>



</head>
<body>


<div align="center">
<form id="frm" action="control">
<input type="hidden" name="work" value="write">
<table class="writebor" >


<tr>

	<th>
	<font color="white" >ID:&nbsp;&nbsp;&nbsp;</font><input type="text" readonly="readonly" 
	  id="id" name="id" value="<%=mdto.getId() %>" >
	</th>
</tr>

<tr>

	<td bgcolor="#ffcc00" height="1"></td>
</tr>

<tr>

	<td ><font color="white">제목:&nbsp;&nbsp;</font><input type="text" id="title" name="title" size="81"> </td>
</tr>

<tr>

	<td><textarea rows="20" cols="90" id="content" name="content"></textarea> </td>
</tr>

</table>



</form>

<button type="button" id="listBtn" class="button">글 목록</button>
<button type="button" id="addBtn" class="button">글 추가</button>
</div>


<script type="text/javascript">

$(function () {
	$('#listBtn').click(function() {
		
		location.href="control?work=list";
	});
	
	$('#addBtn').click(function() {
		if($('#title').val().trim() == ""){
			
			alert("제목을 입력해주세요");
			$('#title').focus();
		}
		else if($('#content').val().trim() == ""){
			
			alert("내용을 입력해주세요");
			$('#content').focus();
		}
		
		else{
			
			$('#frm').submit();
		}
		
	});
	
	
});


</script>




</body>
</html>