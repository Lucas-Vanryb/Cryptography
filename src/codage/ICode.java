package codage;

import message.IMessage;

/**
 * Interface d�finissant l'ensemble des m�thodes n�cessaires � un Code.
 * @author Lucas VANRYB
 *
 */
public interface ICode {

	/*
	 * ACCESSEURS
	 */
	
	/**
	 * M�thode permettant de savoir le temps qu'� pris la derni�re op�ration effectu�e
	 * @return la dur�e en ms de la derni�re action
	 */
	public long tempsCrypto();
	
	/**
	 * Methode permettant de savoir si la cryptanalyse a fonctionn�e
	 * @return true si la m�thode de cryptanalyse a ete appel�e et a fonctionne
	 */
	public boolean succesCrypto();
	
	/**
	 * Methode permettant de savoir si la cryptanalyse � clair connu a fonctionn�e
	 * @return true si la m�thode de cryptanalyse � clair connu a ete appel�e et a fonctionne
	 */
	public boolean succesCle();
	
	/*
	 * SERVICES
	 */
	
	/**
	 * M�thode permettant de crypter un message
	 * @param clair le message � crypter
	 * @param key la cl� � utiliser pour le cryptage
	 * @return le message crypt�
	 */
	public IMessage crypter(IMessage clair, String key);
	
	/**
	 * M�thode permettant de d�crypter un message
	 * @param crypte le message � decrypter
	 * @param key la cl� de cryptage associ�e
	 * @return le message d�crypt�
	 */
	public IMessage decrypter(IMessage crypte, String key);
	
	/**
	 * M�thode permettant de g�nerer une clef valide pour le type de code de this
	 * @return une cl� valide pour ce code
	 */
	public String genererCle();
	
	/**
	 * M�thode permettant de v�rifier si une cl� est correcte
	 * @param k la cl� dont on v�rifie la conformit�
	 * @return true si la cl� est valable, false sinon
	 */
	public boolean estCleValable(String k);
}
