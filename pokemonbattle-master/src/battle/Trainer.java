package battle;

public class Trainer {
	private String name;
	private Pokemon[] team = new Pokemon[6];
	private Item[] bag = new Item[5];
	private int PA; //Pokemon ativo na batalha
	private boolean defeated = false;
	private boolean encounter = false;
	
	public Trainer(String name, Pokemon[] t, Item[] b){
		this.name = name;
		for(int i = 0; i != t.length; i++){
			team[i] = t[i];
		}
		for(int i = 0; i != b.length; i++){
			bag[i] = b[i];
		}
	}
	
	public void encounter() {
		encounter = true;
	}
	
	public boolean getEncounter() {
		return encounter;
	}
	
	public Pokemon getTeamMember(int i){
		return team[i];
	}
	
	public Item getItem(int i){
		return bag[i];
	}
	
	public void setPA(int i){
		PA = i;
	}
	
	public int getPA(){
		return PA;
	}
	
	public boolean nextPA() {
		for(int i = 0; i < 6; i++) {
			if(!team[i].getFainted() && i != PA){
				PA = i;
				return true;
			}
		}
		defeated = true;
		return false;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean defeated(){
		return defeated;
	}
	
}
