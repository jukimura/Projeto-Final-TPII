import java.lang.reflect.Array;
import java.util.ArrayList;

public class Partida extends Thread
{
    private Baralho baralho;

    private Carta descarte = null;

    private ArrayList<Parceiro> jogadores;

    public Partida(ArrayList<Parceiro> usuarios, Baralho baralho){
        this.jogadores = usuarios;
        this.baralho = baralho;
    }

    public void run()
    {
        while(this.jogadores.size() < 3){
            System.out.println("Esperando jogadores...  (" + this.jogadores.size() + "/3)");
            try {
                Thread.sleep(4000);
            }
            catch (InterruptedException e)
            {}
        }

		servidor.receba(new ComunicadoIniciarJogo());
				
		Comunicado comunicado = null;
		do
		{
			comunicado = (Comunicado)servidor.espie();
			System.out.println("BBBBBBBBBBB");
		}
		while(!(comunicado instanceof Mao));
		Mao mao = (Mao)servidor.envie();
        for(;;){
            System.out.println("Comecando o jogo...");
           
        }
    }
}
