package codage;

import java.util.Date;

import javax.swing.JOptionPane;

import message.IMessage;


import fabrique.Fabrique;

/**
 * Classe permettant de crypter,d�crypter et cryptanalyser des messages � l'aide du chiffre de
 * Cesar
 * @see codage.ICode
 * @see codage.ICodeCryptanalysable
 * @see codage.Code
 * @author Lucas VANRYB
 *
 */
public class Caesar extends Code implements ICode,ICodeCryptanalysable{

	/*
	 * CONSTRUCTEUR
	 */
	
	/**
	 * Constructeur initialisant les variables d'instance
	 */
	public Caesar() {
		super();
	}
	
	/*
	 * SERVICES
	 */

	/**
	 * M�thode permettant de crypter un message en utilisant le chiffre de Cesar
	 * @param clair le message � crypter
	 * @param key la cl� � utiliser pour le cryptage, qui doit etre un entier
	 * compris entre 0 et 26
	 * @return le message crypt�
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * Les caract�res sont encod�s un � un via op�rations elementaires
		 * Les caract�res ne correspondant pas � des lettres sont ajout�s tel quels.
		 */
		long d=new Date().getTime();
		int k=Integer.parseInt(key);
		char[] c=new char[clair.taille()];
		for(int i=0;i<clair.taille();i++) {
			if(clair.getChar(i)>=65 && clair.getChar(i)<=90) {
				c[i]=(char)(65+((clair.getChar(i)-65+k)%26));
			}
			else {
				if(clair.getChar(i)>=97 && clair.getChar(i)<=122) {
					c[i]=(char)(97+((clair.getChar(i)-97+k)%26));
				}
				else{
					c[i]=clair.getChar(i);
				}
			}
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(c);
	}

	/**
	 * M�thode permettant de tenter de cryptanalyser un message encod� avec le chiffre de Cesar
	 * et qui garde en m�moire le resultat de cette cryptanalyse
	 * @param crypte le message � essayer de cryptanalyser
	 * @return le message decrypte si la cryptanalyse a reussie, null sinon
	 */
	public IMessage cryptanalyse(IMessage crypte) {
		/*
		 * La m�thode essaie toutes les cl�s possible pour Cesar
		 * Puis � l'aide d'une analyse frequentielle selectionne la plus probable
		 * Si l'analyse frequentielle montre que la cl� la plus probable comporte suffisament
		 * de voyelles, consid�re la cryptanalyse reussie
		 */
		long d=new Date().getTime();
		IMessage m=this.decrypter(crypte, "0");
		double min=Outils.comparerCourt(m.getChar());
		Integer r=-1;
		for(Integer i=1;i<26;i++) {
			m=this.decrypter(crypte, i.toString());
			if(Outils.comparerCourt(m.getChar())<min) {
				min=Outils.comparerCourt(m.getChar());
				r=i;
			}
		}
		this.time=new Date().getTime()-d;
		if(min<cst) {
			this.succesCrypto=true;
			return this.decrypter(crypte,r.toString());
		}
		return null;

	}

	/**
	 * M�thode permettant de tenter une cryptanalyse � clair connu d'un message crypt�
	 * � l'aide du chiffre de Cesar puis qui stocke le resultat de cette tentative
	 * @param clair le message clair
	 * @param crypte le message crypte
	 * @return la cl� de cryptage si la cryptanalyse a reussi, une chaine quelquoncque sinon
	 */
	public String cryptanalyseCle(IMessage clair, IMessage crypte) {
		/*
		 * La m�thode cherche le premier caract�re crypt� du texte, puis evalue la clef de cryptage
		 * possible.
		 * Elle v�rifie ensuite si le texte entier est coh�rent avec cette cl�.
		 */
		long d=new Date().getTime();
		int i=0;
		while(i<clair.taille() && clair.getChar(i)==crypte.getChar(i)) {
			i++;
		}
		int z=(crypte.getChar(i)-clair.getChar(i)+26)%26;
		int j=0;
		boolean trouve=false;
		while(j<clair.taille() && !trouve) {
			if((char)(65+(clair.getChar(j)+z-65)%26)!=crypte.getChar(j) && clair.getChar(j)!=crypte.getChar(j) && (char)(97+(clair.getChar(j)+z-97)%26)!=crypte.getChar(j)) {
				trouve=true;
			}
			j++;
		}
		this.time=new Date().getTime()-d;
		if(!trouve) {
			this.succesCle=true;
		}
		if(i==clair.taille()) {
			return "0";
		}
		Integer r=z;
		return r.toString();
	}

	/**
	 * M�thode permettant de d�crypter un message cod� � l'aide du chiffre de Cesar
	 * @param crypte le message � decrypter
	 * @param key la cl� de cryptage associ�e qui doit etre un entier
	 * @return le message d�crypt�
	 */
	public IMessage decrypter(IMessage crypte, String key) {
		/*
		 * Les caract�res sont d�cod�s un � un via op�rations elementaires
		 * Les caract�res ne correspondant pas � des lettres sont ajout�s tel quels.
		 */
		long d=new Date().getTime();
		int k=Integer.parseInt(key);
		char[] c=new char[crypte.taille()];
		for(int i=0;i<crypte.taille();i++) {
			if(crypte.getChar(i)>=65 && crypte.getChar(i)<=90) {
				if((crypte.getChar(i)-65-k)<0) {
					c[i]=(char)(65+26+((crypte.getChar(i)-65-k)));
				}
				else {
					c[i]=(char)(65+((crypte.getChar(i)-65-k)));
				}
			}
			else {
				if(crypte.getChar(i)>=97 && crypte.getChar(i)<=122) {
					if((crypte.getChar(i)-97-k)<0) {
						c[i]=(char)(97+26+((crypte.getChar(i)-97-k)));
					}
					else {
						c[i]=(char)(97+((crypte.getChar(i)-97-k)));
					}
				}
				else{
					c[i]=crypte.getChar(i);
				}
			}
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(c);
	}

	/**
	 * M�thode permettant de g�nerer une clef valide pour le chiffre de Cesar
	 * @return une cl� enti�re comprise entre 0 et 26
	 */
	public String genererCle() {
		return Integer.toString((int)(Math.random()*26));
	}

	/**
	 * M�thode v�rifiant si une cl� est valable pour le chiffre de Cesar
	 * et affichant une erreur si elle ne l'est pas
	 * @param k la cl� � tester
	 * @return true si la cl� est valable pour le chiffre de Cesar, false sinon
	 */
	public boolean estCleValable(String k) {
		/*
		 * La m�thode v�rifie que la cl� est enti�re et qu'elle est comrpise entre 0 et 26
		 */
		try {
			int r=Integer.parseInt(k);
			if(r>=0 && r<26) {
				return true;
			}
			else {
				JOptionPane.showMessageDialog(null, "Veuillez saisir une cl� enti�re compris entre 0 et 25", "Cl� incorrecte", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Veuillez saisir une cl� enti�re compris entre 0 et 25", "Cl� incorrecte", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	/**
	 * Classe main permettant de tester le bon fonctionnement de la classe
	 * @param args les arguments(inutiles) d'appel du programme
	 */
	public static void main(String[] args) {
		IMessage clair=Fabrique.fabriquerMessage("J'AIME LES ANIMAUX DE COMPAGNIE, PLUS PARTICULI�REMENT LES PONEYS!");
		IMessage crypte=Fabrique.fabriquerMessage("B'SAEW DWK SFAESMP VW UGEHSYFAW, HDMK HSJLAUMDA�JWEWFL DWK HGFWQK!");
		ICodeCryptanalysable cryptanalyse=new Caesar();
		ICodeCryptanalysable cle=new Caesar();
		System.out.println("Cryptage du message :"+new Caesar().crypter(clair, "18"));
		System.out.println("Decryptage avec cle :"+new Caesar().decrypter(crypte, "18"));
		System.out.println("Tentative de decryption auto :"+cryptanalyse.cryptanalyse(crypte));
		System.out.println("Resultat :"+cryptanalyse.succesCrypto());
		System.out.println("Duree :"+cryptanalyse.tempsCrypto());
		System.out.println("Tentative de recuperation de la cle de cryptage :"+cle.cryptanalyseCle(clair, crypte));
		System.out.println("Resultat :"+cle.succesCle());
		System.out.println("Dur�e :"+cle.tempsCrypto());
	}

}
