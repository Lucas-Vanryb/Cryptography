package codage;

import message.IMessage;
import fabrique.Fabrique;

/**
 * Classe composée exclusivement de méthodes statiques permettant de faire l'interface entre le package
 * codage et l'IHM
 * @author Lucas VANRYB
 *
 */
public class Interface {

	/**
	 * Enum repertoriant les différents codes, afin de pouvoir les selectionner,
	 * les instancier et les proposer
	 * @author Lucas VANRYB
	 *
	 */
	public enum CODES {
		ENIGMA("Enigma",0),
		ROT13("Rot13",1),
		ROT47("Rot47",2),
		CAESAR("Cesar",3),
		VIGENERE("Vigenere",4),
		PLAYFAIR("Playfair",5),
		BEALE("Beale",6),
		MERKLEHELLMAN("Merkle-Hellman",7),
		RSA("RSA",8),
		WOLSELEY("Wolseley",9),
		HOMEBREW("Maison",10);

		/*
		 * ATTRIBUTS
		 */

		/**
		 * Nom du code
		 */
		String nom;

		/**
		 * Identifiant(unique) du code
		 */
		int n;

		/*
		 * CONSTRUCTEUR
		 */

		/**
		 * 
		 */
		private CODES(String nom, int n) {
			this.n=n;
			this.nom=nom;
		}

		/*
		 * ACCESSEURS
		 */

		/**
		 * Retourne le nom du code
		 * @return le nom du code
		 */
		public String getNom() {
			return this.nom;
		}

		/**
		 * Retourne l'identifiant unique du code
		 * @return l'identifiant unique du code
		 */
		public int getNb() {
			return this.n;
		}

		/**
		 * Retourne true si la cryptanalyse est implémentée pour ce code,
		 * false sinon
		 * @return true si la cryptanalyse est implémentée pour ce code,
		 * false sinon
		 */
		public boolean estCryptanalysable() {
			return this.creerCode() instanceof ICodeCryptanalysable;
		}

		/*
		 * SERVICES
		 */

		/**
		 * Méthode renvoyant une instance de ICode du type associé
		 * à this
		 * @return une instance de ICode du type associé
		 */
		public ICode creerCode() {
			switch(this.getNb()) {
			case 0:
				return Fabrique.fabriquerEnigma();
			case 1:
				return Fabrique.fabriquerRot13();
			case 2:
				return Fabrique.fabriquerRot47();
			case 3:
				return Fabrique.fabriquerCaesar();
			case 4:
				return Fabrique.fabriquerVigenere();
			case 5:
				return Fabrique.fabriquerPlayfair();
			case 6:
				return Fabrique.fabriquerBeale();
			case 7:
				return Fabrique.fabriquerMerkleHellman();
			case 8:
				return Fabrique.fabriquerRsa();
			case 9:
				return Fabrique.fabriquerWolseley();
			case 10:
				return Fabrique.fabriquerHomeBrew();
			default:
				return null;
			}
		}

		public String toString() {
			return this.getNom();
		}
	}

	/**
	 * Méthode renvoyant un IMessageCode après avoir execute l'action de cryptage sur clair
	 * @param clair le message à crypter
	 * @param type le type de code à utilise
	 * @param key la clé à utiliser
	 * @return Le IMessageCode après cryptage, contenant toues les informations 
	 */
	public static IMessageCode crypter(IMessage clair, CODES type, String key) {
		return new MessageCode(clair,type.creerCode(),key);
	}

	/**
	 * Méthode renvoyant un IMessageCode après avoir execute l'action de décryptage sur crypte
	 * @param crypte le message à décrypter
	 * @param type le type de code à utilise
	 * @param key la clé à utiliser
	 * @return Le IMessageCode après décryptage, contenant toues les informations 
	 */
	public static IMessageCode decrypter(IMessage crypte, CODES type, String key) {
		return new MessageCode(type.creerCode(),crypte,key);
	}

	/**
	 * Méthode renvoyant un IMessageCode après avoir tente une cryptanalyse sur crypte
	 * @param crypte le message à cryptanalyser
	 * @param type le type de code à utiliser
	 * @return le IMessageCode après cryptanalyse, contenant toutes les informations
	 */
	public static IMessageCode cryptanalyse(IMessage crypte, CODES type) {
		return new MessageCode(type.creerCode(),crypte);
	}

	/**
	 * Méthode renvoyant une clé compatible avec le type de Code passé en paramètre
	 * @param type le type de code pour lequel on génère une clé
	 * @return une clé compatible avec un code de type typ
	 */
	public static String genererCle(CODES type) {
		return type.creerCode().genererCle();
	}

	/**
	 * Méthode renvoyant un IMessageCode après avoir tente une cryptanalyse sur crypte,
	 * avec tous les codes disponnibles
	 * @param crypte le message à cryptanalyser
	 * @return le IMessageCode après cryptanalyse, contenant toutes les informations
	 */
	public static IMessageCode cryptanalyseTout(IMessage crypte) {
		int i=0;
		CODES[] c=CODES.values();
		IMessageCode m=new MessageCode();
		while(i<c.length && !m.foundMessage()) {
			if(c[i].estCryptanalysable()) {
				m=cryptanalyse(crypte,c[i]);
			}
			i++;
		}
		if(m.foundMessage()) {
			return m;
		}
		else {
			return m;
		}
	}

	/**
	 * Méthode renvoyant un IMessageCode après avoir tente une cryptanalyse à clair connu sur crypte
	 * @param crypte le message crypté
	 * @param clair le message en clair
	 * @param type le type de code à utiliser
	 * @return le IMessageCode après cryptanalyse, contenant toutes les informations
	 */
	public static IMessageCode cryptanalyseCle(IMessage clair, IMessage crypte, CODES type) {
		return new MessageCode(clair,crypte,type.creerCode());
	}

	/**
	 * Méthode renvoyant un IMessageCode après avoir tente une cryptanalyse  à clair connu sur crypte,
	 * avec tous les codes disponnibles
	 * @param clair le message en clair
	 * @param crypte le message crypté
	 * @return le IMessageCode après cryptanalyse, contenant toutes les informations
	 */
	public static IMessageCode cryptanalyseCleTout(IMessage clair, IMessage crypte) {
		int i=0;
		CODES[] c=CODES.values();
		IMessageCode m=new MessageCode();
		while(i<c.length && !m.foundKey()) {
			if(c[i].estCryptanalysable()) {
				m=cryptanalyseCle(clair,crypte,c[i]);
			}
			i++;
		}
		if(m.foundKey()) {
			return m;
		}
		else {
			return m;
		}
	}

	/**
	 * Permet d'identifier le type d'une instance de ICode
	 * @param c le code à identifier
	 * @return un CODES de type correspondant
	 */
	public static CODES identifierCode(ICode c) {
		CODES[] co=CODES.values();
		if(c instanceof Enigma) {
			return co[0];
		}
		if(c instanceof Rot13) {
			return co[1];
		}
		if(c instanceof Rot47) {
			return co[2];
		}
		if(c instanceof Caesar) {
			return co[3];
		}
		if(c instanceof Vigenere) {
			return co[4];
		}
		if(c instanceof Playfair) {
			return co[5];
		}
		if(c instanceof Beale) {
			return co[6];
		}
		if(c instanceof MerkleHellman) {
			return co[7];
		}
		if(c instanceof RSA) {
			return co[8];
		}
		if(c instanceof Wolseley) {
			return co[9];
		}
		if(c instanceof HomeMade) {
			return co[10];
		}
		return null;
	}
}

