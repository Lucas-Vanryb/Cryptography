package ihm;

/**
 * Interface definissant une interface graphique permettant de selectionner
 * un utilisateur
 * @author Lucas VANRYB
 *
 */
public interface ISelectionUtilisateur extends IGUI {

	/**
	 * Enum contenant et definissant les diff�rents utilisateurs du programme
	 * @author Lucas VANRYB
	 *
	 */
	public enum NOMS{
		ALICE("Alice",true,false,false),
		BOB("Bob",false,true,false),
		EVE("Eve",false,false,true);

		/*
		 * ATTRIBUTS
		 */

		/**
		 * Chaine de caract�re correspondant au nom de l'utilisateur
		 */
		String nom;

		/**
		 * Booleen valant true si l'utilisateur peut crypter
		 */
		boolean cry;

		/**
		 * Booleen valant true si l'utilisateur peut d�crypter
		 */
		boolean decry;

		/**
		 * Booleen valant true si l'utilisateur peut cryptanalyser
		 */
		boolean crypta;

		/*
		 * CONSTRUCTEUR
		 */

		/**
		 * Constructeur initialisant un utilisateur en lui attribuant toutes ses caract�ristiques
		 */
		private NOMS(String nom, boolean cry, boolean decry, boolean crypta) {
			this.nom=nom;
			this.cry=cry;
			this.decry=decry;
			this.crypta=crypta;
		}

		/**
		 * Renvoie true si l'utilisateur peut crypter
		 * @return true si l'utilisateur peut crypter
		 */
		public boolean peutCryptanalyser() {
			return this.crypta;
		}

		/**
		 Renvoie true si l'utilisateur peut d�crypter
		 * @return true si l'utilisateur peut d�crypter
		 */
		public boolean peutCrypter() {
			return this.cry;
		}

		/**
		 Renvoie true si l'utilisateur peut cryptanalyser
		 * @return true si l'utilisateur peut cryptanalyser
		 */
		public boolean peutDecrypter() {
			return this.decry;
		}

		/**
		 Renvoie le nom de l'utilisateur
		 * @return le nom de l'utilisateur
		 */
		public String getNom() {
			return this.nom;
		}

		/**
		 * Renvoie true si l'utilisateur peut generer une cle
		 * @return true si l'utilisateur peut generer une cle
		 */
		public boolean peutGenerer() {
			return !this.peutCryptanalyser();
		}

		public String toString() {
			return this.nom;
		}	
	}

	/**
	 * Retourne l'utilisateur selectionn� par l'utilisateur
	 * @return l'utilisateur selectionn� par l'utilisateur
	 */
	public ISelectionUtilisateur.NOMS getUser();
}