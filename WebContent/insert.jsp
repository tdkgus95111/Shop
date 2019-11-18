<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="dbpkg.DAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>홈쇼핑 회원 등록</title>
<link rel="stylesheet" href="style.css" type="text/css">
<script type="text/javascript">
function check() {
	if (frm.custname.value == "") {
		alert("회원성명 입력하세요!!");
		frm.custname.focus();
		return false;
	}
	if (frm.phone.value == "") {
		alert("회원 번호 입력하세요!! ");
		frm.phone.focus();
		return false;
	}
	if (frm.address.value == "") {
		alert("회원 주소 입력하세요!!");
		frm.address.focus();
		return false;
	}
	if (frm.grade.value == "") {
		alert("회원 등급 입력하세요!!");
		frm.grade.focus();
		return false;
	} else if (!(frm.grade.value == "A" || frm.grade.value == "B" || frm.grade.value == "C")) {
		alert("회원 등급이 옳바르지 않습니다 ( A, B, C) 입력하세요");
		frm.grade.focus();
		return false;
	}
	if (frm.city.value == "") {
		alert("회원 도시코드 입력하세요");
		frm.city.focus();
		return false;
	} else if (frm.city.value.length > 2) {
		alert("도시코드는 2자리까지 입력하세요!!");
		frm.city.focus();
		return false;
	}
	document.frm.method = "post";
	document.frm.action = "insertPro.jsp";
	document.frm.submit();
}

</script>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	DAO dao = DAO.getInstance();

	int custno = dao.getCustno();
	
	SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
	Date nowTime = new Date();
%>
	<header>
		<h1>쇼핑몰 회원관리 ver.1.0</h1>
	</header>
	<nav>
		<ul>
			<li><a href="insert.jsp">회원등록</a></li>
			<li><a href="memberList.jsp">회원목록 조회/수정</a></li>
			<li><a href="priceList.jsp">회원매출조회</a></li>
			<li><a href="index.jsp">홈으로.</a></li>
		</ul>
	</nav>
	<section>
		<h3>홈쇼핑 회원등록</h3>
		<form name="frm">
			<table border="1">
				<tr>
					<th>회원번호(자동생성)</th>
					<td><input type="text" name="custno" id="custno" value="<%=custno %>" readonly="readonly"></td>
				</tr>
				<tr>
					<th>회원성명</th>
					<td><input type="text" name="custname" id="custname"></td>
				</tr>
				<tr>
					<th>회원전화</th>
					<td><input type="text" name="phone" id="phone"></td>
				</tr>
				<tr>
					<th>회원주소</th>
					<td><input type="text" name="address" id="address"></td>
				</tr>
				<tr>
					<th>가입일자</th>
					<td><input type="text" name="joindate" id="joindate" value="<%= sdf.format(nowTime)%>" readonly="readonly"></td>
				</tr>
				<tr>
					<th>고객등급[A:VIP, B:일반, C:직원]</th>
					<td><input type="text" name="grade" id="grade"></td>
				</tr>
				<tr>
					<th>도시코드</th>
					<td><input type="text" name="city" id="city"></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" value="등 록" onclick="check()">&nbsp;
						<input type="button" value="조 회" onclick="location.href='memberList.jsp'">&nbsp;
						<input type="reset" value="리 셋">
					</td>
				</tr>
			</table>
		</form>
	</section>
	<footer>
		HREKOREA Copyright&copy; All right reserved
	</footer>
</body>
</html>