package fr.eni.ecole.exception;

/**
 * Cette classe contient des constantes qui sont des messages d'erreur
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
	public static final String NUMERO_TELEPHONE_INVALIDE = "Numero de telephone invalide";
	public static final String PSEUDO_INSUFFISANT = "Pseudo doit contenir au moins 6 caracteres";
	public static final String MOT_DE_PASSE_INVALIDE = "Mot de passe doit contenir entre 8 et 12 caracteres (1 chiffre, 1 majuscule, 1 caractere special)";
	public static final String CONFIRMATION_CORRESPONDE_PAS = "Votre mot de passe ne correspond pas aà celui de la confirmation";

	/**
	 * Echec lié au changement d'un article ou une enchere
	 */
	public static final String REGLE_CODE_POSTAL = "Erreur: le code postal est incorrect";
	   
    public static final String REGLE_ARTICLE = "Article contient 30 caracteres maximum";
    public static final String REGLE_DESCRIPTION = "Description contient 300 caracteres maximum";
    public static final String REGLE_DATE = "la date ne peut etre anterieure a la date du jour";
    public static final String REGLE_DATE_MAX = "la date doit etre comprise entre 2021 et 2029";
    public static final String MONTANT_ENTIER = "Le montant doit etre un entier";
    
    /** Echec d'accès à la base de données */
    
    public static final String ERREUR_INSERT = "Ajout dans la base de données impossible";
    public static final String ERREUR_SELECT = "Problème de connection à la base de donnée";
    public static final String ERREUR_DELETE = "Suppression impossible";
    public static final String ERREUR_UPDATE = "Mise à jour impossible";
	
}
