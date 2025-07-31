package com.devnmarki.engine.ui;

import com.devnmarki.engine.ecs.Entity;

public abstract class Widget extends Entity {

   public Widget() {
       this.isUI = true;
   }

}
