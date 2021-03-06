package inf112.skeleton.app.game;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.Objects.AIPlayer;
import inf112.skeleton.app.Objects.Player;
import inf112.skeleton.app.Screen.GameOverScreen;

import java.util.List;

public class DrawCards {
    private List<Player> playerList;
    private int playersTurn;
    private RoboGame game;
    private int cardsDrawByPlayer;
    private boolean playersFinishedPickingCards;
    private BitmapFont font = new BitmapFont();



    public DrawCards(RoboGame game) {
        this.game = game;
        playerList = game.playerList;
        playersTurn = 0;
        playersFinishedPickingCards = false;
    }


    public void drawCards() {
        if (playerList.size() == 1)
            playersTurn = 0;

        if (playerList.size() == 0){
            game.setScreen(new GameOverScreen(game));
            return;
        }

        Player playerToDraw = playerList.get(playersTurn);
        Gdx.input.setInputProcessor(playerToDraw);
        drawCurrentPlayer();
 
        if (playerToDraw instanceof AIPlayer) {
            ((AIPlayer) playerToDraw).pickCards();
        }
        this.cardsDrawByPlayer = playerList.get(playersTurn).moveCardQueue.size();

        if (playerToDraw.powerDownOn ||
                (cardsDrawByPlayer != 0) && cardsDrawByPlayer % playerToDraw.maxCardsAllowedForPlayer == 0)
            setNextPlayersTurn();
    }


    public void setNextPlayersTurn() {
        int currentPlayer = playersTurn;
        this.playersTurn = (currentPlayer+1) % playerList.size();
        drawNewCardsFromDeck();
        cardsDrawByPlayer = 0;

        if (allPlayersDone()) {
            playersFinishedPickingCards = true;
            game.putCardsBackInDeck();
            game.drawNineCardsFromDeck();
        }
    }

    private void drawCurrentPlayer() {
        game.sb.begin();
        font.getData().setScale(3);
        font.setColor(new Color(Color.GREEN));
        String str = "player drawing card: " + playerList.get(playersTurn);
        Sprite sprite = playerList.get(playersTurn).getSprite();
        sprite.setPosition(280,-100);
        sprite.draw(game.sb);


        font.draw(game.sb, str, 360,-10);
        game.sb.end();
    }

    private void executeAllMoves() {
        for (Player player : playerList) {
            player.executeCard();
        }
    }

    private void drawNewCardsFromDeck() {
        game.drawNineCardsFromDeck();
    }

    public boolean allPlayersDone() {

        for (Player player : playerList) {
            if (player.moveCardQueue.size() != player.maxCardsAllowedForPlayer)
                return false;
        }

        return true;
    }

    public boolean playersFinishedPickingCards() {
        return this.playersFinishedPickingCards;
    }

    public void startPickingCards() {
        this.playersFinishedPickingCards = false;
    }
}
