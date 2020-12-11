import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread
{
    private double              valor=0;
    private Parceiro            usuario;
    private Socket              conexao;
    private ArrayList<Parceiro> usuarios;
    private Baralho             baralho;
	

    public SupervisoraDeConexao
            (Socket conexao, ArrayList<Parceiro> usuarios, Baralho baralho)
            throws Exception
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");

        if (baralho ==null)
            throw new Exception("Baralho ausente");

        this.conexao  = conexao;
        this.usuarios = usuarios;
        this.baralho = baralho;
    }


    public void run ()
    {
        ObjectOutputStream transmissor;
        try
        {
            transmissor =
                    new ObjectOutputStream(
                            this.conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            return;
        }

        ObjectInputStream receptor=null;
        try
        {
            receptor=
                    new ObjectInputStream(
                            this.conexao.getInputStream());
        }
        catch (Exception err0)
        {
            try
            {
                transmissor.close();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }

        try
        {
            this.usuario =
                    new Parceiro (this.conexao,
                            receptor,
                            transmissor);
        }
        catch (Exception erro)
        {} // sei que passei os parametros corretos



        try
        {
            synchronized (this.usuarios)
            {
                this.usuarios.add (this.usuario);
            }

            QuantidadeJogadoresMsg qtdJogadores = new QuantidadeJogadoresMsg();

            for(;usuarios.size()<3;)
            {
                this.usuario.receba(qtdJogadores);
            }

            for(;;)
            {
                Comunicado comunicado = this.usuario.envie ();

                if (comunicado==null)
                    return;
                    
                else if (comunicado instanceof PedidoDeCompra)
                {
                    this.usuario.compra (baralho.comprar());
                }
                else if (comunicado instanceof Carta)
                {
                    this.usuario.compra(baralho.comprar());
                }
                else if (comunicado instanceof PedidoDeMao)
                {
					Mao mao = new Mao(baralho.comprar(), baralho.comprar(), baralho.comprar());
                    this.usuario.receba(mao);
                }
                else if (comunicado instanceof PedidoParaSair)
                {
                    synchronized (this.usuarios)
                    {
                        this.usuarios.remove (this.usuario);
                    }
                    this.usuario.adeus();
                }
            }
        }
        catch (Exception erro)
        {
            try
            {
                transmissor.close ();
                receptor   .close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }
}

