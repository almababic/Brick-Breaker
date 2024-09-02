package konzola;

import java.util.Scanner;

import logika.LOGIKA;

public class KONZOLA {
	private static Scanner sc = new Scanner(System.in);
	
    public static void main(String[] args) {
        final int red = 5;  //broj redova ploce za igru
        final int kolona = 10;  //broj kolona ploce za igru
        final int brojBricksNaPocetku = 20;   //prva 2 reda su bricks (cigle), a svaki red ima 10 kolona pa imamo 20 cigli
        final int sirinaIgraca = 3;
        
        LOGIKA game = new LOGIKA(red, kolona, brojBricksNaPocetku, sirinaIgraca);
        
        System.out.println("BRICK BREAKER - ALMA BABIC");
        System.out.println();
        
        //igra 
        while(true) {
        	game.pomjeranjeLopte();
        	
        	if (game.izgubio()) {
        		System.out.println("Izgubili ste!");
        		System.out.println("Broj bodova: " + game.getBrojBodova());
        		break;
        	}
        	
        	//provjerava sudare lopte sa ciglom, zidovima i igracem
        	game.glavnaFunkcija1();
       
        	//crtanje ploce za igru
        	game.crtanjePloce();
        	
        	//u svakom potezu se ispisuje broj bodova
        	System.out.println("Broj bodova: " + game.getBrojBodova());
        	
        	//provjera da li je igrac pobijedio
        	if (game.pobijedio()) {
        		System.out.println("Pobijedili ste!");
        		System.out.println("Broj bodova: " + game.getBrojBodova());
        		break;
        	}
        	
        	//pomjeranje igraca pomocu slova "a" za lijevo i "d" za desno
        	String potez = sc.nextLine();
        	if (potez.equals("a")) {
        		game.igracLijevo();
        	} else if (potez.equals("d")) {
        		game.igracDesno();
        	}
        }
        
        sc.close();
    }
}
