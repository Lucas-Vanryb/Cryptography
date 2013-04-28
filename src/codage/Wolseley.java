package codage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JOptionPane;

import fabrique.Fabrique;

import message.IMessage;

/**
 * Le classe Wolseley permet d'utiliser le chiffre de Wolseley
 * Le cryptage et le d�cryptage sont impl�ment�s
 * @see codage.ICode
 * @see codage.Code
 * @author Lucas VANRYB
 *
 */
public class Wolseley extends Code implements ICode {

	/**
	 * Constante contenant tous les caract�res de l'alphabet
	 */
	public final static String ALPHABET="abcdefghijklmnopqrstuvwxyz";

	/**
	 * Constante determinant la longueur de la cl� generee
	 */
	private static final int LONGUEUR_CLE = 6;

	/**
	 * Caract�re interdit
	 */
	private static final char CARACTERE_INTERDIT='w';

	/**
	 * Chaine de substitution
	 */
	private static final String SUBSTITUTION="vv";

	/*
	 * ATTRIBUTS
	 */

	/**
	 * HashMap stockant les permutations � effectuer pour coder/decoder
	 */
	private HashMap<Character,Character> h=new HashMap<Character,Character>();

	/**
	 * Alphabet sans la lettre interdite
	 */
	private String alpha="";

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur initialisant les variables d'instance
	 */
	public Wolseley() {
		super();
		for(int i=0;i<ALPHABET.length();i++) {
			if(ALPHABET.charAt(i)!=CARACTERE_INTERDIT) {
				alpha+=ALPHABET.charAt(i);
			}
		}
	}

	/*
	 * SERVICES
	 */

	/**
	 * M�thode g�n�rant une cl� de la taille LONGUEUR_CLE valide pour le chiffre 
	 * de Wolseley
	 * @return une cl� valide pour le chiffre de Wolseley
	 */
	public String genererCle() {
		ArrayList<Character> dejaMis=new ArrayList<Character>();
		String res="";
		boolean ajoute;
		int rand;
		char t;
		for(int i=0;i<LONGUEUR_CLE;i++) {
			ajoute=false;
			while(!ajoute) {
				rand=(int)(Math.random()*26);
				t=(char)(97+rand);
				if(!dejaMis.contains(t)) {
					res+=t;
					dejaMis.add(t);
					ajoute=true;
				}
			}
		}
		return res;
	}

	/*
	 * M�thode permettant de lire la cl� adapt�e afin de remplir la HashMap
	 */
	private void remplirHashMap(String key) {
		ArrayList<Character> al=new ArrayList<Character>();
		for(int i=0;i<key.length();i++) {
			if(this.alpha.contains(key.charAt(i)+"")) {
				al.add(key.charAt(i));
			}
		}
		for(int i=0;i<ALPHABET.length();i++) {
			if(!al.contains(ALPHABET.charAt(i)) && this.alpha.contains(ALPHABET.charAt(i)+"")) {
				al.add(ALPHABET.charAt(i));
			}
		}
		for(int i=0;i<al.size();i++) {
			this.h.put(al.get(i), al.get(al.size()-i-1));
		}
	}

	/*
	 * M�thode permettant d'ignorer tous les caract�res n'etant pas des lettres dans la cl�
	 */
	private String adapterCle(String k) {
		String res="";
		for(int i=0;i<k.length();i++) {
			if(ALPHABET.contains(""+k.charAt(i))) {
				res+=k.charAt(i);
			}
			if(ALPHABET.toUpperCase().contains(""+k.charAt(i))) {
				res+=k.toLowerCase().charAt(i);
			}
		}
		return res;
	}

	/*
	 * M�thode permettant d'adapter le message
	 */
	private IMessage adapterMessage(IMessage m) {
		ArrayList<Character> al=new ArrayList<Character>();
		for(int i=0;i<m.taille();i++) {
			if(m.getChar(i)==CARACTERE_INTERDIT) {
				for(int j=0;j<SUBSTITUTION.length();j++) {
					al.add(SUBSTITUTION.charAt(j));
				}
			}
			else {
				if(m.getChar(i)==Character.toUpperCase(CARACTERE_INTERDIT)) {
					for(int j=0;j<SUBSTITUTION.length();j++) {
						al.add(SUBSTITUTION.toUpperCase().charAt(j));
					}
				}
				else {
					al.add(m.getChar(i));
				}
			}
		}
		Character[] ch=new Character[al.size()];
		for(int i=0;i<al.size();i++) {
			ch[i]=al.get(i);
		}
		return Fabrique.fabriquerMessage(ch);
	}

	/*
	 * Encode un caractere � l'aide de la cl� stock�e en m�moire sous forme de HashMap
	 */
	private char crypterChar(char c) {
		try {
			return this.h.get(c);
		}
		catch (Exception e){
			return Character.toUpperCase(this.h.get(Character.toLowerCase(c)));
		}
	}

	/**
	 * M�thode permettant de crypter un message � l'aide du chiffre de Wolseley
	 * @param clair le message � crypter
	 * @param key la cl� � utiliser pour le cryptage
	 * @return le message crypt�
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * Les caract�res sont encod�s un � un via op�rations elementaires
		 * Les caract�res ne correspondant pas � des lettres sont ajout�s tel quels.
		 */
		long d=new Date().getTime();
		this.remplirHashMap(this.adapterCle(key));
		IMessage m=this.adapterMessage(clair);
		char[] c=new char[m.taille()];
		for(int i=0;i<m.taille();i++) {
			if(m.getChar(i)>=65 && m.getChar(i)<=90) {
				c[i]=Character.toUpperCase(this.crypterChar(m.getChar(i)));
			}
			else {
				if(m.getChar(i)>=97 && m.getChar(i)<=122) {
					c[i]=this.crypterChar(m.getChar(i));
				}
				else{
					c[i]=m.getChar(i);
				}
			}
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(c);
	}

	/**
	 * M�thode permettant de d�crypter un message crypt� � l'aide du chiffre de Wolseley
	 * @param crypte le message � decrypter
	 * @param key la cl� de cryptage associ�e
	 * @return le message d�crypt�
	 */
	public IMessage decrypter(IMessage crypte, String key) {
		/*
		 * La codage �tant bas� sur des permutations, le codage et le decryptage sont identiques	
		 */
		return this.crypter(crypte, key);
	}

	/**
	 * M�thode permettant de g�nerer une clef valide pour Wolseley,
	 * c'est � dire une suite de lettres
	 * @return une cl� valide pour Wolseley
	 */
	public boolean estCleValable(String k) {
		int i=0;
		boolean trouve=false;
		while(i<k.length() && !trouve) {
			if(!ALPHABET.contains(""+k.charAt(i))&&!ALPHABET.toUpperCase().contains(""+k.charAt(i))&&(k.charAt(i)!=' ')) {
				trouve=true;
				JOptionPane.showMessageDialog(null, "Veuillez saisir une cl� constitu�e uniquement d'une suite de lettres majuscules et minuscule non accentu�es","Cle incorrecte",JOptionPane.ERROR_MESSAGE);
			}
			i++;
		}
		return !trouve;
	}

	/**
	 * Fonction main permettant de tester la bonne marche de la classe
	 * @param args
	 */
	public static void main(String[] args) {
		String key=new Wolseley().genererCle();
		System.out.println(key);
		IMessage clair=Fabrique.fabriquerMessage("Message � crypter!");
		IMessage crypte=new Wolseley().crypter(clair, key);
		System.out.println(crypte);
		System.out.println(new Wolseley().decrypter(crypte, key));

	}

}
