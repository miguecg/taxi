/*
 @autor: Miguel Angel Cedeno Garciduenas
 U . M . S . N . H . 
 P R O N A D
 S I I A
 */

function operTam(nform, nom, cmp, tam) {
    this.nombre = nom;
    this.tamanio = parseInt(tam);
    this.compara = cmp;
    this.nform = nform;
}


function msgError(campo, mensaje) {
    this.campo = campo;
    this.mensaje = mensaje;
}


(function($) {

// Valida un formulario	
    $.validaFormulario = function(options) {

        var anError = new Array();
        var hayErrores = false;
        var mailFiltro = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        var numeroFiltro = /(^-?\d\d*\.\d*$)|(^-?\d\d*$)|(^-?\.\d\d*$)|(^-?\d*$)/;
        var alfaFiltro = /[a-zA-Z\u00f1\u00d1\u00e1\u00c1\u00e9\u00c9\u00ed\u00cd\u00f3\u00fa\u00dc\u00fc]+$/;
        var textoFiltro = /([a-zA-Z\u00f1\u00d1\u00e1\u00c1\u00e9\u00c9\u00ed\u00cd\u00f3\u00fa\u00dc\u00fc])+[\s{1}+]?([a-zA-Z\u00f1\u00d1\u00e1\u00c1\u00e9\u00c9\u00ed\u00cd\u00f3\u00fa\u00dc\u00fc])+$/;
        var doblesEspaciosFiltro = /\s{2}/;
        var letrasFiltro = /[a-zA-Z]+$/;


        $.validaFormulario.limpia(options);

        // Validacion de los requeridos
        $('form[name=' + options.nform + '] :input').filter('.requerido').each(
                function() {


                    if ($(this).attr('type') == 'radio') { // Para Radios

                        $('input[name=' + $(this).attr('name') + ']:radio').each(
                                function() {
                                    if (this.checked || $(this).attr('checked')) {
                                        ch = true;
                                        return false;
                                    }
                                }
                        );

                        if (!ch) {
                            anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqMsg));
                        }


                    } else if ($(this).attr('type') == 'select') { // Para select
                        var ch = false;

                        if ($('select[name=' + $(this).attr('name') + '] option:selected').val() != '' && $('select[name=' + $(this).attr('name') + '] option:selected').val() != null) {
                            if (!doblesEspaciosFiltro.test($('select[name=' + $(this).attr('name') + '] option:selected').val())) {
                                ch = true;
                            } else {
                                anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.dobleEspacioMsg));
                            }
                        }

                        if (!ch) {
                            anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqMsg));
                        }

                    } else if ($(this).attr('type') == 'checkbox') { // Para checkbox
                        var ch = false;
                        $('input[name=' + $(this).attr('name') + ']:checkbox').each(
                                function() {
                                    if (this.checked) {
                                        ch = true;
                                        return false;
                                    }
                                }
                        );

                        if (!ch) {
                            anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqMsg));
                        }
                    } else { // Para todo lo demas...				
                        if ($(this).val() == '' || $(this).val() == null) {
                            anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqMsg));
                        }
                    }
                }
        );

        // Para cuando solamente son requeridos los numeros
        $('form[name=' + options.nform + '] :input').filter('.req-numero').each(
                function() {
                    if ($(this).val() != '' && $(this).val() != null) { // Reviso que no son nulos
                        if (!numeroFiltro.test($(this).val())) {  // Reviso que son numericos
                            anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqNumMsg));
                        }
                    } else {
                        anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqMsg));
                    }
                }
        );

        // Para cuando son texto
        $('form[name=' + options.nform + '] :input').filter('.req-texto').each(
                function() {
                    if ($(this).val() != '' && $(this).val() != null) { // Reviso que no sean nulos
                        if (!doblesEspaciosFiltro.test($(this).val())) {    // Reviso que no haya dobles espacio
                            if (!textoFiltro.test($(this).val())) {          // Reviso que sea texto
                                anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqAlMsg));
                            }
                        } else {
                            anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.dobleEspacioMsg));
                        }
                    } else {
                        anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqMsg));
                    }
                }
        );

        // Para cuando son texto
        $('form[name=' + options.nform + '] :input').filter('.req-letra').each(
                function() {
                    if ($(this).val() != '' && $(this).val() != null) { // Reviso que no sean nulos
                        if (!doblesEspaciosFiltro.test($(this).val())) {    // Reviso que no haya dobles espacio
                            if (!letrasFiltro.test($(this).val())) {          // Reviso que sea texto
                                anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqAlMsg));
                            }
                        } else {
                            anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.dobleEspacioMsg));
                        }
                    } else {
                        anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqMsg));
                    }
                }
        );


        // Para cuando es email
        $('form[name=' + options.nform + '] :input').filter('.req-email').each(
                function() {
                    if ($(this).val() != '' && $(this).val() != null) {  // reviso que no sean nulos
                        if (!mailFiltro.test($(this).val())) {             // Reviso que sea email
                            anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqEmailMsg));
                        }
                    } else {
                        anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqMsg));
                    }
                }
        );

        $('form[name=' + options.nform + '] :input').filter('.nreq-email').each(
                function() {
                    if ($(this).val() != '' && $(this).val() != null) {  // reviso que no sean nulos
                        if (!mailFiltro.test($(this).val())) {             // Reviso que sea email
                            anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqEmailMsg));
                        }
                    }
                }
        );

        $('form[name=' + options.nform + '] :input').filter('.nreq-numero').each(
                function() {
                    if ($(this).val() != '' && $(this).val() != null) { // Reviso que no son nulos
                        if (!numeroFiltro.test($(this).val())) {  // Reviso que son numericos
                            anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqNumMsg));
                        }
                    }
                }
        );

        // Para cuando son texto
        $('form[name=' + options.nform + '] :input').filter('.nreq-texto').each(
                function() {
                    if ($(this).val() != '' && $(this).val() != null) { // Reviso que no sean nulos
                        if (!doblesEspaciosFiltro.test($(this).val())) {    // Reviso que no haya dobles espacio
                            if (!textoFiltro.test($(this).val())) {          // Reviso que sea texto
                                anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqAlMsg));
                            }
                        } else {
                            anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.dobleEspacioMsg));
                        }
                    }
                }
        );

        // Para cuando son texto
        $('form[name=' + options.nform + '] :input').filter('.nreq-letra').each(
                function() {
                    if ($(this).val() != '' && $(this).val() != null) { // Reviso que no sean nulos
                        if (!doblesEspaciosFiltro.test($(this).val())) {    // Reviso que no haya dobles espacio
                            if (!letrasFiltro.test($(this).val())) {          // Reviso que sea texto
                                anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.reqAlMsg));
                            }
                        } else {
                            anError.push(new msgError($(this).attr('name'), $.validaFormulario.defaults.dobleEspacioMsg));
                        }
                    }
                }
        );

        if (anError.length > 0) {
            hayErrores = true;
            $.validaFormulario.despliegaErrores(anError);
        }

        return hayErrores;
    };

    // Despliega los errores que ocurrieron
    $.validaFormulario.despliegaErrores = function(errors) {
        var mCampo = '';
        for (var i = 0; i < errors.length; i++) {
            if ((mCampo == '') || (mCampo != (errors[i]).campo)) {
                $('#ca-' + (errors[i]).campo).addClass('msgError').show('slow');
                $('#msg-' + (errors[i]).campo).append('<p>' + (errors[i]).mensaje + '</p>').show('slow');
                mCampo = (errors[i]).campo;
            }
        }
    };


    $.validaFormulario.limpia = function(options) {
        $('form[name=' + options.nform + '] :input').filter('.requerido').each(
                function() {
                    $('#ca-' + $(this).attr('name')).removeClass('msgError');
                    $('#msg-' + $(this).attr('name')).empty();
                }
        );
        $('form[name=' + options.nform + '] :input').filter('.req-email').each(
                function() {
                    $('#ca-' + $(this).attr('name')).removeClass('msgError');
                    $('#msg-' + $(this).attr('name')).empty();
                }
        );
        $('form[name=' + options.nform + '] :input').filter('.req-numero').each(
                function() {
                    $('#ca-' + $(this).attr('name')).removeClass('msgError');
                    $('#msg-' + $(this).attr('name')).empty();
                }
        );
        $('form[name=' + options.nform + '] :input').filter('.req-texto').each(
                function() {
                    $('#ca-' + $(this).attr('name')).removeClass('msgError');
                    $('#msg-' + $(this).attr('name')).empty();
                }
        );
        $('form[name=' + options.nform + '] :input').filter('.req-letra').each(
                function() {
                    $('#ca-' + $(this).attr('name')).removeClass('msgError');
                    $('#msg-' + $(this).attr('name')).empty();
                }
        );
    };

    $.validaFormulario.revisaTamanio = function(oper) {
        var cError = new Array();
        var hayErrores = false;

        $('form[name=' + oper.nform + '] :input').filter('.tama').each(
                function() {

                    if ($(this).attr('name') == oper.nombre) {
                        var valor = new String($(this).val());
                        var size = valor.length;

                        if (oper.compara == 'eq') {
                            if (size != oper.tamanio) {
                                cError.push(new msgError($(this).attr('name'), 'El n&uacute;mero de carateres es ' + size + ' y debe ser igual a ' + oper.tamanio));
                            }
                        }

                        if (oper.compara == 'gte') {
                            if (size < oper.tamanio) {
                                cError.push(new msgError($(this).attr('name'), 'El n&uacute;mero de caracteres es ' + size + ' y debe ser mayor o igual a ' + oper.tamanio));
                            }
                        }

                        if (oper.compara == 'gt') {
                            if (size <= oper.tamanio) {
                                cError.push(new msgError($(this).attr('name'), 'El n&uacute;mero de caracteres es ' + size + ' y debe ser mayor a ' + oper.tamanio));
                            }
                        }

                        if (oper.compara == 'lte') {
                            if (size > oper.tamanio) {
                                cError.push(new msgError($(this).attr('name'), 'El n&uacute;mero de caracteres es ' + size + ' y debe ser menor o igual a ' + oper.tamanio));
                            }
                        }

                        if (oper.comapara == 'lt') {
                            if (size >= oper.tamanio) {
                                cError.push(new msgError($(this).attr('name'), 'El n&acute;mero de caracteres es ' + size + ' y debe ser menor a ' + oper.tamanio));
                            }
                        }
                        if (cError.length > 0) {
                            hayErrores = true;
                            $.validaFormulario.despliegaErrores(cError);
                        }
                    }
                }
        );

        return hayErrores;
    };

    $.validaFormulario.defaults = {
        reqMsg: 'Este campo es requerido',
        reqAlMsg: 'Este campo solo acepta letras',
        reqNumMsg: 'Este campo solo acepta numeros',
        reqEmailMsg: 'Este campo no parece ser un E-mail...',
        dobleEspacioMsg: 'Este campo tiene doble espacio...'

    };

})(jQuery);




function codificaCuerpo(oform) {
    var sParam = "";

    for (var i = 0; i < oform.elements.length; i++) {

        var ocamp = oform.elements[i];
        var valor = "";
        var nombre = "";

        if (ocamp.type == "hidden") {
            valor = ocamp.value;
            nombre = ocamp.name;
        } else if (ocamp.type == "text") {
            valor = ocamp.value;
            nombre = ocamp.name;
        } else if (ocamp.type == "textarea") {
            valor = ocamp.value;
            nombre = ocamp.name;
        } else if (ocamp.type == "radio") {
            var oRadios = document.getElementsByName(ocamp.name);
            for (var j = 0; j < oRadios.length; j++) {
                if ((oRadios[j]).checked === true) {
                    valor = oRadios[j].value;
                    nombre = oRadios[j].name;
                    break;
                }
            }
        } else if (ocamp.type == "checkbox") {
            var oChecks = document.getElementsByName(ocamp.name);
            for (var z = 0; z < oChecks.length; z++) {
                if ((oChecks[z]).checked === true) {
                    valor = oChecks[z].value;
                    nombre = oChecks[z].name;
                    break;
                }
            }
        } else if (ocamp.type == "select-one") {
            valor = ocamp.options[ocamp.selectedIndex].value;
            nombre = ocamp.name;
        } else if (ocamp.type == "password") {
            valor = ocamp.value;
            nombre = ocamp.name;
        }

        if (valor != "") {
            sParam += encodeURI(nombre);
            sParam += "=";
            sParam += encodeURI(valor) + "&";
        }
    }

    return sParam;
}
