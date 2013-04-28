package codage;

import java.util.Date;

import javax.swing.JOptionPane;

import fabrique.Fabrique;
import message.IMessage;

/**
 * Classe permettant d'effectuer des operations de cryptage et de decryptage � l'aide du
 * syst�me RSA
 * @see codage.ICode
 * @see codage.ICodeClePublique
 * @see codage.Code
 * @see codage.CodeClePublique
 * @author Lucas VANRYB
 *
 */
public class RSA extends CodeClePublique implements ICodeClePublique {

	/**
	 * D�finit le nombre de bits necessaires pour encoder un caract�re
	 */
	public static int TAILLE_CHAR=3;

	/**
	 * D�finit le nombre de caract�re qui seront mis ensemble dans un bloc d'encodage
	 */
	public static int TAILLE_ENCODAGE=2*TAILLE_CHAR;

	/*
	 * ATTRIBUTS
	 */

	/**
	 * L'entier commun � la cl� publique et priv�e
	 */
	private long n;

	/**
	 * L'entier propre � la cl� priv�e
	 */
	private long d;

	/**
	 * L'entier propre � la cl� publique
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
	 * M�thode permettant de crypter un message � l'aide de la m�thode RSA
	 * @param clair le message � crypter
	 * @param key la cl� � utiliser pour le cryptage
	 * @return le message crypt�
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * Cette fonction divise le messag een bloc de caract�re et appelle la fonction encoderSerie()
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
	 * Cette m�thode concat�ne les entiers associ�s aux caract�res afin de
	 * former un gros entier, qui sera cod� � l'aide du RSA
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
	 * M�thode permettant de d�crypter un message � l'aide de RSA
	 * @param crypte le message � decrypter
	 * @param key la cl� de cryptage associ�e
	 * @return le message d�crypt�
	 */
	public IMessage decrypter(IMessage crypte, String key) {
		/*
		 * M�thode d�coupant le message en suite d'entier qui sont d�cod�s
		 * un � un � l'aide de decoderSerie
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
			JOptionPane.showMessageDialog(null, "Ce message n'a pas �t� cod� � l'aide de ce type de code", "Erreur", JOptionPane.ERROR_MESSAGE);
			return Fabrique.fabriquerMessage("Erreur");
		}
	}

	/*
	 * On retrouve l'entier concat�n� � l'aide de RSA,
	 * puis on divise ce nombre en trois et on retrouve les caract�res adapt�s
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
	 * M�thode permettant de �n�rer une cl� valide pour RSA
	 * @return une cl� valide pour RSA
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
	 * M�thode permettant d elire la cl� priv�e
	 */
	private void interpreterClePrive() {
		String temp=this.getPrivateKey().substring(this.getPrivateKey().indexOf('(')+1, this.getPrivateKey().indexOf(')'));
		this.n=Integer.parseInt(temp.split(",")[0]);
		this.d=Integer.parseInt(temp.split(",")[1]);	
	}

	/*
	 * M�thode permettant de lire la cl� publique
	 */
	private void interpreterClePublique() {
		String temp=this.getPublicKey().substring(this.getPublicKey().indexOf('(')+1, this.getPublicKey().indexOf(')'));
		this.n=Integer.parseInt(temp.split(",")[0]);
		this.c=Integer.parseInt(temp.split(",")[1]);
	}

	/**
	 * M�thode v�rifiant la validit� du format de la cl� priv�e
	 * @param k la cl� priv�e � tester
	 * @return true si le format de la cl� priv�e est valable
	 */
	public boolean estClePriveValable(String k) {
		try {
			this.diviserKey(" "+k);
			this.interpreterClePrive();
			if(this.d>this.n) {
				JOptionPane.showMessageDialog(null, "La cl� priv�e est incorrecte", "Cl� priv�e incorrecte", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "La cl� priv� ne respecte pas le format : (entier,entier)", "Cl� priv� incorrecte", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * M�thode v�rifiant la validit� du format de la cl� publique
	 * @param k la cl� publique � tester
	 * @return true si le format de la cl� publique est valable
	 */
	public boolean estClePubliqueValable(String k) {
		try {
			this.diviserKey(k+" ");
			this.interpreterClePublique();
			if(this.c>this.n) {
				JOptionPane.showMessageDialog(null, "La cl� publique est incorrecte", "Cl� publique incorrecte", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "La cl� publique ne respecte pas le format : (entier,entier)", "Cl� publique incorrecte", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * M�thode v�rifiant si la cl� est valide pour RSA, en v�rifiant son format,
	 * et en v�rifiant que la cl� publique et la cl� priv�e partagent le m�me n
	 * @param k la cl� � tester
	 * @return true si la cl� est a priori valide pour RSA
	 */
	public boolean estCleValable(String k) {
		if(super.estCleValable(k)) {
			long f=this.n;
			this.diviserKey(k);
			this.interpreterClePublique();
			if(f!=this.n) {
				JOptionPane.showMessageDialog(null, "Les cl�s publique et priv� ne correspondent pas", "Cl� incorrecte", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * M�thode main permettant de tester la classe
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("\u26601");
		String key=new RSA().genererCle();
		System.out.println(key);
		IMessage clair=Fabrique.fabriquerMessage("J'AIME LES ANIMAUX DE COMPAGNIE, PLUS PARTICULI�REMENT LES PONEYS!");
		IMessage crypte=new RSA().crypter(clair, key);
		System.out.println(new RSA().estCleValable(key));
		System.out.println("Cryptage du message :"+new RSA().crypter(clair, key));
		System.out.println("Decryptage avec cle :"+new RSA().decrypter(crypte, key));
		System.out.println(new RSA().crypter(Fabrique.fabriquerMessage("\u2660"), key));
	}
}
