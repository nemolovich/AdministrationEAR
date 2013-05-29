/**
 * Inspecte le formulaire contenu dans un <p:dialog> pour
 * vérifier s'il est correct.
 * @param {c} form, La boite de dialogue contenant le formulaire
 * @param {Object} xhr, 
 * @param {Object} status, Etat du formulaire
 * @param {string} args, Retour du formulaire
 * @returns {Boolean}, vrai si le formulaire est correcte
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
 * Recharge la page après le nombre de secondes indiquées
 * @param {int} secondes, le temps d'attente avant rafraichissement
 * @returns {void}
 */
function reloadInterval(secondes)
{
    setTimeout('location.reload(true);',1000*secondes);
}