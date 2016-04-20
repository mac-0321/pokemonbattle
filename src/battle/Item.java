package battle;

public class Item {
	private String nome;
	public Item(String nome) {
		this.nome = nome;
	}
	public String nome(){
		return nome;
	}
}

class Pokeball extends Item {
	public Pokeball (String nome){
		super(nome);
	}
	private Pokemon captured;
	private String type;
	private int M;
	private int F;
	private int N;
}

class Potion extends Item {
	private int healedHP;
	public Potion (String nome, int HP) {
		super(nome);
		healedHP = HP;
	}
	public int healingHP (Potion potion, Pokemon p) {
		p.setCurrentHP(p.getCurrentHP() + potion.healedHP);
		int restoredHP = potion.healedHP;
		if (p.getCurrentHP() > p.getHP()) {
			restoredHP = p.getHP() + potion.healedHP - p.getCurrentHP();
			p.setCurrentHP(p.getHP());
		}
		return restoredHP;
	}
}