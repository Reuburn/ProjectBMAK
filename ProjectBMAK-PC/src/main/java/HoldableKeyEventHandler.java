import java.util.List;

/**
 * Created by Reuben Spiers on 14/03/2016.
 */
public class HoldableKeyEventHandler implements KeyEventHandler {
    private final Integer modifierToUpdate;

    public HoldableKeyEventHandler(Integer modifierToUpdate) {
        this.modifierToUpdate = modifierToUpdate;
    }

    public void handleKeyEvent(List<Integer> modifiers) {
        System.out.println("Key Modifier " + modifierToUpdate + " Added.");
        modifiers.add(modifierToUpdate);
    }
}