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
        String textoCarta = ""; 
        int valorCarta = 0;
		PilhaDeDescarte pilhaDeDescarte = new PilhaDeDescarte();
		Teclado teclado = new Teclado();
		Carta cartaComprada;
		
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
				Mao mao = (Mao)servidor.envie();
				
				System.out.println(mao.toString());
				soma = mao.getValorTotal();
				
				System.out.print("\nSua soma e : " + soma);
				
				do
				{
					if(pilhaDeDescarte.getQtdCartas() == 0)
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
							System.out.println("\n Pilha de descartes: " + pilhaDeDescarte.toString());
							System.out.print("\n\nDigite 1 para comprar do monte ou 2 para comprar da pilha de descartes: ");			
							opcao = teclado.getUmInt();
							if(opcao != 1 || opcao != 2)
								System.out.println("Opcao invalida");
						}
						catch(Exception erro)
						{
							System.out.println("Opcao invalida");
						}
					}
					
				}while (opcao != 1 || opcao != 2);
				
				
				if(opcao == 1)
				{
					servidor.receba(new PedidoDeCompra());
					do
					{
						comunicado = (Comunicado)servidor.espie();
					}
					while(!(comunicado instanceof Carta));
					cartaComprada = (Carta)servidor.envie();
				}
				else
				{
					servidor.receba(new PedidoPilhaDeDescarte());
					do
					{
						comunicado = (Comunicado)servidor.espie();
					}
					while(!(comunicado instanceof Carta));
					cartaComprada = (Carta)servidor.envie();
				}
				
				System.out.print("\nA carta que voce comprou e: " + cartaComprada + "\n\n");
				System.out.print("\nAgora, estas sao suas cartas: " +mao.toString() + "\n");
				
				soma = mao.getValorTotal();
				System.out.println("\nSua soma e: " + soma + "\n");
				
				System.out.print("\nQual carta voce deseja descartar[1, 2, 3, 4]? ");
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
						System.out.print("Opcao invalida");
					}
				}while(ehValida != true);
				
				switch(opcaoCartaASerDescartada)
				{
					case 1:
					{
						Carta carta1 = mao.descartar(0);
						pilhaDeDescarte.adicionarNaPilha(carta1); 
						servidor.receba(carta1);
					}	
					break;	
					case 2:
						Carta carta2 = mao.descartar(1);
						pilhaDeDescarte.adicionarNaPilha(carta2); 
						servidor.receba(carta2);
					break;
					case 3:
						Carta carta3 = mao.descartar(2);
						pilhaDeDescarte.adicionarNaPilha(carta3); 
						servidor.receba(carta3);
					break;
					case 4:
						Carta carta4 = mao.descartar(3);
						pilhaDeDescarte.adicionarNaPilha(carta4); 
						servidor.receba(carta4);
					break;
				}
				System.out.print("Agora, estas sao suas cartas: " + mao.toString() + "\n");
				soma = mao.getValorTotal();
				System.out.println("Sua soma agora e: " + soma);
				
				if(soma == 21)
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


