package com.devnmarki.game.game_objects;

import com.badlogic.gdx.physics.box2d.Contact;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.game.Direction;
import com.devnmarki.game.IDamageable;
import com.devnmarki.game.characters.Player;

public class PlayerBullet extends Bullet {

    public PlayerBullet() {
        super(Player.getInstance().getFacingDirection(), 12f, 0.75f);
    }

    @Override
    public void onCollisionEnter(Entity actor, Vector2 normal, Contact contact) {
        super.onCollisionEnter(actor, normal, contact);

        if (actor instanceof IDamageable damageable) {
            damageable.onDamage(1);
        }

        if (!(actor instanceof Player))
            setShouldDestroy(true);
    }

}
