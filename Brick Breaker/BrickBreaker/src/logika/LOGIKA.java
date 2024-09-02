package logika;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import javax.swing.Timer;

public class LOGIKA {
	//za gui:
	private int igrac = 300;
	
	private int pozicijaLopteX = 100;
	private int pozicijaLopteY = 300;
	private int smjerLopteX = -1;
	private int smjerLopteY = -2;
	
	private Bricks bricks;
	
	private boolean play = false;
	private int bodovi = 0;
	
	private int bricksTotal = 48;
	
	public Timer timer;
	public int delay = 7;
	
	//za konzolu:
	private int red;
	private int kolona;
	
	private boolean[][] bricks1;
	private int brojPreostalihBricks;
	private int brojBodova = 0;
	
	private int sirinaIgraca;
	private int pozicijaIgraca;
	
    private int loptaX;
    private int loptaY;
    private int smjerLoptex = -1;
    private int smjerLoptey = 1;
	
	//konstruktor za gui:
	public LOGIKA() {
		bricks = new Bricks(4, 12);
	}
	
	//konstruktor za konzolu:
	public LOGIKA(int red, int kolona, int brojBricksNaPocetku, int sirinaIgraca) {
		this.red = red;
		this.kolona = kolona;
		this.sirinaIgraca = sirinaIgraca;
		bricks1 = new boolean[red][kolona];
		brojPreostalihBricks = brojBricksNaPocetku;
		pozicijaIgraca = kolona/2;
		loptaX = red-2;
        loptaY = pozicijaIgraca;
        smjerLoptex = -1;
        smjerLoptey = 1;
        
        //bricks - cigle
        for (int i=0; i<red; i++) {
        	for (int j=0; j<kolona; j++) {
        		//prva 2 reda su bricks (cigle)
        		if (i == 0 || i == 1) {
        			bricks1[i][j] = true;
        		}
        	}
        }
	}
	
	//za gui:
	public class Bricks {
		public int bricks[][];
		public int brickSirina;
		public int brickVisina;
		
		//konstruktor
		public Bricks(int red, int kolona) {
			bricks = new int[red][kolona];
			
			for (int i=0; i<bricks.length; i++) {
				for (int j=0; j<bricks[0].length; j++) {
					bricks[i][j] = 1;
				}
			}
			
			brickSirina = 540/kolona;
			brickVisina = 150/red;
		}
		
		public void draw(Graphics2D g) {
			final Color [] boje = {Color.red, Color.gray, Color.yellow, Color.pink, Color.green, Color.orange, Color.white, Color.cyan, Color.darkGray, Color.blue, Color.magenta};
			int indeks = 0;
			for (int i=0; i<bricks.length; i++) {
				for (int j=0; j<bricks[0].length; j++) {
					g.setColor(boje[indeks]);
					indeks++;
					if (indeks == boje.length) {
						indeks = 0;
					}
					if (bricks[i][j] > 0) {
						//bricks (cigle)
						g.fillRect(j*brickSirina+80, i*brickVisina+55, brickSirina, brickVisina);
						
						//crni okvir oko svake cigle (brick-a)
						g.setStroke(new BasicStroke(3));
						g.setColor(Color.black);
						g.drawRect(j*brickSirina+80, i*brickVisina+55, brickSirina, brickVisina);
					}
				}
			}
		}
		
		public void setVrijednost(int vrijednost, int red, int kolona) {
			bricks[red][kolona] = vrijednost;
		}
	}
	
	//za gui:
	public boolean win() {
		if (bricksTotal <= 0) {
			return true;
		}
		return false;
	}
	
	//za gui:
	public boolean lose() {
		if (pozicijaLopteY > 570) {
			return true;
		}
		return false;
	}
	
	//za gui:
	public void desno() {
		if (igrac >= 600) {
			igrac = 600;
		} else {
			play = true;
			igrac += 20;
		}
	}
	
	//za gui:
	public void lijevo() {
		if (igrac < 10) {
			igrac = 10;
		} else {
			play = true;
			igrac -= 20;
		}
	}
	
	//za gui:
	public void restart() {
		play = false;
		smjerLopteX = 0;
		smjerLopteY = 0;
	}
	
	//za gui:
	public void glavnaFunkcija() {
		if (new Rectangle(pozicijaLopteX, pozicijaLopteY, 20, 20).intersects(new Rectangle(igrac, 550, 30, 8))) {
			smjerLopteY = -smjerLopteY;
			smjerLopteX = -2;
		} else if (new Rectangle(pozicijaLopteX, pozicijaLopteY, 20, 20).intersects(new Rectangle(igrac+70, 550, 30, 8))) {
			smjerLopteY = -smjerLopteY;
			smjerLopteX = smjerLopteX+1;
		} else if (new Rectangle(pozicijaLopteX, pozicijaLopteY, 20, 20).intersects(new Rectangle(igrac+30, 550, 40, 8))) {
			smjerLopteY = -smjerLopteY;
		}
		
		//provjeravamo sudar bricks sa loptom
		for (int i=0; i<bricks.bricks.length; i++) {
			for (int j=0; j<bricks.bricks[0].length; j++) {
				if (bricks.bricks[i][j] > 0) {
					int brickX = j*bricks.brickSirina+80;
					int brickY = i*bricks.brickVisina+50;
					
					int brickSirina = bricks.brickSirina;
					int brickVisina = bricks.brickVisina;
					
					Rectangle rect = new Rectangle(brickX, brickY, brickSirina, brickVisina);
					Rectangle loptaRect = new Rectangle(pozicijaLopteX, pozicijaLopteY, 20, 20);
					Rectangle brickRect = rect;
					
					if (loptaRect.intersects(brickRect)) {
						bricks.setVrijednost(0, i, j);
						bodovi += 5;
						bricksTotal--;
						
						//kad lopta udari desno ili lijevo od brick
						if (pozicijaLopteX+19 <= brickRect.x || pozicijaLopteX+1 >= brickRect.x+brickRect.width) {
							smjerLopteX = -smjerLopteX;
						} else { //kad lopta udari gore ili dole od brick
							smjerLopteY = -smjerLopteY;
						}
					}
				}
			}
		}
		
		pozicijaLopteX += smjerLopteX;
		pozicijaLopteY += smjerLopteY;
		
		if (pozicijaLopteX < 0) {
			smjerLopteX = -smjerLopteX;
		}
		
		if (pozicijaLopteY < 0) {
			smjerLopteY = -smjerLopteY;
		}
		
		if (pozicijaLopteX > 670) {
			smjerLopteX = -smjerLopteX;
		}
	}
	
	//za gui:
	public Bricks getBricks() {
		return bricks;
	}
	
	//za gui:
	public int getBodovi() {
		return bodovi;
	}
	
	//za gui:
	public int getIgrac() {
		return igrac;
	}
	
	//za gui:
	public int getPozicijaLopteX() {
		return pozicijaLopteX;
	}
	
	//za gui:
	public int getPozicijaLopteY() {
		return pozicijaLopteY;
	}
	
	//za gui:
	public boolean getPlay() {
		return play;
	}
	
	//za konzolu:
	public void pomjeranjeLopte() {
		//pomjeranje lopte
    	loptaX += smjerLoptex;
    	loptaY += smjerLoptey;
	}
	
	//za konzolu:
	public void glavnaFunkcija1() {
    	//sudar lopte sa ciglom
    	if (bricks1[loptaX][loptaY]) {
    		bricks1[loptaX][loptaY] = false;  //na mjesto gdje je bila cigla stavljamo false
    		brojPreostalihBricks--;  //posto je cigla pogodjena smanjujemo ukupan broj cigli
    		brojBodova += 5;  //svaka pogodjena cigla nosi 5 bodova
    		smjerLoptex = -smjerLoptex;
    		
    	//sudar lopte sa zidovima
    	}else if (loptaX == 0) {
    		smjerLoptex = 1;
    	} else if (loptaY == 0 || loptaY == kolona-1) {
    		smjerLoptey = -smjerLoptey;
    		
    	//sudar lopte sa igracem
    	} else if (loptaX == red-1 && loptaY >= pozicijaIgraca-sirinaIgraca/2 && loptaY <= pozicijaIgraca+sirinaIgraca/2) {
    		smjerLoptex = -1;
    	}
	}
	
	//za konzolu:
	public boolean izgubio() {
		//lopta je pala na zadnji red ploce i nije na igracu (kliznoj traci)
		if (loptaX == red-1 && loptaY <= pozicijaIgraca-sirinaIgraca/2 && loptaY >= pozicijaIgraca+sirinaIgraca/2) {
			return true;
		}
		return false;
	}
	
	//za konzolu:
	public boolean pobijedio() {
		//ukoliko ostane 0 cigli igrac je pobijedio
		if (brojPreostalihBricks == 0) {
			return true;
		}
		return false;
	}
	
	//za konzolu:
	public int getBrojBodova() {
		return brojBodova;
	}
	
	//za konzolu:
	public void crtanjePloce() {
		for (int i=0; i<red; i++) {
    		for (int j=0; j<kolona; j++) {
    			if (i == loptaX && j == loptaY) {
    				System.out.print("O");  //lopta
    			} else if (i == red-1 && j >= pozicijaIgraca-sirinaIgraca/2 && j <= pozicijaIgraca+sirinaIgraca/2) {
    				System.out.print("=");  //igrac
    			} else if (bricks1[i][j]) {
    				System.out.print("#");  //bricks (cigle)
    			} else {
    				System.out.print(" ");  //prazan prostor
    			}
    		}
    		System.out.println();
    	}
	}
	
	//za konzolu:
	public void igracLijevo() {
		if (pozicijaIgraca > 0) {
			pozicijaIgraca--;
		}
	}
	
	//za konzolu:
	public void igracDesno() {
		if (pozicijaIgraca < kolona-1) {
			pozicijaIgraca++;
		}
	}
}
