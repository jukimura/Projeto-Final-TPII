public class Mao extends Comunicado{
    private Carta[] mao = new Carta[4];
    private int qtd = 0;

    public Mao(Carta carta1, Carta carta2, Carta carta3)
    {
        this.mao[0] = carta1;
        this.mao[1] = carta2;
        this.mao[2] = carta3;
        qtd = 3;
    }

    public void comprarOuPegarDoMonte(Carta carta)
    {
        this.mao[3] = carta;
        qtd  = 4;
    }

    public void descartar(int pos){
        if(pos == 3)
            this.mao[pos] = null;
        else {
            for (int i = pos; i < qtd-1; i++){
                this.mao[i] = this.mao[i + 1];
            }
            this.mao[3] = null;
        }
        qtd = 3;
    }

    public String toString()
    {
        String toString = "";
        if(qtd > 3){
            for(int i = 0; i < 4; i++){
                toString = toString + this.mao[i].toString() + " ";
            }
        }
        else{
            for(int i = 0; i  < 3; i++){
                toString = toString + this.mao[i].toString() + " ";
            }
        }
        return toString;
    }

    public int getQtd(){
        return this.qtd;
    }

    public boolean somaE21(){
        byte soma = 0;
        for(Carta carta:mao)
        {
            soma += carta.getValor();
        }
        if (soma == 21)
            return true;

        return false;
    }
}

