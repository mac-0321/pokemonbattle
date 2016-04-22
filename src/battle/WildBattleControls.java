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
	private Pokemon Pid = new Pidgeot();
	private Pokemon Ala = new Alakazam();
	private Pokemon Rhy = new Rhydon();
	private Pokemon Arc = new Arcanine();
	private Pokemon Gya = new Gyarados();
	private Pokemon Drag = new Dragonite();
	private Pokemon[] teamRed = {Char, Bla, Venu, Pika, Lap, Snor};
	private Pokemon[] PokemonList = {Char, Bla, Venu, Pika, Lap, Snor, Pid, Ala, Rhy, Arc, Gya, Drag};
	private Potion hpotion = new Potion("Hyper Potion", 200);
	private Potion spotion = new Potion("Super Potion", 50);
	private Pokeball pball = new Pokeball("Poke Ball", "Poke");
	private Pokeball gball = new Pokeball("Great Ball", "Great");
	private Pokeball mball = new Pokeball("Master Ball", "Master");
	private Item[] itemList = {hpotion, spotion, pball, gball, mball};
	private Trainer Red = new Trainer("Red", teamRed, itemList);
	private Trainer Wild;
	
	private class MapWalk extends Event {
		Trainer wild;
		Item[] itemList = {};
		boolean bush;
		Random gerador = new Random();
		public MapWalk(long eventTime, int op, Pokemon[] list, Trainer wild){
			super(eventTime);
			if(op==1) {
				bush = false;
			}
			else bush = true;
			int i = gerador.nextInt(12);
			Pokemon[] wildPokemon = {list[i]};
			wild = new Trainer("", wildPokemon, itemList);
			this.wild = wild;
		}
		public void action() {
			int x = gerador.nextInt(100);
			if(bush) {
				if(x < 40) {
					wild.encounter();
				}
			}
		}
		public String description() {
			if(wild.getEncounter()){
				return "Wild" + wild.getTeamMember(0).getName() + "appeared!\n";
			}
			else return "...\n";
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
			nextPokemon = P1.nextPA();
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
		Pokeball ball;
		int shakeTime;
		boolean caught;
		public CatchPokemon(long eventTime, Item b, Trainer P2){
			super(eventTime);
			this.ball = (Pokeball) b;
			this.wild = P2.getTeamMember(0);
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
				return "1..2..3..Wild " + wild.getName() + " was caught!\n";
			}
			else if(shakeTime < 2){
				return "Oh, no! The Pokemon broke free!";
			}
			else return "Aww! It appeared to be caught!";
		}
	}
	
	private class Run extends Event {
		public Run(long eventTime) {
			super(eventTime);
		}
		public void action() {
			return;
			//n?o sei o que botar aqui
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
				
				/*message = ATK.getName()+"'s " + ATK.getTeamMember(ATK.getPA()).getName() + " used " + ATK.getTeamMember(ATK.getPA()).moves[i].getMove() +
						  "!\n" + ATK.getTeamMember(ATK.getPA()).getName() + " dealt " + damage + "damage!\n" + ATK.getTeamMember(ATK.getPA()).getAdvString(ATK.getTeamMember(ATK.getPA()).getAdv(DEF.getTeamMember(DEF.getPA()).getType()));*/
			}
			if(DEF.getTeamMember(DEF.getPA()).getFainted()) {
				//message += DEF.getTeamMember(DEF.getPA()).getName() + " fainted!";
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
		Random gerador = new Random();
		int option = 0;
		Scanner scan = new Scanner(System.in);
		
		while(option != 3){
			option = bc.choosePlace(bc.Red, scan);
			Thread.sleep(1000);
			bc.addEvent(bc.new MapWalk(System.currentTimeMillis(), option, bc.PokemonList, bc.Wild));
			if(bc.Wild.getEncounter()){
				while(bc.Red.getTeamMember(bc.Red.getPA()).getFainted() == false && bc.Wild.getTeamMember(bc.Wild.getPA()).getFainted() == false && option != 4) {
					bc.currentStatus(bc.Red, bc.Wild);
					int RedMove = gerador.nextInt(4); //sera escolhida uma habilidade aleatoria do pokemon de Red
					int WildMove = gerador.nextInt(4); //sera escolhida uma habilidade aleatoria do pokemon de Wild
					Thread.sleep(500);
					option = bc.chooseEvent(bc.Red, scan);
					Thread.sleep(1000);

					if (option == 1) { 
						if((bc.Red.getTeamMember(bc.Red.getPA()).moves[RedMove].getPri() == bc.Wild.getTeamMember(bc.Wild.getPA()).moves[WildMove].getPri())) {
							if(bc.Red.getTeamMember(bc.Red.getPA()).getSpeed() < bc.Wild.getTeamMember(bc.Wild.getPA()).getSpeed()){
								bc.addEvent(bc.new Fight(System.currentTimeMillis(), bc.Wild, bc.Red, WildMove));
								bc.addEvent(bc.new Fight((System.currentTimeMillis() + 1500), bc.Red, bc.Wild, RedMove));
								bc.run();
								Thread.sleep(1500);
							}
							else {
								bc.addEvent(bc.new Fight(System.currentTimeMillis(), bc.Red, bc.Wild, RedMove));
								bc.addEvent(bc.new Fight((System.currentTimeMillis() + 1500), bc.Wild, bc.Red, WildMove));
								bc.run();
								Thread.sleep(1500);
							}
						}
						else if(bc.Red.getTeamMember(bc.Red.getPA()).moves[RedMove].getPri() == true && bc.Wild.getTeamMember(bc.Wild.getPA()).moves[WildMove].getPri() == false){
							bc.addEvent(bc.new Fight(System.currentTimeMillis(), bc.Red, bc.Wild, RedMove));
							bc.addEvent(bc.new Fight((System.currentTimeMillis() + 1500), bc.Wild, bc.Red, WildMove));
							bc.run();
							Thread.sleep(1500);
						}
						else {
							bc.addEvent(bc.new Fight(System.currentTimeMillis(), bc.Wild, bc.Red, WildMove));
							bc.addEvent(bc.new Fight((System.currentTimeMillis() + 1500), bc.Red, bc.Wild, RedMove));
							bc.run();
							Thread.sleep(1500);
						}
					}
					else if (option == 2) {
						bc.addEvent(bc.new SwitchPKMN(System.currentTimeMillis(), bc.Red, bc.Wild));
						bc.addEvent(bc.new Fight((System.currentTimeMillis() + 1500), bc.Wild, bc.Red, WildMove));
						bc.run();
						Thread.sleep(1500);
					}
					else if (option == 3) {
						bc.showItemOptions();
						int pot = scan.nextInt();
						if (pot == 1) {
							bc.addEvent(bc.new HealPokemon(System.currentTimeMillis(), bc.Red, bc.spotion));
							bc.addEvent(bc.new Fight((System.currentTimeMillis() + 1500), bc.Wild, bc.Red, WildMove));
						}
						if (pot == 2) {
							bc.addEvent(bc.new HealPokemon(System.currentTimeMillis(), bc.Red, bc.hpotion));
							bc.addEvent(bc.new Fight((System.currentTimeMillis() + 1500), bc.Wild, bc.Red, WildMove));
						}
						if (pot == 3) {
							bc.addEvent(bc.new CatchPokemon(System.currentTimeMillis(), bc.pball, bc.Wild));
						}
						if (pot == 4) {
							bc.addEvent(bc.new CatchPokemon(System.currentTimeMillis(), bc.gball, bc.Wild));
						}
						if (pot == 5) {
							bc.addEvent(bc.new CatchPokemon(System.currentTimeMillis(), bc.mball, bc.Wild));
						}
						bc.run();
						Thread.sleep(1500);
					}
					else if (option == 4) {
						bc.addEvent(bc.new Run((System.currentTimeMillis() + 3000)));
						bc.run();
					}
					else {
						System.out.println("Oak's words echoed...\"There's a time and place for everything, but not now!\"");
					}
				}
			}
			Thread.sleep(1000);
		}
		
		scan.close();
		bc.run();
		
	}
	
	public void currentStatus(Trainer P1, Trainer P2) {
		System.out.println(P1.getName() + "\n" + "Active Pokemon: " + P1.getTeamMember(P1.getPA()).getName() + "\nHP: " + P1.getTeamMember(P1.getPA()).getCurrentHP() + "/" + P1.getTeamMember(P1.getPA()).getHP() + "\n");
		System.out.println(P2.getName() + "\n" + "Active Pokemon: " + P2.getTeamMember(P2.getPA()).getName() + "\nHP: " + P2.getTeamMember(P2.getPA()).getCurrentHP() + "/" + P2.getTeamMember(P2.getPA()).getHP() + "\n");
	}
	
	public int chooseEvent(Trainer P1, Scanner scan) {
		int opcao;
		System.out.println("What will " + P1.getTeamMember(P1.getPA()).getName() + " do?");
		System.out.println("1 - Fight\n2 - Switch Pokemon\n3 - Bag\n4 - Run");
		opcao = scan.nextInt();
		return opcao;
	}
	
	public int choosePlace(Trainer P1, Scanner scan) {
		int opcao;
		System.out.println("What will " + P1.getName() + " do?");
		System.out.println("1 - Walk on Ground\n2 - Walk on Bush\n3 - Finish travel");
		opcao = scan.nextInt();
		return opcao;
	}
	
	public void showItemOptions() {
		System.out.println("Which item?\n");
		System.out.println("1 - Super Potion: Recovers 50 HP\n2 - Hyper Potion - Recovers 200 HP"
				+ "3 - Poke Ball\n4 - Great Ball\n5 - Master Ball\n");
	}
}