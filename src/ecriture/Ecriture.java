package ecriture;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import codage.ICode;
import codage.ICodeClePublique;
import codage.IMessageCode;
import codage.Interface;

import message.IMessage;

/**
 * Classe permettant d'ecrire dans un fichier texte
 * @see ecriture.IEcriture
 * @author Lucas VANRYB
 *
 */
public class Ecriture implements IEcriture {

	/*
	 * ATTRIBUT
	 */

	/**
	 * PrintWriter permettant d'ouvri un fichier texte
	 */
	PrintWriter ecrire;

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur qui ouvre et nettoie le fichier texte à ecrire
	 * @param nomFichier le chemin vers le fichier à ecrire
	 */
	public Ecriture(String nomFichier) {
		try {
			/*
			 * On supprime le fichier de nom identique dans le repertoire du programme si il existe, sinon on ne fait rien
			 */
			File fichier=new File(nomFichier);
			fichier.delete();
		}
		catch(Exception e) {
		}
		try { 
			/*
			 * On crée le fichier texte dans lequel on va écrire à partir du nom transmis en paramètre
			 */
			File nouveauFichier = new File(nomFichier);
			nouveauFichier.createNewFile();
			/*
			 * On initialise la méthode qui nous permettra d'écrire dans ce fichier
			 */
			this.ecrire = new PrintWriter(new BufferedWriter(new FileWriter(nomFichier)));
		}
		catch(IOException e) {
		}
	}

	/*
	 * ACCESSEURS
	 */

	public void ecrireMessage(IMessage m) {
		this.EcrireTexte(m.toString());
		this.finaliser();
	}

	public void ecrireCle(IMessageCode m) {
		this.EcrireTexte(Interface.identifierCode(m.getCode()).getNom()+" "+m.getKey());
		this.finaliser();
	}

	public void ecrireClePublique(IMessageCode m) {
		ICode c=m.getCode();
		if(c instanceof ICodeClePublique) {
			this.EcrireTexte(Interface.identifierCode(c).getNom()+" "+((ICodeClePublique)c).getPublicKey()+" ");
			this.finaliser();
		}
		else {
			this.ecrireCle(m);
		}
	}

	/**
	 * Méthode permettant d'écrire la String passée en paramètre dans le fichier texte lié à l'instance d'objet
	 * @param aEcrire la String à écrire dans le fichier texte
	 */
	private void EcrireTexte (String aEcrire){
		this.ecrire.print(aEcrire);  
		this.ecrire.flush();
	}

	/**
	 * Méthode qui ferme le lien entre le fichier texte et l'instance d'objet. Cette méthode est invoquée lorsque l'écriture est terminée.
	 */
	private void finaliser() {
		ecrire.close();
	}

}
