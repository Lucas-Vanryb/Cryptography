package codage;


/**
 * Classe modélisant un code utilisant un système de clé publique/privé
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
	 * Variable contenant la clé privé
	 */
	protected String privateKey;

	/**
	 * Variable contenant la clé publique
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
	 * Méthode permettant d'obtenir la clé privée en mémoire de this
	 * @return la clé privée en mémoire
	 */
	public String getPrivateKey() {
		return this.privateKey;
	}

	/**
	 * Méthode permettant d'obtenir la clé publique en mémoire de this
	 * @return la clé publique en mémoire
	 */
	public String getPublicKey() {
		return this.publicKey;
	}

	/*
	 * Fonction qui modifie la clé privée en mémoire
	 */
	private void setPrivateKey(String par) {
		this.privateKey=par;
	}

	/*
	 * Fonction qui modifie la cle publique en mémoire
	 */
	private void setPublicKey(String par) {
		this.publicKey=par;
	}

	/*
	 * SERVICES
	 */

	/**
	 * Méthode permettant de découper une clé complète en deux clés
	 * (publique et privé) et de les stocker en mémoire
	 * @param infos la clé à découper
	 */
	public void diviserKey(String infos) {
		String[] cles=infos.split(" ");
		if(cles.length>1) {
			this.setPrivateKey(cles[1]);
		}
		this.setPublicKey(cles [0]);
	}

	/**
	 * Méthode permettant de vérifier si une clé est correcte
	 * @param k la clé dont on vérifie la conformité
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
	 * Méthode permettant de tester une clé privée pour un code de meme type que this
	 * @param k la clé privée à tester
	 * @return true si la clef privée est valide pour ce code, false sinon
	 */
	public abstract boolean estClePriveValable(String k);

	/**
	 * Méthode permettant de tester une clé publique pour un code de meme type que this
	 * @param k la clé publique à tester
	 * @return true si la clef publique est valide pour ce code, false sinon
	 */
	public abstract boolean estClePubliqueValable(String k);
}
