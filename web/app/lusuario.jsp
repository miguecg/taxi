<%-- 
    Document   : lusuario
    Created on : 17-may-2016, 0:44:17
    Author     : miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="usua" uri="/WEB-INF/tlds/lusua.tld" %>
<script type="text/javascript" src="/taxi/js/utils.js"></script>
<script type="text/javascript">
    $(function () {
        $('#uAgr').on('click', function () {
            irv('/taxi/app/usuario.jsp');
        });

        $('#cPass').on('click', function () {
            if ($('input[name="idusua"]:checked')) {
                irv('/taxi/app/pass.jsp?v_idusuario=' + $('input[name="idusua"]:checked').val());
            }
        });
        $('#uMod').on('click', function () {
            if ($('input[name="idusua"]:checked')) {                
                irv('/taxi/app/usuario.jsp?v_idusuario=' + $('input[name="idusua"]:checked').val());
            }
        });

        $('#cRol').on('click', function () {
            if ($('input[name="idusua"]:checked')) {
                irv('/taxi/app/roles.jsp?v_idusuario=' + $('input[name="idusua"]:checked').val());
            }
        });

    });
</script>

<div id="vent"></div>
<usua:usuarios/>