/**
 * 
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
 * @returns {Boolean} - vrai si le formulaire est correcte
 */
function createRequest(form, xhr, status, args)
{
    console.log(status);
    console.log(args);
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
 * @param {c} item - l'objet à faire intéragir
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
 * @param {Number} secondes - le temps d'attente avant rafraichissement
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