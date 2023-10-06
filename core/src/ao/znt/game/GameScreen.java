package ao.znt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {
    final Balde game;
    BitmapFont fontPontuacao;
    Texture baldeImage;
    Texture gotaImage;
    Sound gotaSom;
    Music rainMusic;

    OrthographicCamera camera;
    com.badlogic.gdx.math.Rectangle balde;
    ArrayList<Rectangle> gotasDeChuva;
    long dificuldade = 1000000000;
    long tempoQueUltimaGotaCaio;
    int contador_gotas_no_balde;
    private State state;



    public GameScreen(final Balde game) {
            this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 1.0f;

        fontPontuacao = new BitmapFont();

        state = State.RUN;
        gotaImage = new Texture(Gdx.files.internal("droplet.png"));
        baldeImage = new Texture(Gdx.files.internal("bucket.png"));

        gotaSom = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));



        gotasDeChuva = new ArrayList<Rectangle>();
        criarGotas();

        rainMusic.setLooping(true);
        rainMusic.play();


        balde = new Rectangle();
        balde.x = Gdx.graphics.getWidth() / 2 - 64 / 2;
        balde.y = 20;
        balde.width = 64;
        balde.height = 64;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.begin();
        game.batch.draw(baldeImage,balde.x,balde.y);
        //desenhar a pontuacao
        fontPontuacao.draw(game.batch,""+contador_gotas_no_balde,10,Gdx.graphics.getHeight() - 10);

        for (Rectangle rectangle: gotasDeChuva){
            game.batch.draw(gotaImage, rectangle.x,rectangle.y);
        }
        game.batch.end();
        switch (state){
            case RUN:
                // mover o balde onde tocares na tela
                if (Gdx.input.isTouched()){
                    Vector3 touchPos = new Vector3();
                    touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
                    camera.unproject(touchPos);
                    balde.x = touchPos.x - 64 / 2;
                    //balde.y = touchPos.y - 64 / 2;
                }
// mover a gota e remove-la se sair da tela
                if (TimeUtils.nanoTime() - tempoQueUltimaGotaCaio > dificuldade)
                    criarGotas();
                for (Iterator<Rectangle> it = gotasDeChuva.iterator(); it.hasNext();){
                    Rectangle rectangle = it.next();
                    rectangle.y -= 200 * Gdx.graphics.getDeltaTime();
                    if(rectangle.y + 64 < 0){//Se uma gota caio no chao
                        it.remove();
                        state = State.GAMEOVER;
                    }
//verificar colisao entre o balde e a gota
                    if(balde.overlaps(rectangle)){
                        contador_gotas_no_balde++;
                        if (contador_gotas_no_balde == 20)
                            dificuldade = 700000000;
                        gotaSom.play();
                        it.remove();
                    }
                }
                break;
            case PAUSE:
                game.batch.begin();
                game.font.draw(game.batch,"PAUSADO",308,250);
                game.batch.end();
                break;
            case GAMEOVER:
                game.batch.begin();
                game.font.draw(game.batch,"LAMENTO VOCÊ PERDEU",
                        Gdx.graphics.getWidth()/2,
                        Gdx.graphics.getHeight()/2);
                game.font.draw(game.batch,"SUA PONTUAÇÃO: "+contador_gotas_no_balde
                        ,Gdx.graphics.getWidth()/2,
                        Gdx.graphics.getHeight()/2 - 40);
                game.batch.end();
                break;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        this.state = State.PAUSE;
    }

    @Override
    public void resume() {
        this.state = State.RUN;
    }


    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        baldeImage.dispose();
        gotaImage.dispose();
        gotaSom.dispose();
        rainMusic.dispose();
        fontPontuacao.dispose();
    }
    private void criarGotas(){
        Rectangle rectangle = new Rectangle();
        rectangle.x = MathUtils.random(0,Gdx.graphics.getWidth() - 64);
        rectangle.y = Gdx.graphics.getHeight();
        rectangle.width = 64;
        rectangle.height = 64;
        gotasDeChuva.add(rectangle);
        tempoQueUltimaGotaCaio = TimeUtils.nanoTime();
    }

}
