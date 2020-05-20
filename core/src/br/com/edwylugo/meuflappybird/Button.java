package br.com.edwylugo.meuflappybird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button {

    private Rectangle but;

    public boolean high = false;
    private  float highf = 1.1f;

    private Texture texture;

    public Button (Texture texture, int posx, int posy, int size) {
        this.texture = texture;
        but = new Rectangle(posx, posy, size, size);
    }

    public void draw(SpriteBatch batch) {
        if (high) {
            batch.draw(texture,but.x - (but.getWidth()*(highf-1))/2,
                    but.y - (but.getHeight()*(highf-1))/2,
                    but.getWidth()*highf, but.getHeight()*highf);
        } else {
            batch.draw(texture, but.x, but.y, but.getWidth(), but.getHeight());
        }
    }

    public boolean verif(int x, int y) {
        if (but.x <= x && but.x + but.width >=x &&
                but.y <= y && but.y + but.height >=y) {
            high = true;
        } else {
            high = false;
        }
        return high;
    }

    public void dispose() {
        texture.dispose();
    }
}
