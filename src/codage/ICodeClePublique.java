package codage;

/**
 * Interface d�finissant les m�thodes n�cessaires �
 * un Code fonctionnant � l'aide d'un syst�me de cl� publique/priv�
 * @see codage.ICode
 * @author Lucas VANRYB
 *
 */
public interface ICodeClePublique extends ICode {

	/*
	 * ACCESSEURS
	 */
	
	/**
	 * M�thode permettant d'obtenir la cl� publique en m�moire de this
	 * @return la cl� publique en m�moire
	 */
	public String getPublicKey();
	
	/**
	 * M�thode permettant d'obtenir la cl� priv�e en m�moire de this
	 * @return la cl� priv�e en m�moire
	 */
	public String getPrivateKey();
	
	/*
	 * SERVICES
	 */
	
	/**
	 * M�thode permettant de d�couper une cl� compl�te en deux cl�s
	 * (publique et priv�) et de les stocker en m�moire
	 * @param key la cl� � d�couper
	 */
	public void diviserKey(String key);
	
	/**
	 * M�thode permettant de tester une cl� priv�e pour un code de meme type que this
	 * @param k la cl� priv�e � tester
	 * @return true si la clef priv�e est valide pour ce code, false sinon
	 */
	public boolean estClePriveValable(String k);

	/**
	 * M�thode permettant de tester une cl� publique pour un code de meme type que this
	 * @param k la cl� publique � tester
	 * @return true si la clef publique est valide pour ce code, false sinon
	 */
	public boolean estClePubliqueValable(String k);
	
}
