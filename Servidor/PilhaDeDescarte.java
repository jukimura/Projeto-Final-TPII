public class PilhaDeDescarte {
    private Carta[] pilhaDeDescarte = new Carta[52];
    private byte qtd = 0;

    public Carta getCarta(int pos) throws Exception
    {
        if(this.pilhaDeDescarte[pos] == null)
            throw new Exception("Posição inválida");


        Carta carta = this.pilhaDeDescarte[pos];

        for(int i = pos; i < this.qtd; i++)
        {
            this.pilhaDeDescarte[i] = this.pilhaDeDescarte[i+1];
        }

        this.qtd--;
        return carta;
    }

    public void adicionarNaPilha(Carta carta)
    {
        this.pilhaDeDescarte[qtd] = carta;
        this.qtd++;
    }

    public String toString()
    {
        String toString = "";

        for(int i = 0; i < qtd; i++)
        {
            toString += pilhaDeDescarte[i].toString() + " ";
        }

        return toString;
    }
}
