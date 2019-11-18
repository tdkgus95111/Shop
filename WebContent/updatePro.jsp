<%@page import="dbpkg.MemberVO"%>
<%@page import="dbpkg.DAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	DAO dao = DAO.getInstance();
	MemberVO vo = new MemberVO();
	vo.setCustno(Integer.parseInt(request.getParameter("custno")));
	vo.setCustname(request.getParameter("custname"));
	vo.setPhone(request.getParameter("phone"));
	vo.setAddress(request.getParameter("address"));
	vo.setJoindate(request.getParameter("joindate"));
	vo.setGrade(request.getParameter("grade"));
	vo.setCity(request.getParameter("city"));
	
	int rs = dao.memUpdate(vo);
	if(rs == 1) {
%>
<script type="text/javascript">
	window.alert("수정 성공!!");
	location.href = "memberList.jsp";
</script>
<%
	}
%>
<script type="text/javascript">
	window.alert("수정 실패!!");
	window.history.go(-1);
</script>
</body>
</html>