package fr.eni.ecole.bo;

import java.sql.Timestamp;
import java.time.LocalDate;
/**
 * 
 * Classe repr�sente les ench�res
 *
 */
public class Enchere {
	private int numero;
	private int montant;
	private Timestamp date;
	private Utilisateur utilisateur;
	private Article article;
	
	public Enchere() {}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@Override
	public String toString() {
		return "Enchere [numero=" + numero + ", montant=" + montant + ", date=" + date + ", utilisateur=" + utilisateur
				+ ", article=" + article + "]";
	}

	
}
