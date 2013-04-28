package codage;

import java.util.Date;

import javax.swing.JOptionPane;

import fabrique.Fabrique;
import message.IMessage;
import message.Message;

/**
 * Classe permettant de crypter et de decrypter des messages, en utilisant
 * le chiffre de Merkle-Hellman basé sur le problème du sac à dos.
 * Cette classe code les caractères par bloc afin de ne pas être vulnérable
 * à une analyse fréquentielle
 * @author Lucas VANRYB
 *
 */
public class MerkleHellman extends CodeClePublique implements ICodeClePublique {

	/**
	 * Constante définissant le nombre de bits utilisés pour coder un caractère
	 */
	public static final int TAILLE_CHAR=8;

	/**
	 * Constante définissant la taille d'un bloc d'encodage.
	 * Doit valoir k*TAILLE_CHAR avec k entier positif
	 */
	public static final int TAILLE_ENCODAGE=3*TAILLE_CHAR; 

	/**
	 * Un coefficient d'aleatoire
	 */
	public static final int COEFF_ALEA1=5;

	/**
	 * Un autre coefficient d'aleatoire
	 */
	public static final int COEFF_ALEA2=3; 

	/*
	 * ATTRIBUTS
	 */

	/**
	 * Sigma1 est un des index de permutation (contenu dans la clé privée)
	 */
	private int sigma1;

	/**
	 * Sigma2 est l'autre index de permutation (contenu dans la clé privée)
	 */
	private int sigma2;

	/**
	 * Coefficients de la suite contenu dans la clé publique
	 */
	private long[] suite2;

	/**
	 * Coefficients de la suite contenu dans la clé privée
	 */
	private long[] suite1;

	/**
	 * Un majorant de la suite privée, et permettant la construction de la suite publique
	 */
	private long M;

	/**
	 * Un entier constituant une partie de la clé privée,et permettant la construction de
	 * la suite publique
	 */
	private long W;

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur initialisant les variables d'instance
	 */
	public MerkleHellman() {
		super();
		this.sigma1=0;
		this.sigma2=0;
		this.M=0;
		this.W=0;
		this.suite1=new long[TAILLE_ENCODAGE];
		this.suite2=new long[TAILLE_ENCODAGE];
		this.init();
	}

	/*
	 * Initialise les deux suites qui correspondront aux clés privé et publique
	 */
	private void init() {
		for(int i=0;i<TAILLE_ENCODAGE;i++) {
			this.suite1[i]=0;
			this.suite2[i]=0;
		}
	}

	/*
	 * SERVICES
	 */

	/**
	 * Méthode permettant de crypter un message à l'aide de la méthode de Merkle-Hellman
	 * @param clair le message à crypter
	 * @param key la clé à utiliser pour le cryptage
	 * @return le message crypté
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * La fonction découpe le message en série de TAILLE_ENCODAGE/TAILLE_CHAR
		 * caractères et appelle encoderSerie pour chaque bloc
		 */
		long d=new Date().getTime();
		this.diviserKey(key);
		this.interpreterClePublique();
		String s="";
		for(int i=0; i<clair.taille();i+=TAILLE_ENCODAGE/TAILLE_CHAR) {
			if(i<(clair.taille()-TAILLE_ENCODAGE/TAILLE_CHAR)) {
				s+=this.encoderSerie(new String(clair.toString().substring(i,i+TAILLE_ENCODAGE/TAILLE_CHAR)));
				s+=" ";
			}
			else {
				String temp="";
				for(int z=clair.taille();z<i+TAILLE_ENCODAGE/TAILLE_CHAR;z++) {
					temp+=(char)0;
				}
				s+=this.encoderSerie(new String(clair.toString().substring(i,clair.taille())+temp));
			}
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(s);
	}

	/*
	 * Méthode prenant une string en paramètre,
	 * qu'elle convertit en une série binaire,
	 * puis encode la string comme etant l'entier
	 * correspondant au poids maximum du sac à dos
	 * tq la solution est la serie binaire camouflée
	 */
	private int encoderSerie(String s) {
		String concatBin="";
		for(int i=0;i<s.length();i++) {
			short e=(short)s.charAt(i);
			String st=Integer.toBinaryString(e);
			for(int j=st.length();j<TAILLE_CHAR;j++) {
				st="0"+st;
			} 
			concatBin+=st;
		}
		int aRetourner=0;
		for(int i=0;i<concatBin.length();i++) {
			if(concatBin.charAt(i)=='1') {
				aRetourner+=this.suite2[i];
			}
		}
		return aRetourner;
	}

	/**
	 * Méthode permettant de décrypter un message à l'aide de la méthode de Merkle-Hellman
	 * @param crypte le message à decrypter
	 * @param key la clé de cryptage associée
	 * @return le message décrypté
	 */
	public IMessage decrypter(IMessage crypte, String key) {
		/*
		 * La méthode prend les entiers constituant le message un par un,
		 * et décode chaque bloc correspondant à un entier
		 * à l'aide d'appels à la méthode decoderSerie
		 */
		try {
			long d=new Date().getTime();
			this.diviserKey(key);
			this.interpreterClePrive();
			String[] coupes=crypte.toString().split(" ");
			String s="";
			for(int i=0;i<coupes.length;i++) {
				s+=this.decoderSerie(Integer.parseInt(coupes[i]));
			}
			this.time=new Date().getTime()-d;
			return Fabrique.fabriquerMessage(s);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Ce message n'a pas été codé à l'aide de ce type de code", "Erreur", JOptionPane.ERROR_MESSAGE);
			return Fabrique.fabriquerMessage("Erreur");
		}
	}

	/*
	 * Méthode permettant de décoder une série de caractère chiffrés à l'aide de Merkl-Hellman
	 * Tout d'abord à l'aide de W et de M on retrouve D, le poids du sac à dos max,
	 * à partir de u (l'entier representant le bloc de caractères)
	 * Puis on résout le sac à dos supercroissant privé avec pour poids max D
	 * On décode ensuite du binaire aux caractères
	 */
	private String decoderSerie(int u) {
		long D=((u%this.M)*(Outils.inverseModulo(this.W, this.M))%this.M)%this.M;
		String s=this.resoudreSacADosSuper(D,TAILLE_ENCODAGE-1);
		char[] c=s.toCharArray();
		char temp=c[this.sigma1];
		c[this.sigma1]=c[this.sigma2];
		c[this.sigma2]=temp;
		String envers="";
		for(int i=0;i<TAILLE_ENCODAGE/TAILLE_CHAR;i++) {
			int s2=0;
			for(int j=TAILLE_CHAR*i;j<TAILLE_CHAR*(i+1);j++) {
				if(c[c.length-1-j]=='1') {
					s2+=Math.pow(2, j-TAILLE_CHAR*i);
				}	
			}
			envers+=(char)s2;
		}
		String aRetourner="";
		for(int z=1;z<=envers.length();z++) {
			aRetourner+=envers.charAt(envers.length()-z);
		}
		return aRetourner;
	}

	/*
	 * Méthode permettant de résoudre le sac à dos supercroissant en mémoire avec pour poids max
	 * D
	 */
	private String resoudreSacADosSuper(long D, int i) {
		if(i==-1) {
			return "";
		}
		else {
			if(this.suite1[i]<=D) {
				return (this.resoudreSacADosSuper(D-this.suite1[i], i-1)+"1");
			}
			else {
				return (this.resoudreSacADosSuper(D, i-1)+"0");
			}
		}
	}

	/**
	 * Méthode générant une clé valable aléatoire pour Merkle-Hellman
	 * @return une clé valable pour Merkle-Hellman
	 */
	public String genererCle() {
		long d=new Date().getTime();
		long semiArithmetique=(long)(Math.random()*COEFF_ALEA1);
		long[] suite=new long[TAILLE_ENCODAGE];
		long majorant;
		suite[0]=(long)(Math.random()*COEFF_ALEA1)+1;
		long[] suite2=new long[TAILLE_ENCODAGE];
		for(int i=1;i<suite.length;i++) {
			suite[i]=2*suite[i-1]+semiArithmetique;
		}
		majorant=((long)(Math.random()*COEFF_ALEA2)+2)*suite[suite.length-1]+semiArithmetique;
		long W=1+(long)(Math.random()*(majorant-2));
		while(!Outils.premierAvec(W,majorant)) {
			W=1+(long)(Math.random()*(majorant-2));
		}
		int sigma1=(int)(Math.random()*(TAILLE_ENCODAGE/2));
		int sigma2=(int)(Math.random()*(TAILLE_ENCODAGE/2))+(TAILLE_ENCODAGE/2);
		suite2=suite.clone();
		for(int i=0;i<suite.length;i++) {
			if(i==sigma1) {
				suite2[i]=(suite[sigma2]*W)%majorant;
			}
			else {
				if(i==sigma2) {
					suite2[i]=(suite[sigma1]*W)%majorant;
				}
				else {
					suite2[i]=(suite[i]*W)%majorant;
				}
			}
		}
		String aRetourner="(";
		for(int i=0;i<suite2.length;i++) {
			aRetourner+=suite2[i];
			if(i!=suite2.length-1) {
				aRetourner+=",";
			}
		}
		aRetourner+=")";
		aRetourner+=" (";
		for(int i=0;i<suite.length;i++) {
			aRetourner+=suite[i];
			if(i!=suite.length-1) {
				aRetourner+=",";
			}
		}
		aRetourner+=")";
		aRetourner+="{"+majorant+","+W+"}";
		aRetourner+="["+sigma1+","+sigma2+"]";
		this.time=new Date().getTime()-d;
		return aRetourner;
	}

	/*
	 * Méthode permettant de lire la clé publique
	 */
	private void interpreterClePublique() {
		String aAnalyser=this.getPublicKey();
		String temp=aAnalyser.substring(1);
		int i=-1;
		int j=0;
		while (j<TAILLE_ENCODAGE) {
			i=temp.indexOf(',');
			if(j!=(TAILLE_ENCODAGE-1)) {
				this.suite2[j]=Integer.parseInt(temp.substring(0, i));
				temp=temp.substring(i+1);
			}
			else {
				this.suite2[j]=Integer.parseInt(temp.substring(0, temp.indexOf(')')));
			}
			j++;
		}

	}

	/*
	 * Méthode permettant de lire la clé privée
	 */
	private void interpreterClePrive() {
		String aAnalyser=this.getPrivateKey();
		String temp=aAnalyser.substring(1);
		int i=-1;
		int j=0;
		while (j<TAILLE_ENCODAGE) {
			i=temp.indexOf(',');
			if(j!=(TAILLE_ENCODAGE-1)) {
				this.suite1[j]=Integer.parseInt(temp.substring(0, i));
				temp=temp.substring(i+1);
			}
			else {
				this.suite1[j]=Integer.parseInt(temp.substring(0, temp.indexOf(')')));
			}
			j++;
		}
		String temp2=aAnalyser.substring(aAnalyser.indexOf('{')+1, aAnalyser.indexOf('}'));
		String temp3=aAnalyser.substring(aAnalyser.indexOf('[')+1, aAnalyser.indexOf(']'));
		this.M=Integer.parseInt(temp2.split(",")[0]);
		this.W=Integer.parseInt(temp2.split(",")[1]);
		this.sigma1=Integer.parseInt(temp3.split(",")[0]);
		this.sigma2=Integer.parseInt(temp3.split(",")[1]);
	}


	/**
	 * Teste si la clé est valable comme clé privée pour Merkle-Hellman, on teste ici
	 * le format de la clé privé, on vérifie que le majorant en est bien un, et que la suite
	 * est bien supercroissante ainsi que la primalité entre W et M.
	 * On avertit l'utilisateur en cas de problème
	 * @param k la clé à tester
	 * @return true si la clé est une clé privé valable pour Merkle-Hellman, false sinon
	 */
	public boolean estClePriveValable(String k) {
		try {
			this.diviserKey(" "+k);
			this.interpreterClePrive();
			if(this.M<this.suite1[this.suite1.length-1] || this.M<this.W) {
				JOptionPane.showMessageDialog(null, "Le majorant n'en n'est pas un, cette clé n'est donc pas valable pour Merkle-Hcellman", "Majorant incorrect", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			else {
				if(this.sigma1>this.suite1.length-1 || this.sigma2>this.suite1.length-1 || this.sigma1<0 || this.sigma2<0) {
					JOptionPane.showMessageDialog(null, "Les index de permutation sont incorrects", "Permutations impossibles", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				else {
					if(!Outils.premierAvec(this.M, this.W)) {
						JOptionPane.showMessageDialog(null, "L'entier W n'est pas correct/cohérent avec le majorant", "Entier W incorrect", JOptionPane.ERROR_MESSAGE);
						return false;
					}
					else {
						boolean trouve=false;
						int i=1;
						long res=this.suite1[0];
						while(i<suite1.length && !trouve) {
							if(this.suite1[i]<=res) {
								trouve=true;
							}
							if(i!=suite1.length-1) {
								res+=this.suite1[i];
							}
							i++;
						}
						if(trouve) {
							JOptionPane.showMessageDialog(null, "La suite privée n'est pas supercroissante", "Suite privé incorrecte", JOptionPane.ERROR_MESSAGE);
							return false;
						}
						return true;
					}
				}
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Format de la clé privé incorrect, le format correct est :\n (entier,....,entier){entier,entier}[entier,entier]\n avec "+TAILLE_ENCODAGE+" termes dans les paranthèses", "Clé privé incorrecte", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/**
	 * Teste si une clé est valable comme clé publique pour Merkle-Hellman, on ne teste ici que le format de
	 * la clé publique, et on avertir l'utilisateur si il est incorrect
	 * @param k la clé à tester
	 * @return true si la clé est une clé publique du bon format pour Merkle-Hellman, false sinon
	 */
	public boolean estClePubliqueValable(String k) {
		try {
			this.diviserKey(k+" ");
			this.interpreterClePublique();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Format de la clé publique incorrect, le format correct est :\n (entier,....,entier)\n avec "+TAILLE_ENCODAGE+" termes dans les paranthèses", "Clé publique incorrecte", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * Méthode utilisée pour verifier si une cle est valable pour Merkle-Hellman
	 * En plus d'appeler les méthode vérifiant les clés privé et publique indépendamment,
	 * cette méthode reconstruit la clé publique à partir de la clé privé et vérifie qu'elle coreespond à
	 * la clé publique fournie, et signale à l'utilisateur des enventuelles erreurs
	 * @param k la clé à tester
	 * @return true si la clé est valable pour Merkle-Hellman, false sinon
	 */
	public boolean estCleValable(String k) {
		if(super.estCleValable(k)) {
			boolean trouve=false;
			int i=0;
			while(i<this.suite2.length && !trouve) {
				if(i==this.sigma1) {
					if(this.suite2[i]!=(this.suite1[this.sigma2]*this.W)%this.M) {
						trouve=true;
					}
				}
				else {
					if(i==this.sigma2) {
						if(this.suite2[i]!=(this.suite1[this.sigma1]*this.W)%this.M) {
							trouve=true;
						}
					}
					else {
						if(this.suite2[i]!=((this.suite1[i])*this.W)%this.M) {
							trouve=true;
						}
					}
				}
				i++;
			}
			if(trouve) {
				JOptionPane.showMessageDialog(null, "La clé publique et la clé privé ne correspondent pas", "Clé incorrecte", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * Méthode main permettant de tester la classe
	 * @param args
	 */
	public static void main(String[] args) {
		String key=new MerkleHellman().genererCle();
		IMessage m=new MerkleHellman().crypter(new Message("Tu penses qu'ils nous ont entendus ?!"), key);
		IMessage m2=new MerkleHellman().decrypter(m, key);
		System.out.println(m);
		System.out.println(m2);
		System.out.println(key);
	}

}
