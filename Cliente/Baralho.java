import java.lang.reflect.Array;

public class Baralho {
    //optei por fazer uma classe não genérica pois a classe Conjunto que você fez
    //tem métodos de adicionar, redimencionar etc que o baralho não precisará ter

    private Carta[] baralho = new Carta[52];
    private int qtd; //qtd de cartas no jogo

    //♣ ♠ ♦ ♥
    private String[] texto = {"A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣",
                              "A♠", "2♠", "3♠", "4♠", "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠", "Q♠", "K♠",
                              "A♦", "2♦", "3♦", "4♦", "5♦", "6♦", "7♦", "8♦", "9♦", "10♦", "J♦", "Q♦", "K♦",
                              "A♥", "2♥", "3♥", "4♥", "5♥", "6♥", "7♥", "8♥", "9♥", "10♥", "J♥", "Q♥", "K♥"};

    private int[] valor = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,
                           1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,
                           1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,
                           1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};

    public Baralho(){
        for (byte i = 0; i < 52; i++){
            baralho[i] = new Carta(texto[i], valor[i]);
        }
        qtd = 52;
    }

    public void reset(){
        for (byte i = 0; i < 52; i++){
            baralho[i] = new Carta(texto[i], valor[i]);
        }
        qtd = 52;
    }

    private void removerDoBaralho(int pos){
        for(int i = pos; i < qtd-1; i++){
            baralho[i] = baralho[i+1];
        }
        qtd--;
    }

    public Carta comprar(){
        int pos = (int)Math.round((Math.random() * 100)*(qtd-1)/100);

        Carta carta = baralho[pos];
        removerDoBaralho(pos);
        return carta;
    }
}
