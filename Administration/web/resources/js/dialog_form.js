/**
 * Nom de l'application (notamment pour l'URL de navigation)
 * @type String
 */
var appName='Administration';

/**
 * Inspecte le formulaire contenu dans un <p:dialog> pour
 * vérifier s'il est correct.
 * @param {c} form - La boite de dialogue contenant le formulaire
 * @param {Object} xhr - HttpRequest 
 * @param {Object} status - Etat du formulaire
 * @param {String} args - Retour du formulaire
 * @returns {Boolean} - Vrai si le formulaire est correcte
 */
function createRequest(form, xhr, status, args)
{
    if(args.validationFailed)
    {
        form.jq.effect("shake", { times:2 }, 100);
        return false;
    }
    else
    {
        form.hide();
        return true;
    }
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
    $("#"+id+" .ui-layout-unit-header-icon.ui-state-default.ui-corner-all").attr('title',"Masquer "+tips);
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
        console.log('Force filter for table with id="'+filter.jqId.replace(/\\/,'')+'"');
        filter.clearFilters();
        filter.filter();
    }
}

function addExpandableButton(id)
{
    console.log("addAppend");
    var panel=$("#"+id);
    var parent=panel.find("div.ui-layout-unit-header.ui-widget-header.ui-corner-all");
    var link=document.createElement("a");
    link.className="ui-layout-unit-header-icon ui-state-default ui-corner-all";
    link.href="javascript:void(0);";
    link.title="Agrandir";
    var span=document.createElement("span");
    span.className="ui-icon ui-icon-extlink";
    link.appendChild(span);
    parent.append(link);
//    '<a href="javascript:void(0)" class="ui-layout-unit-header-icon ui-state-default ui-corner-all" title="Masquer la liste des tâches"><span class="ui-icon ui-icon-triangle-1-e"></span></a>';
}
