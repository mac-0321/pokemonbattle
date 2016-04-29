package battle;
import java.util.Random;
import java.util.Scanner;

public class WildBattleControls extends Controller {
	private Pokemon Char = new Charizard();
	private Pokemon Bla = new Blastoise();
	private Pokemon Venu = new Venusaur();
	private Pokemon Pika = new Pikachu();
	private Pokemon Lap = new Lapras();
	private Pokemon Snor = new Snorlax();
	private Pokemon WChar = new Charizard();
	private Pokemon WBla = new Blastoise();
	private Pokemon WVenu = new Venusaur();
	private Pokemon WPika = new Pikachu();
	private Pokemon WLap = new Lapras();
	private Pokemon WSnor = new Snorlax();
	private Pokemon Pid = new Pidgeot();
	private Pokemon Ala = new Alakazam();
	private Pokemon Rhy = new Rhydon();
	private Pokemon Arc = new Arcanine();
	private Pokemon Gya = new Gyarados();
	private Pokemon Drag = new Dragonite();
	private Pokemon[] teamRed = {Char, Bla, Venu, Pika, Lap, Snor};
	private Pokemon[] PokemonList = {WChar, WBla, WVenu, WPika, WLap, WSnor, Pid, Ala, Rhy, Arc, Gya, Drag};
	private Potion hpotion = new Potion("Hyper Potion", 200);
	private Potion spotion = new Potion("Super Potion", 50);
	private Pokeball pball = new Pokeball("Poke Ball", "Poke");
	private Pokeball gball = new Pokeball("Great Ball", "Great");
	private Pokeball mball = new Pokeball("Master Ball", "Master");
	private Item[] itemList = {hpotion, spotion, pball, gball, mball};
	private Trainer Red = new Trainer("Red", teamRed, itemList);
	private Trainer Wild;
	private boolean encounter;
	
	private class MapWalk extends Event {
		Item[] itemList = {};
		boolean bush;
		Random gerador = new Random();
		public MapWalk(long eventTime, int op){
			super(eventTime);
			if(op==1) {
				bush = false;
			}
			else bush = true;
		}
		public void action() {
			int x = gerador.nextInt(10);
			if(bush) {
				if(x > 2) {
					int i = gerador.nextInt(12);
					Pokemon[] wildPokemon = {PokemonList[i]};
					while(wildPokemon[0].getFainted()){
						i = gerador.nextInt(12);
					}
					Wild = new Trainer("", wildPokemon, itemList);
					encounter = true;
				}
			}
		}
		public String description() {
			if(encounter){
				return "Wild " + Wild.getTeamMember(0).getName() + " appeared!\n";
			}
			else return "Nothing happened...\n";
		}
	}
	
	private class SwitchPKMN extends Event {
		Trainer P1;
		Trainer P2;
		boolean nextPokemon;
		public SwitchPKMN (long eventTime, Trainer P1, Trainer P2) {
			super(eventTime);
			this.P1 = P1;
			this.P2 = P2;
		}
		public void action() {
			if(P2.getName().equals("")){
				nextPokemon = P1.nextPA();
			}
			else{
				P2.setDefeated();
			}
		}
		public String description() {
			if(!nextPokemon){
				if(P2.getName().equals("")){
					return P1.getName() + " blacked out!\n";
				}
				else{
					return P2.getName() + " defeated " + P1.getTeamMember(P1.getPA()).getName() + "!\n" + P2.getName() + " got $320 for winning!";
				}
			}
			else{
				return P1.getName() + " sent out " + P1.getTeamMember(P1.getPA()).getName() + "!\n";
			}
		}
	}
	
	private class HealPokemon extends Event {
		Trainer P1;
		Potion potion;
		int restoredHP;
		boolean potionUsed;
		public HealPokemon(long eventTime, Trainer P1, Item p){
			super(eventTime);
			this.P1 = P1;
			this.potion = (Potion) p;
		}
		public void action() {
			if (!P1.getTeamMember(P1.getPA()).getFainted()){
				restoredHP = potion.healingHP(potion, P1.getTeamMember(P1.getPA()));
				potionUsed = true;
			}
			else {
				potionUsed = false;
			}
		}
		public String description() {
			if (potionUsed){
				return P1.getName() + " used " + potion.nome() + "!\n" + P1.getTeamMember(P1.getPA()).getName() + "'s HP was healed by " + restoredHP + " points.\n";
			}
			else {
				return "You cannot use a potion on a fainted Pokemon!";
			}
		}
	}
	
	private class CatchPokemon extends Event {
		Pokemon wild;
		Trainer P1;
		Pokeball ball;
		int shakeTime;
		boolean caught;
		public CatchPokemon(long eventTime, Item b, Trainer P1, Trainer P2){
			super(eventTime);
			this.ball = (Pokeball) b;
			this.wild = P2.getTeamMember(0);
			this.P1 = P1;
		}
		public void action() {
			caught = ball.catching(wild);
			shakeTime = ball.getShake(wild);
			if(caught) {
				wild.Fainted();
			}
		}
		public String description() {
			if(caught){
				return P1.getName() + " used " + ball.nome() + "!\n" + "1..2..3..Wild " + wild.getName() + " N°" + wild.getID() + " was caught!\n";
			}
			else if(shakeTime < 2){
				return P1.getName() + " used " + ball.nome() + "!\n" + "Oh, no! The Pokemon broke free!";
			}
			else return P1.getName() + " used " + ball.nome() + "!\n" + "Aww! It appeared to be caught!";
		}
	}
	
	private class Run extends Event {
		public Run(long eventTime) {
			super(eventTime);
		}
		public void action() {
			return;
		}
		public String description() {
			return "Got away safely!";
		}
	}
	
	private class Fight extends Event {
		Trainer ATK;
		Trainer DEF;
		int i;
		int damage;
		public Fight(long eventTime, Trainer P1, Trainer P2, int move) {
			super(eventTime);
			ATK = P1;
			DEF = P2;
			i = move;
		}
		public void action() {
			if(ATK.getTeamMember(ATK.getPA()).getFainted() == false && DEF.getTeamMember(DEF.getPA()).getFainted() == false){
				damage = (int) ATK.getTeamMember(ATK.getPA()).moves[i].DamageCalculate(ATK.getTeamMember(ATK.getPA()).getAtt(), ATK.getTeamMember(ATK.getPA()).getDef(),
								ATK.getTeamMember(ATK.getPA()).getAdv(DEF.getTeamMember(DEF.getPA()).getType()));
				
				DEF.getTeamMember(DEF.getPA()).dmgReceived((int)damage);
				
			}
			if(DEF.getTeamMember(DEF.getPA()).getFainted()) {
				addEvent(new SwitchPKMN(System.currentTimeMillis() + 2000, DEF, ATK));
			}
		}
		public String description() {
			if(ATK.getName().equals("")){
				if(ATK.getTeamMember(ATK.getPA()).getFainted() == false && DEF.getTeamMember(DEF.getPA()).getFainted() == false){
					return "Wild " + ATK.getTeamMember(ATK.getPA()).getName() + " used " + ATK.getTeamMember(ATK.getPA()).moves[i].getMove() +
							"!\n" + ATK.getTeamMember(ATK.getPA()).getName() + " dealt " + damage + "damage!\n" + ATK.getTeamMember(ATK.getPA()).getAdvString(ATK.getTeamMember(ATK.getPA()).getAdv(DEF.getTeamMember(DEF.getPA()).getType()));
				}
				else if(ATK.getTeamMember(ATK.getPA()).getFainted() == false && DEF.getTeamMember(DEF.getPA()).getFainted() == true){
					return "Wild " + ATK.getTeamMember(ATK.getPA()).getName() + " used " + ATK.getTeamMember(ATK.getPA()).moves[i].getMove() +
					"!\n" + ATK.getTeamMember(ATK.getPA()).getName() + " dealt " + damage + "damage!\n" + ATK.getTeamMember(ATK.getPA()).getAdvString(ATK.getTeamMember(ATK.getPA()).getAdv(DEF.getTeamMember(DEF.getPA()).getType())) + DEF.getTeamMember(DEF.getPA()).getName() + " fainted!";
				}
				else return "";
			}
			else if(ATK.getTeamMember(ATK.getPA()).getFainted() == false && DEF.getTeamMember(DEF.getPA()).getFainted() == false){
				return ATK.getName()+"'s " + ATK.getTeamMember(ATK.getPA()).getName() + " used " + ATK.getTeamMember(ATK.getPA()).moves[i].getMove() +
						"!\n" + ATK.getTeamMember(ATK.getPA()).getName() + " dealt " + damage + "damage!\n" + ATK.getTeamMember(ATK.getPA()).getAdvString(ATK.getTeamMember(ATK.getPA()).getAdv(DEF.getTeamMember(DEF.getPA()).getType()));
			}
			else if(ATK.getTeamMember(ATK.getPA()).getFainted() == false && DEF.getTeamMember(DEF.getPA()).getFainted() == true){
				return ATK.getName()+"'s " + ATK.getTeamMember(ATK.getPA()).getName() + " used " + ATK.getTeamMember(ATK.getPA()).moves[i].getMove() +
				"!\n" + ATK.getTeamMember(ATK.getPA()).getName() + " dealt " + damage + "damage!\n" + ATK.getTeamMember(ATK.getPA()).getAdvString(ATK.getTeamMember(ATK.getPA()).getAdv(DEF.getTeamMember(DEF.getPA()).getType())) + DEF.getTeamMember(DEF.getPA()).getName() + " fainted!";
			}
			else return "";
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		WildBattleControls bc = new WildBattleControls();
		int option = 0;
		Scanner scan = new Scanner(System.in);
		Random gerador = new Random();
		option = bc.choosePlace(bc.Red, scan);
		Thread.sleep(1000);
		while(option != 3){
			bc.addEvent(bc.new MapWalk(System.currentTimeMillis(), option));
			bc.run();
			if(bc.encounter){
				int moveIndexRed = 0;
				int moveIndexWild = 0;
				boolean runFromBattle = false;
				int[] actionsRed = {1, 1, 1, 2, 2};
				int[] actionsWild = new int[50];
				for (int i = 0; i < actionsWild.length; i++) {
					actionsWild[i] = 1;
					if (i % 10 == 0 && i != 0) actionsWild[i] = 2;
					if (i == 37) actionsWild[i] = 2;
				}
				for (int i = 0; i < actionsRed.length && !runFromBattle && bc.Red.getTeamMember(bc.Red.getPA()).getFainted() == false && bc.Wild.getTeamMember(0).getFainted() == false; i++) {
					bc.currentStatus(bc.Red, bc.Wild);
					Thread.sleep(1500);
					moveIndexRed = bc.changeMove(moveIndexRed);
					moveIndexWild = bc.changeMove(moveIndexWild);
					if(actionsRed[i] == 1){
						if (bc.checkPriority(bc.Red.getTeamMember(bc.Red.getPA()).moves[moveIndexRed].getPri(), bc.Wild.getTeamMember(0).moves[moveIndexWild].getPri(), bc.Red.getTeamMember(bc.Red.getPA()).getSpeed(), bc.Wild.getTeamMember(0).getSpeed())){
							bc.addEvent(bc.new Fight(System.currentTimeMillis(), bc.Red, bc.Wild, moveIndexRed));
							bc.addEvent(bc.new Fight(System.currentTimeMillis() + 1500, bc.Wild, bc.Red, moveIndexWild));
						}
						else {
							bc.addEvent(bc.new Fight(System.currentTimeMillis(), bc.Wild, bc.Red, moveIndexWild));
							bc.addEvent(bc.new Fight(System.currentTimeMillis() + 1500, bc.Red, bc.Wild, moveIndexRed));
						}
					}
					if(actionsRed[i] == 2){
						int b = gerador.nextInt(5);
						if(b < 2){
							bc.addEvent(bc.new HealPokemon(System.currentTimeMillis(), bc.Red, bc.Red.getItem(b)));
							bc.addEvent(bc.new Fight(System.currentTimeMillis() + 1500, bc.Wild, bc.Red, moveIndexWild));
						}
						else {
							bc.addEvent(bc.new CatchPokemon(System.currentTimeMillis(), bc.Red.getItem(b), bc.Red, bc.Wild));
							bc.addEvent(bc.new Fight(System.currentTimeMillis() + 1500, bc.Wild, bc.Red, moveIndexWild));
						}
					}
					if(actionsRed[i] == 3) {
						bc.addEvent(bc.new SwitchPKMN(System.currentTimeMillis(), bc.Red, bc.Wild));
						bc.addEvent(bc.new Fight(System.currentTimeMillis() + 1500, bc.Wild, bc.Red, moveIndexWild));
					}
					if(actionsRed[i] == 4) {
						bc.addEvent(bc.new Run((System.currentTimeMillis() + 2000)));
						runFromBattle = true;
					}
					bc.run();
					Thread.sleep(2000);
					System.out.println("***********************************************");
					if(i == 4){
						i=-1;
					}
				}
			}
			bc.encounter = false;
			Thread.sleep(500);
			option = bc.choosePlace(bc.Red, scan);
			Thread.sleep(1000);
		}
		scan.close();
	}
	
	public void currentStatus(Trainer P1, Trainer P2) {
		System.out.println(P1.getName() + "\n" + "Active Pokemon: " + P1.getTeamMember(P1.getPA()).getName() + "\nHP: " + P1.getTeamMember(P1.getPA()).getCurrentHP() + "/" + P1.getTeamMember(P1.getPA()).getHP() + "\n");
		System.out.println(P2.getName() + "\n" + "Active Pokemon: " + P2.getTeamMember(P2.getPA()).getName() + "\nHP: " + P2.getTeamMember(P2.getPA()).getCurrentHP() + "/" + P2.getTeamMember(P2.getPA()).getHP() + "\n");
	}
	
	public int choosePlace(Trainer P1, Scanner scan) {
		int opcao;
		System.out.println("What will " + P1.getName() + " do?");
		System.out.println("1 - Walk on Ground\n2 - Walk on Bush\n3 - Finish travel");
		opcao = scan.nextInt();
		return opcao;
	}
	
	public int changeMove(int i) {
		i++;
		if (i == 4)
			i = 0;
		return i;
	}
	
	public boolean checkPriority (boolean pri1, boolean pri2, int spd1, int spd2) {
		if (pri1 == pri2) {
			if(spd1 >= spd2) return true;
			else return false;
		}
		else if (pri1 && !pri2) return true;
		else return false;
	}
}