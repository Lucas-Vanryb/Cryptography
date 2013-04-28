package codage;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import message.IMessage;
import message.Message;


import fabrique.Fabrique;

/**
 * Le classe Beale permet d'utiliser le chiffre de Beale
 * Le cryptage et le décryptage sont implémentés mais pas la cryptanalyse
 * @author Lucas VANRYB
 *
 */
public class Beale extends Code implements ICode {

	/**
	 * Constante determinant la longueur de la clé generee
	 */
	private static final int LONGUEUR_CLE = 4000;

	/*
	 * ATTRIBUTS
	 */

	/**
	 * Tableau dont chaque valeur correspond à l'ArrayList contenant tous les entiers associés
	 * à une lettre
	 */
	private ArrayList<Integer>[] f;

	@SuppressWarnings("unchecked")

	/*
	 * CONSTRUCTEUR
	 */
	/**
	 * Constructeur sans paramètre. Initialise les attributs
	 */
	public Beale() {
		super();
		this.f=new ArrayList[26];
		for(int i=0;i<26;i++) {
			f[i]=new ArrayList<Integer>();
		}
	}

	/*
	 * SERVICES
	 */

	/**
	 * Méthode permettant de crypter un message en suivant la méthode du chiffre de Beale 
	 * @param clair le message clair à crypter
	 * @param key la clé de crpytage(un texte contenant au moins un mot commencant
	 * par chacune des lettres de l'alphabet
	 * @return le IMessage contenant la version cryptée de clair
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * La méthode appele remplirArray afin de lire la clé,
		 * puis adapte le message et enfin l'encode caractère par caractère
		 * via l'appel à encoderChar
		 */
		try {
			long d=new Date().getTime();
			this.remplirArray(key);
			IMessage cc=this.adapter(clair);
			String s="";
			for(int i=0;i<cc.taille();i++) {
				s+=this.encoder(cc.getChar(i));
				if(i!=cc.taille()-1) {
					s+=" ";
				}
			}
			this.time=new Date().getTime()-d;
			return Fabrique.fabriquerMessage(s);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Ce message n'a pas été codé à l'aide de ce type de code", "Erreur", JOptionPane.ERROR_MESSAGE);
			return Fabrique.fabriquerMessage("Erreur");
		}
	}

	/*
	 * Méthode permettant d'adapter un message avant le chiffrement,
	 * supprime tous les caractères ne correspondant pas à une lettre,
	 * et met tout le texte en majuscule
	 */
	private IMessage adapter(IMessage m) {
		char[] c=new char[m.taille()];
		int r=0;
		for(int i=0;i<m.taille();i++) {
			if(m.getChar(i)>=65 && m.getChar(i)<=90) {
				c[r]=m.getChar(i);
				r++;
			}
			if(m.getChar(i)>=97 && m.getChar(i)<=122) {
				c[r]=(char)(m.getChar(i)-32);
				r++;
			}
		}
		char[] aRetourner=new char[r];
		for(int i=0;i<r;i++) {
			aRetourner[i]=c[i];
		}
		return Fabrique.fabriquerMessage(aRetourner);
	}

	/*
	 * Encode un caractère en suivant la méthode de Beale par l'utilisation
	 * du tableau d'arraylist en memoire
	 */
	private int encoder(char c) {
		return this.f[c-65].get((int)(this.f[c-65].size()*Math.random()));
	}

	/*
	 * Méthode permettant de remplir le tableau d'arraylist f en mémoire
	 * via lecture de la clé key passée en paramètre.
	 * Elle considère comme mot toute suite de lettre encadré par un espace et/ou un apostrophe
	 * et un autre espace et/ou apostrophe
	 * La méthode compte les mots tout en lisant leur première lettre, et associe entier et let lettre dans
	 * le tableau f en mémoire
	 */
	private void remplirArray(String key) {
		boolean rdy=true;
		int k=1;
		for(int i=0;i<key.length();i++) {
			if(key.charAt(i)==' ' || key.charAt(i)==39) {
				rdy=true;
			}
			else {
				if(rdy) {
					if(key.charAt(i)>=65 && key.charAt(i)<=90) {
						this.f[key.charAt(i)-65].add(k);
						k++;
						rdy=false;
					}
					else {
						if(key.charAt(i)>=97 && key.charAt(i)<=122) {
							this.f[key.charAt(i)-97].add(k);
							k++;
							rdy=false;
						}
					}
				}
			}
		}
	}

	/*
	 * Décode un caractère à partir d'un entier en utilisant la méthode du chiffre de Beale et le tableau
	 * en mémoire
	 */
	private char decoder(int e) {
		char aRetourner;
		int z=0;
		int i=0;
		boolean trouve=false;
		while(i<26 && !trouve) {
			if(this.f[i].contains(e)) {
				z=i;
				trouve=true;
			}
			i++;
		}
		aRetourner=(char)(z+97);
		return aRetourner;
	}

	/**
	 * Méthode permettant de décrypter un message crypte à l'aide du chiffre de Beale
	 * @param crypte le message à decrypter
	 * @param key la clé utilisée pour crypter le message crypte
	 * @return le message crypte decrypte
	 */
	public IMessage decrypter(IMessage crypte, String key) {
		/*
		 * Cette méthode lit d'abord la clé à l'aide de remplirArray, puis
		 * décode les caractères un à un via des appels repetes à encoderChar
		 */
		long d=new Date().getTime();
		this.remplirArray(key);
		char[] c=new char[crypte.taille()];
		int v=-1;
		int y=-1;
		int r=0;
		String temp="";
		for(int i=0;i<crypte.taille();i++) {
			if(crypte.getChar(i)==' ') {
				v=y;
				y=i;
				temp="";
				for(int q=(v+1);q<y;q++) {
					temp+=crypte.getChar(q);
				}
				c[r]=this.decoder(Integer.parseInt(temp));
				r++;
			}
		}
		temp="";
		for(int q=(y+1);q<crypte.taille();q++) {
			temp+=crypte.getChar(q);
		}
		c[r]=this.decoder(Integer.parseInt(temp));

		char[] aRetourner=new char[r+1];
		for(int i=0;i<=r;i++) {
			aRetourner[i]=c[i];
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(aRetourner);
	}

	/**
	 * Méthode permettant de generer une cle pour le chiffre de Beale.
	 * Plutot que de generer des mots, cette dernière genere une suite de lettres majuscules
	 * séparées par des espaces
	 * @return une clef générée aléatoirement utilisable pour le chiffre de Beale
	 */
	public String genererCle() {
		String aRetourner="";
		for(int i=0;i<LONGUEUR_CLE;i++) {
			aRetourner+=(char)(65+(int)(Math.random()*26));
			aRetourner+=' ';
		}
		if(this.estCleValable(aRetourner)) {
			return aRetourner;
		}
		else {
			return this.genererCle();
		}
	}

	/**
	 * Méthode permettant de vérifier si une clé est correcte pour l'usage du chiffre de Beale,
	 * c'est à dire si le texte contient bien au moins un mot commencant par chacune des lettres de l'alphabet
	 * et affichant une erreur si ce n'est pas le cas
	 * @param k la clé dont on vérifie la conformité
	 * @return true si la clé est consideree comme valable, false sinon
	 */
	public boolean estCleValable(String k) {
		/*
		 * La méthode vérifie que la clef est non nul est de longueur au moins égale à 26
		 * Puis si c'est le cas, la méthode verifie que le texte contienne au moins un mot commencant
		 * par chacune des lettres de l'alphabet
		 */
		if(k.equals("")) {
			JOptionPane.showMessageDialog(null, "Veuillez saisir une clé non vide", "Clé incorrecte", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else {
			if(k.length()<26) {
				JOptionPane.showMessageDialog(null, "Veuillez saisir une clé texte contenant au moins un mot commencant par chacune des lettres de l'alphabet.", "Clé incorrecte", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			else {
				this.remplirArray(k);
				int i=0;
				boolean trouve=false;
				while(i<26 && !trouve) {
					if(this.f[i].size()==0) {
						trouve=true;
					}
					i++;
				}
				if(trouve) {
					JOptionPane.showMessageDialog(null, "Veuillez saisir une clé texte contenant au moins un mot commencant par chacune des lettres de l'alphabet.", "Clé incorrecte", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				else {
					return true;
				}
			}
		}
	}

	/**
	 * Méthode main permettant de tester la classe
	 * @param args les arguments transmis aux programmes(inutiles)
	 */
	public static void main(String[] args) {
		IMessage m=new Beale().crypter(new Message("J'aime les animaux de compagnie"), "La petite taille de l'entreprise renforce considérablement le rôle du dirigeant et c'est un lieu commun de dire que le patron de PME, souvent majoritaire, doit faire face à une multitude de tâches, rassembler des compétences qui, dans les grandes structures, sont confiées à des spécialistes. Mais si, de ce fait, le dirigeant de PME entretien une relation particulière avec le pouvoir, de multiples facteurs concourent à une grande diversité de  profils de patron  : son origine (créateur, acheteur, héritier), sa formation (autodidacte, diplômé), ses motivations et ses ambitions l'argent, la liberté, le pouvoir, ses méthodes de travail et son style de management, la nature des relations qu'il entretient ou qu'il suppose -et fait supporter à ses proches- entre sa vie privée et sa vie professionnelle, tout doit être pris en compte. Il était donc passionnant de consacrer aux patrons de PME un ouvrage qui soit tout autant un ambitieux  portraits de groupe  qu'une source documentée et précise d'informations sur tel ou tel aspect de cette catégorie  à part  mais qui rassemble la majorité des dirigeants d'entreprises en France. Présenté par le CEPME, ce livre analyse l'étonnant foisonnement de questions et de situations particulières liées à ces dirigeants : leurs spécificités dans le contexte historique et culturel du patronat français, l'image caractéristique et ambivalente du patron, à la fois homme de l'intérieur qui règle les problèmes et homme à l'extérieur qui promeut son entreprise et joue un rôle social souvent déterminant, leurs attentes et leurs perspectives, leurs visions de l'avenir. Cette enquête s'achève par une synthèse de ces informations et par l'élaboration d'une typologie originale et inédite des grandes  catégories  de dirigeants de PME. Xoxo");
		IMessage m2=new Beale().decrypter(m, "La petite taille de l'entreprise renforce considérablement le rôle du dirigeant et c'est un lieu commun de dire que le patron de PME, souvent majoritaire, doit faire face à une multitude de tâches, rassembler des compétences qui, dans les grandes structures, sont confiées à des spécialistes. Mais si, de ce fait, le dirigeant de PME entretien une relation particulière avec le pouvoir, de multiples facteurs concourent à une grande diversité de  profils de patron  : son origine (créateur, acheteur, héritier), sa formation (autodidacte, diplômé), ses motivations et ses ambitions l'argent, la liberté, le pouvoir, ses méthodes de travail et son style de management, la nature des relations qu'il entretient ou qu'il suppose -et fait supporter à ses proches- entre sa vie privée et sa vie professionnelle, tout doit être pris en compte. Il était donc passionnant de consacrer aux patrons de PME un ouvrage qui soit tout autant un ambitieux  portraits de groupe  qu'une source documentée et précise d'informations sur tel ou tel aspect de cette catégorie  à part  mais qui rassemble la majorité des dirigeants d'entreprises en France. Présenté par le CEPME, ce livre analyse l'étonnant foisonnement de questions et de situations particulières liées à ces dirigeants : leurs spécificités dans le contexte historique et culturel du patronat français, l'image caractéristique et ambivalente du patron, à la fois homme de l'intérieur qui règle les problèmes et homme à l'extérieur qui promeut son entreprise et joue un rôle social souvent déterminant, leurs attentes et leurs perspectives, leurs visions de l'avenir. Cette enquête s'achève par une synthèse de ces informations et par l'élaboration d'une typologie originale et inédite des grandes  catégories  de dirigeants de PME. Xoxo");
		System.out.println(m);
		System.out.println(m2);
		System.out.println(new Beale().genererCle());
	}
}
