package ao.znt.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.io.File;

public class MainMenuScreen implements Screen {
    OrthographicCamera camera;
    final Balde game;

    public MainMenuScreen(final Balde game){
        this.game = game;

        //ler arquivo
        game.gamerRecord = game.fileHandler.readString();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 1.0f;
        Gdx.files.internal("/").list();

    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch,"Bem vindo ao game Balde",100, 300);
        game.font.draw(game.batch,"Toca na tela para iniciar o jogo",100, 250);
        game.font.draw(game.batch,"SUPORTE A ARMAZENAMENTO LOCAL: "+game.isLocalAvaliable,100, 200);
        game.font.draw(game.batch,"SUPORTE A ARMAZENAMENTO EXTERNO: "+game.isExternalAvaliable,100, 150);
        game.font.draw(game.batch,"Recorde: "+game.gamerRecord,100, 100);
        game.font.draw(game.batch,"EXISTE: "+game.existe,100, 50);

        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
