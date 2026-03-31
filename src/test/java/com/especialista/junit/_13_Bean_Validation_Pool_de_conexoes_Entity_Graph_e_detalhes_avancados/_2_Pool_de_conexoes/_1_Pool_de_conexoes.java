package com.especialista.junit._13_Bean_Validation_Pool_de_conexoes_Entity_Graph_e_detalhes_avancados._2_Pool_de_conexoes;

import com.especialista.junit.utils.EntityManagerTest;

public class _1_Pool_de_conexoes extends EntityManagerTest {

    /*
        - O que é um Pool de conexões?

            - Um grupo de conexões, que já estarão estabelecidas, só esperando para ser utilizadas


        - Por que usar um Pool de conexões?

            - Criar uma conexão não é algo instantâneo porque envolve um sistema externo que é
           o banco de dados. Principalmente, quando o banco está em uma máquina diferente, pois
           envolve o delay natural da rede


        - Quantas conexões abrir?

            - Fatore que vão influenciar:

                - COREs da máquina em que a aplicação está hospedada
                - Rede
                - Performance da máquina do banco de dados

            - Fórmula de base

                - Use o número de COREs da sua máquina multiplicando por 2
                - E testar

     */
}
