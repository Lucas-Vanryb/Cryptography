package codage;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import fabrique.Fabrique;

import message.IMessage;

/**
 * Classe permettant le cryptage et le decryptage via le système Enigma (permutations
 * de lettres)
 * @see codage.Code
 * @see codage.ICode
 * @author Lucas VANRYB
 *
 */
public class Enigma extends Code implements ICode {

	/**
	 * Constante contenant tous les caractères de l'alphabet
	 */
	public final static String ALPHABET="abcdefghijklmnopqrstuvwxyz";

	/*
	 * ATTRIBUTS
	 */
	
	/**
	 * HashMap stockant les permutations à effectuer pour coder/decoder
	 */
	HashMap<Character,Character> h=new HashMap<Character,Character>();

	/**
	 * Constructeur initialisant les variables d'instance, et reglant les permutations par defaut,
	 * à savoir la conservation des lettres
	 */
	public Enigma() {
		super();
		for(int i=0;i<ALPHABET.length();i++) {
			h.put(ALPHABET.charAt(i), ALPHABET.charAt(i));
		}
	}

	/**
	 * Méthode permettant de crypter un message à l'aide du système Enigma
	 * @param clair le message à crypter
	 * @param key la clé à utiliser pour le cryptage
	 * @return le message crypté
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * Cette méthode code tous les caractères correspondant à des lettres via des appels à
		 * encoderChar, après avoir adapté et lu la clé.
		 * Tous les caractères ne correspondant pas à des lettres ne sont pas cryptés
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
	 * Méthode permettant de décrypter un message crypté à l'aide du système Enigma
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
	 * Méthode permettant de génerer une clef valide pour Enigma,
	 * c'est à dire un alphabet en désordre
	 * @return une clé valide pour Enigma
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
	 * Méthode permettant de vérifier si une clé est correcte pour Enigma
	 * @param k la clé dont on vérifie la conformité
	 * @return true si k est conforme, false sinon
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

	/*
	 * Méthode prenant en paramètre une clé k et retournant la chaine de caractère correspondant
	 * à la suite de lettres presentes dans la clé k
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
	 * Lit la clé k passée en paramètre de la facon suivante :
	 * lorsque deux lettres se suivent dans la clé, ces deux lettres devront être permutées
	 * Tout le reste(et si deux lettres identiques apparaissent à la suite, ou les apparitions d'une meme 
	 * lettre en double dans la meme cle) est ignoré
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
	 * Fonction main permettant de tester la bonne marche de la classe
	 * @param args
	 */
	public static void main(String[] args) {
		String key=new Enigma().genererCle();
		System.out.println(key);
		IMessage clair=Fabrique.fabriquerMessage("Message à crypter!");
		IMessage crypte=new Enigma().crypter(clair, key);
		System.out.println(crypte);
		System.out.println(new Enigma().decrypter(crypte, key));

	}

}
