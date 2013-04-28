package codage;


/**
 * Classe mod�lisant un code utilisant un syst�me de cl� publique/priv�
 * @see codage.Code
 * @see codage.ICode
 * @see codage.ICodeClePublique
 * @author Lucas VANRYB
 *
 */
public abstract class CodeClePublique extends Code implements ICodeClePublique{

	/*
	 * ATTRIBUTS
	 */

	/**
	 * Variable contenant la cl� priv�
	 */
	protected String privateKey;

	/**
	 * Variable contenant la cl� publique
	 */
	protected String publicKey;

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur initialisant les variables d'instances
	 */
	public CodeClePublique() {
		super();
		this.privateKey="";
		this.publicKey="";
	}

	/*
	 * ACCESSEURS
	 */

	/**
	 * M�thode permettant d'obtenir la cl� priv�e en m�moire de this
	 * @return la cl� priv�e en m�moire
	 */
	public String getPrivateKey() {
		return this.privateKey;
	}

	/**
	 * M�thode permettant d'obtenir la cl� publique en m�moire de this
	 * @return la cl� publique en m�moire
	 */
	public String getPublicKey() {
		return this.publicKey;
	}

	/*
	 * Fonction qui modifie la cl� priv�e en m�moire
	 */
	private void setPrivateKey(String par) {
		this.privateKey=par;
	}

	/*
	 * Fonction qui modifie la cle publique en m�moire
	 */
	private void setPublicKey(String par) {
		this.publicKey=par;
	}

	/*
	 * SERVICES
	 */

	/**
	 * M�thode permettant de d�couper une cl� compl�te en deux cl�s
	 * (publique et priv�) et de les stocker en m�moire
	 * @param infos la cl� � d�couper
	 */
	public void diviserKey(String infos) {
		String[] cles=infos.split(" ");
		if(cles.length>1) {
			this.setPrivateKey(cles[1]);
		}
		this.setPublicKey(cles [0]);
	}

	/**
	 * M�thode permettant de v�rifier si une cl� est correcte
	 * @param k la cl� dont on v�rifie la conformit�
	 */
	public boolean estCleValable(String k) {
		String[] keys=k.split(" ");
		if(keys.length==2 && keys[0]!="" && keys[1]!="") {
			if(this.estClePubliqueValable(keys[0]) && this.estClePriveValable(keys[1])) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * M�thode permettant de tester une cl� priv�e pour un code de meme type que this
	 * @param k la cl� priv�e � tester
	 * @return true si la clef priv�e est valide pour ce code, false sinon
	 */
	public abstract boolean estClePriveValable(String k);

	/**
	 * M�thode permettant de tester une cl� publique pour un code de meme type que this
	 * @param k la cl� publique � tester
	 * @return true si la clef publique est valide pour ce code, false sinon
	 */
	public abstract boolean estClePubliqueValable(String k);
}
