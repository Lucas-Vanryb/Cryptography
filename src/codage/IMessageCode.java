package codage;

import message.IMessage;

/**
 * Interface definissant le type abstrait message cod�, dans lequel s'execute toutes les 
 * op�rations qu'offrent le programme Cryptanalyse
 * @author Lucas VANRYB
 *
 */
public interface IMessageCode {
	
	/*
	 * ACCESSEURS
	 */
	
	/**
	 * M�thode permettant d'obtenir le message cod�
	 * @return le message crypte
	 */
	public IMessage getMessageCode();
	
	/**
	 * M�thode permettant d'obtenir le message clair
	 * @return le message clair
	 */
	public IMessage getMessageDecode();
	
	/**
	 * M�thode permettant d'obtenir le resultat d'une cryptanalyse � clair connu
	 * @return true si la cryptanalyse � clair connu a march�, false sinon ou si elle n'a
	 * pas ete executee
	 */
	public boolean foundKey();
	
	/**
	 * M�thode permettant d'obtenir le resultat d'une cryptanalyse
	 * @return true si la cryptanalyse a march�, false sinon ou si elle n'a
	 * pas ete executee
	 */
	public boolean foundMessage();
	
	/**
	 * M�thode retournant le code associ� � this
	 * @return le ICode associ� � this
	 */
	public ICode getCode();
	
	/**
	 * M�thode retournant la cl� associ�e au IMessageCode
	 * @return la cl� associ�e � this
	 */
	public String getKey();
	
	/**
	 * M�thode renvoyant la dur�e de la derni�re op�ration en ms
	 * @return la dur�e de la derni�re op�ration en ms 
	 */
	public long getDuree();
}
