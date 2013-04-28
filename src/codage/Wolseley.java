package codage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JOptionPane;

import fabrique.Fabrique;

import message.IMessage;

/**
 * Le classe Wolseley permet d'utiliser le chiffre de Wolseley
 * Le cryptage et le décryptage sont implémentés
 * @see codage.ICode
 * @see codage.Code
 * @author Lucas VANRYB
 *
 */
public class Wolseley extends Code implements ICode {

	/**
	 * Constante contenant tous les caractères de l'alphabet
	 */
	public final static String ALPHABET="abcdefghijklmnopqrstuvwxyz";

	/**
	 * Constante determinant la longueur de la clé generee
	 */
	private static final int LONGUEUR_CLE = 6;

	/**
	 * Caractère interdit
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
	 * HashMap stockant les permutations à effectuer pour coder/decoder
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
	 * Méthode générant une clé de la taille LONGUEUR_CLE valide pour le chiffre 
	 * de Wolseley
	 * @return une clé valide pour le chiffre de Wolseley
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
	 * Méthode permettant de lire la clé adaptée afin de remplir la HashMap
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
	 * Méthode permettant d'ignorer tous les caractères n'etant pas des lettres dans la clé
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
	 * Méthode permettant d'adapter le message
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
	 * Encode un caractere à l'aide de la clé stockée en mémoire sous forme de HashMap
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
	 * Méthode permettant de crypter un message à l'aide du chiffre de Wolseley
	 * @param clair le message à crypter
	 * @param key la clé à utiliser pour le cryptage
	 * @return le message crypté
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * Les caractères sont encodés un à un via opérations elementaires
		 * Les caractères ne correspondant pas à des lettres sont ajoutés tel quels.
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
	 * Méthode permettant de décrypter un message crypté à l'aide du chiffre de Wolseley
	 * @param crypte le message à decrypter
	 * @param key la clé de cryptage associée
	 * @return le message décrypté
	 */
	public IMessage decrypter(IMessage crypte, String key) {
		/*
		 * La codage étant basé sur des permutations, le codage et le decryptage sont identiques	
		 */
		return this.crypter(crypte, key);
	}

	/**
	 * Méthode permettant de génerer une clef valide pour Wolseley,
	 * c'est à dire une suite de lettres
	 * @return une clé valide pour Wolseley
	 */
	public boolean estCleValable(String k) {
		int i=0;
		boolean trouve=false;
		while(i<k.length() && !trouve) {
			if(!ALPHABET.contains(""+k.charAt(i))&&!ALPHABET.toUpperCase().contains(""+k.charAt(i))&&(k.charAt(i)!=' ')) {
				trouve=true;
				JOptionPane.showMessageDialog(null, "Veuillez saisir une clé constituée uniquement d'une suite de lettres majuscules et minuscule non accentuées","Cle incorrecte",JOptionPane.ERROR_MESSAGE);
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
		IMessage clair=Fabrique.fabriquerMessage("Message à crypter!");
		IMessage crypte=new Wolseley().crypter(clair, key);
		System.out.println(crypte);
		System.out.println(new Wolseley().decrypter(crypte, key));

	}

}
