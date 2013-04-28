package ihm;

/**
 * Interface decrivant le type abstrait d'une interface graphique
 * @author Lucas VANRYB
 *
 */
public interface IGUI {
	
	/*
	 * ACCESSEURS
	 */
	
	/**
	 * Retourne true si l'utilisateur n'a pas fini, false sinon
	 * @return true si l'utilisateur n'a pas fini, false sinon
	 */
	public boolean estEnCours();

	/**
	 * Masque l'interface graphique
	 */
	public void masquer();

	/**
	 * Libere la memoire occupée par l'interface graphique
	 */
	public void liberer();
}
