function ir(uri) {
    $.ajax({
        url: uri,
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        type: 'POST',
        beforeSend: function () {
            $('#msg').dialog(
                    {
                        resizable: false,
                        draggable: false,
                        modal: true,
                        closeOnScape: false
                    }
            )
                    .html('<span style="text-align:center,font-size:14px;font-weight:bold;margin:auto">Cargando, espere...</span><br/><p style="text-aling:center"><center><img src="/taxi/css/cargando.gif"/></center></p>')
        }
    }).done(function (html) {
        $('#principal').html(html);
        $('#msg').dialog('close');
    }).fail(function (jqXHR, textStatus) {
        $('#msg').html('<span style=\"padding:3px\">' + jqXHR.responseText + '</span>').addClass('ui-state-error ui-corner-all');
        $('#msg').dialog('close');
    });
}

function irv(uri) {    
    $.ajax({
        url: uri,
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        type: 'POST',
        beforeSend: function () {
            $('#msg').dialog(
                    {
                        resizable: false,
                        draggable: false,
                        modal: true,
                        closeOnScape: false,
                        width: 500,
                        height: 600
                    }
            )
                    .html('<span style="text-align:center,font-size:14px;font-weight:bold;margin:auto">Cargando, espere...</span><br/><p style="text-aling:center"><center><img src="/taxi/css/cargando.gif"/></center></p>')
        }
    }).done(function (html) {
        $('#msg').dialog('close');
        $('#vent').dialog(
                    {
                        resizable: false,
                        draggable: false,
                        modal: true,
                        closeOnScape: false,
                        width: 500,
                        height: 600
                    }
            ).html(html);        
    }).fail(function (jqXHR, textStatus) {
        $('#msg').html('<span style=\"padding:3px\">' + jqXHR.responseText + '</span>').addClass('ui-state-error ui-corner-all');
        $('#vent').dialog('close');
    });
}

