<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://github.org/rodzac/webcorpus" prefix="wc" %>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${wc:message("title")} 0.1</title>
		<link rel="StyleSheet" href="css/main.css" type="text/css"/>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/jquery.contextMenu.js"></script>
		<script type="text/javascript" src="js/main.js"></script>
	</head>

	<body>
		<div id="title">
			<h1>${wc:message("title")}</h1>
			<h6>0.1</h6>
		</div>

		<ul id="commands">
		    <li><a href="#" onclick="next();" class="next-button">${wc:message("next")}</a></li>
		    <li><a href="#" onclick="save();" class="save-button">${wc:message("save")}</a></li>
		    <li><a href="#" onclick="archive();" class="archive-button">${wc:message("archive")}</a></li>
		</ul>

		<input type="hidden" id="id" value="" />
		<div id="text" contentEditable="true" class="text"></div>
		
		<div id="mask"></div>
		<div id="archive">
			<div id="close" onclick="closeArchive();">${wc:message("close")}</div>
			<div id="list"></div>
		</div>

		<ul id="tags" class="contextMenu"></ul>

		<div id="styles"></div>

		<div id="localization">
			<input type="hidden" id="message-success" value="${wc:message("save.success")}" />
			<input type="hidden" id="message-error" value="${wc:message("save.error")}" />
		</div>

	</body>

	<script>
		//archive();
	</script>

</html>
