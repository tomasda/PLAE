<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
    <%
		boolean find = false;	
		Cookie[] cookies = null;
		// Get an array of Cookies associated with this domain
		cookies = request.getCookies();
		if(cookies != null && cookies.length != 0){
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase("jwtAPSCT") && !cookie.getValue().equalsIgnoreCase("")){
					out.println("<META HTTP-EQUIV='REFRESH' CONTENT='1;URL=index.xhtml'> ");
					find = true;
				}
			}
		}
		if (!find){
			out.println("<META HTTP-EQUIV='REFRESH' CONTENT='1;URL=login.xhtml'> ");
		}
	%>
		<link rel="shortcut icon" type="image/x-icon" href="resources/img/favicon.ico"/>
    	<link rel="stylesheet" href="resources/css/font-awesome.min.css">
    </head>    
    <body>
	    <div style="margin-top:25%;text-align: center;" >
		    <i class="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i>
		</div>
    </body>
</html>