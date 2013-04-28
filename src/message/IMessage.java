package message;

/**
 * Interface d�finissant le type de donn�e abstrait message,
 * utilis� dans tous les codages.
 * Ce type permet de modifier � volont� les m�thodes equals,
 * et eventuellement toString des messages, ainsi que de les adapter � un certain format
 * en cas de necessit�
 * @author Lucas VANRYB
 *
 */
public interface IMessage {

	/**
	 * Affiche le message dans la console
	 */
	public void afficherMessage();

	/**
	 * Renvoie le ieme caract�re du message, ou cause une exception
	 * @param i l'index du caract�re souhait�
	 * @return le caract�re d'index i du message
	 */
	public char getChar(int i);

	/**
	 * Renvoie le tableau des caract�res composant le message
	 * @return le tableau des caract�res composant le message
	 */
	public char[] getChar();

	public String toString();

	/**
	 * Retourne la taille du message, c'est � dire le nombre de caract�res qui le composent
	 * @return le nombre de caract�res du message
	 */
	public int taille();

	public boolean equals(Object o);

}
