package inf112.skeleton.app.collision.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.Objects.IGameObject;
import inf112.skeleton.app.Objects.Player;
import inf112.skeleton.app.game.RoboGame;
import inf112.skeleton.app.grid.TileGrid;
import inf112.skeleton.app.map.GameMap;

import java.util.ArrayList;
import java.util.List;

public class GameObjectFactory {
    public GameMap map;
    public TileGrid grid;
    public Player player;
    public List<IGameObject> flags;
    public TeleportObstacle teleportObstacle;

    public List<ConveyorBeltObject> oneForwardBelts;
    public List<TurnGearObject> turnGears;


    public GameObjectFactory(GameMap map, TileGrid grid) {
        this.map = map;
        this.grid = grid;

        createPlayer();
        createFlags();
        createTeleporter();
        createConveyorBelts();
        createTurnGears();
    }

    private void createPlayer() {
        RoboGame.Direction startDirection = RoboGame.Direction.North;
        player = new Player(new Texture("robot1.png"), startDirection);
    }

    private void createFlags() {
        flags = new ArrayList<>();

        MapLayer layer = map.getMapLayerByName("Flags");

        for (MapObject flag : layer.getObjects()) {
            RectangleMapObject flagRectangleObject = (RectangleMapObject) flag;
            flags.add(new FlagObject(flagRectangleObject, grid));
        }

    }

    private void createConveyorBelts() {
        createNormalBelts();
    }

    private void createNormalBelts() {
        this.oneForwardBelts = new ArrayList<>();
        int speed = 1;
        MapLayer belts = map.getMapLayerByName("Belts");


       for (MapObject belt: belts.getObjects()) {
            RectangleMapObject beltRectangleObject = (RectangleMapObject) belt;
            oneForwardBelts.add(new ConveyorBeltObject(beltRectangleObject, grid, speed));
        }
    }

    private void createTurnGears(){
        this.turnGears = new ArrayList<>();

        MapLayer mapLayer = map.getMapLayerByName("turnGears");

        for (MapObject turnGear: mapLayer.getObjects()) {
            RectangleMapObject turnGearObject = (RectangleMapObject) turnGear;
            turnGears.add(new TurnGearObject(turnGearObject, grid));
        }
    }



    private void createTeleporter() {
        this.teleportObstacle = new TeleportObstacle(map, grid);
    }
}
