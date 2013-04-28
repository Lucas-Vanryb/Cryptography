package codage;

import java.util.Date;

import javax.swing.JOptionPane;

import fabrique.Fabrique;
import message.IMessage;

/**
 * Classe permettant d'effectuer des operations de cryptage et de decryptage à l'aide du
 * système RSA
 * @see codage.ICode
 * @see codage.ICodeClePublique
 * @see codage.Code
 * @see codage.CodeClePublique
 * @author Lucas VANRYB
 *
 */
public class RSA extends CodeClePublique implements ICodeClePublique {

	/**
	 * Définit le nombre de bits necessaires pour encoder un caractère
	 */
	public static int TAILLE_CHAR=3;

	/**
	 * Définit le nombre de caractère qui seront mis ensemble dans un bloc d'encodage
	 */
	public static int TAILLE_ENCODAGE=2*TAILLE_CHAR;

	/*
	 * ATTRIBUTS
	 */

	/**
	 * L'entier commun à la clé publique et privée
	 */
	private long n;

	/**
	 * L'entier propre à la clé privée
	 */
	private long d;

	/**
	 * L'entier propre à la clé publique
	 */
	private long c;

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur permettant d'initialiser les variables
	 */
	public RSA() {
		super();
		this.n=0;
		this.c=0;
		this.d=0;
	}

	/*
	 * SERVICES
	 */

	/**
	 * Méthode permettant de crypter un message à l'aide de la méthode RSA
	 * @param clair le message à crypter
	 * @param key la clé à utiliser pour le cryptage
	 * @return le message crypté
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * Cette fonction divise le messag een bloc de caractère et appelle la fonction encoderSerie()
		 */
		long d=new Date().getTime();
		this.diviserKey(key);
		this.interpreterClePublique();
		String s="";
		for(int i=0; i<clair.taille();i+=TAILLE_ENCODAGE/TAILLE_CHAR) {
			if(i<(clair.taille()-TAILLE_ENCODAGE/TAILLE_CHAR)) {
				s+=this.encoderSerie(new String(clair.toString().substring(i,i+TAILLE_ENCODAGE/TAILLE_CHAR)));
				s+=" ";
			}
			else {
				String temp="";
				for(int z=clair.taille();z<i+TAILLE_ENCODAGE/TAILLE_CHAR;z++) {
					temp+=' ';
				}
				s+=this.encoderSerie(new String(clair.toString().substring(i,clair.taille())+temp));
			}
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(s);
	}

	/*
	 * Cette méthode concatène les entiers associés aux caractères afin de
	 * former un gros entier, qui sera codé à l'aide du RSA
	 */
	private int encoderSerie(String s) {
		String concatInt="";
		for(int i=0;i<s.length();i++) {
			Integer e=(Integer)(int)s.charAt(i);
			String st=e.toString();
			for(int j=st.length();j<TAILLE_CHAR;j++) {
				st="0"+st;
			} 
			concatInt+=st;
		}
		long aCoder=Integer.parseInt(concatInt);
		return (int)Outils.puisNonDebord(aCoder, c, n);
	}

	/**
	 * Méthode permettant de décrypter un message à l'aide de RSA
	 * @param crypte le message à decrypter
	 * @param key la clé de cryptage associée
	 * @return le message décrypté
	 */
	public IMessage decrypter(IMessage crypte, String key) {
		/*
		 * Méthode découpant le message en suite d'entier qui sont décodés
		 * un à un à l'aide de decoderSerie
		 */
		try {
			long d=new Date().getTime();
			this.diviserKey(key);
			this.interpreterClePrive();
			String[] coupes=crypte.toString().split(" ");
			String s="";
			for(int i=0;i<coupes.length;i++) {
				s+=this.decoderSerie(Integer.parseInt(coupes[i]));
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
	 * On retrouve l'entier concaténé à l'aide de RSA,
	 * puis on divise ce nombre en trois et on retrouve les caractères adaptés
	 */
	public String decoderSerie(int u) {
		int aSplitter=(int)Outils.puisNonDebord(u, d, n);
		String s=""+aSplitter;
		for(int j=s.length();j<TAILLE_ENCODAGE;j++) {
			s="0"+s;
		}
		String aRetourner="";
		for(int i=0;i<TAILLE_ENCODAGE;i+=TAILLE_CHAR) {
			aRetourner+=(char)Integer.parseInt(s.substring(i,i+TAILLE_ENCODAGE/TAILLE_CHAR+1));
		}
		return aRetourner;
	}

	/**
	 * Méthode permettant de énérer une clé valide pour RSA
	 * @return une clé valide pour RSA
	 */
	public String genererCle() {
		long p=Outils.genererNbPremier();
		long q=Outils.genererNbPremier();
		long n=p*q;
		long phiN=(p-1)*(q-1);
		long c=(long)(Math.random()*n);
		while(!Outils.premierAvec(c, phiN)) {
			c++;
		}
		long d=Outils.inverseModulo(c, phiN);
		String aRetourner="";
		aRetourner+="(";
		aRetourner+=n;
		aRetourner+=",";
		aRetourner+=c;
		aRetourner+=")";
		aRetourner+=" ";
		aRetourner+="(";
		aRetourner+=n;
		aRetourner+=",";
		aRetourner+=d;
		aRetourner+=")";
		return aRetourner;
	}

	/*
	 * Méthode permettant d elire la clé privée
	 */
	private void interpreterClePrive() {
		String temp=this.getPrivateKey().substring(this.getPrivateKey().indexOf('(')+1, this.getPrivateKey().indexOf(')'));
		this.n=Integer.parseInt(temp.split(",")[0]);
		this.d=Integer.parseInt(temp.split(",")[1]);	
	}

	/*
	 * Méthode permettant de lire la clé publique
	 */
	private void interpreterClePublique() {
		String temp=this.getPublicKey().substring(this.getPublicKey().indexOf('(')+1, this.getPublicKey().indexOf(')'));
		this.n=Integer.parseInt(temp.split(",")[0]);
		this.c=Integer.parseInt(temp.split(",")[1]);
	}

	/**
	 * Méthode vérifiant la validité du format de la clé privée
	 * @param k la clé privée à tester
	 * @return true si le format de la clé privée est valable
	 */
	public boolean estClePriveValable(String k) {
		try {
			this.diviserKey(" "+k);
			this.interpreterClePrive();
			if(this.d>this.n) {
				JOptionPane.showMessageDialog(null, "La clé privée est incorrecte", "Clé privée incorrecte", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "La clé privé ne respecte pas le format : (entier,entier)", "Clé privé incorrecte", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * Méthode vérifiant la validité du format de la clé publique
	 * @param k la clé publique à tester
	 * @return true si le format de la clé publique est valable
	 */
	public boolean estClePubliqueValable(String k) {
		try {
			this.diviserKey(k+" ");
			this.interpreterClePublique();
			if(this.c>this.n) {
				JOptionPane.showMessageDialog(null, "La clé publique est incorrecte", "Clé publique incorrecte", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "La clé publique ne respecte pas le format : (entier,entier)", "Clé publique incorrecte", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * Méthode vérifiant si la clé est valide pour RSA, en vérifiant son format,
	 * et en vérifiant que la clé publique et la clé privée partagent le même n
	 * @param k la clé à tester
	 * @return true si la clé est a priori valide pour RSA
	 */
	public boolean estCleValable(String k) {
		if(super.estCleValable(k)) {
			long f=this.n;
			this.diviserKey(k);
			this.interpreterClePublique();
			if(f!=this.n) {
				JOptionPane.showMessageDialog(null, "Les clés publique et privé ne correspondent pas", "Clé incorrecte", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Méthode main permettant de tester la classe
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("\u26601");
		String key=new RSA().genererCle();
		System.out.println(key);
		IMessage clair=Fabrique.fabriquerMessage("J'AIME LES ANIMAUX DE COMPAGNIE, PLUS PARTICULIÈREMENT LES PONEYS!");
		IMessage crypte=new RSA().crypter(clair, key);
		System.out.println(new RSA().estCleValable(key));
		System.out.println("Cryptage du message :"+new RSA().crypter(clair, key));
		System.out.println("Decryptage avec cle :"+new RSA().decrypter(crypte, key));
		System.out.println(new RSA().crypter(Fabrique.fabriquerMessage("\u2660"), key));
	}
}
