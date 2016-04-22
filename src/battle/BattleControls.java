package battle;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class BattleControls extends Controller {
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
	private String message;
	private Pokemon[] teamRed = {Char, Bla, Venu, Pika, Lap, Snor};
	private Pokemon[] teamGary = {Pid, Ala, Rhy, Arc, Gya, Drag};
	private Potion hpotion = new Potion("Hyper Potion", 200);
	private Potion spotion = new Potion("Super Potion", 50);
	private Item[] itemList = {hpotion, spotion};
	private Trainer Red = new Trainer("Red", teamRed, itemList);
	private Trainer Gary = new Trainer("Gary", teamGary, itemList);
	
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
				return P1.getName() + " defeated " + P2.getName() + "!\n" + P1.getName() + "got $2000 for winning!";
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
	
	private class Run extends Event {
		public Run(long eventTime) {
			super(eventTime);
		}
		public void action() {
			return;
			//não sei o que botar aqui
		}
		public String description() {
			return "Got away safely!";
		}
	}
	
	private class Fight extends Event {
		Trainer ATK;
		Trainer DEF;
		int i;
		public Fight(long eventTime, Trainer P1, Trainer P2, int move) {
			super(eventTime);
			ATK = P1;
			DEF = P2;
			i = move;
		}
		public void action() {
			/*if((DEF.getTeamMember(DEF.getPA()).moves[j].getPri()) && (ATK.getTeamMember(ATK.getPA()).moves[i].getPri())) {
				if(ATK.getTeamMember(ATK.getPA()).getSpeed() < DEF.getTeamMember(DEF.getPA()).getSpeed()) {
					Trainer Temp = ATK;
					ATK = DEF;
					DEF = Temp;
					int temp = i;
					i = j;
					j = temp;
				}
			}
			else if(DEF.getTeamMember(DEF.getPA()).moves[j].getPri()){
				Trainer Temp = ATK;
				ATK = DEF;
				DEF = Temp;
				int temp = i;
				i = j;
				j = temp;
			}
			else if(ATK.getTeamMember(ATK.getPA()).getSpeed() < DEF.getTeamMember(DEF.getPA()).getSpeed()) {
				Trainer Temp = ATK;
				ATK = DEF;
				DEF = Temp;
				int temp = i;
				i = j;
				j = temp;
			}*/
			if(ATK.getTeamMember(ATK.getPA()).getFainted() == false){
				int damage = (int) ATK.getTeamMember(ATK.getPA()).moves[i].DamageCalculate(ATK.getTeamMember(ATK.getPA()).getAtt(), ATK.getTeamMember(ATK.getPA()).getDef(),
								ATK.getTeamMember(ATK.getPA()).getAdv(DEF.getTeamMember(DEF.getPA()).getType()));
				
				DEF.getTeamMember(DEF.getPA()).dmgReceived((int)damage);
				
				message = ATK.getName()+"'s " + ATK.getTeamMember(ATK.getPA()).getName() + " used " + ATK.getTeamMember(ATK.getPA()).moves[i].getMove() +
						  "!\n" + ATK.getTeamMember(ATK.getPA()).getName() + " dealt " + damage + "damage!\n" + ATK.getTeamMember(ATK.getPA()).getAdvString(ATK.getTeamMember(ATK.getPA()).getAdv(DEF.getTeamMember(DEF.getPA()).getType()));
			}
			if(DEF.getTeamMember(DEF.getPA()).getFainted()) {
				message += DEF.getTeamMember(DEF.getPA()).getName() + " fainted!";
				addEvent(new SwitchPKMN(System.currentTimeMillis() + 2000, DEF, ATK));
			}
		}
		public String description() {
			return message;
		}
	}
	
	/*public class BattleStarts extends Event {
		long time = System.currentTimeMillis();
		public BattleStarts (long eventTime){
			super(eventTime);
		}
		public void action() {
			Random gerador = new Random();
			int option;
			while(Red.getTeamMember(Red.getPA()).getFainted() == false) {
				currentStatus(Red, Gary);
				int RedMove = gerador.nextInt(4); //sera escolhida uma habilidade aleatoria do pokemon de Red
				int GaryMove = gerador.nextInt(4); //sera escolhida uma habilidade aleatoria do pokemon de Gary
				int GaryChoice = gerador.nextInt(100) + 1; //Se cair entre 1 e 85, Gary ataca (85% de chances de acontecer). 86 a 100, Gary usa pocao.
				option = chooseEvent(Red);
				if (GaryChoice <= 85 && option == 1) { //se Red e Gary decidirem atacar, ver qual pokemon e mais rapido
					if((Red.getTeamMember(Red.getPA()).moves[RedMove].getPri() && Gary.getTeamMember(Gary.getPA()).moves[GaryMove].getPri()) || (!Red.getTeamMember(Red.getPA()).moves[RedMove].getPri() && !Gary.getTeamMember(Gary.getPA()).moves[GaryMove].getPri())) {
						if(Red.getTeamMember(Red.getPA()).getSpeed() < Gary.getTeamMember(Gary.getPA()).getSpeed()){
							addEvent(new Fight(time + milli, Gary, Red, GaryMove));
							milli = setMillisec(milli);
							addEvent(new Fight(time + milli, Red, Gary, RedMove));
							milli = setMillisec(milli);
						}
						else {
							addEvent(new Fight(time + milli, Red, Gary, RedMove));
							milli = setMillisec(milli);
							addEvent(new Fight(time + milli, Gary, Red, GaryMove));
							milli = setMillisec(milli);
						}
					}
					else if(Red.getTeamMember(Red.getPA()).moves[RedMove].getPri() && !Gary.getTeamMember(Gary.getPA()).moves[GaryMove].getPri()){
						addEvent(new Fight(time + milli, Red, Gary, RedMove));
						milli = setMillisec(milli);
						addEvent(new Fight(time + milli, Gary, Red, GaryMove));
						milli = setMillisec(milli);
					}
					else {
						addEvent(new Fight(time + milli, Gary, Red, GaryMove));
						milli = setMillisec(milli);
						addEvent(new Fight(time + milli, Red, Gary, RedMove));
						milli = setMillisec(milli);
					}
				}
			}
		}
		public String description() {
			return "Gary challenged to a battle!";
		}
	}
	*/
	public static void main(String[] args) throws InterruptedException {
		BattleControls bc = new BattleControls();
		long time = System.currentTimeMillis();
		Random gerador = new Random();
		int option;
		Scanner scan = new Scanner(System.in);
		
		while(bc.Red.getTeamMember(bc.Red.getPA()).getFainted() == false) {
			bc.currentStatus(bc.Red, bc.Gary);
			int RedMove = gerador.nextInt(4); //sera escolhida uma habilidade aleatoria do pokemon de Red
			int GaryMove = gerador.nextInt(4); //sera escolhida uma habilidade aleatoria do pokemon de Gary
			int GaryChoice = gerador.nextInt(100) + 1; //Se cair entre 1 e 85, Gary ataca (85% de chances de acontecer). 86 a 100, Gary usa pocao.
			Thread.sleep(1500);
			option = bc.chooseEvent(bc.Red, scan);

			if (GaryChoice <= 85 && option == 1) { //se Red e Gary decidirem atacar, ver qual pokemon e mais rapido
				if((bc.Red.getTeamMember(bc.Red.getPA()).moves[RedMove].getPri() == bc.Gary.getTeamMember(bc.Gary.getPA()).moves[GaryMove].getPri())) {
					if(bc.Red.getTeamMember(bc.Red.getPA()).getSpeed() < bc.Gary.getTeamMember(bc.Gary.getPA()).getSpeed()){
						bc.addEvent(bc.new Fight(time + 1000, bc.Gary, bc.Red, GaryMove));
						bc.addEvent(bc.new Fight(time + 2000, bc.Red, bc.Gary, RedMove));
						bc.run();
						Thread.sleep(2000);
					}
					else {
						bc.addEvent(bc.new Fight(time + 1000, bc.Red, bc.Gary, RedMove));
						bc.addEvent(bc.new Fight(time + 2000, bc.Gary, bc.Red, GaryMove));
						bc.run();
						Thread.sleep(2000);
					}
				}
				else if(bc.Red.getTeamMember(bc.Red.getPA()).moves[RedMove].getPri() == true && bc.Gary.getTeamMember(bc.Gary.getPA()).moves[GaryMove].getPri() == false){
					bc.addEvent(bc.new Fight(time + 1000, bc.Red, bc.Gary, RedMove));
					bc.addEvent(bc.new Fight(time + 2000, bc.Gary, bc.Red, GaryMove));
					bc.run();
					Thread.sleep(2000);
				}
				else {
					bc.addEvent(bc.new Fight(time + 2000, bc.Gary, bc.Red, GaryMove));
					bc.addEvent(bc.new Fight(time + 1000, bc.Red, bc.Gary, RedMove));
					bc.run();
					Thread.sleep(2000);
				}
			}
			else if (GaryChoice <= 85 && option == 2) {
				bc.addEvent(bc.new SwitchPKMN(time, bc.Red, bc.Gary));
				bc.addEvent(bc.new Fight(time + 2000, bc.Gary, bc.Red, GaryMove));
				bc.run();
				Thread.sleep(2000);
			}
			else if (GaryChoice <= 85 && option == 3) {
				bc.showPotionOptions();
				int pot = scan.nextInt();
				if (pot == 1) {
					bc.addEvent(bc.new HealPokemon(time, bc.Red, bc.spotion));
				}
				else {
					bc.addEvent(bc.new HealPokemon(time, bc.Red, bc.hpotion));
				}
				bc.addEvent(bc.new Fight(time + 2000, bc.Gary, bc.Red, GaryMove));
				bc.run();
				Thread.sleep(2000);
			}
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
	
	public void showPotionOptions() {
		System.out.println("Which item?\n");
		System.out.println("1 - Super Potion: Recovers 50 HP\n2 - Hyper Potion - Recovers 200 HP");
	}

}