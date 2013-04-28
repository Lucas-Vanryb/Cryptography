package ihm;

import java.lang.reflect.Constructor;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import message.IMessage;
import codage.IMessageCode;
import codage.Interface;
import fabrique.Fabrique;

public class IHM {

	/*
	 * ATTRIBUTS
	 */

	/**
	 * Le message saisi par l'utilisateur
	 */
	private IMessage m;

	/**
	 * La clé saisie par l'utilisateur
	 */
	private String key;

	/**
	 * Le type de code selectionnée par l'utilisateur
	 */
	private Interface.CODES typeCode;

	/**
	 * L'action selectionée par l'utilisateur
	 */
	private ACTIONS action;

	/**
	 * Le nom de l'utilisateur connecté
	 */
	private ISelectionUtilisateur.NOMS nom;

	/**
	 * Le booleen valant true si l'ordinateur executant est un mac
	 */
	public boolean macOS=false;

	/**
	 * Booleen permettant d'interrompre une action de l'exeterieur d'ihm
	 */
	private boolean stop;

	/**
	 * Barre de menu
	 */
	private JMenuBar menu;

	/**
	 * IMessageCode contenant le resultat de la derniere operation
	 */
	private IMessageCode res;

	/**
	 * Booleen permettant de deconnecter l'utilisateur de l'exterieur d'ihm
	 */
	private boolean deco;


	/**
	 * Booleen determinant si le programme fonctionne en mode réel
	 * Si reel est à true le programme est en mode reel, il n'y a pas d'utilisateur et 
	 * toutes les actions sont accessibles
	 * Sinon le système de connexion d'utilisateur est utilisé, et les actions sont limitées
	 * au possibilités de l'utilisateur connecté
	 */
	final static boolean reel=false;

	/**
	 * Booléen determinant si le menu edition doit apparaitre ou non dans la barre de menu
	 */
	final static boolean edition=true;

	/**
	 * Enum referencant les actions disponnibles du programme
	 * @author Lucas VANRYB
	 *
	 */
	public enum ACTIONS{
		GENERER("Générer une clé",1),
		CRYPTER("Crypter un message",2),
		CRYPTANALYSE("Cryptanalyser un message",3),
		CRYPTANALYSECLAIR("Cryptanalyser à clair connu",4),
		DECRYPTER("Décrypter un message",5);

		/*
		 * ATTRIBUTS
		 */

		/**
		 * Le nom de l'action
		 */
		private String nom;

		/**
		 * L'identifiant unique de l'action
		 */
		private int nb;

		/*
		 * CONSTRUCTEUR
		 */

		/**
		 * Initialise les ACTIONS
		 */
		private ACTIONS(String nom, int nb) {
			this.nom=nom;
			this.nb=nb;
		}

		/*
		 * ACCESSEUR
		 */

		/**
		 * Retourne l'identifiant unique de l'action
		 * @return l'identifiant unique de l'action
		 */
		public int getNb() {
			return this.nb;
		}

		/*
		 * SERVICE
		 */

		public String toString() {
			return this.nom;
		}
	}

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur initialisant les variables d'instances d'IHM
	 */
	public IHM() {
		this.macOS=this.isMacOS();
		if(reel) {
			this.getMenuBar(edition);
			this.deco=false;
		}
		this.reinitialiser();
		this.reinitialiserUser();
	}

	/*
	 * SERVICES
	 */

	/*
	 * Reinitialise certains attributs
	 */
	private void reinitialiser() {
		this.stop=false;
		this.m=Fabrique.fabriquerMessage("");
		this.key=null;
		this.typeCode=null;
		this.action=null;
		this.res=null;
	}

	/*
	 * Reinitialise l'attribut utilisateur
	 */
	private void reinitialiserUser() {
		this.nom=null;
	}

	/*
	 * Méthode permettant à l'utilisateur de saisir toutes les données necessaires à
	 * un cryptage ou un décryptage
	 * puis appelle la fonction enregistrer associée
	 */
	private void getSaisie(boolean decrypt) {
		ISaisie s=new Saisie(this.menu,decrypt,this);
		while(s.estEnCours() && !this.stop) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		s.masquer();
		if(!this.stop) {
			this.key=s.getKey();
			this.m=s.getMessage();
			this.typeCode=s.getTypeCode();
			if(decrypt) {
				this.res=Interface.decrypter(this.m, this.typeCode, this.key);
				s.liberer();
				s=null;
				this.enregistrer("décrypté");
			}
			else {
				this.res=Interface.crypter(this.m, this.typeCode, this.key);
				s.liberer();
				s=null;
				this.enregistrer("crpyté");
			}
		}
		else {
			this.stop=false;
			s.liberer();
			s=null;
			this.action();
		}
	}

	/*
	 * Méthode permettant à l'utilisateur après un cryptage ou un décryptage d'afficher le message obtenu
	 * ainsi que la clé et le type de code utilisés et propose de les enregistrer
	 * puis appelle la selection d'action
	 */
	private void enregistrer(String s) {
		EnregistrerTexte e;
		if(s.equals("décrypté")) {
			e=new EnregistrerTexte(s,this.res.getMessageDecode(),this.res, this.menu,this);
		}
		else {
			e=new EnregistrerTexte(s,this.res.getMessageCode(),this.res, this.menu,this);
		}
		while(e.estEnCours() && !this.stop) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
			}
		}
		this.stop=false;
		e.masquer();
		e.liberer();
		e=null;
		this.action();
	}

	/*
	 * Méthode n'existant qu'hors du mode reel permettant de selectionner une action parmi les actions autorisées
	 * pour l'utilisateur connecté
	 */
	private void getAction() {
		this.reinitialiser();
		if(!reel) {
			String[] options=this.getOptions();
			int reponse=JOptionPane.showOptionDialog(null, "Quelle action souhaitez vous effectuer ?", "Choix de l'action", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
			if (!(reponse==JOptionPane.OK_OPTION)) {
				this.reglerAction(options[1]);
			}
			else {
				this.reglerAction(options[0]);
			}
		}
		else {
			this.getActionReelle();
		}
		this.action();
	}

	/*
	 * Méthode permettant à getAction() de lire l'action saisie par l'utilisateur
	 */
	private void reglerAction(String s) {
		if(s.equals("Crypter un message")) {
			this.action=ACTIONS.CRYPTER;
		}
		if(s.equals("Générer une clé")) {
			this.action=ACTIONS.GENERER;
		}
		if(s.equals("Décrypter un message")) {
			this.action=ACTIONS.DECRYPTER;
		}
		if(s.equals("Cryptanalyser")) {
			this.action=ACTIONS.CRYPTANALYSE;
		}
		if(s.equals("Cryptanalyser à clair connu")) {
			this.action=ACTIONS.CRYPTANALYSECLAIR;
		}
	}


	/*
	 * Méthode permettant d'obtenir les actions autorisées en fonction de l'utilisateur connecté
	 */
	private String[] getOptions() {
		String[] aRetourner=new String[2];
		if(this.nom==ISelectionUtilisateur.NOMS.BOB) {
			aRetourner[0]="Crypter un message";
			aRetourner[1]="Générer une clé";
		}
		else {
			if(this.nom==ISelectionUtilisateur.NOMS.ALICE) {
				aRetourner[0]="Décrypter un message";
				aRetourner[1]="Générer une clé";
			}
			else {
				aRetourner[0]="Cryptanalyser";
				aRetourner[1]="Cryptanalyser à clair connu";
			}
		}
		return aRetourner;
	}

	/*
	 * Méthode permettant à l'utilisateur d'afficher et d'enregistrer la clé qui
	 * vient d'etre cryptanalysée, puis appelle la selection d'action
	 */
	private void enregistrerCle() {
		EnregistrerCle e=new EnregistrerCle(this.res, this.menu, this);
		while(e.estEnCours() && !this.stop) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
			}
		}
		this.stop=false;
		e.masquer();
		e.liberer();
		e=null;
		this.action();
	}

	/*
	 * Méthode appelant la méthode de saisie adaptée à l'action qui a été selectionnée
	 * ou qui appelle la méthode de saisie d'action si aucune action n'est selectionnée
	 */
	private void action() {
		if(this.action!=null) {
			switch(this.action.getNb()) {
			case 1:
				this.generer();
			case 2:
				this.getSaisie(false);
			case 3:
				this.getSaisieCryptanalyse();
			case 4:
				this.getSaisieCryptanalyseCle();
			default:
				this.getSaisie(true);
			}
		}
		else {
			if(!this.deco) {
				this.getAction();
			}
			else {
				this.getUtilisateur();
			}
		}

	}

	/*
	 * Méthode permettant à l'utilisateur de saisir toutes les données necessaires à
	 * une cryptanalyse, puis l'effectue et si elle fonctionne appelle la fonction enregistrer
	 * adaptée
	 */
	private void getSaisieCryptanalyse() {
		ISaisieCryptanalyse s=new SaisieCryptanalyse(this, this.menu);
		while(s.estEnCours() && !this.stop) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		s.masquer();
		if(!this.stop) {
			if(s.getType()!=null) {
				IMessage m=s.getMessage();
				Interface.CODES typ=s.getType();
				this.res=Interface.cryptanalyse(m, typ);
			}
			else {
				IMessage m=s.getMessage();
				this.res=Interface.cryptanalyseTout(m);
			}
			if(this.res.foundMessage()) {
				s.liberer();
				s=null;
				this.enregistrerMessage();
			}
			else {
				JOptionPane.showMessageDialog(null, "Echec de la cryptanalyse", "Echec", JOptionPane.WARNING_MESSAGE);
				s.liberer();
				s=null;
				this.setAction(ACTIONS.CRYPTANALYSE);
				this.action();
			}
		}
	}

	/*
	 * Méthode permettant à l'utilisateur de saisir toutes les données necessaires à
	 * une cryptanalyse à clair connu, puis l'effectue et si elle fonctionne appelle la fonction enregistrer
	 * adaptée
	 */
	private void getSaisieCryptanalyseCle() {
		ISaisieCryptanalyseCle s=new SaisieCryptanalyseCle(this, this.menu);
		while(s.estEnCours() && !this.stop) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		s.masquer();
		if(!this.stop) {
			if(s.getType()!=null) {
				IMessage m=s.getMessageClair();
				IMessage mCrypte=s.getMessageCrypte();
				Interface.CODES typ=s.getType();
				this.res=Interface.cryptanalyseCle(m, mCrypte, typ);
			}
			else {
				IMessage m=s.getMessageClair();
				IMessage mCrypte=s.getMessageCrypte();
				this.res=Interface.cryptanalyseCleTout(m,mCrypte);
			}
			if(this.res.foundKey()) {
				s.liberer();
				s=null;
				this.enregistrerCle();
			}
			else {
				JOptionPane.showMessageDialog(null, "Echec de la cryptanalyse", "Echec", JOptionPane.WARNING_MESSAGE);
				s.liberer();
				s=null;
				this.setAction(ACTIONS.CRYPTANALYSECLAIR);
				this.action();
			}
		}
	}

	/*
	 * Méthode permettant à l'utilisateur d'afficher et d'enregistrer un message cryptanalysé
	 * Puis appelle la selection d'action
	 */
	private void enregistrerMessage() {
		EnregistrerMessage e=new EnregistrerMessage(this, this.res, this.menu);
		while(e.estEnCours() && !this.stop) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
			}
		}
		this.stop=false;
		e.masquer();
		e.liberer();
		e=null;
		this.action();

	}

	/*
	 * Méthode créant la barre des menus
	 */
	private void getMenuBar(boolean edition) {
		this.menu=new MenuBar(this.nom,edition,this.macOS,this);
	}

	/*
	 * Méthode demandant à l'utilisateur pour quel type de code il veut generer une cle
	 * puis genere la clé et appelle la méthode enregistrer associée
	 */
	private void generer() {
		Generer g=new Generer(this,this.menu);
		while(g.estEnCours() && !this.stop) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		g.masquer();
		if(!this.stop) {
			this.res=Interface.crypter(Fabrique.fabriquerMessage("Ce message est inutile"), g.getTypCode(), Interface.genererCle(g.getTypCode()));
			g.liberer();
			g=null;
			this.enregistrerCle();
		}
		else {
			this.stop=false;
			g.liberer();
			g=null;
			this.action();
		}
	}

	/*
	 * Méthode n'existant qu'en dehors du mode réel, permet de demander à l'utilisateur
	 * de s'identifier, puis appelle la demande de saisie d'action, et crée la barre de menu
	 */
	private void getUtilisateur() {
		this.reinitialiserUser();
		ISelectionUtilisateur s=new SelectionUtilisateur();
		while (s.estEnCours()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		this.nom=s.getUser();
		this.deco=false;
		s.masquer();
		s.liberer();
		s=null;
		this.getMenuBar(edition);
		this.getAction();
	}

	@SuppressWarnings({ "rawtypes" })
	/*
	 * Méthode renvoyant true si l'utilisateur est sous macOS et false sinon
	 * De plus, si l'utilisateur est sous mac, execute la classe Mac qui applique
	 * la configuration propre à MacOS 
	 */
	private boolean isMacOS() {
		if (System.getProperty("os.name").charAt(0)=='M') {
			try {
				Class mac_class = Class.forName("ihm.Mac");
				Constructor new_one = mac_class.getConstructor();
				new_one.newInstance();
			}
			catch(Exception e) {
				return false;
			}

			return true;
		}
		else {
			return false;
		}
	}

	/*
	 * Utilisé en mode réel uniquemment, permet à l'utilisateur de selectionner une action
	 * parmi toutes celles que le programme propose, puis appelle la saisie adaptée
	 */
	private void getActionReelle() {
		ISelectionActionReel s=new SelectionActionReel();
		while(s.estEnCours()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		this.action=s.getAction();
		s.masquer();
		s=null;
		this.action();
	}

	/**
	 * Méthode permettant d'interrompre l'action en cours
	 */
	public void stop() {
		this.stop=true;
	}

	/**
	 * Méthode permenant de determiner la prochaine action que devra faire le proramme, à la fin de
	 * celle en cours
	 * @param act la prochaine action à effectuer
	 */
	public void setAction(ACTIONS act) {
		this.action=act;
	}

	/**
	 * Lance l'IHM, et donc le programme en appelant la première action à accomplir
	 * en fonction du mode d'utilisation
	 */
	public void start() {
		if(!reel) {
			this.getUtilisateur();
		}
		else {
			this.getAction();
		}
	}

	/**
	 * Deconnecte l'utilisateur, ce qui a pour effet de redemander une identification à la prochaine action
	 */
	public void deconnecter() {
		this.deco=true;
	}
}
