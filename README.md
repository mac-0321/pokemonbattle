# pokemonbattle

9395050 - Alexandre Ohara Keima

9344852 - Chanyee Kim

Exercício 1:

- O programa consiste em uma simulação de batalha Pokémon. Cada treinador possui 6 Pokémons
- O time do Red é: Charizard, Blastoise, Venusaur, Pikachu, Lapras e Snorlax
- O time do Gary é: Pidgeot, Alakazam, Rhydon, Arcanine, Gyarados e Dragonite
- Cada Pokémon possui 4 habilidades diferentes, que possuem o mesmo tipo do Pokémon (Ex: Gyarados usando Thunder(elétrico) terá efeito água, pois Gyarados é do tipo água)
- A fórmula do cálculo do dano é : Damage = ((((2 * Level / 5 + 2) * AttackStat * AttackPower / DefenseStat) / 50) + 2) * STAB * Weakness/Resistance * RandomNumber / 100
- O treinador pode atacar, usar poção, trocar de pokémon e fugir da batalha
- A ordem de prioridade em uma rodada é: FUGIR DA BATALHA > TROCAR POKÉMON ATIVO > USAR POÇÃO > PRIORIDADE DE ATK > SPEED POKÉMON
- Prioridade de ataque são ataques que sempre atacam primeiro independente da velocidade do Pokémon (Ex: Quick Attack)
- Existem dois tipos de poções implementadas: Super potion (recupera 50 HP) e Hyper potion (recupera 200 HP)
- As ações do Red foram pré-definidas para que não ocorram diferentes batalhas em cada execução do programa, conforme pedido pelo professor
- Gary apenas ataca e cura seus pokémons a cada 10 rodadas
- Red usa apenas Hyper Potions quando seleciona o evento de curar o Pokémon. Gary usa Super Potions e 1 Hyper potion
- A batalha termina quando não há mais Pokémons com vida ou quando Red decide fugir (na simulação, Red ganha a batalha, então o evento Run não é utilizado)
- (Crédito Extra) Foi implementado o sistema de fraqueza baseado no tipo do Pokémon. A tabela de fraqueza pode ser conferida no site http://pokemondb.net/type
