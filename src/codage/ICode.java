package codage;

import message.IMessage;

/**
 * Interface définissant l'ensemble des méthodes nécessaires à un Code.
 * @author Lucas VANRYB
 *
 */
public interface ICode {

	/*
	 * ACCESSEURS
	 */
	
	/**
	 * Méthode permettant de savoir le temps qu'à pris la dernière opération effectuée
	 * @return la durée en ms de la dernière action
	 */
	public long tempsCrypto();
	
	/**
	 * Methode permettant de savoir si la cryptanalyse a fonctionnée
	 * @return true si la méthode de cryptanalyse a ete appelée et a fonctionne
	 */
	public boolean succesCrypto();
	
	/**
	 * Methode permettant de savoir si la cryptanalyse à clair connu a fonctionnée
	 * @return true si la méthode de cryptanalyse à clair connu a ete appelée et a fonctionne
	 */
	public boolean succesCle();
	
	/*
	 * SERVICES
	 */
	
	/**
	 * Méthode permettant de crypter un message
	 * @param clair le message à crypter
	 * @param key la clé à utiliser pour le cryptage
	 * @return le message crypté
	 */
	public IMessage crypter(IMessage clair, String key);
	
	/**
	 * Méthode permettant de décrypter un message
	 * @param crypte le message à decrypter
	 * @param key la clé de cryptage associée
	 * @return le message décrypté
	 */
	public IMessage decrypter(IMessage crypte, String key);
	
	/**
	 * Méthode permettant de génerer une clef valide pour le type de code de this
	 * @return une clé valide pour ce code
	 */
	public String genererCle();
	
	/**
	 * Méthode permettant de vérifier si une clé est correcte
	 * @param k la clé dont on vérifie la conformité
	 * @return true si la clé est valable, false sinon
	 */
	public boolean estCleValable(String k);
}
