package com.sad;

import com.sad.models.*;
import com.sad.models.command.SaveCommand;

import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SaveCommandTest {

    @Test
    public void testSerializeRectangle() {
        ConcreteRectangle rect = new ConcreteRectangle(10, 20, 100, 50, Color.BLACK, Color.RED);
        SaveCommand cmd = new SaveCommand(List.of(rect), new Pane());
        String result = cmd.serializeShape(rect);
        assertEquals("Rectangle 10,000000 20,000000 100,000000 50,000000 #000000 #FF0000", result);
    }

    @Test
    public void testSerializeEllipse() {
        ConcreteEllipse ellipse = new ConcreteEllipse(15, 25, 60, 40, Color.BLUE, Color.TRANSPARENT);
        SaveCommand cmd = new SaveCommand(List.of(ellipse), new Pane());
        String result = cmd.serializeShape(ellipse);
        assertEquals("Ellipse 15,000000 25,000000 60,000000 40,000000 #0000FF transparent", result);
    }

    @Test
    public void testSerializeLine() {
        ConcreteLine line = new ConcreteLine(5, 5, 100, 100, Color.GREEN);
        SaveCommand cmd = new SaveCommand(List.of(line), new Pane());
        String result = cmd.serializeShape(line);
        assertEquals("Line 5,000000 5,000000 100,000000 100,000000 #008000", result);
    }
}