package com.devnmarki.game.game_objects;

import com.badlogic.gdx.physics.box2d.Contact;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.game.Direction;
import com.devnmarki.game.characters.Player;

public class EnemyBullet extends Bullet {

    public EnemyBullet(Direction facingDirection) {
        super(facingDirection, 7f, 1f);
    }

    @Override
    public void onCollisionEnter(Entity actor, Vector2 normal, Contact contact) {
        super.onCollisionEnter(actor, normal, contact);

        if (actor instanceof Player) {
            Player.getInstance().damage(1f);
        }

        setShouldDestroy(true);
    }

}
