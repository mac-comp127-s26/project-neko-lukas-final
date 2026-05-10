package ParticleSim;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.TextField;

import java.awt.Color;
import java.util.function.Consumer;

public class AppUI extends GraphicsGroup {
    private Color color;
    private int radius;

    private TextField redField, greenField, blueField, sizeField;

    public AppUI(int initialSize) {
        redField = addComponentField("Gravity", colorDisplay, 8);
        greenField = addComponentField("Speed", redField, 4);
        blueField = addComponentField("Blue", greenField, 4);

        redField  .onChange((text) -> updateColorFromField(0, redField));
        greenField.onChange((text) -> updateColorFromField(1, greenField));
        blueField .onChange((text) -> updateColorFromField(2, blueField));

        sizeField = addComponentField("Size", blueField, 16);
        sizeField.onChange((text) -> updateBrushSizeFromField());
        sizeField.setText(String.valueOf(initialSize));
        radius = initialSize;
    }
}