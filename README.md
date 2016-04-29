# pokemonbattle

9395050 - Alexandre Ohara Keima

9344852 - Chanyee Kim

Exercício 1:
Executar a classe BattleControls para iniciar batalha
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

Exercício 2:

- O programa consiste em uma simulação do jogo Pokémon de forma simplificada. Seu treinador possui 6 Pokémons
- Você escolhe uma das três opções: andar no chão comum, andar na grama, ou finalizar viagem(terminar programa)
- Escolhendo opção de andar no chão comum, sempre aparecerá uma mesma mensagem avisando que nada aconteceu, pois a possibilidade de encontrar um Pokémon selvagem é 0
- Caso escolha andar na grama, terá uma chance aleatória de aparecer um Pokémon selvagem e a batalha(Exercício 1) se inicia automaticamente
- O Pokémon selvagem foi implementado como um treinador com um único Pokémon no seu time
- (Crédito Extra) Nessa batalha, o treinador pode utilizar item e nos casos em que item utilizado é uma PokéBola, o Pokémon selvagem pode ser capturado(nesse caso, a batalha acaba), ou escapar
- Existem 3 tipos de PokéBola: PokéBall, GreatBall, MasterBall
- A fórmula da chance de captura é: f=(HPmax * 255 * 4)/(HPcur * B), que é comparado a um número M aleatório. A fórmula foi tirada do site bulbapedia.bulbagarden.net/wiki/Catch_rate 
