package classes;

import ui.UiLocator;

public class ControllerBuilderImpl implements ControllerBuilder {
    private final UiLocator locator;

    public ControllerBuilderImpl(UiLocator locator) {
        this.locator = locator;
    }

    @Override
    public void buildSelectionController() {
//        SelectionControllerImpl controller = new SelectionControllerImpl(new SelectionMenuController(),
//                         new SelectionMenuController(), new SelectionControllerImpl.SelectionMouseAdapter(),
//                         new TextBoundsRounder());

    }

    @Override
    public void buildEraserController() {

    }

    @Override
    public void buildFillController() {

    }

    @Override
    public void buildLineController() {

    }

    @Override
    public void buildBrushController() {

    }

    @Override
    public void buildTextController() {

    }
}
