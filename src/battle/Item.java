package battle;

import java.util.Random;

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
	private String type;
	private int M;
	private int F;
	private int Ball;
	private int B;
	public Pokeball (String nome, String t){
		super(nome);
		this.type = t;
	}
	Random gerador = new Random();
	public boolean catching (Pokemon p) {
		if(type.equals("Master")) {
			return true;
		}
		else if(type.equals("Poke")) {
			M = gerador.nextInt(256);
			Ball = 12;
			B = 255;
		}
		else if(type.equals("Great")) {
			M = gerador.nextInt(201);
			Ball = 8;
			B = 200;
		}
		F = (p.getHP() * 255 * 4)/(p.getCurrentHP() * Ball);
		if (F < M) {
			return false;
		}
		else return true;
	}
	public int getShake (Pokemon p) {
		int D = (p.getCatchRate() * 100)/B;
		int x;
		if(D >= 256) {
			return 3;
		}
		else x = (D * F)/255;
		if(x < 10) {
			return 0;
		}
		else if(x < 30) {
			return 1;
		}
		else if(x < 70) {
			return 2;
		}
		else return 3;
	}
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