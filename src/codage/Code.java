package codage;

import message.IMessage;

/**
 * Classe representant un Code en m�moire, avec toutes les op�rations associ�es
 * 
 * @see codage.ICode
 * @author Lucas VANRYB
 *
 */
public abstract class Code implements ICode {

	/**
	 * Variable constante utilis�e pour definir le seuil max acceptable 
	 * de d�rivation lors de l'analyse frequentielle.
	 * Plus cette constante est elevee, moins le programme sera exigeant pour considerer une analyse
	 * frequentielle reussie
	 */
	final static double cst=0.10;

	/*
	 * ATTRIBUTS
	 */

	/**
	 * Booleen permettant de stocker en memoire le resultat d'une op�ration de cryptanalyse
	 */
	protected boolean succesCrypto;

	/**
	 * Booleen permettant de stocker en memoire le resultat d'une op�ration de cryptanalyse � clair
	 * connu
	 */
	protected boolean succesCle;

	/**
	 * Variable stockant le temps en ms qu'� dur�e la derni�re op�ration r�alis�e
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
	 * Methode permettant de savoir si la cryptanalyse � clair connu a fonctionn�e
	 * @return true si la m�thode de cryptanalyse � clair connu a ete appel�e et a fonctionne
	 */
	public boolean succesCle() {
		return this.succesCle;
	}

	/**
	 * Methode permettant de savoir si la cryptanalyse a fonctionn�e
	 * @return true si la m�thode de cryptanalyse a ete appel�e et a fonctionne
	 */
	public boolean succesCrypto() {
		return this.succesCrypto;
	}

	/**
	 * M�thode permettant de savoir le temps qu'� pris la derni�re op�ration effectu�e
	 * @return la dur�e en ms de la derni�re action
	 */	
	public long tempsCrypto() {
		return this.time;
	}

	/*
	 * SERVICES
	 */

	/**
	 * M�thode permettant de crypter un message
	 * @param clair le message � crypter
	 * @param key la cl� � utiliser pour le cryptage
	 * @return le message crypt�
	 */
	public abstract IMessage crypter(IMessage clair, String key);

	/**
	 * M�thode permettant de d�crypter un message
	 * @param crypte le message � decrypter
	 * @param key la cl� de cryptage associ�e
	 * @return le message d�crypt�
	 */
	public abstract IMessage decrypter(IMessage crypte, String key);

	/**
	 * M�thode permettant de g�nerer une clef valide pour le type de code de this
	 * @return une cl� valide pour ce code
	 */
	public abstract String genererCle();

	/**
	 * M�thode permettant de v�rifier si une cl� est correcte
	 * @param k la cl� dont on v�rifie la conformit�
	 * @return true si la cl� est valable, false sinon
	 */
	public abstract boolean estCleValable(String k);
}
