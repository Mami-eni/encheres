package fr.eni.ecole.exception;

/**
 * Les codes disponibles sont entre 20000 et 29999
 */
public class Errors {
	
	/**
	 * Echec général quand tentative d'ajouter un objet null
	 */
	public static final String SELECT_ENCHERE_NULL="Une erreur est survenue à la lecture d'une liste d'encheres.";
	
	/**
	 * Echec lié au changement de l'utilisateur
	 */
	public static final String CODE_POSTAL_INVALIDE = "Code postal invalide";
	public static final String NOMBRE_TELEPHONE_INVALIDE = "Nombre de téléphone invalide";
	public static final String PSEUDO_INSUFFISANT = "Pseudo doit contenir au moins 6 caractères";
	public static final String MOT_DE_PASSE_INVALIDE = "Mot de passe doit contenir entre 8 et 12 caractères (1 chiffre, 1 majuscule, 1 caractère spécial)";
	public static final String CONFIRMATION_CORRESPONDE_PAS = "Votre mot de passe ne corresponde pas avec la confirmation";

	/**
	 * Echec lié au changement d'un article ou une enchère
	 */
	public static final String REGLE_CODE_POSTAL = "Erreur: le code postal est incorrect";
	   
    public static final String REGLE_ARTICLE = "Article contient 30 caractères maximum";
    public static final String REGLE_DESCRIPTION = "Description contient 300 caractères maximum";
    public static final String REGLE_DATE = "la date ne peut être antérieure à la date du jour";
    public static final String REGLE_DATE_MAX = "la date doit être comprise entre 2021 et 2029";
    public static final String MONTANT_ENTIER = "Le montant doit être un entier";
	
}
