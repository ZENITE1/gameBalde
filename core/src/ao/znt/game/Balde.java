package ao.znt.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Balde extends Game {
    public SpriteBatch batch;
   public BitmapFont font;
    /* apenas */

    @Override
    public void create() {

        font = new BitmapFont();
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
        /*penas*/

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        //liberar espaco na memoria ao fechar o jogo
        font.dispose();
        batch.dispose();
        /*apenas*/
    }
}
