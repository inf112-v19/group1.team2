package inf112.skeleton.app.CardTest;

import inf112.skeleton.app.Objects.Player;
import inf112.skeleton.app.card.MoveCard;
import inf112.skeleton.app.card.StackOfCards;
import inf112.skeleton.app.game.RoboGame;
import inf112.skeleton.app.grid.TileGrid;
import inf112.skeleton.app.logic.Round;

//TODO
public class MoveCardTest {
	private Round round;
	private Player player;
	private MoveCard card;
	private RoboGame game;
	private TileGrid grid;
	private StackOfCards deck;
	private int TILE_SIZE_IN_PIX;
/*
	@Test
	void MoveCardTest(){
		SetupVariables setup = new SetupVariables();
		this.player = new Player();
		player.setPosition(2* TILE_SIZE_IN_PIX, 2 * TILE_SIZE_IN_PIX, grid);
		Texture textureMove1 = new Texture("cardLayouts/Move1.png");
		card = new MoveCard(MoveCard.Type.move1, textureMove1);

		assertEquals(3 * TILE_SIZE_IN_PIX, player.getY());
	}
	public void setup(){
		SetupVariables setupVariable = new SetupVariables();
		grid = setupVariable.grid;
		player = setupVariable.player;
		TILE_SIZE_IN_PIX = setupVariable.gameMap.getTileSize();
	}*/
}
