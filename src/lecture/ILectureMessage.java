package lecture;

import message.IMessage;

/**
 * Interface decrivant un type de donnée abstrait permettant d'obtenir un message
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
	 * Retourne true si la lecture a posé un problème, false sinon
	 * @return true si la lecture a posé un problème, false sinon
	 */
	public boolean probleme();
	
}
