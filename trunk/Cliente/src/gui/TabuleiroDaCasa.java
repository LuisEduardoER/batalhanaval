package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logica.PictureTabuleiro;
import logica.TabuleiroLogico;

public class TabuleiroDaCasa extends JPanel {

	private static final long serialVersionUID = 1L;

	private TabuleiroLogico tabuleiroLogico = null; // @jve:decl-index=0:

	private ArrayList<PictureTabuleiro> picturesList = null;

	private PainelControle painelControle = null;

	private AdaptadorDoMouse adaptadorDoMouse = null; // @jve:decl-index=0:

	private AdptadorDoMouseMovimento adptadorDoMouseMovimento = null; // @jve:decl-index=0:

	private Vector<PictureTabuleiro> pictureTabuleiro = null;  //  @jve:decl-index=0:

	private Point posicaoDoCursor = null; // @jve:decl-index=0:

	private boolean handlerOFF;

	private String shipName = null;

	private int larguraShip = -1;

	private int alturaShip = -1;

	private int naviosPosicionadosParaJogar = 0;

	/**
	 * Default Construtor
	 *
	 * @param painelControle
	 */
	public TabuleiroDaCasa(PainelControle painelControle) {
		super();
		initialize();
		this.painelControle = painelControle;
	}


	/**
	 * Default Construtor
	 */
	public TabuleiroDaCasa() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(new Dimension(250, 250));
		this.setLayout(null);
		this
				.setBorder(BorderFactory.createLineBorder(
						new Color(0, 100, 90), 1));

		this.tabuleiroLogico = new TabuleiroLogico();
		this.picturesList = new ArrayList<PictureTabuleiro>();
		this.pictureTabuleiro = new Vector<PictureTabuleiro>(5);
		this.pictureTabuleiro.setSize(5);
		this.handlerOFF = false;
		this.adaptadorDoMouse = new AdaptadorDoMouse();
		this.adptadorDoMouseMovimento = new AdptadorDoMouseMovimento();
		this.addMouseListener(this.adaptadorDoMouse);
		this.addMouseMotionListener(this.adptadorDoMouseMovimento);
		this.posicaoDoCursor = new Point();
	}

	/**
	 * Metodo para pintar na tela o tabuleiro e os navios
	 */
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		GradientPaint gp = new GradientPaint(0.0f, 0.0f, Color.BLUE, 250.0f,
				250.0f, Color.CYAN);
		g2.setPaint(gp);

		// Desenha o tabuleiro, de acordo com o gradiente
		g2.fillRect(0, 0, 250, 251);

		g2.setColor(new Color(0, 100, 90));

		// Desenha as linhas do tabuleiro
		for (int i = 1; i < 10; i++) {
			g2.drawLine(i * 25, 0, i * 25, 250);
			g2.drawLine(0, i * 25, 250, i * 25);
		}

		// Desenha as imagens
		for (int i = 0; i < pictureTabuleiro.size(); i++) {

			if (pictureTabuleiro.get(i) != null) {

				g2.drawImage(pictureTabuleiro.get(i).getImagem(),
						pictureTabuleiro.get(i).getPointBegin().x,
						pictureTabuleiro.get(i).getPointBegin().y, this);
			}
		}

		for (PictureTabuleiro i : this.picturesList) {
			g.drawImage(i.getImagem(), i.getPointBegin().x,
					i.getPointBegin().y, this);
		}

		if (this.painelControle.imagemLastShip == null) {
			return;
		}

		Point p = corrigirPoint(this.posicaoDoCursor.x, this.posicaoDoCursor.y);

		if (this.painelControle.verticalShip) {
			g2.fill3DRect(p.x, p.y, 25 + 1,
					this.painelControle.larguraLastShip + 1, true);
		} else {
			g2.fill3DRect(p.x, p.y, this.painelControle.larguraLastShip + 1,
					25 + 1, true);
		}

	}

	/**
	 * Retorna um ponto "corrido" para desenhar o navio na tela corretamente
	 */
	private Point corrigirPoint(int x, int y) {

		int pointXcorrigido = (int) (x / 25);
		int pointYcorrigido = (int) (y / 25);

		return new Point(pointXcorrigido * 25, pointYcorrigido * 25);
	}

	/**
	 * MouseHandler.java
	 *
	 *
	 * O prop�sito desta classe � lidar com eventos de mouse sobre o tabuleiro,
	 * de forma a saber em qual posi��o ser� inserida a imagem
	 */

	/**
	 * Classe interna para realizar os eventos do Mouse.
	 */
	private class AdaptadorDoMouse extends MouseAdapter {

		/**
		 * Metodo para o evento do mouse precionado. Se for botao Esquerdo do
		 * mouse, posiciona o navio no tabuleiro Se for botao Direito rotaciona
		 * o navio para vertical e horizontal
		 *
		 * @param MouseEvent
		 */
		public void mousePressed(MouseEvent me) {

			/*
			 * Botao Esquerdo do Mouse
			 */
			if (me.getButton() == MouseEvent.BUTTON1) {

				if (painelControle.lastShipName == null
						&& painelControle.posicaoLastShip == -1) {
					JOptionPane.showMessageDialog(null,
							"SELECIONE UM NAVIO PARA POSICIONAR NO TABULEIRO.",
							"SELECIONE UM NAVIO", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (painelControle != null) {

					if (painelControle.verticalShip) {

						shipName = painelControle.lastShipName + "v";
						larguraShip = 25;
						alturaShip = painelControle.larguraLastShip;

					} else {

						shipName = painelControle.lastShipName;
						alturaShip = 25;
						larguraShip = painelControle.larguraLastShip;
					}

					addNavioTabuleiro(me.getX(), me.getY());
					painelControle.verticalShip = false;
				}
				return;
			}

			/*
			 * Botao Direito do Mouse
			 */
			if (me.getButton() == MouseEvent.BUTTON3) {

				painelControle.verticalShip = !painelControle.verticalShip;
				repaint();
			}

		}
	}

	/**
	 * Classe interna para tratar o evento do Mouse, em relacao ao deslocamento
	 * do mesmo
	 */
	private class AdptadorDoMouseMovimento extends MouseMotionAdapter {

		/**
		 * Metodo para tratar o evento do movimento do Mouse e ao mesmo tempo
		 * ajsta o ponto atual do cursor
		 *
		 * @param MouseEvent
		 */
		public void mouseMoved(MouseEvent me) {

			posicaoDoCursor.setLocation(me.getPoint());
			repaint();
		}

	}

	/**
	 * Insere o navio no tabuleiro nas coordenadas X e Y do evento do mouseMoved
	 *
	 * @see AdptadorDoMouseMovimento
	 * @param x,y
	 */
	private void addNavioTabuleiro(int x, int y) {

		boolean checkPosicao = this.tabuleiroLogico.validaPosicaoInsercao(
				this.shipName, this.larguraShip, this.alturaShip, x, y,
				this.painelControle.verticalShip);

		if (checkPosicao) {

			// Atualiza o nome da imagem para "X.v", onde X � o nome do navio
			// solicitado.

			if (painelControle.verticalShip) {

				this.pictureTabuleiro.setElementAt(new PictureTabuleiro(
						new ImageIcon(PainelControle.DIRETORIO_IMAGES
								+ this.shipName + ".gif").getImage(), this
								.corrigirPoint(x, y)),
						this.painelControle.posicaoLastShip);

			} else {
				this.pictureTabuleiro.setElementAt(new PictureTabuleiro(
						this.painelControle.imagemLastShip, this.corrigirPoint(
								x, y)), this.painelControle.posicaoLastShip);
				/*
				 * this.pictureTabuleiro.add(this.painelControle.posicaoLastShip,
				 * new PictureTabuleiro( this.painelControle.imagemLastShip,
				 * this .corrigirPoint(x, y)));
				 */
			}

			// Reconfigura a �rea de navio
			this.painelControle.desativarLastShipSelected();
			this.painelControle.lastShipName = null;
			this.painelControle.larguraLastShip = -1;
			this.painelControle.posicaoLastShip = -1;
			this.painelControle.imagemLastShip = null;

			// Se todos os navios est�o configurados, retira-se o listener de
			// evento do tabuleiro.
			if (++this.naviosPosicionadosParaJogar == 5) {

				this.turnOFFHandlers();
				// painelDoJogo.trocarPaineis();
				// areaCentral.habilitaBotaoOk();
				JOptionPane.showMessageDialog(null,
						"Pecas Posicionadas!!!\n\nPronto para Jogar!",
						"Ao Ataque!", JOptionPane.INFORMATION_MESSAGE);
			}
			repaint();
		} else {
			JOptionPane
					.showMessageDialog(
							null,
							"SEU NAVIO N�O PODE SER COLOCADO NESTA POSI��O. TENTE NOVAMENTE",
							"ERRO DE POSICIONAMENTO DO NAVIO",
							JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Metodo para retirar os Eventos do mouse, depois que o tabuleiro estiver
	 * Pronto
	 */
	public void turnOFFHandlers() {

		if (!this.handlerOFF) {
			removeMouseListener(this.adaptadorDoMouse);
			removeMouseMotionListener(this.adptadorDoMouseMovimento);
			setEnabled(false);
			this.handlerOFF = true;
		}

	}

	/**
	 * Metodo para ativar os Eventos do Mouse
	 */
	public void turnONHandlers() {

		if (this.handlerOFF) {
			addMouseListener(this.adaptadorDoMouse);
			addMouseMotionListener(this.adptadorDoMouseMovimento);
			setEnabled(true);
			this.handlerOFF = false;
		}
	}

	/**
	 * Metodo para Limpar o tabuleiro
	 */
	public void clearPicture() {
		/*
		 * for (int i = 0; i < this.pictureTabuleiro.size(); i++) {
		 * this.pictureTabuleiro.add(i, null); }
		 */
		this.pictureTabuleiro.clear();
		this.picturesList.clear();
		repaint();
		this.naviosPosicionadosParaJogar = 0;
	}


	/**
	 * Configura a Jogada
	 *
	 * @param posicaoX
	 * @param posicaoY
	 */
	public void configuraJogada(int posicaoX, int posicaoY) {

		int verificaJogada = this.checkJogada(posicaoX, posicaoY);
		Point point = this.corrigirPoint(posicaoX, posicaoY);
		posicaoX = posicaoX / 25;
		posicaoY = posicaoY / 25;

		/*
		 * Se acertou na agua, configura o tabuleiro
		 */
		if (verificaJogada == TabuleiroLogico.ACERTOU_NA_AGUA) {
			this.tabuleiroLogico.setPosicaoTabuleiro(posicaoX, posicaoY, "A");
			this.picturesList.add(new PictureTabuleiro(new ImageIcon(
					PainelControle.DIRETORIO_IMAGES + "splash.gif").getImage(),
					point));
			// Som.playAudio(Som.ERRO);
			repaint();

		}

		/*
		 * Se acertou no navio, configura o tabuleiro
		 */
		else if (verificaJogada == TabuleiroLogico.ACERTOU_NO_NAVIO) {
			this.tabuleiroLogico.setPosicaoTabuleiro(posicaoX, posicaoY, "N");
			this.tabuleiroLogico.navioDestruidosPlusPlus();

			this.picturesList.add(new PictureTabuleiro(new ImageIcon(
					PainelControle.DIRETORIO_IMAGES + "explodido.gif")
					.getImage(), point));

			//Som.playAudio(Som.ACERTO);

			repaint();
		}
	}

	/**
	 * Check a jogada
	 *
	 * @param x
	 * @param y
	 * @return int
	 */
	public int checkJogada(int x, int y) {
		int jogadaX = (int) x / 25;
		int jogadaY = (int) y / 25;

		/*
		 * se acertou na agua
		 */
		if (this.tabuleiroLogico.getPosicaoTabuleiro(jogadaX, jogadaY)
				.equalsIgnoreCase("water")) {
			return TabuleiroLogico.ACERTOU_NA_AGUA;
		}

		/*
		 * se acertou em alguma posicao ja acertada
		 */
		else if (this.tabuleiroLogico.getPosicaoTabuleiro(jogadaX, jogadaY)
				.equalsIgnoreCase("A")
				|| this.tabuleiroLogico.getPosicaoTabuleiro(jogadaX, jogadaY)
						.equalsIgnoreCase("N")) {

			return TabuleiroLogico.ACERTOU_POSICAO_USADA;
		}

		/*
		 * se acertou na agua
		 */
		return TabuleiroLogico.ACERTOU_NA_AGUA;
	}

	public int getNaviosPosicionadosParaJogar() {
		return naviosPosicionadosParaJogar;
	}

	public void setNaviosPosicionadosParaJogar(int naviosPosicionadosParaJogar) {
		this.naviosPosicionadosParaJogar = naviosPosicionadosParaJogar;
	}

	public TabuleiroLogico getTabuleiroLogico() {
		return tabuleiroLogico;
	}

	public void setTabuleiroLogico(TabuleiroLogico tabuleiroLogico) {
		this.tabuleiroLogico = tabuleiroLogico;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public int getLarguraShip() {
		return larguraShip;
	}

	public void setLarguraShip(int larguraShip) {
		this.larguraShip = larguraShip;
	}

	public int getAlturaShip() {
		return alturaShip;
	}

	public void setAlturaShip(int alturaShip) {
		this.alturaShip = alturaShip;
	}

	public boolean isHandlerOFF() {
		return handlerOFF;
	}

	public void setHandlerOFF(boolean handlerOFF) {
		this.handlerOFF = handlerOFF;
	}

	public PainelControle getPainelControle() {
		return painelControle;
	}

	public void setPainelControle(PainelControle painelControle) {
		this.painelControle = painelControle;
	}

}