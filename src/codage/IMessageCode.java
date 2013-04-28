package codage;

import message.IMessage;

/**
 * Interface definissant le type abstrait message codé, dans lequel s'execute toutes les 
 * opérations qu'offrent le programme Cryptanalyse
 * @author Lucas VANRYB
 *
 */
public interface IMessageCode {
	
	/*
	 * ACCESSEURS
	 */
	
	/**
	 * Méthode permettant d'obtenir le message codé
	 * @return le message crypte
	 */
	public IMessage getMessageCode();
	
	/**
	 * Méthode permettant d'obtenir le message clair
	 * @return le message clair
	 */
	public IMessage getMessageDecode();
	
	/**
	 * Méthode permettant d'obtenir le resultat d'une cryptanalyse à clair connu
	 * @return true si la cryptanalyse à clair connu a marché, false sinon ou si elle n'a
	 * pas ete executee
	 */
	public boolean foundKey();
	
	/**
	 * Méthode permettant d'obtenir le resultat d'une cryptanalyse
	 * @return true si la cryptanalyse a marché, false sinon ou si elle n'a
	 * pas ete executee
	 */
	public boolean foundMessage();
	
	/**
	 * Méthode retournant le code associé à this
	 * @return le ICode associé à this
	 */
	public ICode getCode();
	
	/**
	 * Méthode retournant la clé associée au IMessageCode
	 * @return la clé associée à this
	 */
	public String getKey();
	
	/**
	 * Méthode renvoyant la durée de la dernière opération en ms
	 * @return la durée de la dernière opération en ms 
	 */
	public long getDuree();
}
