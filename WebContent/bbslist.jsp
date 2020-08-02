
<%@page import="utilEx.utilEx"%>
<%@page import="dto.BbsDto"%>
<%@page import="java.util.List"%>
<%@page import="dao.BbsDao"%>
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

// 선택한 항목과 키워드 데이터, 없으면 CHOICE,""
String sel = request.getAttribute("sel")+"";
String keyword = request.getAttribute("keyword")+"";

// 항목-키워드, 페이지 번호를 적용한 게시물을 10개 추출
@SuppressWarnings("unchecked")
List<BbsDto> list = (List<BbsDto>)request.getAttribute("bbslist"); 
// 전체 게시글 갯수
int bbsAmount =(int)request.getAttribute("bbsAmount");
// 현재 페이지
int currPage = (int)request.getAttribute("currPage");
%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 페이지</title>

<style type="text/css">
table.bor{
		width : 700;
		background-color:#7ba6d1;
		border : 1px solid black;
		
}

th.bor{
		border-collapse :collapse;
		border : 1px non;
		background-color: #7ba6d1;
		color : white;
}
th.bor2{
		border-collapse :collapse;
		border : 1px solid;
		background-color: #7ba6d1;
		color : white;
}
td.bor{
		font-family: 휴먼고딕;
		background-color: white;
}


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
a:link{
	
	text-decoration: none;
	color : #000000;
}

a:visited{
	color : #000000;
}

a:hover{
	color:#ffa31a;
	text-decoration: none;
}


</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>

<!-- 게시물 테이블 -->

<div align="center">

<h2 style="font-family: 휴먼고딕" ><%=mdto.getId() %>님 환영합니다.</h2>

<table class="bor">
<col width="50"> <col width="550"> <col width="100">


<tr>

	<th class="bor">번호</th>
	<th class="bor">제목</th>
	<th class="bor">작성자</th>
</tr>


<tr>

	<td colspan="3" bgcolor="#ffcc00" height="1"></td>
</tr>

<%
// 게시된 글이 없을 경우
if(list.size() == 0 || list == null){	
%>


<tr>

<td colspan="3" align="center">게시된 글이 없습니다.</td>
</tr>


<%
// 게시된 글이 있을 경우
}else{
	for(int i = 0; i < list.size(); i++){
	BbsDto bdto = list.get(i);
		// 삭제된 글은 제외
		if(bdto.getDel() != 1){
			
			String depth = utilEx.arrow(bdto.getDepth());
%>

	<tr>
	
		<th class="bor2"><%=i+1 %></th>
		<td class="bor"><a href="control?work=detail&seq=<%=bdto.getSeq()%>" ><%=depth+bdto.getTitle() %></a></td>
		<td align="center" class="bor"><%=bdto.getId() %></td>
	</tr>
	
<%	
		}
		// 삭제된 글일 경우
		else{
		%>
		
	<tr>
	<td class="bor" colspan="3" align="center" >삭제된게시물입니다.</td>
	</tr>	
	
		<%
		}
	}
}
%>

</table>
<div>


<table>

<tr>

<%
for(int i = 0; i < bbsAmount; i++){
	// 로그인했을 경우나 선택한 페이지 번호일 경우
	if(currPage==i){
%>	

	<td><font color="#ffa31a" style="text-decoration: underline; font-weight: bold "><%=(i+1) %></font>
	</td>	
	
<%	// 나머지 페이지
	}else{
		
%>	
	
	<td><a href="#" title="<%=(i+1) %>" onclick="goPage(<%=i%>)"><%=(i+1) %></a>
	</td>	
	
<%		
	}
}
%>

</tr>

</table>
</div>


<a href="control?work=writeView">글쓰기</a>


<!-- 검색 테이블 -->

<div align="center">
<form id="frm" action="control">
<input type="hidden" name="work" value="list">
<table width="700">

<tr>
	<td align = "center">
	<select style="height:28px" name="sel" id="sel">
	
	<option value="CHOICE">선택</option>
	<option value="TITLE">제목</option>
	<option value="CONTENT">내용</option>
	<option value="ID">작성자</option>
	</select>
	
	<input style="height:20px" value="" id="keyword" name="keyword" id="keyword">
	<button type="button"class="button" id="selBtn">검색</button>
	
	</td>
</tr>

<tr>
	<td align="center"><button type="button"class="button" id="listBtn">목록</button></td>
</tr>
</table>
</form>
</div>

<script type="text/javascript">
$(function () {
	
	// 검색한 결과 페이지 이동
	$('#selBtn').click(function() {
		
		$('#frm').submit();
		
	});
	
	// 게시판으로 이동
	$('#listBtn').click(function() {
		
		location.href = "control?work=list";
	});
});

let sel2 ="<%=sel%>";
let keyword2 ="<%=keyword%>";

document.getElementById("sel").value = sel2;
document.getElementById("keyword").value = keyword2;

function goPage(rcvPage){
	
	location.href = "control?work=list&rcvPage=" + rcvPage +"&sel=" + sel2 + "&keyword=" + keyword2;
	
}

</script>


</body>
</html>