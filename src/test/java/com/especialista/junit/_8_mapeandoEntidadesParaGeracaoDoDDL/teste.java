package com.especialista.junit._8_mapeandoEntidadesParaGeracaoDoDDL;

public class teste {

    public static void main(String[] args) {

        int linhas = 8;
        int colunas = 13;
        char[][] a = new char[linhas][colunas];

        for (int x = 0; x < linhas; x++) {

            int meio = colunas / 2;
            int inicio = meio - x;
            int fim = meio + x;

            for (int y = 0; y < colunas; y++) {

                if (y >= inicio && y <= fim) {
                    a[x][y] = '*';
                } else {
                    a[x][y] = ' ';
                }

                if (linhas == x + 1) {

                    if (y == meio) {
                        a[x][y] = '|'; // coloca | no meio da última linha
                    }else {
                        a[x][y] = ' ';
                    }
                }


            }
        }

        for (int x = 0; x < linhas; x++) {
            for (int y = 0; y < colunas; y++) {
                System.out.print(a[x][y] + " ");
            }
            System.out.println();
        }
    }
}
