/**
 * 
 */

function radioDisable() {
	var checkboxesAchat = document.getElementsByName('flitreCheckboxAchat');
	var checkboxesVente = document.getElementsByName('flitreCheckboxVente');

	if (document.getElementById('radioAchat').checked) {
		for (var i = 0; i < checkboxesAchat.length; i++) {
			checkboxesAchat[i].disabled = false;
			checkboxesVente[i].disabled = true;
		}
	}
	if (document.getElementById('radioVente').checked) {
		for (var i = 0; i < checkboxesAchat.length; i++) {
			checkboxesAchat[i].disabled = true;
			checkboxesVente[i].disabled = false;
		}
	}
}