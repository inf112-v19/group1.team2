package inf112.skeleton.app.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.skeleton.app.Objects.IGameObject;
import inf112.skeleton.app.Objects.LaserAnimation;
import inf112.skeleton.app.Objects.Player;
import inf112.skeleton.app.Screen.GameScreen;
import inf112.skeleton.app.Screen.MainMenuScreen;
import inf112.skeleton.app.card.MoveCard;
import inf112.skeleton.app.card.StackOfCards;
import inf112.skeleton.app.collision.objects.GameObjectFactory;
import inf112.skeleton.app.grid.TileGrid;
import inf112.skeleton.app.map.GameMap;

import java.util.ArrayList;
import java.util.Arrays;

public class RoboGame extends Game {
    public static OrthographicCamera camera;
    public int TILE_SIZE_IN_PX;
    public TiledMap tiledMap;
    public static TiledMapRenderer tiledMapRenderer;
    public SpriteBatch sb;
    public Player player;
    public Player player2;
    public TileGrid grid;
    public GameMap gameMap;
    private StackOfCards deck;
    public MoveCard cardPickedByPlayer;
    public MoveCard[] listOfNine;
    public MoveCard[] chosenFive;
    private Sprite backboard;
    private Sprite lives;
    private Texture texture;
    private RoboGame game;
    public GameScreen gameScreen;
    public ArrayList<Player> playerList;

    public int currentPlayer;


    public static final int ROBO_GAME_WIDTH = 1200;
    public static final int ROBO_GAME_HEIGHT = 700;

    public Player getPlayer(Player player) {
        return player;
    }

    @Override
    public void create() {
        playerList = new ArrayList<>();
        gameMap = new GameMap("map.v.01.tmx");
        this.TILE_SIZE_IN_PX = getTileSize();
       // tiledMap = new TmxMapLoader().load("map.v.01.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(gameMap.getTiledMap());
        this.grid = makeGrid();
        GameObjectFactory constructor = new GameObjectFactory(gameMap, grid, this);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(gameMap.getTiledMap());

        camera = new CustomCamera(gameMap.getTiledMap());
        camera.translate( -470, -700);

        sb = new SpriteBatch();

        texture = new Texture("cardLayouts/mech.jpg");
        backboard = new Sprite(texture);
        backboard.setSize(1950,1600);
        backboard.setPosition(-480,-700);


        deck = new StackOfCards();
        listOfNine = new MoveCard[9];
        chosenFive = new MoveCard[5];

        Gdx.input.setInputProcessor(playerList.get(currentPlayer));

        this.setScreen(new MainMenuScreen(this, constructor));

        drawNineCardsFromDeck();
    }

    public Player getCurrentPlayer(){
        if (currentPlayer == 0)
            return player2;
        return player;
    }

    public MoveCard chooseCard(int index, Player currentPlayer) {
        if(!canChooseMoreCard(currentPlayer))
            return null;

        cardPickedByPlayer = listOfNine[index];
        for (int i = 0; i < chosenFive.length; i++) {
            if (chosenFive[i] == null) {
                chosenFive[i] = alignCard(cardPickedByPlayer, i);

                listOfNine[index] = null;
                break;
            }
        }

        //currentPlayer.chosencards++;
        currentPlayer.increaseDeckCount();
        return cardPickedByPlayer;
    }


    private boolean canChooseMoreCard(Player currentPlayer) {
        if (currentPlayer.chosencards == currentPlayer.MaxMoveCardLength)
            return false;

        return true;
    }

    public MoveCard getCard(int index){
        return listOfNine[index];
    }

    private MoveCard alignCard(MoveCard temp, int index) {
        int x = 0 + 170*(index);
        int y = - 370;
        temp.setPosition(x,y);
        temp.setSize(300,400);
        return temp;
    }

    public void drawNineCardsFromDeck() {
        int cardYPos = -770;
        int cardXPos = -550;
        MoveCard card;
        for (int i = 0; i < listOfNine.length; i++) {
            card = deck.nextCard();
            if (card != null) {
                card.setSize(400, 520);
                card.setPosition(cardXPos, cardYPos);
            }
            listOfNine[i] = card;
            cardXPos += 205;
        }
        for (int i = 0; i < chosenFive.length; i++) {
            chosenFive[i] = null;
        }
    }

    public TileGrid makeGrid() {
        TiledMapTileLayer layer = (TiledMapTileLayer)gameMap.getMapLayerByIndex(0);

        int heightNumberOfTiles = layer.getHeight();
        int widthNumberOfTiles = layer.getWidth();

        return new TileGrid(heightNumberOfTiles, widthNumberOfTiles, TILE_SIZE_IN_PX);
    }

    //Only works if each tile is a square with equal sides
    public int getTileSize() {
        TiledMapTileLayer layer = (TiledMapTileLayer)gameMap.getMapLayerByIndex(0);
        return (int) layer.getTileWidth();

    }

    @Override
    public void render() {
        handleInput(Gdx.graphics.getDeltaTime());
         super.render();
    }

    public void handleInput(float deltaTime) {
        if (player != null) {
            player.handleInput(deltaTime, this);
            }
        }



    public void drawHUD() {
        sb.end();
        sb.begin();
        backboard.draw(sb);
        for (int i = 0; i < listOfNine.length; i++) {
            if (listOfNine[i] != null) {
                listOfNine[i].draw(sb);
            }
        }
        for (int i = 0; i < chosenFive.length; i++) {
            if (chosenFive[i] != null) {
                chosenFive[i].draw(sb);
            }
        }
        //lives.draw(sb);

        sb.end();
    }

    public void drawSpritesFromGrid() {
        sb.begin();
        for (IGameObject gameObject : grid.getAllSpritesOnMap()) {
            if (gameObject instanceof LaserAnimation)
                System.out.println("Found laserAnimation");

            //Not all GameObjects have sprites
            if (gameObject.getSprite() != null)
                gameObject.getSprite().draw(sb);

        }
        sb.end();
    }


    @Override
    public void dispose() {
        sb.dispose();
        texture.dispose();
        gameMap.getTiledMap().dispose();
    }

    public void putCardsBackInDeck() {
        for (int i = 0; i < listOfNine.length; i++) {
            if(listOfNine[i] != null) {
                deck.stack.push(listOfNine[i]);
            }
        }
    }

    public void update(float deltaTime) {
        getCurrentPlayer().update(deltaTime, grid);
    }


    public enum Direction{
        North, East, South, West
    }

    public GameScreen getGameScreen() {
        return this.gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
}
