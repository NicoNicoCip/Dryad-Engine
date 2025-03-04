package test.core;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class ClassHook<T> {
  private Class<T> mType;

  /**
   * Singleton function that loads all the passed classes found<br>
   * in the file specified at App.executionFolder. recomended<br>
   * to be ran in a constructor.<br>
   * @param identifier - the identifier that the file manager will<br>
   * search for in the working directory to hook via "runOn" function<br>
   */
  public void load(Class<T> type,String identifier) {
    try {
      this.mType = type;
      File root = new File(Bench.executionFolder);
      for (File file : root.listFiles()){
        getFilesRecursive(identifier,file);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Utility function that finds the classes in the working direcory and passes
  // the result to the runOn function, to be processed further.
  private void getFilesRecursive(String identifier, File pFile) throws Exception {
    for(File files : pFile.listFiles()){
      if(files.isDirectory()){
        getFilesRecursive(identifier,files);
      } else {
        String data = Files.readString(Paths.get(files.getAbsolutePath()));
        data = data.replaceAll("/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/", "").replaceAll("//.*(?:\\R|$)", "");
        if(!data.contains(identifier)) continue;
        
        String pazz = data.split("package ")[1].split(";")[0];
        String clazz = data.split("class ")[1].split(" ")[0];
        
        String fullClassName = pazz + "." + clazz;
        Class<?> loadedClass = Class.forName(fullClassName);
        if (this.mType.isAssignableFrom(loadedClass)) {
          @SuppressWarnings("unchecked")
          Class<? extends T> castedClass = (Class<? extends T>) loadedClass;
          T instance = castedClass.getDeclaredConstructor().newInstance();
          runOn(instance);
        }
      }
    }
  }

  /**
   * Override only function that gets ran at the end of the getFilesRecursive<br>
   * function to actually do something with the found classes.<br>
   * @param instance - instance of the specified type to do stuff to.
   */ 
  public void runOn(T instance) {
    Debug.print(new UnsupportedOperationException("Subclass must inplement runOn method. Otherwise the hook is pointless."));
  }
}
