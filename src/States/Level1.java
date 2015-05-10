package States;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Controller.Game;
import Objects.Number;
import Objects.Player;

public class Level1 extends BasicGameState {
	private String backtext, exittext;
	private Color backcolor, exitcolor;
	private Sound clickSound;
	private float mouseX, mouseY;
	private int texturesize;
	private Number[][] maxitmap = new Number[5][5];
	private Player player1, player2;
	private Random randomx, randomy;
	private int spointx, spointy;

	public Level1(int state) {
	}
	
	public void markSelectable(int spointx, int spointy){
		for (int i = spointx; i < 5; i++) {
			maxitmap[i][spointy].setSelectable(true); // on horizontal to right
		}
		for (int i = spointy; i < 5; i++) {
			maxitmap[spointx][i].setSelectable(true); // on vertical to down
		}
		for (int i = spointx; i > 0; i--) {
			maxitmap[i - 1][spointy].setSelectable(true); // on horizontal to left
		}
		for (int i = spointy; i > 0; i--) {
			maxitmap[spointx][i - 1].setSelectable(true); // on vertical to up
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {

		player1 = new Player("player1");
		player2 = new Player("player2");

		texturesize = 64;

		backtext = "Menu";
		exittext = "Exit";

		backcolor = Color.white;
		exitcolor = Color.white;

		clickSound = new Sound("/res/sound/clickSound.wav");

		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 5; k++) {
				maxitmap[i][k] = new Number((Game.width / 2)
						- (texturesize * 3) + i * 74, k * 74 + 200);
			}
		}

		spointx = (randomx = new Random()).nextInt(5);
		spointy = (randomy = new Random()).nextInt(5);

		maxitmap[spointx][spointy].setSelected(true);

	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setBackground(Color.darkGray);

		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 5; k++) {
				maxitmap[i][k].draw(g);
			}
		}

		g.setColor(Color.cyan);
		g.drawString("Kitten Maxit", (Game.width / 2) - 70, 20);

		g.setColor(backcolor);
		g.drawString(backtext, 40, Game.height - 75);

		g.setColor(exitcolor);
		g.drawString(exittext, Game.width - 100, 30);

		g.setColor(Color.red);
		g.drawString(player1.getName() + " : " + player1.getScore(), 100, 200);

		g.setColor(Color.red);
		g.drawString(player2.getName() + " : " + player2.getScore(), 1100, 200);

	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {

		mouseX = Mouse.getX();
		mouseY = Mouse.getY();

		markSelectable(spointx, spointy);

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Display.destroy();
			System.exit(0);
		}

		if (mouseX > Game.width - 100 && mouseX < Game.width - 60
				&& mouseY < Game.height - 30 && mouseY > Game.height - 45) {
			exitcolor = Color.red;
			if (Mouse.isButtonDown(0)) {
				clickSound.play();
				Display.destroy();
				System.exit(0);
			}
		} else {
			exitcolor = Color.white;
		}

		if (mouseX > 40 && mouseX < 80 && mouseY < 70 && mouseY > 58) {
			backcolor = Color.red;
			if (Mouse.isButtonDown(0)) {
				clickSound.play();
				sbg.enterState(0);
			}
		} else {
			backcolor = Color.white;
		}

	}

	@Override
	public int getID() {
		return 1;
	}

}
