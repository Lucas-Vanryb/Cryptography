package ihm;

import message.IMessage;
import codage.Interface;

/**
 * Interface d�finissant le type abstrait saisie cryptanalyse � clair connu, correspondant � un GUI
 * permettant � l'utilisateur de saisir un message clair, un message cod�
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
	 * @return le message crypt� saisi par l'utilisateur
	 */
	public IMessage getMessageCrypte();

	/**
	 * Retourne le type de code selectionn� par l'utilisateur
	 * @return le type de code selectionn� par l'utilisateur, null si il n'en a choisi aucun
	 */
	public Interface.CODES getType();
}