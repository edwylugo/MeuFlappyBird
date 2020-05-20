package br.com.edwylugo.meuflappybird;
import com.badlogic.gdx.math.Rectangle;
import static br.com.edwylugo.meuflappybird.Constantes.*;

public class ObjPontos {

    public Rectangle corpo;

    public ObjPontos(float posx, float posy) {
        corpo = new Rectangle(posx, posy, 10, gap);
    }

    public int update (float time) {
        corpo.x += canovelx * time;
        if(corpo.x + corpo.getWidth() < 0) {
            return 1;
        }
        return 0;
    }

}
