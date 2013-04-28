package codage;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Classe fournissant divers outils utilisés dans les fonctions des Codes
 * @author Lucas VANRYB
 *
 */
public class Outils {

	/**
	 * Frequence d'apparition des voyelles dans la langue francaise
	 */
	public static final double[] FREQUENCE_APPARITION_WIKI= {8.1,0.901,3.260,3.669,17.3,1.066,0.866,0.737,7.529,0.545,0.049,5.456,2.968,7.095,5.378,3.021,1.362,6.553,7.948,7.244,6.35,1.628,0.114,0.387,0.308,0.136};

	/**
	 * Fréquence d'apparition des lettres dans la langue francaise
	 */
	public static final double[] FREQUENCE_LETTRE={7.636,0.901,3.260,3.669,14.715,1.066,0.866,0.737,7.529,0.545,0.049,5.456,2.968,7.095,5.378,3.021,1.362,6.553,7.948,7.244,6.311,1.626,0.114,0.387,0.308,0.136};

	/**
	 * Determin e la fiabilite des tests de primalite effectues
	 */
	public static final int NB_TESTS=100;

	/**
	 * Determine la taille minimale des nombres premiers generes
	 */
	public static final int NB_CHIFFRES_MINI=4;

	/**
	 * Compare la frequence d'apparition des voyelles dans le message et dans la langue francaise
	 * et retourne une evaluation des la "distance" à la frequence normale
	 * @param c un tableau de caractere à analyser
	 * @return une evaluation des la "distance" à la frequence normale d'apparition des lettres
	 * dans la langue francaise
	 */
	public static double comparerCourt(char[] c) {
		int cpt=0;	
		for(int i=0;i<c.length;i++){
			if(c[i]=='E' || c[i]=='A' || c[i]=='I' || c[i]=='O' || c[i]=='U' || c[i]=='Y'
				|| c[i]=='e' || c[i]=='a' || c[i]=='i' || c[i]=='o' || c[i]=='u' || c[i]=='y'
					||	c[i]=='é' || c[i]=='è' || c[i]=='ê' || c[i]=='à' || c[i]=='ù') {
				cpt++;
			}
		}
		if(((double)cpt/(double)c.length-(Outils.FREQUENCE_APPARITION_WIKI[0]+Outils.FREQUENCE_APPARITION_WIKI[4]+Outils.FREQUENCE_APPARITION_WIKI[8]+Outils.FREQUENCE_APPARITION_WIKI[14]+Outils.FREQUENCE_APPARITION_WIKI[20]+Outils.FREQUENCE_APPARITION_WIKI[24])/100)>0) {
			return ((double)cpt/(double)c.length-(Outils.FREQUENCE_APPARITION_WIKI[0]+Outils.FREQUENCE_APPARITION_WIKI[4]+Outils.FREQUENCE_APPARITION_WIKI[8]+Outils.FREQUENCE_APPARITION_WIKI[14]+Outils.FREQUENCE_APPARITION_WIKI[20]+Outils.FREQUENCE_APPARITION_WIKI[24])/100);
		}
		else {
			return -((double)cpt/(double)c.length-(Outils.FREQUENCE_APPARITION_WIKI[0]+Outils.FREQUENCE_APPARITION_WIKI[4]+Outils.FREQUENCE_APPARITION_WIKI[8]+Outils.FREQUENCE_APPARITION_WIKI[14]+Outils.FREQUENCE_APPARITION_WIKI[20]+Outils.FREQUENCE_APPARITION_WIKI[24])/100);
		}
	}

	/**
	 * Compare la frequence d'apparition des lettres dans la langue francaise et celle du texte
	 * @param c le texte à analyser
	 * @return une évaluation de la distance entre le texte et la langue francaise
	 */
	public static double comparerLong(char[] c) {
		double[] nbApparitions=new double[26];
		double s=0;
		for(int i=0;i<c.length;i++) {
			if(c[i]>=65 && c[i]<=90) {
				nbApparitions[c[i]-65]++;
				s++;
			}
			if(c[i]>=97 && c[i]<=122) {
				nbApparitions[c[i]-97]++;
				s++;
			}
		}
		double res=0;
		for(int i=0;i<26;i++) {
			res+=Math.abs((nbApparitions[i]*100/s)-FREQUENCE_LETTRE[i]);
		}
		return res;
	}

	/**
	 * Retourne un grand nombre premier
	 * @return un grand entier premier
	 */
	public static long genererNbPremier() {
		long r=0;
		long t=0;
		boolean trouve=false;
		while(!trouve) {
			r=(long)Math.pow(10, (NB_CHIFFRES_MINI-1));
			r+=(long)(Math.random()*r);
			t=2*r+1;
			if(testerPrimalite(t)) {
				trouve=true;
			}
		}
		return t;
	}

	/**
	 * Renvoie true si n est un nombre premier
	 * @param n un entier positif
	 * @return true si n ets premier
	 */
	public static boolean testerPrimalite(long n) {
		boolean trouve=false;
		int i=0;
		long temp=0;
		while(!trouve && i<NB_TESTS) {
			temp=(int)((Math.random()*n));
			if(temp==0) {
				temp++;
			}
			if(calculSoloStras(temp,n)!=syJacobi(temp,n)) {
				trouve=true;
			}
			i++;
		}
		return !trouve;
	}

	/**
	 * Calcul de a^((n-1)/2)%n
	 * @param a un entier positif
	 * @param n un entier positif
	 * @return a^((n-1)/2)%n
	 */
	public static long calculSoloStras(long a, long n) {
		long aRetourner;
		aRetourner=puisNonDebord(a,(n-1)/2,n);
		if(aRetourner==n-1) {
			aRetourner=-1;
		}
		return aRetourner;
	}

	/**
	 * Retourne a^l%n sans debordement
	 * @param a l'entier positid à élever
	 * @param l la puissance soit un entier positif
	 * @param n l'entier positif modulo
	 * @return a^l%n
	 */
	public static long puisNonDebord(long a, long l,long n) {	
		long aRetourner=1;
		long b=a%n;
		for(long i=l;i>0;i--) {
			aRetourner*=b;
			aRetourner%=n;
		}
		return aRetourner%n;
	}

	/**
	 * Retourne le symbole de Legendre (a/n)
	 * @param a un entier positif
	 * @param n un entier positif
	 * @return le symbole de Legendre (a/n)
	 */
	public static int syLegendre(long a, long n) {
		if(a%n==0) {
			return 0;
		}
		else {
			long p=a%n;
			if((double)((int)Math.sqrt(p))==Math.sqrt(p)) {
				return 1;				
			}
			else {
				return -1;
			}
		}
	}

	/**
	 * Retourne le symbole de Jacobi (a/n)
	 * @param a un entier positif
	 * @param n un entier positif
	 * @return le symbole de Jacobi (a/n)
	 */
	public static long syJacobi(long a, long n) {
		long v=n;
		long u=a;
		long s=1; 
		long temp;
		if(u<0) { 
			u=-u; 
			s=(long) Math.pow(-1,(v-1)/2);
		}
		while(u>=2) { 
			if(u%2==0) {
				u/=2; 
				s*=(long) Math.pow(-1,(v*v-1)/8);
			}
			else {
				s*=(long) Math.pow(-1,(v-1)*(u-1)/4);
				temp=v%u; 
				v=u; 
				u=temp; 
			} 
		} 
		if(u==0) {
			s=0 ; 
		}
		return s;
	}

	/**
	 * Retourne true si les paramètres sont premiers entre eux
	 * @param p un entier positif
	 * @param g un entier positif
	 * @return true si les paramètres sont premiers entre eux
	 */
	public static boolean premierAvec(long p, long g) {
		return (PGCD(p,g)==1);
	}

	/**
	 * Retourner l'inverse modulo (inverse entier)
	 * @param a un entier positif
	 * @param m un entier positif
	 * @return a^-1%m
	 */
	public static long inverseModulo(long a, long m) {
		long m0=m;
		long a0=a;
		long t0=0;
		long t=1;
		long q=m0/a0;
		long r=m0-q*a0;
		long temp;
		while(r>0) {
			temp=t0-q*t;
			if(temp>=0) {
				temp=temp%m;
			}
			else {
				temp=m-((-temp)%m);
			}
			t0=t;
			t=temp;
			m0=a0;
			a0=r;
			q=m0/a0;
			r=m0-q*a0;
		}
		if(a0==1) {
			return t; 
		}
		else {
			return -1;
		}
	}

	/**
	 * Retourne le PGCD des paramètres
	 * @param p un entier positif
	 * @param g un entier positif
	 * @return le PGCD de (p,g)
	 */
	public static long PGCD(long p, long g) {
		boolean alterne=true;
		long r=p;
		long a=g;
		while(r!=0 && a!=0) {
			if(alterne) {
				a%=r;
			}
			else {
				r%=a;
			}
			alterne=!alterne;
		}
		return Math.max(r, a);
	}

	/**
	 * Méthode permettant de calculer les i premières décimales de pi
	 * Algorithme trouvé sur internet
	 * @param i le nombre de décimales à calculer
	 * @return le tableau des i premières décimales de pi
	 */
	public static byte[] calculerDecimalesPi(int i) {
		byte[] aRetourner=new byte[i+1];
		BigDecimal somme = BigDecimal.ZERO; 
		for (int j=0; j<2*i; j++){ 
			BigDecimal part1 = new BigDecimal(4.0/(8*j+1)); 
			BigDecimal part2 = new BigDecimal(2.0/(8*j+4)); 
			BigDecimal part3 = new BigDecimal(1.0/(8*j+5)); 
			BigDecimal part4 = new BigDecimal(1.0/(8*j+6)); 
			BigInteger part5 = BigInteger.valueOf(16).pow (j); 
			part1 = part1.subtract(part2); 
			part1 = part1.subtract(part3); 
			part1 = part1.subtract(part4); 
			part1 = part1.divide (new BigDecimal(part5)); 
			somme = somme.add (part1); 
		} 
		// On ne retourne que les décimales demandées car le reste est faux 
		BigDecimal nbd = new BigDecimal (10.0).pow (2*i); 
		// On multiplie par 10^nbdecimal 
		somme = somme.multiply (nbd); 
		// On prends la partie entière 
		BigInteger tempSomme = somme.toBigInteger (); 
		String s=new BigDecimal(tempSomme).divide (nbd).toString ();
		int z=0;
		for(int r=2;z<i+1;r++) {
			if(Byte.parseByte(""+s.charAt(r))!=0) {
				aRetourner[z]=Byte.parseByte(""+s.charAt(r));
				z++;
			}
		}
		return aRetourner;
	}

	/**
	 * Fonction main de test
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(genererNbPremier());
		System.out.println(inverseModulo(27,113));
		System.out.println(inverseModulo(2, 3));
		System.out.println(PGCD(12,20));
	}
}
