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
    public ConveyorBeltObject conveyorBeltObject;
    public List<IGameObject> flags;
    public List<IGameObject> lasers;
    public List<IGameObject> pitfalls;
    public List<IGameObject> repairs;
    public TeleportObstacle teleportObstacle;

    public List<ConveyorBeltObject> ForwardBelts;
    public List<TurnGearObject> turnGears;


    public GameObjectFactory(GameMap map, TileGrid grid, RoboGame game) {
        this.map = map;
        this.grid = grid;

        createPlayer(game);
        createFlags();
        createTeleporter();
        createConveyorBelts();

        createLasers();
        createRepairs();
        createTurnGears();
        createPits();
    }

    private void createPlayer(RoboGame game) {
        RoboGame.Direction startDirection = RoboGame.Direction.North;
        player = new Player(new Texture("robot1.png"), startDirection,game);
    }

    private void createFlags() {
        flags = new ArrayList<>();

        MapLayer layer = map.getMapLayerByName("Flags");

        for (MapObject flag : layer.getObjects()) {
            RectangleMapObject flagRectangleObject = (RectangleMapObject) flag;
            flags.add(new FlagObject(flagRectangleObject, grid));
        }

    }

    private void createLasers(){
        lasers = new ArrayList<>();

        MapLayer laserLayer = map.getMapLayerByName("Lasers");

        for (MapObject laser : laserLayer.getObjects() ) {
            RectangleMapObject laserRectangleObject = (RectangleMapObject) laser;
            lasers.add(new LaserObject(laserRectangleObject, grid));
        }

    }
    private void createRepairs(){
        repairs = new ArrayList<>();

        MapLayer repairLayer = map.getMapLayerByName("Repairs");

        for (MapObject repair : repairLayer.getObjects() ) {
            RectangleMapObject repairRectangleObject = (RectangleMapObject) repair;
            repairs.add(new RepairObject(repairRectangleObject, grid));
        }

    }

    private void createPits(){
        pitfalls = new ArrayList<>();

        MapLayer pitLayer = map.getMapLayerByName("Pits");

        for (MapObject pit : pitLayer.getObjects() ) {
            RectangleMapObject pitRectangleObject = (RectangleMapObject) pit;
            pitfalls.add(new PitObject(pitRectangleObject, grid));
        }

    }


    private void createConveyorBelts() {
        this.ForwardBelts = new ArrayList<>();
        MapLayer belts = map.getMapLayerByName("Belts");


        for (MapObject belt: belts.getObjects()) {
            RectangleMapObject beltRectangleObject = (RectangleMapObject) belt;
            ForwardBelts.add(new ConveyorBeltObject(beltRectangleObject, grid));
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
