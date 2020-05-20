package br.com.edwylugo.meuflappybird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


import static br.com.edwylugo.meuflappybird.Constantes.*;

public class Cano {
    private Texture tex;

    public Rectangle corpo;

    private boolean cima;

    public Cano(float posx, float posy, boolean cima) {
        this.cima = cima;
        if(cima) {
            corpo = new Rectangle(posx, posy, canow, screeny);
        } else {
            corpo = new Rectangle(posx, posy-screeny, canow, screeny);
        }

        tex = new Texture("cano.png");
    }

    public void draw(SpriteBatch batch) {
        batch.draw(tex, corpo.x, corpo.y, corpo.getWidth(), corpo.getHeight(),0,0, tex.getWidth(), tex.getHeight(), false, cima);
    }

    public int update(float time) {
        corpo.x += canovelx * time;
        if(corpo.x + corpo.getWidth() < 0) {
            return 1;
        }
        return 0;
    }

    public void dispose() {
        tex.dispose();
    }

}
