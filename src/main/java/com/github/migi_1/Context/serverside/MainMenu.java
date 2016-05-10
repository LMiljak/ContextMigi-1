package com.github.migi_1.Context.serverside;

import com.github.migi_1.Context.clientside.MainMenuFunctionsAndroid;
import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ControlBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
/**
 *
 * @author Remi
 */
public class MainMenu extends SimpleApplication {

    private Nifty nifty;
    /**
     * main function
     * @param args String[]
     */
    public static void main(String[] args) {
        MainMenu app = new MainMenu();
        app.start();
    }

    /**
     * Builds the menu.
     */
    @Override
   public void simpleInitApp() {
       NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
               assetManager, inputManager, audioRenderer, guiViewPort);
       nifty = niftyDisplay.getNifty();
       guiViewPort.addProcessor(niftyDisplay);
       flyCam.setDragToRotate(true);

       nifty.loadStyleFile("nifty-default-styles.xml");
       nifty.loadControlFile("nifty-default-controls.xml");

       createStartScreen();
       createJoinScreen();

       nifty.gotoScreen("start"); // start the screen
   }

     private void createStartScreen() {
         nifty.addScreen("start", new ScreenBuilder("start") {{
             controller(new com.github.migi_1.Context.clientside.MainMenuFunctionsAndroid());
             layer(createBGLayer());

             layer(new LayerBuilder("foreground") {{
                     childLayoutVertical();
                     backgroundColor("#0000");
                     panel(panelWithText("panel_top", "center", "center", "none", "#f008", "25%", "75%",
                             createDefaultText("CarriedAway")));
                     panel(panelWithText("panel_mid", "center", "center", "center", "#0f08", "50%", "75%",
                             createDefaultText("Press Start to host a server \n Press Quit to exit the game")));

                 panel(new PanelBuilder("panel_bottom") {{
                     childLayoutHorizontal();
                     alignCenter();
                     backgroundColor("#00f8");
                     height("25%");
                     width("75%");
                     panel(panelWithControl("panel_botton_left", "center", "none", "center", "#44f8", "50%", "50%",
                             createButtonControl("StartButton", "Start", "none", "center", "center", "50%", "50%", "toScreen(join)")));
                     panel(panelWithControl("panel_botton_right", "center", "none", "center", "#88f8", "50%", "50%",
                             createButtonControl("QuitButton", "Quit", "none", "center", "center", "50%", "50%", "quitGame()")));

                 }}); // panel added
             }});

         }}.build(nifty));
     }

     private void createJoinScreen() {
         nifty.addScreen("join", new ScreenBuilder("join"){{
             controller(new MainMenuFunctionsAndroid());
             //Add bg layer
             layer(createBGLayer());

             layer(new LayerBuilder("foreground") {{
                     childLayoutVertical();
                     backgroundColor("#0000");

                     panel(panelWithText("panel_mid", "center", "center", "none", "#0f08", "50%", "75%",
                             createDefaultText("There is literally nothing here, go back")));

                     panel(panelWithControl("panel_bottom", "horizontal", "center", "none", "#00f8", "75%", "25%",
                             createButtonControl("BackButton", "Back", "none", "center", "center", "50%", "50%", "toScreen(start)")));

             }});
             // layer added

           }}.build(nifty));

     }

     private LayerBuilder createBGLayer() {
         return new LayerBuilder("background") {{
             childLayoutCenter();
             backgroundColor("#000f");
          }};
     }

     private TextBuilder createDefaultText(String txt) {
         return new TextBuilder() {{
             text(txt);
             font("Interface/Fonts/Default.fnt");
             height("100%");
             width("100%");
         }};
     }

     private ButtonBuilder createButtonControl(String buttonId, String buttonLabel, String childLayout, String align, String valign, String height, String width, String func) {
         return new ButtonBuilder(buttonId, buttonLabel) {{
             switch (childLayout) {
                 case "none" : break;
                 case "center" :
                     childLayoutCenter();
                     break;
                 default : throw new IllegalStateException("Programmer, you screwed up ");
             }

             switch (align) {
                 case "none" : break;
                 case "center":
                     alignCenter();
                     break;
                 default : throw new IllegalStateException("Programmer, you screwed up ");
             }

             switch (valign) {
                 case "none" : break;
                 case "center":
                     valignCenter();
                     break;
                 default: throw new IllegalStateException("Programmer, you screwed up ");
             }
             height(height);
             width(width);
             interactOnClick(func);
          }};
     }

     private PanelBuilder panelWithText(String id, String childLayout, String align, String valign, String color, String height, String width, TextBuilder txt) {
         return new PanelBuilder(id) {{
             switch (childLayout) {
             case "none" : break;
             case "center" :
                 childLayoutCenter();
                 break;
             default : throw new IllegalStateException("Programmer, you screwed up");
         }

         switch (align) {
             case "none" : break;
             case "center":
                 alignCenter();
                 break;
             default : throw new IllegalStateException("Programmer, you screwed up");
         }

         switch (valign) {
             case "none" : break;
             case "center" :
                 valignCenter();
                 break;
             default: throw new IllegalStateException("Programmer, you screwed up");
         }
             backgroundColor(color);
             height(height);
             width(width);
             text(txt);
         }};
     }

     private PanelBuilder panelWithControl(String id, String childLayout, String align, String valign, String color, String width, String heigth, ControlBuilder con) {
         return new PanelBuilder(id) {{
             switch (childLayout) {
                 case "none" : break;
                 case "center" :
                     childLayoutCenter();
                     break;
                 case "horizontal" :
                     childLayoutHorizontal();
                     break;
                 default : throw new IllegalStateException("Programmer, you screwed up");
             }

             switch (align) {
                 case "none" : break;
                 case "center":
                     alignCenter();
                     break;
                 default : throw new IllegalStateException("Programmer, you screwed up");
             }

             switch (valign) {
                 case "none" : break;
                 case "center" :
                     valignCenter();
                     break;
                 default: throw new IllegalStateException("Programmer, you screwed up");
             }
             backgroundColor(color);
             height(heigth);
             width(width);

             // add control
             control(con);
         }};
     }

}
