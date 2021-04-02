package fr.eni.ecole.bo;
/**
 * 
 * Classe représente les catégories
 *
 */
public class Categorie {
	private int numero;
	private String libelle;
	
	public Categorie() {}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return "Categorie [numero=" + numero + ", libelle=" + libelle + "]";
	}

	
	
}
