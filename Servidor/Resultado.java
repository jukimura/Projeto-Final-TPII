public class Resultado implements Comunicado
{
    private double valorResultante;

    public Resultado (double valorResultante)
    {
        this.valorResultante = valorResultante;
    }

    public double getValorResultante ()
    {
        return this.valorResultante;
    }
    
    public String toString ()
    {
		return (""+this.valorResultante);
	}

}
