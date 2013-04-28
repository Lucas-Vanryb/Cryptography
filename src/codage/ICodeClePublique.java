package codage;

/**
 * Interface définissant les méthodes nécessaires à
 * un Code fonctionnant à l'aide d'un système de clé publique/privé
 * @see codage.ICode
 * @author Lucas VANRYB
 *
 */
public interface ICodeClePublique extends ICode {

	/*
	 * ACCESSEURS
	 */
	
	/**
	 * Méthode permettant d'obtenir la clé publique en mémoire de this
	 * @return la clé publique en mémoire
	 */
	public String getPublicKey();
	
	/**
	 * Méthode permettant d'obtenir la clé privée en mémoire de this
	 * @return la clé privée en mémoire
	 */
	public String getPrivateKey();
	
	/*
	 * SERVICES
	 */
	
	/**
	 * Méthode permettant de découper une clé complète en deux clés
	 * (publique et privé) et de les stocker en mémoire
	 * @param key la clé à découper
	 */
	public void diviserKey(String key);
	
	/**
	 * Méthode permettant de tester une clé privée pour un code de meme type que this
	 * @param k la clé privée à tester
	 * @return true si la clef privée est valide pour ce code, false sinon
	 */
	public boolean estClePriveValable(String k);

	/**
	 * Méthode permettant de tester une clé publique pour un code de meme type que this
	 * @param k la clé publique à tester
	 * @return true si la clef publique est valide pour ce code, false sinon
	 */
	public boolean estClePubliqueValable(String k);
	
}
