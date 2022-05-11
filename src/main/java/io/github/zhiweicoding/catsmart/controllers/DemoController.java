package io.github.zhiweicoding.catsmart.controllers;

import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoader;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoaderBean;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static io.github.palexdev.materialfx.MFXResourcesLoader.loadURL;

public class DemoController implements Initializable {
    private final Stage stage;
    private double xOffset;
    private double yOffset;
    private final ToggleGroup toggleGroup;

    @FXML
    private HBox windowHeader;

    @FXML
    private MFXFontIcon closeIcon;

    @FXML
    private MFXFontIcon minimizeIcon;

    @FXML
    private MFXFontIcon alwaysOnTopIcon;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private MFXScrollPane scrollPane;

    @FXML
    private VBox navBar;

    @FXML
    private StackPane contentPane;

    public DemoController(Stage stage) {
        this.stage = stage;
        this.toggleGroup = new ToggleGroup();
        ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
        minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).setIconified(true));
        alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            boolean newVal = !stage.isAlwaysOnTop();
            alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass.getPseudoClass("always-on-top"), newVal);
            stage.setAlwaysOnTop(newVal);
        });

        windowHeader.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        windowHeader.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });

        initializeLoader();

        ScrollUtils.addSmoothScrolling(scrollPane);
    }

    private void initializeLoader() {
        MFXLoader loader = new MFXLoader();
        loader.addView(MFXLoaderBean.of("BUTTONS", loadURL("fxml/Buttons.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-circle-dot", "Buttons")).setDefaultRoot(true).get());
        loader.addView(MFXLoaderBean.of("CHECKS_RADIOS_TOGGLES", loadURL("fxml/ChecksRadiosToggles.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-toggle-on", "Checks, Radios, Toggles")).get());
        loader.addView(MFXLoaderBean.of("COMBOS", loadURL("fxml/ComboBoxes.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-list-dropdown", "ComboBoxes")).get());
        loader.addView(MFXLoaderBean.of("DIALOGS", loadURL("fxml/Dialogs.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-messages", "Dialogs")).setControllerFactory(c -> new DialogsController(stage)).get());
        loader.addView(MFXLoaderBean.of("TEXT-FIELDS", loadURL("fxml/TextFields.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-input-pipe-alt", "Fields")).get());
        loader.addView(MFXLoaderBean.of("LISTS", loadURL("fxml/ListViews.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-square-list", "Lists")).get());
        loader.addView(MFXLoaderBean.of("NOTIFICATIONS", loadURL("fxml/Notifications.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-bell", "Notifications")).setControllerFactory(c -> new NotificationsController(stage)).get());
        loader.addView(MFXLoaderBean.of("PICKERS", loadURL("fxml/Pickers.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-calendars", "Pickers")).get());
        loader.addView(MFXLoaderBean.of("PROGRESS", loadURL("fxml/Progress.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-progress-bars", "Progress")).get());
        loader.addView(MFXLoaderBean.of("SCROLL-PANES", loadURL("fxml/ScrollPanes.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-scroll-bar", "Scroll Panes")).get());
        loader.addView(MFXLoaderBean.of("SLIDERS", loadURL("fxml/Sliders.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-sliders", "Sliders")).get());
        loader.addView(MFXLoaderBean.of("STEPPER", loadURL("fxml/Stepper.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-stepper", "Stepper")).get());
        loader.addView(MFXLoaderBean.of("TABLES", loadURL("fxml/TableViews.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-table", "Tables")).get());
        loader.addView(MFXLoaderBean.of("FONT-RESOURCES", loadURL("fxml/FontResources.fxml")).setBeanToNodeMapper(() -> createToggle("mfx-fonticons", "Font Resources")).get());
        loader.setOnLoadedAction(beans -> {
            List<ToggleButton> nodes = beans.stream()
                    .map(bean -> {
                        ToggleButton toggle = (ToggleButton) bean.getBeanToNodeMapper().get();
                        toggle.setOnAction(event -> contentPane.getChildren().setAll(bean.getRoot()));
                        if (bean.isDefaultView()) {
                            contentPane.getChildren().setAll(bean.getRoot());
                            toggle.setSelected(true);
                        }
                        return toggle;
                    })
                    .toList();
            navBar.getChildren().setAll(nodes);
        });
        loader.start();
    }

    private ToggleButton createToggle(String icon, String text) {
        MFXIconWrapper wrapper = new MFXIconWrapper(icon, 24, 32);

        MFXRectangleToggleNode toggleNode = new MFXRectangleToggleNode(text, wrapper);
        toggleNode.setAlignment(Pos.CENTER_LEFT);
        toggleNode.setMaxWidth(Double.MAX_VALUE);
        toggleNode.setToggleGroup(toggleGroup);

        return toggleNode;
    }
}
