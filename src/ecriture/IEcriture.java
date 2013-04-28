package ecriture;

import codage.IMessageCode;
import message.IMessage;

/**
 * Interface d�crivant un type de donn�e abstrait permettant d'exporter les donn�es vers l'exterieur
 * @author Lucas VANRYB
 *
 */
public interface IEcriture {

	/**
	 * Ecrit le message
	 * @param m le message � ecrire
	 */
	public void ecrireMessage(IMessage m);

	/**
	 * Ecrit la cl� et le type de code
	 * @param c le messagecode contenant les informations
	 */
	public void ecrireCle(IMessageCode c);

	/**
	 * Ecrit la cl� publique et le type de code
	 * @param c le messagecode contenant les informations
	 */
	public void ecrireClePublique(IMessageCode c);
}
