package logica;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Servidor extends Thread{

	private String porta = "";

	private String ipLocal = "";

	private String nome = "";

	private String senhaCriptografada = "";

	private ServerSocket serverSocket;


	/**
	 * Seta os parametros fundamentais do servidor e o inicia.
	 *
	 * @author Thiago A. L. Genez
	 * @param nome
	 * @param ipLocal
	 * @param senhaCriptografada
	 */
	public boolean startServidor(String nome, String porta,
			String senhaCriptografada) {
		this.nome = nome;
		this.porta = porta;
		this.senhaCriptografada = senhaCriptografada;
		return (this.inicializar());
	}

	/**
	 * Construtor
	 */
	public Servidor() {
		this.setIpLocal();
		this.setNome();
	}

	/**
	 * Inicializa o Servidor
	 *
	 * @author Thiago A. L. Genez
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private boolean inicializar() {
		try {
			this.serverSocket = new ServerSocket(Integer.parseInt(this.porta));
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("ERRO NO NUMERO DA PORTA");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERRO NO SERVER SOCKET");
		}
		return false;

	}

	public void esperaConexaoCliente() {
		Socket socket = null;
		while (true) {
			try {
				socket = this.serverSocket.accept();
				new ThreadCliente(socket).start();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try{
					//Encerro o socket de comunica��o
					socket.close();

				    //Encerro o ServerSocket
					this.serverSocket.close();
				}catch(IOException e){
				}
			}
		}
	}

	@Override
	protected void finalize ( ) {
		try{
		    //Encerro o ServerSocket
			this.serverSocket.close();
		}catch(IOException e){
		}
	}

	public String getIpLocal() {
		return this.ipLocal;
	}

	public void setIpLocal() {
		InetAddress in;
		try {
			in = InetAddress.getLocalHost();
			this.ipLocal = in.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.err.println("ERRO NA CAPTURA DO IP LOCAL");
		}
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome() {
		try {
			InetAddress in = InetAddress.getLocalHost();
			this.nome = in.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.err.println("ERRO NA CAPTURA DO IP LOCAL");
		}
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenhaCriptografada() {
		return senhaCriptografada;
	}

	public void setSenhaCriptografada(String senha) {
		this.senhaCriptografada = senha;
	}

}