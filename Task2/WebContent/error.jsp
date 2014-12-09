<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Task 2 - Calculator.</title>
<style type="text/css">
#divElement {
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -50px;
	margin-left: -150px;
	width: 300px;
	height: 100px;
}
€‹
</style>
</head>
<body>
	<div id="divElement">
		<c:forEach var="entry" items="${errorMap}">
    		Error: ${entry.value}<br />
		</c:forEach>
		<a href="/Task2">Go back</a>
	</div>
</body>
</html>