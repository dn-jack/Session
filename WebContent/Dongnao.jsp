<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="session/save" method="post" target="ifram1">
	<input type="text" name="userName" value="姓名"/>
	<input type="text" name="age" value="年龄"/>	
	<input type="submit" value="提交"/>
	<iframe name="ifram1" ></iframe>
</form>
<form action="session/getSession" method="post" target="ifram2">
<input type="submit" value="获取session"/>
<iframe name="ifram2" ></iframe>
</form>
</body>
</html>