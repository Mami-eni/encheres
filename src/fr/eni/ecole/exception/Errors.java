package fr.eni.ecole.exception;

/**
 * Les codes disponibles sont entre 20000 et 29999
 */
public class Errors {
	
	/**
	 * Echec lié à la tentative de selection d'une liste d'enchères en base de donnée
	 */
	public static final String SELECT_ENCHERE_NULL="Une erreur est survenue lors de la lecture d'une liste d'encheres.";
	
	/**
	 * Echec lié à la tentative de selection d'une liste d'objets liée à la recherche par filtre 
	 */
	
	public static final String SELECT_FILTRE_NULL="Une erreur est survenue lors de votre recherche.";
	
	
	/**
	 * Echec li� au changement de l'utilisateur
	 */
	public static final String CODE_POSTAL_INVALIDE = "Code postal invalide";
	public static final String NOMBRE_TELEPHONE_INVALIDE = "Nombre de t�l�phone invalide";
	public static final String PSEUDO_INSUFFISANT = "Pseudo doit contenir au moins 6 caract�res";
	public static final String MOT_DE_PASSE_INVALIDE = "Mot de passe doit contenir entre 8 et 12 caract�res (1 chiffre, 1 majuscule, 1 caract�re sp�cial)";
	public static final String CONFIRMATION_CORRESPONDE_PAS = "Votre mot de passe ne corresponde pas avec la confirmation";

	/**
	 * Echec li� au changement d'un article ou une ench�re
	 */
	public static final String REGLE_CODE_POSTAL = "Erreur: le code postal est incorrect";
	   
    public static final String REGLE_ARTICLE = "Article contient 30 caract�res maximum";
    public static final String REGLE_DESCRIPTION = "Description contient 300 caract�res maximum";
    public static final String REGLE_DATE = "la date ne peut �tre ant�rieure � la date du jour";
    public static final String REGLE_DATE_MAX = "la date doit �tre comprise entre 2021 et 2029";
    public static final String MONTANT_ENTIER = "Le montant doit �tre un entier";
	
}
