package br.com.edwylugo.meuflappybird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import static br.com.edwylugo.meuflappybird.Constantes.*;


public class Passaro {
    public Circle corpo;

    private Texture[] frames;
    private float auxFrames;

    private Vector2 velocidade;

    public Passaro(int posx, int posy) {
        corpo = new Circle(posx, posy, pasrad);

        frames = new Texture[6];
        for(int i=1;i<=6;i++){
            frames[i-1] = new Texture("felpudo/felpudoVoa" + i + ".png");
        }

        velocidade = new Vector2(0,0);
    }

    public void draw(SpriteBatch batch){
        batch.draw(frames[(int)auxFrames%6], corpo.x - corpo.radius, corpo.y - corpo.radius,
                corpo.radius*2, corpo.radius*2);
    }

    public int update(float time){
        auxFrames += 6*time;

        corpo.x += velocidade.x * time;
        corpo.y += velocidade.y * time;

        velocidade.y -= decVely * time;

        if (corpo.y + corpo.radius >= screeny) {
            corpo.y = screeny - corpo.radius;
            velocidade.y = -impulso;
        }

        if (corpo.y - corpo.radius <= 0) {
            corpo.y = corpo.radius;
            velocidade.y = impulso;
        }

        if(corpo.x + corpo.radius <=0) {
            return 1;
        }

        return 0;

    }

    public void impulso() {
        velocidade.y += impulso;
    }

    public void dispose(){
        for(int i=0;i<=5;i++){
            frames[i].dispose();
        }
    }

    public void perdeu() {
        velocidade.x= 2*canovelx;
        velocidade.y = 0;
    }
    public void restart(int posx, int posy) {
        corpo = new Circle(posx,posy, pasrad);
        velocidade = new Vector2(0,0);
    }
}
