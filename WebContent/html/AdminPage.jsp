<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="/LibRecommendSys/css/personalpage.css" />
<link rel="stylesheet" type="text/css"
	href="/LibRecommendSys/css/adminpage.css" />
<title>管理员！你好！</title>
</head>
<body>
	<div>
		<center>
			<img src="/LibRecommendSys/image/headlogo.jpg">
		</center>
		<ul id="MenuBar2" class="MenuBarHorizontal">
			<li><a class="MenuBarItemSubmenu" href="BookMa.html">图书管理</a>
			</li>
			<li><a class="MenuBarItemSubmenu" href="UserMa.html">用户管理</a></li>
			<li><a class="MenuBarItemSubmenu" href="recomma.html">推荐管理</a></li>
		</ul>
		<div class="div1">
			<img src="/LibRecommendSys/image/pp-pimg.jpg">
			<table width="200" border="0">
				<tr>
					<th scope="col">姓名：</th>
					<th scope="col"><%=session.getAttribute("name").toString() %></th>
				</tr>
				<tr>
					<th scope="row">工号：</th>
					<th scope="row"><%=session.getAttribute("pid").toString() %></th>
				</tr>
			</table>
		</div>
		<div class="divad1">
			<img src="/LibRecommendSys/image/Eventlogo.jpg">
			<ul>
				<li>某某员工因为什么事件，被发现。。。。。</li>
				<li>哈哈哈，好消息好消息，特大好消息。</li>
				<li>某某员工因为什么事件，被发现。。。。。</li>
				<li>哈哈哈，好消息好消息，特大好消息。</li>

			</ul>
		</div>

	</div>


</body>
</html>