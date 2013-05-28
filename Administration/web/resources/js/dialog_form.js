/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function createRequest(form, xhr, status, args)
{
    if(args.validationFailed)
    {
        form.jq.effect("shake", { times:2 }, 100);
    }
    else
    {
        form.hide();
    }
}