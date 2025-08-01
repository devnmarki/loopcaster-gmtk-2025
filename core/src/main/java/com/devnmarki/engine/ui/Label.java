package com.devnmarki.engine.ui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.devnmarki.engine.Engine;

public class Label extends Widget {

    private final String fontPath;

    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();

    private String content = "";
    private int fontSize;
    private Color color = Color.WHITE;
    private float borderWidth = 0f;
    private Color borderColor = Color.BLACK;
    private int alignment = Align.left;

    public Label(String fontPath) {
        this.fontPath = fontPath;

        this.generateFont();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        layout.setText(font, content, color, 0, alignment, false);
    }

    @Override
    public void onRender() {
        super.onRender();

        Engine.SPRITE_BATCH.begin();
        font.draw(Engine.SPRITE_BATCH, layout, transform.localPosition.x, transform.localPosition.y);
        Engine.SPRITE_BATCH.end();
    }

    private void generateFont() {
        if (font != null)
            font.dispose();

        FreeTypeFontGenerator tempGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = fontSize;
        params.color = color;
        params.borderWidth = borderWidth;
        params.borderColor = borderColor;
        params.characters = FreeTypeFontGenerator.DEFAULT_CHARS;

        font = tempGenerator.generateFont(params);

        tempGenerator.dispose();
    }

    public Label setContent(String content) {
        this.content = content;

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

    public Label setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        generateFont();

        return this;
    }

    public Label setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        generateFont();

        return this;
    }

    public Label setAlignment(int alignment) {
        this.alignment = alignment;

        return this;
    }

    public float getWidth() {
        return layout.width;
    }

    public float getHeight() {
        return layout.height;
    }

}
