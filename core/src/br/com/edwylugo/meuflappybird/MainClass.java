package br.com.edwylugo.meuflappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Intersector;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static br.com.edwylugo.meuflappybird.Constantes.*;

public class MainClass extends ApplicationAdapter {

	private SpriteBatch batch;
	private Fundo fundo;
	private Passaro passaro;
	private List<Cano> canos;
	private List<ObjPontos> objPontos;

	private float timeCano; //tempo que controla o proximo cano
    private  int estado = 0; //0 - parado 1- rodando 2 - perdeu 3 - restar

	private int pontos = 0;
	private boolean marcou = false;

	private BitmapFont fonte;

	private GlyphLayout glyphLayout = new GlyphLayout();

	private  Button btnStart;
	private  Button btnRestart;

	private Som som;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		fundo = new Fundo();
		passaro = new Passaro(pasInix,screeny/2);

		canos = new ArrayList<Cano>();

		objPontos = new ArrayList<ObjPontos>();

		timeCano = timecanos;

		FreeTypeFontGenerator.setMaxTextureSize(2048);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = (int)(0.2f*screenx);
		parameter.color = new Color(1,1,1,1);
		fonte = generator.generateFont(parameter);
		generator.dispose();

		btnStart = new Button(new Texture("botoes/BotaoPlay.png"), btnx, btny, btnSize);
		btnRestart = new Button(new Texture("botoes/BotaoReplay.png"), btnx, btny, btnSize);

		som = new Som();

	}

	@Override
	public void render () {
		input();
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		draw();

		batch.end();
	}

	private void draw() {
		fundo.draw(batch);

		for (Cano c:canos) {
			c.draw(batch);
		}
		passaro.draw(batch);
		fonte.draw(batch, String.valueOf(pontos),
				(screenx - getTamX(fonte, String.valueOf(pontos)))/2, 0.98f*screeny);

		if(estado == 0) {
			btnStart.draw(batch);
		} else if (estado == 3) {
			btnRestart.draw(batch);
		}
	}

	private void update(float time) {
	    if (estado == 1) {
            fundo.update(time);

            for(int i=0; i < canos.size(); i++) {
                if (canos.get(i).update(time) == 1) {
                    canos.remove(i);
                    i--;
                }
            }

			for(int i=0; i < objPontos.size(); i++) {
				if (objPontos.get(i).update(time) == 1) {
					objPontos.remove(i);
					i--;
				}
			}

            timeCano-=time;
            if (timeCano<=0) {
                Random random = new Random();
                int pos = random.nextInt(posMax);
                pos -= posMax/2;
                canos.add(new Cano(screenx, screeny/2 + pos + gap/2, true));
                canos.add(new Cano(screenx, screeny/2 + pos - gap/2, false));
				objPontos.add(new ObjPontos(screenx + canow + 2*pasrad,screeny/2 + pos - gap/2));
                timeCano = timecanos;
            }

            for (Cano c:canos) {
                if (Intersector.overlaps(passaro.corpo, c.corpo)) {
                    Gdx.app.log("Log", "Bateu no Cano!!!");
					som.play("hit");
                    passaro.perdeu();
                    estado = 2;
                }
            }

            boolean inter = false;

			for (ObjPontos o:objPontos) {
				if (Intersector.overlaps(passaro.corpo, o.corpo)) {
					if (!marcou) {
						pontos++;
						Gdx.app.log("Log", String.valueOf(pontos));
						som.play("score");
						marcou = true;
					}

					inter = true;
				}
			}

			if (!inter) {
				marcou = false;
			}


        }

        if (estado == 1 || estado == 2) {
            if (passaro.update(time) == 1) {
                estado = 3;
            }
        }

	}


	private void input() {
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = screeny - Gdx.input.getY();

			if (estado == 0){
				btnStart.verif(x,y);
                estado = 1;
            } else if (estado == 1) {
                passaro.impulso();
                som.play("voa");
            } else if (estado == 3) {
				btnRestart.verif(x,y);

            }
		} else if (!Gdx.input.isTouched()) {
			if (btnStart.high){
				estado = 1;
				btnStart.high = false;
			}

			if (btnRestart.high) {

			    estado = 1;
			    passaro.restart(pasInix, screeny/2);
			    canos.clear();
			    timeCano = timecanos;
			    pontos = 0;
			    marcou = false;
			    objPontos.clear();
				btnRestart.high = false;
			}
		}
	}

	private float getTamX(BitmapFont fonte, String texto) {
		glyphLayout.reset();
		glyphLayout.setText(fonte, texto);
		return glyphLayout.width;
	}
	
	@Override
	public void dispose () {
		fundo.dispose();
		passaro.dispose();

		for (Cano c:canos) {
			c.dispose();
		}

		fonte.dispose();
		som.dispose();

		btnStart.dispose();
		btnRestart.dispose();

		batch.dispose();

	}
}
