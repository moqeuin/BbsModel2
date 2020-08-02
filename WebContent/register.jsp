<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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

.button2 {

    width:82px;
    height: 30px;

    background-color: #ff9900;

    border: 1px black solid;

    color:#fff;

    padding: 2px 0;

    text-align: center;

    text-decoration: none;

    display: inline-block;

    font-size: 12px;

    margin: 1px;

    cursor: pointer;

}

.writebor{

width:300;  
background-color: #7ba6d1; 
padding:15px;
 border:1px black solid;  
}

td.bor{
		font-family: @UD Digi Kyokasho NK-B;
		background-color: #7ba6d1;
		color : white;
		text-align: center;
}

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>



<div align="center">
<h1>회원가입 페이지</h1>
<form id="regifrm" method="post" action="control">
<input type="hidden" name="work" value="register">


<table class="writebor">
<col width="30"> <col width="180"> <col width="90">


<tr>

	<td colspan="3" bgcolor="#ffcc00" height="1"></td>
</tr>

<tr>

	<td class="bor">ID:</td>
	<td class="bor"><input type="text" name="id" id="id" size="20" value=""></td>
	<td class="bor"><button type="button" class="button2" id="chBtn">ID중복확인</button> </td>
</tr>

<tr>

	<td class="bor">Password:</td>
	<td class="bor" colspan="2"><input type="password" name="pwd" id="pwd" size="33"></td>
</tr>


<tr>

	<td class="bor">Address:</td>
	<td class="bor" colspan="2"><input type="text" name="addr" id="addr" size="33"></td>
</tr>


<tr>

	<td class="bor">Email:</td>
	<td class="bor" colspan="2"><input type="text" name="email" id="email" size="33"></td>
</tr>


</table>

<button type="button" class="button" id="regiBtn">가입하기</button>
<button type="button" class="button" id="loginBtn">로그인화면</button>

</form>
</div>


<script type="text/javascript">
$(function () {
	
	$('#regiBtn').click(function () {
		
		if($('#id').val().trim() == "" ){
			alert("ID를 입력해주세요");
			$('#id').focus();
		}
		else if($('#pwd').val().trim() == "" ){
			alert("패스워드를 입력해주세요");
			$('#pwd').focus();
			
		}
		else if($('#addr').val().trim() == "" ){
			alert("주소를 입력해주세요");
			$('#addr').focus();
			
		}
		else if($('#email').val().trim() == "" ){
			alert("이메일을 입력해주세요");
			$('#email').focus();
			
		}
		else{
			
			
			$('#regifrm').submit();
		}
		
	});
	
	// id 중복확인
	$('#chBtn').click(function () {
		
		// 빈 칸 확인
		if($('#id').val().trim() ==""){
			
			alert("id를 입력해주세요");
			$('#id').focus();
		}
		else{
			$.ajax({
			
				url: "control",
				type: "get",
				data: {id:$('#id').val(),work:"ch_id"},
				success:function(data){
					
					if(data.trim() == "false"){
						
						alert("사용해도 되는 ID입니다.");
						
					}
					else{
						
						alert("중복된 ID입니다.");
						$('#id').val("");
					}
					
				},
				error:function(){
									
					alert("연결실패");
				}
			});
		
		}
	});
	$('#loginBtn').click(function() {
		
		location.href = "control?work=login";
		
	});
});



</script>




</body>
</html>