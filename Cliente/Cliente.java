import java.net.*;
import java.io.*;

public class Cliente 
{
    public static final String HOST = "localhost";
    public static final int PORTA_PADRAO = 3000;
    
    public static void main(String[] args)
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
        int qtdCartas = 0;
        String textoCarta = ""; 
        boolean primeiraVez = true;
        int valorCarta = 0;
		Mao mao;
		PilhaDeDescarte pilhaDeDescarte = new PilhaDeDescarte();
		Teclado teclado = new Teclado();
			
		do
		{      
			try
			{
				System.out.println("=============== JOGO 21 ===============");
				
				System.out.print("\nEstas sao suas cartas: ");
				servidor.receba(new PedidoDeMao());
				Comunicado comunicado = null;
				do
				{
					comunicado = (Comunicado)servidor.espie();
				}
				while(!(comunicado instanceof Mao));
				mao = (Mao)servidor.envie();
				
				System.out.println(mao.toString());
				soma = mao.getValorTotal();
				qtdCartas = 3;
				System.out.print("\nSua soma e : " + soma);
				do
				{
					if(primeiraVez == true)
					{
						try
						{
							System.out.print("\n\nDigite 1 para comprar do monte: ");			
							opcao = teclado.getUmInt();
							if(opcao != 1)
								System.out.println("Opcao invalida");
						}
						catch(Exception erro)
						{
							System.out.println("Opcao invalida");
						}
					}
					else
					{
						try
						{
							System.out.print("\n\nDigite 1 para comprar do monte: ");			
							opcao = teclado.getUmInt();
							if(opcao != 1)
								System.out.println("Opcao invalida");
						}
						catch(Exception erro)
						{
							System.out.println("Opcao invalida");
						}
					}
					
				}while (opcao != 1);
				
				qtdCartas = 4;
				//mao.comprarQuartaCarta();
				//System.out.print("\nA carta que voce comprou e: " + mao.getTexto(mao.getQuartaCarta()) + "\n\n");
				
				//System.out.print("\nAgora, estas sao suas cartas: " + mao.getMaoQuatroCartas() + "\n");
				//soma = mao.getValorTotalQuatroCartas();
				System.out.println("\nSua soma e: " + soma + "\n");
				System.out.print("\nQual carta voce deseja descartar? ");
				int opcaoCartaASerDescartada = 0;
				boolean ehValida = false;
				do
				{
					try
					{
						opcaoCartaASerDescartada = teclado.getUmInt();
						if(opcaoCartaASerDescartada != 1 || opcaoCartaASerDescartada != 2 || opcaoCartaASerDescartada != 3 || opcaoCartaASerDescartada != 4)
							ehValida = false;
						else
							ehValida = true;
					} 
					catch(Exception erro)
					{
						//System.out.print(" Você não possui essa carta. Insira uma carta válida [" + mao.getTexto(mao.getPrimeiraCarta()) + ", " + mao.getTexto(mao.getSegundaCarta()) + ", " + mao.getTexto(mao.getTerceiraCarta()) + ", " + mao.getTexto(mao.getQuartaCarta()) + "]: ");
					}
				}while(ehValida != true);
				
				switch(opcaoCartaASerDescartada)
				{
					case 1:
					{
						servidor.receba(mao.descartar(0));
						//pilhaDescarte = mao.getPrimeiraCarta();
						//mao.removerDaMao(pilhaDescarte);
						qtdCartas = 3;
					}	
					break;	
					case 2:
						//pilhaDescarte = mao.getSegundaCarta();
						//mao.removerDaMao(pilhaDescarte);
						qtdCartas = 3;
					break;
					case 3:
						//pilhaDescarte
						//mao.removerDaMao(pilhaDescarte);
						qtdCartas = 3;
					break;
					case 4:
						//pilhaDescarte = mao.getQuartaCarta();
						//mao.removerDaMao(pilhaDescarte);
						qtdCartas = 3;					
					break;
				}
				System.out.print("Agora, estas sao suas cartas: ");
			//	System.out.print(mao.getMaoTresCartas());
				soma = mao.getValorTotal();
				System.out.println("Sua soma agora e: " + soma);
				
				if(soma == 21 && qtdCartas == 3)
				{
					try
					{
						ComunicadoDeVitoria comunicadoDeVitoria = new ComunicadoDeVitoria();
						servidor.receba(comunicadoDeVitoria);
					}
					catch(Exception erro)
					{
						System.err.println("Erro de transmissao");
					}
				}
				
				System.out.print("\n Voce deseja jogar novamente? [S/N]");
				try
				{
					jogarDeNovo = Character.toUpperCase(teclado.getUmChar());			
				}
				catch(Exception erro)
				{
					System.err.println("Opcao invalida!\n");
					continue;
				}
				if("SN".indexOf(jogarDeNovo) == -1)
				{
					System.err.println("Opcao invalida!\n");
					continue;
				}
			}
			catch (Exception erro)
			{
				System.err.println ("Erro de comunicacao com o servidor;");
				System.err.println ("Tente novamente!");
				System.err.println ("Caso o erro persista, termine o programa");
				System.err.println ("e volte a tentar mais tarde!\n");
			}							
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

