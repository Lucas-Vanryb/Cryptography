package lecture;

import message.IMessage;

/**
 * Interface decrivant un type de donn�e abstrait permettant d'obtenir un message
 * d'une source exterieure
 * @author Lucas VANRYB
 *
 */
public interface ILectureMessage {

	/**
	 * Retourne le message lu
	 * @return le message lu
	 */
	public IMessage getMessage();
	
	/**
	 * Retourne true si la lecture a pos� un probl�me, false sinon
	 * @return true si la lecture a pos� un probl�me, false sinon
	 */
	public boolean probleme();
	
}
