package inf112.skeleton.app.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.game.RoboGame;

public class EndGameScreen implements Screen {
    Texture GameOver;
    Texture SilCon;
    RoboGame game;

    public EndGameScreen(RoboGame game){
        this.game = game;
    }





    @Override
    public void show() {
        GameOver = new Texture("GAMEOVER.png");
        SilCon = new Texture("SiliconRallyOut.png");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT) ;
        game.sb.begin();
        game.sb.draw(GameOver,340,400,game.ROBO_GAME_WIDTH/2, game.ROBO_GAME_HEIGHT/2);
        game.sb.draw(SilCon,420,320, game.ROBO_GAME_WIDTH/3, game.ROBO_GAME_HEIGHT/3 );
        game.sb.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
