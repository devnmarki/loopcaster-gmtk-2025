package com.devnmarki.engine.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.devnmarki.engine.Engine;

public class Label extends Widget {

    private final String fontPath;

    private BitmapFont font;

    private String content = "";
    private int fontSize;
    private Color color = Color.WHITE;

    public Label(String fontPath) {
        this.fontPath = fontPath;

        this.generateFont();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, content);
        font.draw(Engine.SPRITE_BATCH, layout, transform.localPosition.x, transform.localPosition.y);
    }

    private void generateFont() {
        if (font != null)
            font.dispose();

        FreeTypeFontGenerator tempGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = fontSize;
        params.color = color;
        params.characters = FreeTypeFontGenerator.DEFAULT_CHARS;

        font = tempGenerator.generateFont(params);

        tempGenerator.dispose();
    }

    public Label setContent(String content) {
        this.content = content;
        generateFont();

        return this;
    }

    public Label setFontSize(int fontSize) {
        this.fontSize = fontSize;
        generateFont();

        return this;
    }

    public Label setColor(Color color) {
        this.color = color;
        generateFont();

        return this;
    }

}
