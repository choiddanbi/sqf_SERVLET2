<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	User user =(User) session.getAttribute("authentication");
%>

<header>
	<a href="/ssa/index"><h1 class="logo">LOGO</h1></a>
	<ul class="top-nav">
		<%
			if(user != null) {
		%>
				<li><%=user.getName() %>님 환영합니다.</li>
				<a href="/ssa/mypage"><li>마이페이지</li></a>
				<a href="/ssa/logout"><li>로그아웃</li></a>
		<% 
			} else {
		%>
				<a href="/ssa/login"><li>로그인</li></a>
				<a href="/ssa/register"><li>회원가입</li></a>
		<%
			}
		%>
	</ul>
</header>