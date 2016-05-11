package com.github.migi_1.Context.screens;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ControlBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
/**
 * The main menu for the pc (server)
 * @author Remi & Nils
 */
public class MainMenu {

    /**
     * A private variable to be able to split up the creation of the screen in separate methods.
     */
    private Nifty nifty;
    /**
     * Method that creates the screen.
     * The startscreen will be the first screen that is visible after startup.
     */

    public void initMenu(FlyByCamera flyCam, AssetManager assetManager, InputManager inputManager
            , AudioRenderer audioRenderer, ViewPort guiViewPort) {
       NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
               assetManager, inputManager, audioRenderer, guiViewPort);
       nifty = niftyDisplay.getNifty();
       guiViewPort.addProcessor(niftyDisplay);
       flyCam.setDragToRotate(true);

       nifty.loadStyleFile("nifty-default-styles.xml");
       nifty.loadControlFile("nifty-default-controls.xml");

       //Create the start menu
       createStartScreen();
       //Create the join menu.
       createHostScreen();

       nifty.gotoScreen("start"); //Go to the start screen.
    }

     /**
     * Creates the start screen.
     */
     private void createStartScreen() {
         nifty.addScreen("start", new ScreenBuilder("start") {{
             controller(new com.github.migi_1.Context.screens.MainMenuFunctions());
             //Create the background layer (completely black)
             layer(createBGLayer());

             layer(new LayerBuilder("foreground") {{
                     childLayoutVertical();
                     backgroundColor("#0000");
                     //Create the "Carried away bar at the top of the screen.
                     panel(panelWithText("panel_top", "center", "center", "none", "#f008", "25%", "75%",
                             createDefaultText("CarriedAway")));

                     //Create an info panel in the middle of the screen.
                     panel(panelWithText("panel_mid", "center", "center", "center", "#0f08", "50%", "75%",
                             createDefaultText("Press Start to host a server \n Press Quit to exit the game")));

                     //Create a panel at the bottom of the screen in which there will be buttons to go to the join screen or to exit the game.
                     panel(new PanelBuilder("panel_bottom") {{
                         childLayoutHorizontal();
                         alignCenter();
                         backgroundColor("#00f8");
                         height("25%");
                         width("75%");
                         panel(panelWithControl("panel_botton_left", "center", "none", "center", "#44f8", "50%", "50%",
                                 createButtonControl("StartButton", "Start", "none", "center", "center", "50%", "50%", "toScreen(host)")));
                         panel(panelWithControl("panel_botton_right", "center", "none", "center", "#88f8", "50%", "50%",
                                 createButtonControl("QuitButton", "Quit", "none", "center", "center", "50%", "50%", "quitGame()")));

                     }});
             }});

         }}.build(nifty));
     }
     /**
      * Creates the host screen.
      */
     private void createHostScreen() {
         nifty.addScreen("host", new ScreenBuilder("host"){{
             controller(new MainMenuFunctions());
             //Add background layer (completely black)
             layer(createBGLayer());

             //Create a foreground layer in which there are two panels:
             //1: An info panel in the center.
             //2: A panel at the bottom of the screen that contains a back to main button.
             layer(new LayerBuilder("foreground") {{
                     childLayoutVertical();
                     backgroundColor("#0000");

                     //Create panel 1
                     panel(panelWithText("panel_mid", "center", "center", "none", "#0f08", "50%", "75%",
                             createDefaultText("There is literally nothing here, go back")));

                     //Create panel 2

                     panel(new PanelBuilder("panel_bottom") {{
                         childLayoutHorizontal();
                         alignCenter();
                         backgroundColor("#00f8");
                         height("25%");
                         width("75%");
                         panel(panelWithControl("panel_botton_left", "center", "none", "center", "#44f8", "50%", "50%",
                                 createButtonControl("StartGameButton", "Start the game", "none", "center", "center", "50%", "50%", "startGame()")));
                         panel(panelWithControl("panel_bottom_right", "center", "none", "center", "#00f8", "50%", "50%",
                                 createButtonControl("BackToMainButton", "Back to main", "none", "center", "center", "50%", "50%", "toScreen(start)")));

                     }});


             }});
             //Build the screen
           }}.build(nifty));

     }

     /**
      * Method that creates a completely black layer
      * Used to generate the background of screens.
      * @return A layer that is completely black
      * @NOTE usage: layer(createBGLayer())
      */
     private LayerBuilder createBGLayer() {
         return new LayerBuilder("background") {{
             childLayoutCenter();
             backgroundColor("#000f");
          }};
     }

     /**
      * Method that creates text for in panels.
      * Uses default font and default 100% height and 100% width.
      * @param txt (String) the text that must be displayed on the panels.
      * @return A textfield used in a panel.
      */
     private TextBuilder createDefaultText(final String txt) {
         return new TextBuilder() {{
             text(txt);
             font("Interface/Fonts/Default.fnt");
             height("100%");
             width("100%");
         }};
     }

     //TODO: The following methods all contain switch cases, and I am aware that it looks really ugly.
     //If you have another suggestion on how to do this, please suggest them.

     /**
      * Method that creates a button, using the following parameters:
      * @param buttonId the id of the button
      * @param buttonLabel the label of the button (will be displayed on the screen)
      * The following 3 Strings only support either "none" (dont use) or "center" (center the button).
      * If needed this can be expanded.
      * @param childLayout ChildLayout of the button.
      * @param align horizontal alignment of the button
      * @param valign vertical alignment of the button
      * @param height the height of the button in respect to the panel that it is in. Must be a percentage ("100%").
      * @param width the width of the button in respect to the panel that it is in. Must be a percentage ("100%").
      * @param func the function that should be executed when the button is pressed. Function must be present in the MainMenuFunctionAndroid class.
      * @return A button that will be placed on top of the panel.
      */
     private ButtonBuilder createButtonControl(final String buttonId, final String buttonLabel,
                       final String childLayout, final String align, final String valign,
                       final String height, final String width, final String func) {
         return new ButtonBuilder(buttonId, buttonLabel) {{
             switch (childLayout) {
                 case "none" : break;
                 case "center" :
                     childLayoutCenter();
                     break;
                 default : throw new IllegalStateException("invalid string: " + childLayout);
             }

             switch (align) {
                 case "none" : break;
                 case "center":
                     alignCenter();
                     break;
                 default : throw new IllegalStateException("invalid string: " + align);
             }

             switch (valign) {
                 case "none" : break;
                 case "center":
                     valignCenter();
                     break;
                 default: throw new IllegalStateException("invalid string: " + valign);
             }
             height(height);
             width(width);
             interactOnClick(func);
          }};
     }

     /**
      * Method that creates a panel with text using the following parameters:
      * @param id The id of the panel
      * The following 3 Strings only support either "none" (dont use) or "center" (center the button).
      * If needed this can be expanded.
      * @param childLayout the child layout of the panel
      * @param align the horizontal alignment of the panel
      * @param valign the vertical alignment of the panel
      * @param color the color of the panel, must be the hexadecimal value of the color, started with "#" (e.g. #0000 or #ffff)
      * @param height the height of the panel. Must be a percentage ("100%").
      * @param width the width of the panel. Must be a percentage ("100%").
      * @param txt the text that will be displayed on the panel.
      * @return A panel with text.
      */
     private PanelBuilder panelWithText(final String id, final String childLayout,
                    final String align, final String valign, final String color,
                    final String height, final String width, final TextBuilder txt) {
         return new PanelBuilder(id) {{
             switch (childLayout) {
             case "none" : break;
             case "center" :
                 childLayoutCenter();
                 break;
             default : throw new IllegalStateException("Invalid String: " + childLayout);
         }

         switch (align) {
             case "none" : break;
             case "center":
                 alignCenter();
                 break;
             default : throw new IllegalStateException("Invalid String: " + align);
         }

         switch (valign) {
             case "none" : break;
             case "center" :
                 valignCenter();
                 break;
             default: throw new IllegalStateException("Invalid String: " + valign);
         }
             backgroundColor(color);
             height(height);
             width(width);
             text(txt);
         }};
     }

     /**
      * Method to create a panel with a button on it using the following parameters:
      * @param id the id of the panel
      * The following 3 Strings only support either "none" (dont use) or "center" (center the button). The childLayout parameter also supports "horizontal" layout.
      * If needed this can be expanded.
      * @param childLayout the child layout of the panel
      * @param align the horizontal alignment of the panel
      * @param valign the vertical alignment of the panel
      * @param color the color of the panel, must be the hexadecimal value of the color, started with "#" (e.g. #0000 or #ffff)
      * @param height the height of the button in respect to the panel that it is in. Must be a percentage ("100%").
      * @param width the width of the button in respect to the panel that it is in. Must be a percentage ("100%").
      * @param con the control of the panel (the ControlBuilder, can be acquired via the createButtonControl)
      * @return A panel with a functional button.
      */
     private PanelBuilder panelWithControl(final String id, final String childLayout,
                    final String align, final String valign, final String color,
                    final String width, final String heigth, final ControlBuilder con) {
         return new PanelBuilder(id) {{
             switch (childLayout) {
                 case "none" : break;
                 case "center" :
                     childLayoutCenter();
                     break;
                 case "horizontal" :
                     childLayoutHorizontal();
                     break;
                 default : throw new IllegalStateException("Invalid String: " + childLayout);
             }

             switch (align) {
                 case "none" : break;
                 case "center":
                     alignCenter();
                     break;
                 default : throw new IllegalStateException("Invalid String: " + align);
             }

             switch (valign) {
                 case "none" : break;
                 case "center" :
                     valignCenter();
                     break;
                 default: throw new IllegalStateException("Invalid String: " + valign);
             }
             backgroundColor(color);
             height(heigth);
             width(width);

             // add control
             control(con);
         }};
     }
}