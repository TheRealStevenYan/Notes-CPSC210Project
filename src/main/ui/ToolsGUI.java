package ui;

import ui.options.CategoryContainerGUI;
import ui.options.rename.NoteRenameGUI;
import ui.tools.*;
import javax.swing.*;
import java.awt.*;

public class ToolsGUI {
    private static final Color BACKGROUND = new Color(240, 240, 240);
    private final NoteGUI noteGUI;

    private Color selectedColour;

    private PenTool penTool;
    private EraserTool eraserTool;
    private TextTool textTool;

    // CONSTRUCTOR
    // EFFECTS: creates a new ToolsGUI
    ToolsGUI(NoteGUI noteGUI) {
        this.noteGUI = noteGUI;

        selectedColour = Color.black;

        generateColourButtons();
        generateToolButtons();
        generateOptionButtons();
        initTools();
    }

    // EFFECTS: returns this note gui
    public NoteGUI getNoteGUI() {
        return noteGUI;
    }

    // EFFECTS: returns the selected colour
    public Color getSelectedColour() {
        return selectedColour;
    }

    // MODIFIES: this
    // EFFECTS: initialises the tools
    private void initTools() {
        penTool = new PenTool(this);
        eraserTool = new EraserTool(this);
        textTool = new TextTool(this);
    }


    // MODIFIES: this
    // EFFECTS: generates the option button and rename button
    //          option button allows for operations on categories, saving, loading other notes etc.
    //          rename button allows for renaming of this note
    private void generateOptionButtons() {
        JButton optionsButton = new JButton("Options");
        optionsButton.setBorder(BorderFactory.createEmptyBorder());
        optionsButton.setBackground(BACKGROUND);
        optionsButton.addActionListener(e -> new CategoryContainerGUI(this));

        JButton renameButton = new JButton("Rename Note");
        renameButton.setBorder(BorderFactory.createEmptyBorder());
        renameButton.setBackground(BACKGROUND);
        renameButton.addActionListener(e -> new NoteRenameGUI(noteGUI));

        JPanel optionsPanel = noteGUI.getOptionsPanel();
        optionsPanel.add(optionsButton);
        optionsPanel.add(renameButton);
    }

    // https://stackoverflow.com/questions/17653116/action-listener-for-multiple-radio-buttons
    // MODIFIES: this
    // EFFECTS: generates tool buttons for all the tools
    private void generateToolButtons() {
        ButtonGroup toolButtons = new ButtonGroup();
        Tools[] tools;
        tools = Tools.values();

        for (Tools t: tools) {
            String buttonName = t.toString().charAt(0) + t.toString().substring(1).toLowerCase();
            JRadioButton tb = new JRadioButton(buttonName);
            tb.setBackground(BACKGROUND);
            tb.addActionListener(e -> performToolAction(t));
            toolButtons.add(tb);

            JPanel toolsPanel = noteGUI.getToolsPanel();
            toolsPanel.add(tb);
        }
    }

    // MODIFIES: this
    // EFFECTS: generates colour buttons for all the colours
    private void generateColourButtons() {
        ButtonGroup colourButtons = new ButtonGroup();
        Colours[] colours;
        colours = Colours.values();
        for (Colours c: colours) {
            String buttonName = c.toString().charAt(0) + c.toString().substring(1).toLowerCase();
            JRadioButton cb = new JRadioButton(buttonName);
            cb.setBackground(BACKGROUND);
            cb.addActionListener(e -> {
                selectedColour = c.getColour();
                penTool.setColour(selectedColour);
            });

            JPanel coloursPanel = noteGUI.getColoursPanel();
            colourButtons.add(cb);
            coloursPanel.add(cb);
        }
    }

    // MODIFIES: this
    // EFFECTS: performs the tool action for the given tool
    //          tools that aren't the given tool are disabled, while the given tool is activated
    private void performToolAction(Tools t) {
        penTool.setActive(false);
        eraserTool.setActive(false);
        textTool.setActive(false);
        switch (t) {
            case PEN:
                penTool.setActive(true);
                break;
            case ERASER:
                eraserTool.setActive(true);
                break;
            case TEXT:
                textTool.setActive(true);
                break;
            default:
                break;
        }
    }
}
