package codage;

import java.util.Date;

import javax.swing.JOptionPane;

import fabrique.Fabrique;

import message.IMessage;

/**
 * Classe permettant d'utiliser un code de mon invention,
 * bas� sur les d�cimales du chiffre Pi.
 * La clef est un entier compris entre 1 et 150 permettant de d�caler
 * la premi�re d�cimale consid�r�e.
 * Le code fonctionne de la facon suivante :
 * Caractere -> ASCII -> Multiplication de l'ASCII par la i+k eme decimale de pi
 * i �tant l'index du caract�re crypt� dans le message et k la cl� -> Modulo 257
 * @author Lucas VANRYB
 *
 */
public class  HomeMade extends Code implements ICode {

	/**
	 * La cl� maximale acc�pt�e par le code
	 */
	private static final double MAX_CLE = 150;

	/*
	 * ATTRIBUTS
	 */

	/**
	 * Tableau contenant les premi�res d�cimales de Pi
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
	 * M�thode permettant de crypter un message � l'aide de ma m�thode maison
	 * @param clair le message � crypter
	 * @param key la cl� � utiliser pour le cryptage, un entier inf�rieur � MAX_KEY
	 * @return le message crypt�
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * M�thode appelant le calcul des d�cimales de Pi,
		 * puis codant les caract�res du message un � un	
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
	 * M�thode remplissant le tableau en m�moire avec les i premi�res d�cimales
	 * de Pi
	 */
	private void getDecimalesPi(int i) {
		this.decimales=Outils.calculerDecimalesPi(i);

	}

	/**
	 * M�thode permettant de d�crypter un message � l'aide ma m�thode
	 * maison
	 * @param crypte le message � decrypter
	 * @param key la cl� de cryptage associ�e
	 * @return le message d�crypt�
	 */
	public IMessage decrypter(IMessage crypte, String key) {
		/*
		 * M�thode appelant le calcul des d�cimales de Pi,
		 * puis d�codant les caract�res du message un � un	
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
	 * M�thode permettant de d�coder un caract�re
	 */
	private char decoderChar(int d, int i) {
		long r=Outils.inverseModulo(this.decimales[i],257);
		return (char)((d*r)%257);
	}

	/*
	 * M�thode permettant d'encoder un caract�re
	 */
	private int encoderChar(char c, int i) {
		int d=(int)c;
		int r=this.decimales[i];
		return (d*r)%257;	
	}

	/**
	 * M�thode permettant de g�nerer une clef valide pour mon code maison
	 * @return une cl� enti�re comprise entre 0 et la cl� maximale d�finie dans le code
	 */
	public String genererCle() {
		return Integer.toString((int)(Math.random()*MAX_CLE));
	}

	/**
	 * M�thode v�rifiant si une cl� est valable pour mon code fait maison
	 * et affichant une erreur si elle ne l'est pas
	 * Une cl� valable pour ce code est une cl� enti�re comprise entre 0 et un constante determin�e
	 * @param k la cl� � tester
	 * @return true si la cl� est valable pour mon code maison, false sinon
	 */
	public boolean estCleValable(String k) {
		/*
		 * La m�thode v�rifie que la cl� est enti�re et qu'elle est comrpise entre 0 et MAX_CLE
		 */
		try {
			int r=Integer.parseInt(k);
			if(r>=0 && r<MAX_CLE) {
				return true;
			}
			else {
				JOptionPane.showMessageDialog(null, "Veuillez saisir une cl� enti�re compris entre 0 et "+MAX_CLE, "Cl� incorrecte", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Veuillez saisir une cl� enti�re compris entre 0 et "+MAX_CLE, "Cl� incorrecte", JOptionPane.ERROR_MESSAGE);
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
