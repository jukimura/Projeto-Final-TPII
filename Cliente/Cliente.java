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
        int opcaoCartaASerDescartada = 0;
		Mao mao = new Mao();
		Carta cartaNova = new Carta();
		Carta pilhaDescarte = new Carta();
		Teclado teclado = new Teclado();
        do
        {        
			System.out.println(" =============== JOGO 21 ===============");
			
			Baralho baralho = new Baralho();
			//baralho.distribuirCartas();
			//mao.getMao();
			System.out.print("\nEstas sao suas cartas: ");
			//System.out.println(mao.getMao());
			//soma = mao.getvalorTotal();
			System.out.print("\nSua soma e :" + soma);
			System.out.print("\n\nDigite 1 para comprar do monte: ");
			opcao = teclado.getUmInt();
			
			//cartaNova = baralho.sortearCarta();
			//mao.inserirCartaNaMao(cartaNova);
			System.out.print("A carta que voce comprou e :");
			//System.out.print(baralho.getCartaComprada);
			
			//System.out.print("Agora, estas sao suas cartas:" + mao.getMao4Cartas());
			System.out.print("Qual carta voce deseja descartar? ");
			opcaoASerDescartada = teclado.getUmInt();
			
			switch(opcaoCartaASerDescartada)
			{
				case 1:
				
				break;
				
				case 2:
				
				break;
				
				case 3:
				
				break;
				
				case 4: 
				
				break;
			
			
			System.out.print("\n Voce deseja jogar novamente? [S/N]");
			jogarDeNovo = teclado.getUmChar();
		}while(jogarDeNovo == 'S');
    }
}
