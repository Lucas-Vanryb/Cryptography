package codage;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import message.IMessage;
import message.Message;


import fabrique.Fabrique;

/**
 * Le classe Beale permet d'utiliser le chiffre de Beale
 * Le cryptage et le d�cryptage sont impl�ment�s mais pas la cryptanalyse
 * @author Lucas VANRYB
 *
 */
public class Beale extends Code implements ICode {

	/**
	 * Constante determinant la longueur de la cl� generee
	 */
	private static final int LONGUEUR_CLE = 4000;

	/*
	 * ATTRIBUTS
	 */

	/**
	 * Tableau dont chaque valeur correspond � l'ArrayList contenant tous les entiers associ�s
	 * � une lettre
	 */
	private ArrayList<Integer>[] f;

	@SuppressWarnings("unchecked")

	/*
	 * CONSTRUCTEUR
	 */
	/**
	 * Constructeur sans param�tre. Initialise les attributs
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
	 * M�thode permettant de crypter un message en suivant la m�thode du chiffre de Beale 
	 * @param clair le message clair � crypter
	 * @param key la cl� de crpytage(un texte contenant au moins un mot commencant
	 * par chacune des lettres de l'alphabet
	 * @return le IMessage contenant la version crypt�e de clair
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * La m�thode appele remplirArray afin de lire la cl�,
		 * puis adapte le message et enfin l'encode caract�re par caract�re
		 * via l'appel � encoderChar
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
			JOptionPane.showMessageDialog(null, "Ce message n'a pas �t� cod� � l'aide de ce type de code", "Erreur", JOptionPane.ERROR_MESSAGE);
			return Fabrique.fabriquerMessage("Erreur");
		}
	}

	/*
	 * M�thode permettant d'adapter un message avant le chiffrement,
	 * supprime tous les caract�res ne correspondant pas � une lettre,
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
	 * Encode un caract�re en suivant la m�thode de Beale par l'utilisation
	 * du tableau d'arraylist en memoire
	 */
	private int encoder(char c) {
		return this.f[c-65].get((int)(this.f[c-65].size()*Math.random()));
	}

	/*
	 * M�thode permettant de remplir le tableau d'arraylist f en m�moire
	 * via lecture de la cl� key pass�e en param�tre.
	 * Elle consid�re comme mot toute suite de lettre encadr� par un espace et/ou un apostrophe
	 * et un autre espace et/ou apostrophe
	 * La m�thode compte les mots tout en lisant leur premi�re lettre, et associe entier et let lettre dans
	 * le tableau f en m�moire
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
	 * D�code un caract�re � partir d'un entier en utilisant la m�thode du chiffre de Beale et le tableau
	 * en m�moire
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
	 * M�thode permettant de d�crypter un message crypte � l'aide du chiffre de Beale
	 * @param crypte le message � decrypter
	 * @param key la cl� utilis�e pour crypter le message crypte
	 * @return le message crypte decrypte
	 */
	public IMessage decrypter(IMessage crypte, String key) {
		/*
		 * Cette m�thode lit d'abord la cl� � l'aide de remplirArray, puis
		 * d�code les caract�res un � un via des appels repetes � encoderChar
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
	 * M�thode permettant de generer une cle pour le chiffre de Beale.
	 * Plutot que de generer des mots, cette derni�re genere une suite de lettres majuscules
	 * s�par�es par des espaces
	 * @return une clef g�n�r�e al�atoirement utilisable pour le chiffre de Beale
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
	 * M�thode permettant de v�rifier si une cl� est correcte pour l'usage du chiffre de Beale,
	 * c'est � dire si le texte contient bien au moins un mot commencant par chacune des lettres de l'alphabet
	 * et affichant une erreur si ce n'est pas le cas
	 * @param k la cl� dont on v�rifie la conformit�
	 * @return true si la cl� est consideree comme valable, false sinon
	 */
	public boolean estCleValable(String k) {
		/*
		 * La m�thode v�rifie que la clef est non nul est de longueur au moins �gale � 26
		 * Puis si c'est le cas, la m�thode verifie que le texte contienne au moins un mot commencant
		 * par chacune des lettres de l'alphabet
		 */
		if(k.equals("")) {
			JOptionPane.showMessageDialog(null, "Veuillez saisir une cl� non vide", "Cl� incorrecte", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else {
			if(k.length()<26) {
				JOptionPane.showMessageDialog(null, "Veuillez saisir une cl� texte contenant au moins un mot commencant par chacune des lettres de l'alphabet.", "Cl� incorrecte", JOptionPane.ERROR_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "Veuillez saisir une cl� texte contenant au moins un mot commencant par chacune des lettres de l'alphabet.", "Cl� incorrecte", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				else {
					return true;
				}
			}
		}
	}

	/**
	 * M�thode main permettant de tester la classe
	 * @param args les arguments transmis aux programmes(inutiles)
	 */
	public static void main(String[] args) {
		IMessage m=new Beale().crypter(new Message("J'aime les animaux de compagnie"), "La petite taille de l'entreprise renforce consid�rablement le r�le du dirigeant et c'est un lieu commun de dire que le patron de PME, souvent majoritaire, doit faire face � une multitude de t�ches, rassembler des comp�tences qui, dans les grandes structures, sont confi�es � des sp�cialistes. Mais si, de ce fait, le dirigeant de PME entretien une relation particuli�re avec le pouvoir, de multiples facteurs concourent � une grande diversit� de  profils de patron  : son origine (cr�ateur, acheteur, h�ritier), sa formation (autodidacte, dipl�m�), ses motivations et ses ambitions l'argent, la libert�, le pouvoir, ses m�thodes de travail et son style de management, la nature des relations qu'il entretient ou qu'il suppose -et fait supporter � ses proches- entre sa vie priv�e et sa vie professionnelle, tout doit �tre pris en compte. Il �tait donc passionnant de consacrer aux patrons de PME un ouvrage qui soit tout autant un ambitieux  portraits de groupe  qu'une source document�e et pr�cise d'informations sur tel ou tel aspect de cette cat�gorie  � part  mais qui rassemble la majorit� des dirigeants d'entreprises en France. Pr�sent� par le CEPME, ce livre analyse l'�tonnant foisonnement de questions et de situations particuli�res li�es � ces dirigeants : leurs sp�cificit�s dans le contexte historique et culturel du patronat fran�ais, l'image caract�ristique et ambivalente du patron, � la fois homme de l'int�rieur qui r�gle les probl�mes et homme � l'ext�rieur qui promeut son entreprise et joue un r�le social souvent d�terminant, leurs attentes et leurs perspectives, leurs visions de l'avenir. Cette enqu�te s'ach�ve par une synth�se de ces informations et par l'�laboration d'une typologie originale et in�dite des grandes  cat�gories  de dirigeants de PME. Xoxo");
		IMessage m2=new Beale().decrypter(m, "La petite taille de l'entreprise renforce consid�rablement le r�le du dirigeant et c'est un lieu commun de dire que le patron de PME, souvent majoritaire, doit faire face � une multitude de t�ches, rassembler des comp�tences qui, dans les grandes structures, sont confi�es � des sp�cialistes. Mais si, de ce fait, le dirigeant de PME entretien une relation particuli�re avec le pouvoir, de multiples facteurs concourent � une grande diversit� de  profils de patron  : son origine (cr�ateur, acheteur, h�ritier), sa formation (autodidacte, dipl�m�), ses motivations et ses ambitions l'argent, la libert�, le pouvoir, ses m�thodes de travail et son style de management, la nature des relations qu'il entretient ou qu'il suppose -et fait supporter � ses proches- entre sa vie priv�e et sa vie professionnelle, tout doit �tre pris en compte. Il �tait donc passionnant de consacrer aux patrons de PME un ouvrage qui soit tout autant un ambitieux  portraits de groupe  qu'une source document�e et pr�cise d'informations sur tel ou tel aspect de cette cat�gorie  � part  mais qui rassemble la majorit� des dirigeants d'entreprises en France. Pr�sent� par le CEPME, ce livre analyse l'�tonnant foisonnement de questions et de situations particuli�res li�es � ces dirigeants : leurs sp�cificit�s dans le contexte historique et culturel du patronat fran�ais, l'image caract�ristique et ambivalente du patron, � la fois homme de l'int�rieur qui r�gle les probl�mes et homme � l'ext�rieur qui promeut son entreprise et joue un r�le social souvent d�terminant, leurs attentes et leurs perspectives, leurs visions de l'avenir. Cette enqu�te s'ach�ve par une synth�se de ces informations et par l'�laboration d'une typologie originale et in�dite des grandes  cat�gories  de dirigeants de PME. Xoxo");
		System.out.println(m);
		System.out.println(m2);
		System.out.println(new Beale().genererCle());
	}
}
