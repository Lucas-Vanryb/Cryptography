package fabrique;

import message.IMessage;
import message.Message;
import codage.Beale;
import codage.Caesar;
import codage.Enigma;
import codage.HomeMade;
import codage.ICode;
import codage.MerkleHellman;
import codage.Playfair;
import codage.RSA;
import codage.Rot13;
import codage.Rot47;
import codage.Vigenere;
import codage.Wolseley;

/**
 * Classe composée de fonction statiques permettant de regrouper toutes les instanciations des objets de chaque type 
 * sur une ligne de code, afin de pouvoir modifier le code plus aisément
 * @author Lucas VANRYB
 *
 */
public class Fabrique {

	public static IMessage fabriquerMessage(String s) {
		return new Message(s);
	}

	public static IMessage fabriquerMessage(char[] c) {
		return new Message(c);
	}

	public static IMessage fabriquerMessage(Character[] c) {
		return new Message(c);
	}

	public static IMessage fabriquerMessage(IMessage m) {
		return new Message(m);
	}

	public static ICode fabriquerRot13() {
		return new Rot13();
	}

	public static ICode fabriquerRot47() {
		return new Rot47();
	}

	public static ICode fabriquerCaesar() {
		return new Caesar();
	}

	public static ICode fabriquerBeale() {
		return new Beale();
	}

	public static ICode fabriquerPlayfair() {
		return new Playfair();
	}

	public static ICode fabriquerVigenere() {
		return new Vigenere();
	}

	public static ICode fabriquerMerkleHellman() {
		return new MerkleHellman();
	}

	public static ICode fabriquerRsa() {
		return new RSA();
	}

	public static ICode fabriquerEnigma() {
		return new Enigma();
	}

	public static ICode fabriquerWolseley() {
		return new Wolseley();
	}

	public static ICode fabriquerHomeBrew() {
		return new HomeMade();
	}
}
