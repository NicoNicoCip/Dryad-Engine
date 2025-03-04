package com.pws.dryadengine.core.scripts;

// internal
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// packeged
import com.pws.dryadengine.core.App;
import com.pws.dryadengine.func.ClassHook;
import com.pws.dryadengine.func.Window;
import com.pws.dryadengine.physics.CollisionManager_Rect2D;
import com.pws.dryadengine.types.Color;
import com.pws.dryadengine.types.Vector2;

public class ScriptManager extends ClassHook<Script>{
  private List<Script> scripts = new ArrayList<>();
  private boolean changeListFlag = false;
  private int[] scriptExecution = new int[] {0};
  private List<Script> loadedScripts = new ArrayList<>();

  public ScriptManager() {
    this.load(Script.class,"extends Script");
  }

  @Override
  public void runOn(Script instance) {
    this.addscript(instance);
  }

  public void replant(int... newOrder) {
    changeListFlag = true;
    scriptExecution = newOrder;
  }

  public void create() throws Exception {
    // Initialize all scripts with PC values
    for (Script script : scripts) {
        script.setPC();
    }

    // Sort scripts by PC value
    Collections.sort(scripts, Comparator.comparingInt(script -> script.pc));
    Window.title = "Test Platformer";
    Window.position = new Vector2(200, 200);
    Window.scale = new Vector2(1280, 720);
    Window.setBackgroundColor(new Color(255, 255, 255, 0));
    Window.updateWindow();

    while (!App.finnishExecution) {
      loadedScripts.clear();
      
      // Only add scripts that match the current execution order
      for (int pc : scriptExecution) {
        scripts.stream()
          .filter(s -> s.pc == pc)
          .forEach(loadedScripts::add);
      }

      // Plant phase
      for (Script script : loadedScripts) {
          script.plant();
      }

      changeListFlag = false;

      // Grow phase
      while (!changeListFlag) {
        Window.runEvents();
        Window.clear();

        for (Script script : loadedScripts) {
          script.grow();
        }

        CollisionManager_Rect2D.getInstance().update();
        Window.drawBackground();
        Window.print();
      }
    }

    Window.end();
  }
  

  public void addscript(Script toadd) {
      scripts.add(toadd);
  }

  public List<Script> getLoadedScripts() {
      return loadedScripts;
  }
}
