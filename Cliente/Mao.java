
public class Mao 
{
	private Carta primeiraCarta, segundaCarta, terceiraCarta, quartaCarta;
	private Baralho baralho = new Baralho();
	private int soma = 0;
	
	public Mao()
	{
		this.primeiraCarta = null;
		this.segundaCarta = null;
		this.terceiraCarta = null;
		this.quartaCarta = null;
		this.soma = 0;
	}
	
	public void montarMao()
	{
		this.primeiraCarta = baralho.comprar();
		this.segundaCarta = baralho.comprar();
		this.segundaCarta = baralho.comprar();
	}
	
	public int getValorTotal()
	{
		this.soma = getValor(this.primeiraCarta) + getValor(this.segundaCarta) + getValor(this.terceiraCarta);
		return soma;
	}
	
	public String getMaoTresCartas()
	{
		return getTexto(getPrimeiraCarta()) + ", " + getTexto(getSegundaCarta()) + ", " + getTexto(getTerceiraCarta());
	}
	
	public Carta getPrimeiraCarta()
	{
		return primeiraCarta;
	}
	
	public Carta getSegundaCarta()
	{
		return segundaCarta;
	}
	
	public Carta getTerceiraCarta()
	{
		return terceiraCarta;
	}
	
	public Carta getQuartaCarta()
	{
		this.quartaCarta = baralho.comprar();
		return this.quartaCarta;
	}
	
	public String getTexto (Carta carta)
	{
		return carta.toString();
	}
	
	public int getValor(Carta carta)
	{
		return carta.getValor();
	}
	
	public void comprarQuartaCarta()
	{
		this.quartaCarta = baralho.comprar();
	}
	
	public String getMaoQuatroCartas()
	{
		return getTexto(getPrimeiraCarta()) + ", " + getTexto(getSegundaCarta()) + ", " + getTexto(getTerceiraCarta()) + ", " + getTexto(getQuartaCarta());
	}
	
	public void removerDaMao(int opcao)
	{
	}

}
