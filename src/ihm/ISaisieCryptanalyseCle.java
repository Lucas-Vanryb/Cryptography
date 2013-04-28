package ihm;

import message.IMessage;
import codage.Interface;

/**
 * Interface définissant le type abstrait saisie cryptanalyse à clair connu, correspondant à un GUI
 * permettant à l'utilisateur de saisir un message clair, un message codé
 * et eventuellement un type de code
 * @author Lucas VANRYB
 *
 */
public interface ISaisieCryptanalyseCle extends IGUI {

	/*
	 * ACCESSEURS
	 */

	/**
	 * Retourne le message clair saisi par l'utilisateur
	 * @return le message clair saisi par l'utilisateur
	 */
	public IMessage getMessageClair();

	/**
	 * Retourne le message crypte saisi par l'utilisateur
	 * @return le message crypté saisi par l'utilisateur
	 */
	public IMessage getMessageCrypte();

	/**
	 * Retourne le type de code selectionné par l'utilisateur
	 * @return le type de code selectionné par l'utilisateur, null si il n'en a choisi aucun
	 */
	public Interface.CODES getType();
}