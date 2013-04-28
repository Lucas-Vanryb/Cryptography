package codage;

import message.IMessage;

/**
 * Classe representant un Code en mémoire, avec toutes les opérations associées
 * 
 * @see codage.ICode
 * @author Lucas VANRYB
 *
 */
public abstract class Code implements ICode {

	/**
	 * Variable constante utilisée pour definir le seuil max acceptable 
	 * de dérivation lors de l'analyse frequentielle.
	 * Plus cette constante est elevee, moins le programme sera exigeant pour considerer une analyse
	 * frequentielle reussie
	 */
	final static double cst=0.10;

	/*
	 * ATTRIBUTS
	 */

	/**
	 * Booleen permettant de stocker en memoire le resultat d'une opération de cryptanalyse
	 */
	protected boolean succesCrypto;

	/**
	 * Booleen permettant de stocker en memoire le resultat d'une opération de cryptanalyse à clair
	 * connu
	 */
	protected boolean succesCle;

	/**
	 * Variable stockant le temps en ms qu'à durée la dernière opération réalisée
	 */
	protected long time;

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur initialisant les variables d'instances
	 */
	protected Code() {
		this.succesCrypto=false;
		this.succesCle=false;
		this.time=0;
	}

	/*
	 * ACCESSEURS
	 */

	/**
	 * Methode permettant de savoir si la cryptanalyse à clair connu a fonctionnée
	 * @return true si la méthode de cryptanalyse à clair connu a ete appelée et a fonctionne
	 */
	public boolean succesCle() {
		return this.succesCle;
	}

	/**
	 * Methode permettant de savoir si la cryptanalyse a fonctionnée
	 * @return true si la méthode de cryptanalyse a ete appelée et a fonctionne
	 */
	public boolean succesCrypto() {
		return this.succesCrypto;
	}

	/**
	 * Méthode permettant de savoir le temps qu'à pris la dernière opération effectuée
	 * @return la durée en ms de la dernière action
	 */	
	public long tempsCrypto() {
		return this.time;
	}

	/*
	 * SERVICES
	 */

	/**
	 * Méthode permettant de crypter un message
	 * @param clair le message à crypter
	 * @param key la clé à utiliser pour le cryptage
	 * @return le message crypté
	 */
	public abstract IMessage crypter(IMessage clair, String key);

	/**
	 * Méthode permettant de décrypter un message
	 * @param crypte le message à decrypter
	 * @param key la clé de cryptage associée
	 * @return le message décrypté
	 */
	public abstract IMessage decrypter(IMessage crypte, String key);

	/**
	 * Méthode permettant de génerer une clef valide pour le type de code de this
	 * @return une clé valide pour ce code
	 */
	public abstract String genererCle();

	/**
	 * Méthode permettant de vérifier si une clé est correcte
	 * @param k la clé dont on vérifie la conformité
	 * @return true si la clé est valable, false sinon
	 */
	public abstract boolean estCleValable(String k);
}
