package codage;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import fabrique.Fabrique;

import message.IMessage;

/**
 * Classe permettant le cryptage et le decryptage via le syst�me Enigma (permutations
 * de lettres)
 * @see codage.Code
 * @see codage.ICode
 * @author Lucas VANRYB
 *
 */
public class Enigma extends Code implements ICode {

	/**
	 * Constante contenant tous les caract�res de l'alphabet
	 */
	public final static String ALPHABET="abcdefghijklmnopqrstuvwxyz";

	/*
	 * ATTRIBUTS
	 */
	
	/**
	 * HashMap stockant les permutations � effectuer pour coder/decoder
	 */
	HashMap<Character,Character> h=new HashMap<Character,Character>();

	/**
	 * Constructeur initialisant les variables d'instance, et reglant les permutations par defaut,
	 * � savoir la conservation des lettres
	 */
	public Enigma() {
		super();
		for(int i=0;i<ALPHABET.length();i++) {
			h.put(ALPHABET.charAt(i), ALPHABET.charAt(i));
		}
	}

	/**
	 * M�thode permettant de crypter un message � l'aide du syst�me Enigma
	 * @param clair le message � crypter
	 * @param key la cl� � utiliser pour le cryptage
	 * @return le message crypt�
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * Cette m�thode code tous les caract�res correspondant � des lettres via des appels �
		 * encoderChar, apr�s avoir adapt� et lu la cl�.
		 * Tous les caract�res ne correspondant pas � des lettres ne sont pas crypt�s
		 */
		String k=this.adapterCle(key);
		String res="";
		this.interpreterCle(k);
		for(int i=0;i<clair.taille();i++) {
			if(ALPHABET.contains(""+clair.getChar(i)) || ALPHABET.toUpperCase().contains(""+clair.getChar(i))) {
				res+=this.crypterChar(clair.getChar(i));
			}
			else {
				res+=clair.getChar(i);
			}
		}	
		return Fabrique.fabriquerMessage(res);
	}

	/**
	 * M�thode permettant de d�crypter un message crypt� � l'aide du syst�me Enigma
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
	 * M�thode permettant de g�nerer une clef valide pour Enigma,
	 * c'est � dire un alphabet en d�sordre
	 * @return une cl� valide pour Enigma
	 */
	public String genererCle() {
		ArrayList<Character> dejaMis=new ArrayList<Character>();
		String res="";
		boolean ajoute;
		int rand;
		char t;
		for(int i=0;i<ALPHABET.length();i++) {
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

	/**
	 * M�thode permettant de v�rifier si une cl� est correcte pour Enigma
	 * @param k la cl� dont on v�rifie la conformit�
	 * @return true si k est conforme, false sinon
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

	/*
	 * M�thode prenant en param�tre une cl� k et retournant la chaine de caract�re correspondant
	 * � la suite de lettres presentes dans la cl� k
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
	 * Lit la cl� k pass�e en param�tre de la facon suivante :
	 * lorsque deux lettres se suivent dans la cl�, ces deux lettres devront �tre permut�es
	 * Tout le reste(et si deux lettres identiques apparaissent � la suite, ou les apparitions d'une meme 
	 * lettre en double dans la meme cle) est ignor�
	 */
	private void interpreterCle(String k) {
		ArrayList<Character> dejaVu=new ArrayList<Character>();
		for(int i=0;i<k.length();i+=2) {
			if(!dejaVu.contains(k.charAt(i)) && !dejaVu.contains(k.charAt(i+1)) && k.charAt(i)!=k.charAt(i+1)) {
				this.h.put(k.charAt(i), k.charAt(i+1));
				this.h.put(k.charAt(i+1), k.charAt(i));
				dejaVu.add(k.charAt(i));
				dejaVu.add(k.charAt(i+1));
			}
		}
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
	 * Fonction main permettant de tester la bonne marche de la classe
	 * @param args
	 */
	public static void main(String[] args) {
		String key=new Enigma().genererCle();
		System.out.println(key);
		IMessage clair=Fabrique.fabriquerMessage("Message � crypter!");
		IMessage crypte=new Enigma().crypter(clair, key);
		System.out.println(crypte);
		System.out.println(new Enigma().decrypter(crypte, key));

	}

}
