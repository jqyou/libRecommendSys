<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="/LibRecommendSys/css/personalpage.css" />
<script src="/LibRecommendSys/js/jQuery1.82.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$(".eachbook").remove();
	
	$.post("/LibRecommendSys/personBorrow",{
		method:"notReturn",
		requestPage:1
	},function(data){
		var json = data.bookhistory;
		$(".eachbook").remove();
		for(var i=0;i<(json.length-1);i++){
			var divs = "<div class='eachbook' id='"+json[i].id+"'>"
               	+"<div class='booktitle'>"
               	+"<a href='/LibRecommendSys/html/BookInfo.html?id="+json[i].id+"' target='_blank'>"+json[i].name+"</a>&nbsp;"
               		+"<ul class='shuxing'>"
                		+"<li style='list-style-type:none;margin-top:10px;'>作者："+json[i].author+"</li>"
                		+"<li style='list-style-type:none;margin-top:10px;'>类别："+json[i].type+"</li>"
                		+"<li style='list-style-type:none;margin-top:10px;'>索书号："+json[i].id+"</li>"
               		+"</ul>"
               	+"</div>"
               	+"<div class='bookdes'></div>";
               divs = divs + "</div>";      
		    $(".resultList").append(divs);
		}
	},"json");
});
</script>
<title>个人主页</title>
</head>
<body>
	<div>
		<center>
			<img src="/LibRecommendSys/image/headlogo.jpg">
		</center>
		<ul id="MenuBar2" class="MenuBarHorizontal">
			<li><a class="MenuBarItemSubmenu" href="UserBookRecord.html">借阅记录</a>
			</li>
			<li><a class="MenuBarItemSubmenu" href="UserHabitChoose.html">个性化推荐</a></li>
			<li><a class="MenuBarItemSubmenu" href="BookList.html">排行榜</a></li>
		</ul>
		<div class="div1">
			<img src="/LibRecommendSys/image/pp-pimg.jpg">
			<center><input type="button" value="上传"></center>
			<table width="200" border="0">
				<tr>
					<th scope="col">姓名：</th>
					<th scope="col"><%=session.getAttribute("name").toString() %></th>
				</tr>
				<tr>
					<th scope="row">学院：</th>
					<th scope="row"><%=session.getAttribute("xy").toString() %></th>

				</tr>
				<tr>
					<th scope="row">兴趣：</th>
					<th scope="row"><%=session.getAttribute("xq").toString() %></th>

				</tr>

			</table>
		</div>
		<div class="search">
			<img src="/LibRecommendSys/image/booksearch.jpg">
			<div class="searchbar">
				<input id="ST" class="searchtext" type="text"
					onblur="if(this.value==''){this.value=this.getAttribute('dir')}this.style.color='#777'"
					onfocus="if(this.value==this.getAttribute('dir')){this.value=''} this.style.color='#000'"
					style="color: rgb(119, 119, 119);" value="" dir="搜索图书"> <input
					class="searchbtn" type="button" value="搜索">
			</div>
		</div>
		<div class="result" id="result">
			<div class="resulttitle">
				<label class="titlebold">未还图书：</label>
			</div>
			<div class="resultList">
				<div class='eachbook'>
					<div class='booktitle'>
						<a href='#' target='_blank'>程序员面试 </a>&nbsp;
						<ul class="shuxing">
							<li style="list-style-type: none; margin-top: 10px;">作者：</li>
							<li style="list-style-type: none; margin-top: 10px;">类别：</li>
							<li style="list-style-type: none; margin-top: 10px;">索书号：</li>
						</ul>
					</div>
					<div class='bookdes'></div>
				</div>
				<div class='eachbook'>
					<div class='booktitle'>
						<a href='#' target='_blank'>数据结构 </a>&nbsp;
						<ul class="shuxing">
							<li style="list-style-type: none; margin-top: 10px;">作者：</li>
							<li style="list-style-type: none; margin-top: 10px;">类别：</li>
							<li style="list-style-type: none; margin-top: 10px;">索书号：</li>
						</ul>
					</div>
					<div class='bookdes'></div>
				</div>
				<div class='eachbook'>
					<div class='booktitle'>
						<a href='#' target='_blank'>操作系统 </a>&nbsp;
						<ul class="shuxing">
							<li style="list-style-type: none; margin-top: 10px;">作者：</li>
							<li style="list-style-type: none; margin-top: 10px;">类别：</li>
							<li style="list-style-type: none; margin-top: 10px;">索书号：</li>
						</ul>
					</div>
					<div class='bookdes'></div>
				</div>

			</div>
		</div>
		<div class="div2"><img  src="/LibRecommendSys/image/nb-logo.jpg"></div>
	</div>

</body>
</html>