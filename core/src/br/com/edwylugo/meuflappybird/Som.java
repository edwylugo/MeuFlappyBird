package br.com.edwylugo.meuflappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Som {
    private Sound voa, hit, score;

    public Som() {
        voa = Gdx.audio.newSound(Gdx.files.internal("sons/somVoa.mp3"));
        hit = Gdx.audio.newSound(Gdx.files.internal("sons/somHit.mp3"));
        score = Gdx.audio.newSound(Gdx.files.internal("sons/somScore.mp3"));
    }

    public void play(String som) {
        if (som.equals("voa")) {
            voa.play();
        } else if (som.equals("hit")) {
            hit.play();
        } else if (som.equals("score")) {
            score.play();
        }
    }

    public void dispose() {
        voa.dispose();
        hit.dispose();
        score.dispose();
    }
}
