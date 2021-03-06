package inf112.skeleton.app.GameObjectTests;

import inf112.skeleton.app.Objects.Player;
import inf112.skeleton.app.SetupVariables.SetupVariables;
import inf112.skeleton.app.collision.objects.ConveyorBeltObject;
import inf112.skeleton.app.collision.objects.LaserObject;
import inf112.skeleton.app.game.RoboGame;
import inf112.skeleton.app.grid.TileGrid;
import org.junit.jupiter.api.Test;


import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class ConveyorBeltTests {
private TileGrid grid;
private Player player;
private int TILE_SIZE_IN_PIX;
private ConveyorBeltObject belt;
private LaserObject laser;

   public void setup(){
       SetupVariables setupVariable = new SetupVariables();
       grid = setupVariable.grid;
       player = setupVariable.player;
       TILE_SIZE_IN_PIX = setupVariable.gameMap.getTileSize();
       laser = setupVariable.laser;
   }


   @Test
    void conveyorMoveWestTest(){
       setup();
       player = new Player(grid);
       player.setPosition(2 * TILE_SIZE_IN_PIX, 2 *TILE_SIZE_IN_PIX, grid);
       belt = new ConveyorBeltObject(2 * TILE_SIZE_IN_PIX, 2 * TILE_SIZE_IN_PIX, grid, 1, RoboGame.Direction.West);
       belt.handleCollision(player,grid);
       assertEquals(1 * TILE_SIZE_IN_PIX, (int) (player.getX()));
   }

   @Test
   void conveyorMoveTwoWestTest(){
       setup();
       player = new Player(grid);
       player.setPosition(2 * TILE_SIZE_IN_PIX, 2 * TILE_SIZE_IN_PIX, grid);
       belt = new ConveyorBeltObject(2 * TILE_SIZE_IN_PIX, 2 * TILE_SIZE_IN_PIX, grid, 2, RoboGame.Direction.West);
       belt.handleCollision(player,grid);
       assertEquals(0, (int) (player.getX()));

   }
    @Test
    void conveyorMoveEastTest(){
        setup();
        player = new Player(grid);
        player.setPosition(2 * TILE_SIZE_IN_PIX, 2 *TILE_SIZE_IN_PIX, grid);
        belt = new ConveyorBeltObject(2 * TILE_SIZE_IN_PIX, 2 * TILE_SIZE_IN_PIX, grid, 1, RoboGame.Direction.East);
        belt.handleCollision(player,grid);
        assertEquals(3 * TILE_SIZE_IN_PIX, (int) (player.getX()));
    }


    @Test
    void conveyorMoveNorthTest(){
        setup();
        player = new Player(grid);
        player.setPosition(2 * TILE_SIZE_IN_PIX, 2 *TILE_SIZE_IN_PIX, grid);
        belt = new ConveyorBeltObject(2 * TILE_SIZE_IN_PIX, 2 * TILE_SIZE_IN_PIX, grid, 1, RoboGame.Direction.North);
        belt.handleCollision(player,grid);
        assertEquals(3 * TILE_SIZE_IN_PIX, (int) (player.getY()));
    }

    @Test
    void conveyorMoveSouthTest(){
        setup();
        player = new Player(grid);
        player.setPosition(2 * TILE_SIZE_IN_PIX, 2 *TILE_SIZE_IN_PIX, grid);
        belt = new ConveyorBeltObject(2 * TILE_SIZE_IN_PIX, 2 * TILE_SIZE_IN_PIX, grid, 1, RoboGame.Direction.South);
        belt.handleCollision(player,grid);
        assertEquals(1 * TILE_SIZE_IN_PIX, (int) (player.getY()));
    }


    @Test
    void DoesNotMovePlayerOutOfMap(){
       setup();
       player = new Player(grid);
       player.setPosition(0,0,grid);
       belt = new ConveyorBeltObject(0,0, grid, 1, RoboGame.Direction.West);
       belt.handleCollision(player,grid);
       assertEquals(0, (int) (player.getX()));
    }

    @Test
    void DoesNotRotatePlayer(){
       setup();
       player = new Player(grid);
       player.setPosition(0,0,grid);
       belt = new ConveyorBeltObject(0,0, grid, 1, RoboGame.Direction.North);
       belt.handleCollision(player, grid);
       assertEquals(RoboGame.Direction.West, player.getDirection());
    }

    @Test
    void playerTakesDamageWhenMovedOverLaser(){
       setup();
        player = new Player(grid);
        belt = new ConveyorBeltObject(2 * TILE_SIZE_IN_PIX,2 * TILE_SIZE_IN_PIX, grid, 2, RoboGame.Direction.North);
        laser = new LaserObject(3 * TILE_SIZE_IN_PIX,2 * TILE_SIZE_IN_PIX,grid);
        player.setPosition(2 *TILE_SIZE_IN_PIX,2 * TILE_SIZE_IN_PIX,grid);
        belt.handleCollision(player,grid);
        assertEquals(player.playerHP , (player.MAX_HP - laser.LASER_DAMAGE));
        System.out.println(player.playerHP);
    }
}