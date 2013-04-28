package message;

/**
 * Interface définissant le type de donnée abstrait message,
 * utilisé dans tous les codages.
 * Ce type permet de modifier à volonté les méthodes equals,
 * et eventuellement toString des messages, ainsi que de les adapter à un certain format
 * en cas de necessité
 * @author Lucas VANRYB
 *
 */
public interface IMessage {

	/**
	 * Affiche le message dans la console
	 */
	public void afficherMessage();

	/**
	 * Renvoie le ieme caractère du message, ou cause une exception
	 * @param i l'index du caractère souhaité
	 * @return le caractère d'index i du message
	 */
	public char getChar(int i);

	/**
	 * Renvoie le tableau des caractères composant le message
	 * @return le tableau des caractères composant le message
	 */
	public char[] getChar();

	public String toString();

	/**
	 * Retourne la taille du message, c'est à dire le nombre de caractères qui le composent
	 * @return le nombre de caractères du message
	 */
	public int taille();

	public boolean equals(Object o);

}
