package inf112.skeleton.app.GameTests;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.Objects.Player;
import inf112.skeleton.app.SetupVariables.SetupVariables;
import inf112.skeleton.app.card.MoveCard;
import inf112.skeleton.app.card.StackOfCards;
import inf112.skeleton.app.collision.objects.LaserObject;
import inf112.skeleton.app.game.RoboGame;
import inf112.skeleton.app.game.RoundExecutor;
import inf112.skeleton.app.grid.Tile;
import inf112.skeleton.app.grid.TileGrid;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RoundExecutorTests {

    TileGrid grid;
    SetupVariables variable;
    RoundExecutor roundExecutor;
    Player player;
    Player player2;
    List<Player> playerList;
    RoboGame game;
    private MoveCard card;
    private Texture textureCard;		//texture does not matter in testing, it is used to avoid exceptions for classes demanding

    void setup(){
        variable = new SetupVariables();
        grid = variable.grid;
        playerList = variable.getPlayers();
        player = playerList.get(0);
        player2 = playerList.get(1);

        game = variable.game;
        textureCard = new Texture("cardLayouts/Move1.png");
        roundExecutor = new RoundExecutor(playerList, game);
    }






    @Test
    //Checks if there are any movecards to be player, since players are not initilaized with cards
    //this method only needs to create players.
    void roundIsDoneTest(){
        setup();
        assertTrue(roundExecutor.roundIsDone());
    }

    @Test
    void executeCardsTest(){
        setup();
        card = new MoveCard(MoveCard.Type.move1, textureCard);
        game.deck = new StackOfCards();

        float p1Y = player.getY();
        float p2Y = player2.getY();


        player.moveCardQueue.add(card);
        player2.moveCardQueue.add(card);
        roundExecutor.playPlayerNextCard();

        float p1After = player.getY();
        float p2After = player2.getY();

        assertTrue((p1Y < p1After || p2Y < p2After));


    }

    @Test
    void checksForDamageTest(){
        setup();
        LaserObject laser = new LaserObject(2,2,grid);
        Tile laserTile = grid.getTileFromCoordinates(laser.yLocation, laser.xLocation);
        player.setPosition(laserTile.y, laserTile.x, grid);
        roundExecutor.checkCollisions();

        assertTrue(player.MAX_HP > player.playerHP);
    }
}
