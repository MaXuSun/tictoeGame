<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>#字游戏</title>
<%
  pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<link
	href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="${APP_PATH}/static/js/jquery-2.0.0.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script
	src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<style type="text/css">
table {
	border-collapse: collapse;
	border: 0px solid #999;
}

table td {
	border-top: 0;
	border-right: 5px solid #999;
	border-bottom: 5px solid #999;
	border-left: 0;
	width: 100px;
	height: 100px;
}

td button {
	width: 100px;
	height: 100px;
	background: #FFF;
}

#start_btn {
	width: 100px;
	height: 50px;
}

table tr.lastrow td {
	border-bottom: 0;
}

table tr td.lastCol {
	border-right: 0;
}

#begin_div {
	position: relative;
	left: 10px;
	top: 10px
}

#start_div {
	position: relative;
	left: 70px;
	top: 40px;
}

.chance_div {
	position: relative;
	left: 0px;
	top: 30px;
}
</style>
</head>
<body>
	<div id="begin_div">
		<table>
			<tr>
				<td><button id="0_0" class="game_btn"></button></td>
				<td><button id="0_1" class="game_btn"></button></td>
				<td class="lastCol"><button id="0_2" class="game_btn"></button></td>
			</tr>
			<tr>
				<td><button id="1_0" class="game_btn"></button></td>
				<td><button id="1_1" class="game_btn"></button></td>
				<td class="lastCol"><button id="1_2" class="game_btn"></button></td>
			</tr>
			<tr class="lastrow">
				<td><button id="2_0" class="game_btn"></button></td>
				<td><button id="2_1" class="game_btn"></button></td>
				<td class="lastCol"><button id="2_2" class="game_btn"></button></td>
			</tr>
		</table>
	</div>
	<div class="chance_div">
		<form class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-1 control-label">先后选择</label>
				<div class="col-sm-2">
					<select class="form-control ch" name="chance">
						<option value="1">先手</option>
						<option value="2">后手</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1 control-label">难度选择</label>
				<div class="col-sm-2">
					<select class="form-control ch" name="easy" >
						<option value="1">一般</option>
						<option value="2">困难</option>
					</select>
				</div>
			</div>
		</form>
	</div>
	<div id="start_div">
		<button class="btn btn-success" id="start_btn">重新开始</button>
	</div>






	<script type="text/javascript">
		//加载时初始化
		$(function() {
			empty();
			empty_ui();
		});

		//点击时放图片
		$("td button").click(
				function() {
					if ($(this).attr("click_at") == "a") {
						return;
					}
					$(this).attr("click_at", "a");
					var this_id = $(this).attr("id");
					$(this).css("background-image",
							"url(${APP_PATH}/static/1.jpg)");
					$.ajax({
						url : "${APP_PATH}/pos",
						type : "get",
						data : "position=" + this_id,
						success : function(result) {
							console.log(result);
							if (result.isWin != "null") {
								var id = "#" + result.position;
								$(id).css("background-image",
										"url(${APP_PATH}/static/2.jpg)");
								$(id).attr("click_at", "a");
							}
							setTimeout(function() {
								isWin(result)
							}, 100);

						}
					});
				});
		//判断是否赢了
		function isWin(result) {
			if (result.isWin == "2") {
				alert("厉害,你赢了!");
				empty();
				empty_ui();
			} else if (result.isWin == "1") {
				alert("你输了，你可真是个zz!");
				empty();
				empty_ui();
			} else if (result.isWin == "9") {
				alert("平局!");
				empty();
				empty_ui();
			}
		}

		//重新开始按钮
		$("#start_btn").click(function() {
			empty();
			empty_ui();
		});

		//困难先手按钮选择后发送消息
		$(".ch").change(function() {
			empty();
			empty_ui();
		});

		//后台数据的更新
		function empty() {
			$.ajax({
				url : "${APP_PATH}/empty",
				type : "get",
				data : $("form").serialize(),
				success : function(result) {
					if (result.isWin != "null") {
						var id = "#" + result.position;
						$(id).css("background-image",
								"url(${APP_PATH}/static/2.jpg)");
						$(id).attr("click_at", "a");
					}
				}
			});
		}

		//前台界面的更新
		function empty_ui() {
			$(".game_btn").removeClass("disabled");
			$(".game_btn").css("background-image", "");
			$(".game_btn").attr("click_at", "b");
		}
	</script>
</body>
</html>