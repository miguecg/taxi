<%-- 
    Document   : msg
    Created on : 19-may-2016, 9:51:23
    Author     : miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <% if (request.getSession().getAttribute("msg") != null) { %>
        <%= request.getSession().getAttribute("msg") %>
        <% 
          request.getSession().setAttribute("msg", null);
        %>
        <% } %>
    </body>
</html>
