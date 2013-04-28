package ecriture;

import codage.IMessageCode;
import message.IMessage;

/**
 * Interface décrivant un type de donnée abstrait permettant d'exporter les données vers l'exterieur
 * @author Lucas VANRYB
 *
 */
public interface IEcriture {

	/**
	 * Ecrit le message
	 * @param m le message à ecrire
	 */
	public void ecrireMessage(IMessage m);

	/**
	 * Ecrit la clé et le type de code
	 * @param c le messagecode contenant les informations
	 */
	public void ecrireCle(IMessageCode c);

	/**
	 * Ecrit la clé publique et le type de code
	 * @param c le messagecode contenant les informations
	 */
	public void ecrireClePublique(IMessageCode c);
}
