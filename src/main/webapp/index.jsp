<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>qrcode open service</title>
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
</head>
<body>
	<p>Welcome to qrcode open service</p>
	<div style="width: auto; height: auto; border: 2px solid #0099FF;">
		<p>生成二维码</p>
		<textarea id="content" name="content" rows="" cols=""
			style="width: 400px; height: 100px;"></textarea>
		<br />
		<button onclick="encode();">后端生成二维码</button>
		&nbsp;&nbsp;
		<button onclick="jqueryQrcode();">前端生成二维码</button>
		<br />
		<div id="img"></div>
	</div>
	<br />
	<div style="width: auto; height: auto; border: 2px solid #0099FF;">
		<p>解析二维码</p>
		图片地址：<input id="url" name="url" value="" style="width: 500px;" /><br>
		<button onclick="decode();">解析二维码</button>
		<br />
		<div id="contentFrame"></div>
	</div>
	<script type="text/javascript">
		function encode() {
			var content = document.getElementById("content").value;
			if (!content || content.length < 1) {
				return;
			}
			content = encodeURIComponent(content);
			var img = "<img src='encode?content=" + content + "' />";
			document.getElementById("img").innerHTML = img;
		}
		function jqueryQrcode() {
			var content = document.getElementById("content").value;
			if (!content || content.length < 1) {
				return;
			}
			content = utf16to8(content);
			document.getElementById("img").innerHTML = "";
			jQuery('#img').qrcode({
				width : 200,
				height : 200,
				correctLevel : 0,
				text : content
			});
		}
		function decode() {
			var url = document.getElementById("url").value;
			if (!url || url.length < 1) {
				return;
			}
			url = "decode?url=" + encodeURIComponent(url);
			var iframe = '<iframe src="'+url+'" style="width: 400px; height: 100px;"></iframe>';
			document.getElementById("contentFrame").innerHTML = iframe;
		}
		function utf16to8(str) {
			var out, i, len, c;
			out = "";
			len = str.length;
			for (i = 0; i < len; i++) {
				c = str.charCodeAt(i);
				if ((c >= 0x0001) && (c <= 0x007F)) {
					out += str.charAt(i);
				} else if (c > 0x07FF) {
					out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
					out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
					out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
				} else {
					out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
					out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
				}
			}
			return out;
		}
	</script>
</body>
</html>