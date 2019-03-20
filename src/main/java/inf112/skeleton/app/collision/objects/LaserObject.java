package inf112.skeleton.app.collision.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.Objects.IGameObject;
import inf112.skeleton.app.Objects.Player;
import inf112.skeleton.app.grid.Tile;
import inf112.skeleton.app.grid.TileGrid;

public class LaserObject implements IGameObject {

    public int xLocation;
    public int yLocation;
    Sprite sprite;
    Tile laserTile;

    public LaserObject(RectangleMapObject laserFromTiled, TileGrid grid){
        xLocation = (int) laserFromTiled.getRectangle().getX();
        yLocation = (int) laserFromTiled.getRectangle().getY();

        Texture texture = new Texture("TileSprites/tile-laser-1-start.png");
        sprite = new Sprite(texture);
        sprite.setSize(95, 95);

        laserTile = grid.getTileFromCoordinates(xLocation, yLocation);
        laserTile.addGameObject(this);
    }

    public void handleCollision(Player player, TileGrid grid) {
        Tile playerTile = grid.getTileFromCoordinates(player.getY(), player.getX());

        if (laserTile.equals(playerTile)) {
            player.takeDmg(0);
        }
    }

    @Override
    public Sprite getSprite() {
        sprite.setX(xLocation);
        sprite.setY(yLocation);

        return sprite;
    }
}
