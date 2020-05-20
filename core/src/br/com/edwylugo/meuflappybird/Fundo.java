package br.com.edwylugo.meuflappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static br.com.edwylugo.meuflappybird.Constantes.*;

public class Fundo {

    private Texture tex;

    private float posx1;
    private float posx2;

    public Fundo(){
        tex = new Texture("fundo.png");

        posx1 = 0;
        posx2 = screenx;
    }

    public void draw(SpriteBatch batch){
        batch.draw(tex, posx1, 0, screenx, screeny);
        batch.draw(tex, posx2, 0, screenx, screeny);
    }

    public void update(float time){
        posx1 += time * canovelx;
        posx2 += time * canovelx;

        if(posx1 + screenx <= 0){
            posx1 = screenx;
            posx2 = 0;
        }
        if(posx2 + screenx <= 0){
            posx2 = screenx;
            posx1 = 0;
        }
    }

    public void dispose(){
        tex.dispose();
    }

}
