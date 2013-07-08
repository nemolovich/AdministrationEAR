/**
 * Nom de l'application (notamment pour l'URL de navigation)
 * @type String
 */
var appName='Administration';
/**
 * Définir à 'true' pour afficher les détails
 * @type Boolean
 */
var debug=false;

/**
 * Inspecte le formulaire contenu dans un <p:dialog> pour
 * vérifier s'il est correct.
 * @param {c} form - La boite de dialogue contenant le formulaire
 * @param {Object} xhr - HttpRequest 
 * @param {Object} status - Etat du formulaire
 * @param {String} args - Retour du formulaire
 * @param {Boolean} hide - Indique si on ferme le <p:dialog>
 * @returns {Boolean} - Vrai si le formulaire est correcte
 */
function createRequestFun(form, xhr, status, args, hide)
{
    if(args.validationFailed)
    {
        form.jq.effect("shake", { times:2 }, 100);
        return false;
    }
    else
    {
        if(hide===true)
        {
            form.hide();
        }
        return true;
    }
}
/**
 * @see @link{createRequestFun}
 * @param {c} form - La boite de dialogue contenant le formulaire
 * @param {Object} xhr - HttpRequest 
 * @param {Object} status - Etat du formulaire
 * @param {String} args - Retour du formulaire
 * @returns {Boolean} - Vrai si le formulaire est correcte
 */
function createRequest(form, xhr, status, args)
{
    return createRequestFun(form, xhr, status, args, true);
}

/**
 * @see @link{createRequestFun}
 * De même que la fonction précédente mais ne ferme pas
 * le <p:dialog> après validation du formulaire.
 * @param {c} form - La boite de dialogue contenant le formulaire
 * @param {Object} xhr - HttpRequest 
 * @param {Object} status - Etat du formulaire
 * @param {String} args - Retour du formulaire
 * @returns {Boolean} - Vrai si le formulaire est correcte
 */
function createRequestVoid(form, xhr, status, args)
{
    return createRequestFun(form, xhr, status, args, false);
}

/**
 * Permet de récupérer les évènements venant du clavier via une
 * balise <p:ajax> sur un attribut 'oncomplete' et d'en modifier
 * un objet donné
 * @param {c} item - L'objet à faire intéragir
 * @param {keyboardEvents} event - L'évènement à récupérer
 * @returns {void}
 */
function handleEvent(item, event)
{
    /**
     * Si la touche est 'ESC'
     */
    if(event.keyCode===27)
    {
        item.hide();
    }
}

/**
 * Recharge la page après le nombre de secondes indiquées
 * @param {Number} secondes - Le temps d'attente avant rafraichissement
 * @returns {void}
 */
function reloadInterval(secondes)
{
//    setTimeout('location.reload(true);',1000*secondes);
    setTimeout('this.document.location.href=window.location.href',1000*secondes);
    
}

/**
 * Permet de donner un nom aux données contenues dans un layout (<p:layoutUnit>)
 * pour les titre des leurs boutons d'action
 * @param {String} id - Identifiant du layout
 * @param {String} tips - Nom à afficher en tooltip
 * @returns {void}
 */
function setLayoutButtonsTooltips(id,tips)
{
    $(document).ready(function()
    {
        setTooltips(id,tips);
    });
    $("#"+id+" .ui-layout-unit-header-icon.ui-state-default.ui-corner-all").click(function()
    {
        setTooltips(id,tips);
    });
    $("#"+id+"-resizer").hover(function()
    {
        setTooltips(id,tips);
    });
}

/**
 * Définie le titre des éléments d'action des layouts
 * @param {String} id - Identifiant du layout
 * @param {String} tips - Nom à afficher en tooltip
 * @returns {void}
 */
function setTooltips(id,tips)
{
    $("#"+id+"-resizer").attr('title',"Redimensionner "+tips);
    $("#"+id+"-resizer.ui-layout-resizer-closed").attr('title',"");
    $("#"+id+"-toggler").attr('title',"Afficher "+tips);
    $("#"+id+" .ui-layout-unit-header-icon.ui-state-default.ui-corner-all:not(#"+id+"_expand)").attr('title',"Masquer "+tips);
    $("#"+id+" .ui-layout-unit-header-icon.ui-state-default.ui-corner-all .ui-icon-close").attr('title',"Fermer "+tips);
}

/**
 * Remplace la valeur d'un champs dans un formulaire
 * par la valeur donnée
 * @param {String} form - Identifiant du formulaire
 * @param {String} id - Identifiant du champs
 * @param {String} value - Valeur à afficher
 * @returns {void}
 */
function setFormFieldValue(form,id,value)
{
    $("#"+form+"\\:"+id).html(value);
}

/**
 * Variable de Timer
 * @type Number
 */
var counter=5;

/**
 * Remonte le timer pour le temps donné
 * @param {Number} time - Temps en secondes
 * @returns {void}
 */
function setTimer(time)
{
    counter=time;
}

/**
 * Met à jour le timer en le décrémentant de 1 seconde
 * @param {String} form - Identifiant du formulaire
 * @param {String} id - Identifiant du champs
 * @returns {void}
 */
function updateTimer(form,id)
{
    setFormFieldValue(form,id,--counter);
    if(counter<=0)
    {
        window.location='/'+appName;
    }
}

/**
 * Sélectionne une ligne dans une <p:DataTable> multiSelectable
 * @param {c} table - La table des données
 * @param {Number} index - Index de sélection sur la vue
 * @param {Number} maxRows - Le nombre de lignes par vue
 * @returns {void}
 */
function selectLine(table, index, maxRows)
{
    table.unselectAllRows();
    table.selectRow((index-table.getPaginator().getCurrentPage()*maxRows));
}

//function setOptionSelected(selectId,value)
//{
//    $('#'+selectId+' option:selected').removeAttr("selected");
//    $('#'+selectId+' option[value='+value+']').attr("selected","selected");
//}

/**
 * Renvoi la largeur du navigateur
 * @returns {Number} - Largeur du navigateur
 */
function getNavigatorWidth()
{
    winW=700;
    if (document.body && document.body.offsetWidth)
    {
        winW = document.body.offsetWidth;
    }
    return winW;
}

/**
 * Renvoi la hauteur du navigateur
 * @returns {Number} - Hauteur du navigateur
 */
function getNavigatorHeight()
{
    winH=500;
    if (document.body && document.body.offsetWidth)
    {
        winH = document.body.offsetHeight;
    }
    return winH;
}

/**
 * Permet de forcer le filtre d'une <p:DataTable> en
 * possédant
 * @param {c} filter - Filtre à forcer
 * @returns {void}
 */
function forceFilter(filter)
{
    if(filter.filter()===undefined)
    {
        if(debug===true)
        {
            console.log('Force filter for table with id="'+filter.jqId.replace(/\\/,'')+'"');
        }
        filter.clearFilters();
        filter.filter();
    }
}

/**
 * Ajoute un bouton pour ouvrir une <p:dialog> dans un
 * header de menu
 * @param {String} id - Identifiant du menu
 * @param {String} widgetName - Nom du widget <p:dialog>
 * @returns {void}
 */
function addExpandableButton(id, widgetName)
{
    var panel=$("#"+id);
    var parent=panel.find("div.ui-layout-unit-header.ui-widget-header.ui-corner-all");
    var link=document.createElement("a");
    link.className="ui-layout-unit-header-icon-copy ui-state-default ui-corner-all";
    link.id=id+"_expand";
    link.href="javascript:"+widgetName+".show();";
    link.title="Agrandir";
    var span=document.createElement("span");
    span.className="ui-icon ui-icon-extlink";
    link.appendChild(span);
    parent.append(link);
}

/**
 * Ajoute une petite etoile rouge après le label d'un champs
 * pour indiquer qu'il est requis. S'applique sur tous les
 * éléments de la classe 'requiered'.
 * @returns {void}
 */
function addRedStarsToRequieredFields()
{
    var span=document.createElement("span");
    span.innerHTML="*";
    span.className="red-star";
    $(".requiered").append(span);
}

/**
 * Remet à zéro un formulaire
 * @param {String} formId - Identifiant du formaulaire
 * @returns {void}
 */
function resetForm(formId)
{
    $('#'+formId).each(function()
    {
        this.reset();
    });
}

var concurrents=[];

function displayBlock(id)
{
    var ok=true;
    if(containsElement(concurrents,id))
    {
        $.each(concurrents,function(k,v)
        {
            if(id===v[0])
            {
                if(!hideBlock(v[1]))
                {
                    ok=false;
                }
            }
            else if(id===v[1])
            {
                if(!hideBlock(v[0]))
                {
                    ok=false;
                }
            }
        });
    }
    if(ok)
    {
        $("#"+id).css("display","block");
        return true;
    }
    return false;
}

function hideBlock(id)
{
    $("#"+id).css("display","none");
    return true;
}

function addConcurrentBlock(id,listId)
{
    var temp=[];
    $.each(listId,function(i,v)
    {
        temp[temp.length]=v;
        if(!contains(concurrents,[id,v]))
        {
            concurrents[concurrents.length]=[id,v];
        }
    });
    var index=0;
    $.each(temp,function(i,v)
    {
        for(var i=index;i<=temp.length/2;i++)
        {
            if(!contains(concurrents,[temp[i],v])&&temp[i]!==v)
            {
                concurrents[concurrents.length]=[temp[i],v];
            }
        }
        index++;
    });
}

function indexOf(arr,pair)
{
    var index=-1;
    $.each(arr,function(k,v)
    {
        if((pair[0]===v[0]&&pair[1]===v[1])||
            (pair[1]===v[0]&&pair[0]===v[1]))
        {
            index=k;
            return false;
        }
    });
    return index;
}

function containsElement(arr,e)
{
    var contains=false;
    $.each(arr,function(k,v)
    {
        if(e===v[0]||e===v[1])
        {
            contains=true;
            return false;
        }
    });
    return contains;
}

function contains(arr,pair)
{
    return (indexOf(arr,pair)!==-1);
}

function getAt(arr,index)
{
    var value=null;
    $.each(arr,function(k,v)
    {
        if(k===index)
        {
            value=v;
            return false;
        }
    });
    return value;
}

/**
 * Permet de charger les préférences de langue pour les calendriers
 * primefaces <p:calendar>
 * @returns {void}
 */
function loadLocales()
{
    PrimeFaces.locales['fr'] =
    {
        closeText: 'Fermer',
        prevText: 'Précédent',
        nextText: 'Suivant',
        monthNames: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre' ],
        monthNamesShort: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun', 'Jul', 'Aoû', 'Sep', 'Oct', 'Nov', 'Déc' ],
        dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
        dayNamesShort: ['Dim', 'Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam'],
        dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
        weekHeader: 'Semaine',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix:'',
        timeOnlyTitle: 'Choisir une durée',
        timeText: 'Heure',
        hourText: 'Heures',
        minuteText: 'Minutes',
        secondText: 'Secondes',
        currentText: 'Maintenant',
        ampm: false,
        month: 'Mois',
        week: 'Semaine',
        day: 'Jour',
        allDayText: 'Toute la journée'
    };
}

/**!
 * jQuery insertAtIndex
 * project-site: https://github.com/oberlinkwebdev/jQuery.insertAtIndex
 * @author: Jesse Oberlin
 * @version 1.0
 * @param {JQueryElement} $
 * @returns {Boolean}
 * Copyright 2012, Jesse Oberlin
 * Dual licensed under the MIT or GPL Version 2 licenses.
*/
(function ($) { 
$.fn.insertAtIndex = function(index,selector){
    var opts = $.extend({
        index: 0,
        selector: '<div/>'
    }, {index: index, selector: selector});
    return this.each(function() {
        var p = $(this);  
        var i = ($.isNumeric(opts.index) ? parseInt(opts.index) : 0);
        if(i <= 0)
            p.prepend(opts.selector);
        else if( i > p.children().length-1 )
            p.append(opts.selector);
        else
            p.children().eq(i).before(opts.selector);       
    });
};  
})( jQuery );


