package codage;

import java.util.Date;

import javax.swing.JOptionPane;

import fabrique.Fabrique;

import message.IMessage;

/**
 * Classe permettant d'utiliser un code de mon invention,
 * basé sur les décimales du chiffre Pi.
 * La clef est un entier compris entre 1 et 150 permettant de décaler
 * la première décimale considérée.
 * Le code fonctionne de la facon suivante :
 * Caractere -> ASCII -> Multiplication de l'ASCII par la i+k eme decimale de pi
 * i étant l'index du caractère crypté dans le message et k la clé -> Modulo 257
 * @author Lucas VANRYB
 *
 */
public class  HomeMade extends Code implements ICode {

	/**
	 * La clé maximale accéptée par le code
	 */
	private static final double MAX_CLE = 150;

	/*
	 * ATTRIBUTS
	 */

	/**
	 * Tableau contenant les premières décimales de Pi
	 */
	private byte[] decimales;

	/*
	 * CONSTRUCTEUR
	 */

	public HomeMade() {
		super();
	}

	/*
	 * SERVICES
	 */

	/**
	 * Méthode permettant de crypter un message à l'aide de ma méthode maison
	 * @param clair le message à crypter
	 * @param key la clé à utiliser pour le cryptage, un entier inférieur à MAX_KEY
	 * @return le message crypté
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * Méthode appelant le calcul des décimales de Pi,
		 * puis codant les caractères du message un à un	
		 */
		int k=Integer.parseInt(key);
		this.getDecimalesPi(clair.taille()+k);
		String res="";
		for(int i=0;i<clair.taille();i++) {
			res+=this.encoderChar(clair.getChar(i), i+k);
			if(i!=clair.taille()-1) {
				res+=" ";
			}
		}
		return Fabrique.fabriquerMessage(res);
	}

	/*
	 * Méthode remplissant le tableau en mémoire avec les i premières décimales
	 * de Pi
	 */
	private void getDecimalesPi(int i) {
		this.decimales=Outils.calculerDecimalesPi(i);

	}

	/**
	 * Méthode permettant de décrypter un message à l'aide ma méthode
	 * maison
	 * @param crypte le message à decrypter
	 * @param key la clé de cryptage associée
	 * @return le message décrypté
	 */
	public IMessage decrypter(IMessage crypte, String key) {
		/*
		 * Méthode appelant le calcul des décimales de Pi,
		 * puis décodant les caractères du message un à un	
		 */
		long d=new Date().getTime();
		int k=Integer.parseInt(key);
		this.getDecimalesPi(crypte.taille()+k);
		char[] c=new char[crypte.taille()];
		int v=-1;
		int y=-1;
		int r=0;
		int z=0;
		String temp="";
		for(int i=0;i<crypte.taille();i++) {
			if(crypte.getChar(i)==' ') {
				v=y;
				y=i;
				temp="";
				for(int q=(v+1);q<y;q++) {
					temp+=crypte.getChar(q);
				}
				c[r]=this.decoderChar(Integer.parseInt(temp),z+k);
				z++;
				r++;
			}
		}
		temp="";
		for(int q=(y+1);q<crypte.taille();q++) {
			temp+=crypte.getChar(q);
		}
		c[r]=this.decoderChar(Integer.parseInt(temp),z+k);
		z++;
		char[] aRetourner=new char[r+1];
		for(int i=0;i<=r;i++) {
			aRetourner[i]=c[i];
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(aRetourner);
	}

	/*
	 * Méthode permettant de décoder un caractère
	 */
	private char decoderChar(int d, int i) {
		long r=Outils.inverseModulo(this.decimales[i],257);
		return (char)((d*r)%257);
	}

	/*
	 * Méthode permettant d'encoder un caractère
	 */
	private int encoderChar(char c, int i) {
		int d=(int)c;
		int r=this.decimales[i];
		return (d*r)%257;	
	}

	/**
	 * Méthode permettant de génerer une clef valide pour mon code maison
	 * @return une clé entière comprise entre 0 et la clé maximale définie dans le code
	 */
	public String genererCle() {
		return Integer.toString((int)(Math.random()*MAX_CLE));
	}

	/**
	 * Méthode vérifiant si une clé est valable pour mon code fait maison
	 * et affichant une erreur si elle ne l'est pas
	 * Une clé valable pour ce code est une clé entière comprise entre 0 et un constante determinée
	 * @param k la clé à tester
	 * @return true si la clé est valable pour mon code maison, false sinon
	 */
	public boolean estCleValable(String k) {
		/*
		 * La méthode vérifie que la clé est entière et qu'elle est comrpise entre 0 et MAX_CLE
		 */
		try {
			int r=Integer.parseInt(k);
			if(r>=0 && r<MAX_CLE) {
				return true;
			}
			else {
				JOptionPane.showMessageDialog(null, "Veuillez saisir une clé entière compris entre 0 et "+MAX_CLE, "Clé incorrecte", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Veuillez saisir une clé entière compris entre 0 et "+MAX_CLE, "Clé incorrecte", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/**
	 * Classe main permettant de tester le bon fonctionnement de la classe
	 * @param args les arguments(inutiles) d'appel du programme
	 */
	public static void main(String[] args) {
		IMessage clair=Fabrique.fabriquerMessage("tout marche YOUPI");
		String key=new HomeMade().genererCle();
		IMessage crypte=new HomeMade().crypter(clair, key);
		System.out.println("Cryptage du message :"+new HomeMade().crypter(clair, key));
		System.out.println("Decryptage avec cle :"+new HomeMade().decrypter(crypte, key));
	}
}
