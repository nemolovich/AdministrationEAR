/**
 * Inspecte le formulaire contenu dans un <p:dialog> pour
 * vérifier s'il est correct.
 * @param {c} form - La boite de dialogue contenant le formulaire
 * @param {Object} xhr - HttpRequest 
 * @param {Object} status - Etat du formulaire
 * @param {string} args - Retour du formulaire
 * @returns {Boolean} - vrai si le formulaire est correcte
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
 * @param {int} secondes - le temps d'attente avant rafraichissement
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
 * @param {string} id - Identifiant du layout
 * @param {string} tips - Nom à afficher en tooltip
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
 * Dfinie le titre des éléments d'action des layouts
 * @param {string} id - Identifiant du layout
 * @param {string} tips - Nom à afficher en tooltip
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