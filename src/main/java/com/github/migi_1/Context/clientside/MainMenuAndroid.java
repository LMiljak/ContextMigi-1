/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.Context.clientside;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;

/**
 *
 * @author Remi
 */
public class MainMenuAndroid extends SimpleApplication {
    
    public static void main(String[] args) {
        MainMenuAndroid app = new MainMenuAndroid();
        app.start();
    }
    
     @Override
    public void simpleInitApp() {
    NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
            assetManager, inputManager, audioRenderer, guiViewPort);
    Nifty nifty = niftyDisplay.getNifty();
    guiViewPort.addProcessor(niftyDisplay);
    flyCam.setDragToRotate(true);
 
    nifty.loadStyleFile("nifty-default-styles.xml");
    nifty.loadControlFile("nifty-default-controls.xml");
 
    // <screen>
    nifty.addScreen("start", new ScreenBuilder("start") {{
        controller(new com.github.migi_1.Context.serverside.MainMenuFunctions());
        layer(new LayerBuilder("background") {{
            childLayoutCenter();
            backgroundColor("#000f");
        }});
 
        layer(new LayerBuilder("foreground") {{
                childLayoutVertical();
                backgroundColor("#0000");
 
            // panel added
            panel(new PanelBuilder("panel_top") {{
                childLayoutCenter();
                alignCenter();
                backgroundColor("#f008");
                height("25%");
                width("75%");
                
                // add text
                text(new TextBuilder() {{
                    text("Carried Away");
                    font("Interface/Fonts/Default.fnt");
                    height("100%");
                    width("100%");
                }});
            }});
 
            panel(new PanelBuilder("panel_mid") {{
                childLayoutCenter();
                alignCenter();
                backgroundColor("#0f08");
                height("50%");
                width("75%");
                
                // add text
                text(new TextBuilder() {{
                    text("Press Start to host a server"+
                            "\nPress Quit to exit the game");
                    font("Interface/Fonts/Default.fnt");
                    height("100%");
                    width("100%");
                }});
            }});
 
            panel(new PanelBuilder("panel_bottom") {{
                childLayoutHorizontal();
                alignCenter();
                backgroundColor("#00f8");
                height("25%");
                width("75%");
 
                panel(new PanelBuilder("panel_bottom_left") {{
                    childLayoutCenter();
                    valignCenter();
                    backgroundColor("#44f8");
                    height("50%");
                    width("50%");
                    
                    // add control
                    control(new ButtonBuilder("StartButton", "Start") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      visibleToMouse(true);
                      interactOnClick("toScreen(host)");
                    }});
                }});
 
                panel(new PanelBuilder("panel_bottom_right") {{
                    childLayoutCenter();
                    valignCenter();
                    backgroundColor("#88f8");
                    height("50%");
                    width("50%");
                    
                    // add control
                    control(new ButtonBuilder("QuitButton", "Quit") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      visibleToMouse(true);
                      interactOnClick("quitGame()");
                    }});
 
                }});
            }}); // panel added
        }});
 
    }}.build(nifty));
    // </screen>
    
    nifty.addScreen("host", new ScreenBuilder("host"){{
        controller(new com.github.migi_1.Context.serverside.MainMenuFunctions());
 
         // layer added
         layer(new LayerBuilder("background") {{
            childLayoutCenter();
            backgroundColor("#000f"); 
         }});
 
         layer(new LayerBuilder("foreground") {{
                childLayoutVertical();
                backgroundColor("#0000");        
 
            panel(new PanelBuilder("panel_mid") {{
                childLayoutCenter();
                alignCenter();
                backgroundColor("#0f08");
                height("50%");
                width("75%");
                
                // add text
                text(new TextBuilder() {{
                    text("There is literally nothing here, go back");
                    font("Interface/Fonts/Default.fnt");
                    height("100%");
                    width("100%");
                }});
            }});
 
            panel(new PanelBuilder("panel_bottom") {{
                childLayoutHorizontal();
                alignCenter();
                backgroundColor("#00f8");
                height("25%");
                width("75%");
                
                // add control
                control(new ButtonBuilder("BackButton", "Back") {{
                   alignCenter();
                   valignCenter();
                   height("50%");
                   width("50%");
                   visibleToMouse(true);
                   interactOnClick("toScreen(start)");
                }});

            }}); // panel added
         }});
         // layer added
 
      }}.build(nifty));
 
    nifty.gotoScreen("start"); // start the screen
    }
    
}

