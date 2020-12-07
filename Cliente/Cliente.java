import java.net.*;
import java.io.*;

public class Cliente 
{
    public static final String HOST = "localhost";
    public static final int PORTA_PADRAO = 3000;
    
    public static void main(String[] args) throws Exception
    {
         if (args.length>2)
        {
            System.err.println ("Uso esperado: java Cliente [HOST [PORTA]]\n");
            return;
        }
       
        Socket conexao=null;
        try
        {
            String host = Cliente.HOST;
            int    porta= Cliente.PORTA_PADRAO;

            if (args.length>0)
                host = args[0];

            if (args.length==2)
                porta = Integer.parseInt(args[1]);

            conexao = new Socket (host, porta);
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }
        
        
        ObjectOutputStream transmissor=null;
        try
        {
            transmissor =
            new ObjectOutputStream(
            conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }
        
        
        ObjectInputStream receptor=null;
        try
        {
            receptor =
            new ObjectInputStream(
            conexao.getInputStream());
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }
        
        
        Parceiro servidor=null;
        try
        {
            servidor =
            new Parceiro (conexao, receptor, transmissor);
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }
        
        TratadoraDeComunicadoDeDesligamento tratadoraDeComunicadoDeDesligamento = null;
        try
        {
            tratadoraDeComunicadoDeDesligamento = new TratadoraDeComunicadoDeDesligamento (servidor);
        }
        catch (Exception erro)
        {} // sei que servidor foi instanciado
		
        tratadoraDeComunicadoDeDesligamento.start();
        
        
        
        char jogarDeNovo = ' ';
        int soma = 0;
        int opcao = 0;
        String textoCarta = ""; 
        int valorCarta = 0;
		Mao mao = new Mao();
		Carta carta;
		Carta cartaNova;
		Carta pilhaDescarte;
		Teclado teclado = new Teclado();
        do
        {        
			System.out.println(" =============== JOGO 21 ===============");
			
			System.out.print("\nEstas sao suas cartas: ");
			//mao.montarMao();
			//System.out.println(mao.getMaoTresCartas());
			//soma = mao.getValorTotal();
			System.out.print("\nSua soma e : " + soma);
			System.out.print("\n\nDigite 1 para comprar do monte: ");			
			opcao = teclado.getUmInt();
			
			//cartaNova = baralho.sortearCarta();
			//mao.inserirCartaNaMao(cartaNova);
			System.out.print("\nA carta que voce comprou e: " + mao.getTexto(mao.getQuartaCarta()));
			
			System.out.print("\n\nAgora, estas sao suas cartas: " + mao.getMaoQuatroCartas());
			System.out.print("\nQual carta voce deseja descartar? ");
			int opcaoCartaASerDescartada = 0;
			opcaoCartaASerDescartada = teclado.getUmInt();
			if(opcaoCartaASerDescartada != 1 || opcaoCartaASerDescartada != 2 || opcaoCartaASerDescartada != 3 || opcaoCartaASerDescartada != 4)
				throw new Exception(" Você não possui essa carta. Insira uma carta válida: [" + mao.getTexto(mao.getPrimeiraCarta()) + ", " + mao.getTexto(mao.getSegundaCarta()) + ", " + mao.getTexto(mao.getTerceiraCarta()) + ", " + mao.getTexto(mao.getQuartaCarta()) + "] ");
			switch(opcaoCartaASerDescartada)
			{
				case 1:
				{
					pilhaDescarte = mao.getPrimeiraCarta();
					//mao.removerDaMao(pilhaDescarte);
				}	
				break;	
				case 2:
					pilhaDescarte = mao.getSegundaCarta();
					//mao.removerDaMao(pilhaDescarte);

				break;
				case 3:
					pilhaDescarte = mao.getTerceiraCarta();
					//mao.removerDaMao(pilhaDescarte);

				break;
				case 4:
					pilhaDescarte = mao.getQuartaCarta();
					//mao.removerDaMao(pilhaDescarte);
				break;
			}
			System.out.print("\n Voce deseja jogar novamente? [S/N]");
			jogarDeNovo = teclado.getUmChar();
		}
		while(jogarDeNovo == 'S');
		
		try
		{
			servidor.receba (new PedidoParaSair ());
		}
		catch (Exception erro)
		{}
		
		System.out.println ("Obrigado por usar este programa!");
		System.exit(0);
    }
}

