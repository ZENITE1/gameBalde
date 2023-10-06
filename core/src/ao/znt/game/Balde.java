package ao.znt.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.File;


public class Balde extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    //tratamento de arquivo
    public FileHandle fileHandler;
    public boolean existe = false;
    private File file;
    public final String fileName = "pontuacao.txt";
    public String gamerRecord = "0";
    public boolean isLocalAvaliable;
    public boolean isExternalAvaliable;
    @Override
    public void create() {

        font = new BitmapFont();
        batch = new SpriteBatch();

        //verificar se existe armazenamento local e externo no dispositivo
        isLocalAvaliable = Gdx.files.isLocalStorageAvailable();
        isExternalAvaliable = Gdx.files.isExternalStorageAvailable();

        fileHandler = Gdx.files.local(fileName);

        //avaliar se usa o cartao ou a memoria interna para salvar os dados

        if (Gdx.files.local(fileName).exists())
                existe = true;
        else { //se nao existe cria
            fileHandler.writeString(String.valueOf(0), false);
            existe = true;
        }

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        //liberar espaco na memoria ao close o jogo
        font.dispose();
        batch.dispose();
    }
}