package ecriture;

import message.IMessage;
import codage.IMessageCode;
import codage.Interface;
import fabrique.Fabrique;

/**
 * Classe ne contenant que des méthodes statiques permettant de simplifier
 * les accès à IEcriture
 * @author Lucas VANRYB
 *
 */
public class Ecrire {

	/**
	 * Ecrit la clé et le type de code dans un fichier texte
	 * @param nomFichier chemin vers le fichier texte dans lequel ecrire
	 * @param m le messagecode contenant les informations
	 */
	public static void ecrireCle(String nomFichier, IMessageCode m) {
		new Ecriture(nomFichier).ecrireCle(m);
	}

	/**
	 * Ecrit la clé publique et le type de code dans un fichier texte
	 * @param nomFichier chemin vers le fichier texte dans lequel ecrire
	 * @param m le messagecode contenant les informations
	 */
	public static void ecrireClePublique(String nomFichier, IMessageCode m) {
		new Ecriture(nomFichier).ecrireClePublique(m);
	}

	/**
	 * Ecrit le message dans un fichier texte
	 * @param nomFichier chemin vers le fichier texte dans lequel ecrire
	 * @param m le message à ecrire
	 */
	public static void ecrireMessage(String nomFichier, IMessage m) {
		new Ecriture(nomFichier).ecrireMessage(m);
	}

	/**
	 * Méthode main de test
	 * @param args
	 */
	public static void main(String[] args) {
		ecrireMessage("test1.txt",Fabrique.fabriquerMessage("lol"+'\n'+"kikoo"));
		ecrireCle("test2.txt",Interface.crypter(Fabrique.fabriquerMessage("test"+'\n'+"numero"+'\n'+"2"), Interface.CODES.MERKLEHELLMAN, Interface.genererCle(Interface.CODES.MERKLEHELLMAN)));
		ecrireClePublique("test3.txt",Interface.crypter(Fabrique.fabriquerMessage("test"+'\n'+"numero"+'\n'+"3"), Interface.CODES.RSA, Interface.genererCle(Interface.CODES.RSA)));
	}
}
