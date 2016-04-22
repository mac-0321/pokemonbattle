# pokemonbattle
9395050 - Alexandre Ohara Keima
9344852 - Chanyee Kim

Exercício 1:
- Cada treinador possui 6 Pokémons, sendo Red o treinador controlado pelo usuário e Gary o adversário.
- O time do Red é: Charizard, Blastoise, Venusaur, Pikachu, Lapras e Snorlax
- O time do Gary é: Pidgeot, Alakazam, Rhydon, Arcanine, Gyarados e Dragonite
- Cada Pokémon possui 4 habilidades diferentes, que possuem o mesmo tipo do Pokémon (Ex: Gyarados usando Thunder terá efeito água, pois Gyarados é do tipo água)
- A fórmula do cálculo do dano é : Damage = ((((2 * Level / 5 + 2) * AttackStat * AttackPower / DefenseStat) / 50) + 2) * STAB * Weakness/Resistance * RandomNumber / 100
- Em cada rodada, o usuário escolhe se quer atacar, usar poção, trocar de Pokémon ou fugir da batalha
- A ordem de prioridade em uma rodada é: FUGIR DA BATALHA > TROCAR POKÉMON ATIVO > USAR POÇÃO > PRIORIDADE DE ATK > SPEED POKÉMON
- Prioridade de ataque são ataques que sempre atacam primeiro independente da velocidade do Pokémon (Ex: Quick Attack)
- O usuário não escolhe qual habilidade o Pokémon irá usar e nem qual Pokémon será substituído caso escolha a opção de trocar
- O usuário poderá escolher entre dois tipos de poções: Super Potion e Hyper Potion
- Gary apenas ataca ou utiliza poção aleatoriamente.
- Gary apenas utiliza Super Potion (para não tornar a batalha muito extensa)
- A batalha termina quando não há mais Pokémons com vida ou quando Red decide fugir
- (Crédito Extra) Foi implementado o sistema de fraqueza baseado no tipo de Pokémon. A tabela de fraqueza pode ser conferida no site http://pokemondb.net/type
