package com.pws.dryadengine.func;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.pws.dryadengine.core.App;

public abstract class ClassHook<T> {
  private Class<T> mType;
  // Precompile regex patterns for better performance
  private static final Pattern MULTI_LINE_COMMENT_PATTERN = Pattern.compile("/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/");
  private static final Pattern SINGLE_LINE_COMMENT_PATTERN = Pattern.compile("//.*(?:\\R|$)");
  private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([^;]+);");
  private static final Pattern CLASS_PATTERN = Pattern.compile("class\\s+(\\w+)\\s+");

  /**
   * Singleton function that loads all the passed classes found
   * in the file specified at App.executionFolder. recommended
   * to be ran in a constructor.
   * 
   * @param type       - the class type to filter by
   * @param identifier - the identifier that the file manager will
   *                   search for in the working directory to hook via "runOn"
   *                   function
   */
  public void load(Class<T> type, String identifier) {
    try {
      this.mType = type;
      File root = new File(App.projectFolder);

      // Use a thread pool for parallel processing
      int processors = Runtime.getRuntime().availableProcessors();
      ExecutorService executor = Executors.newFixedThreadPool(processors);
      ConcurrentLinkedQueue<File> javaFiles = new ConcurrentLinkedQueue<>();

      // First pass: collect all Java files (faster than processing each file during
      // traversal)
      collectJavaFiles(root, javaFiles);

      // Second pass: process files in parallel
      for (File file : javaFiles) {
        executor.submit(() -> {
          try {
            processFile(file, identifier);
          } catch (Exception e) {
            Debug.logError(e);
          }
        });
      }

      // Shutdown the executor and wait for all tasks to complete
      executor.shutdown();
      executor.awaitTermination(5, TimeUnit.MINUTES);

    } catch (Exception e) {
      Debug.logError(e);
    }
  }

  // Collect all Java files first for better parallelization
  private void collectJavaFiles(File directory, ConcurrentLinkedQueue<File> javaFiles) {
    File[] files = directory.listFiles();
    if (files == null)
      return;

    for (File file : files) {
      if (file.isDirectory()) {
        collectJavaFiles(file, javaFiles);
      } else if (file.getName().endsWith(".java")) {
        javaFiles.add(file);
      }
    }
  }

  // Process a single file
  private void processFile(File file, String identifier) throws Exception {
    Path filePath = Paths.get(file.getAbsolutePath());

    // Use NIO for faster file reading
    String data = Files.readString(filePath);

    // Quick check before applying expensive regex operations
    if (!data.contains(identifier)) {
      return;
    }

    // Remove comments more efficiently
    data = MULTI_LINE_COMMENT_PATTERN.matcher(data).replaceAll("");
    data = SINGLE_LINE_COMMENT_PATTERN.matcher(data).replaceAll("");

    // Double-check after comment removal
    if (!data.contains(identifier)) {
      return;
    }

    // Extract package and class names using regex
    var packageMatcher = PACKAGE_PATTERN.matcher(data);
    var classMatcher = CLASS_PATTERN.matcher(data);

    if (packageMatcher.find() && classMatcher.find()) {
      String packageName = packageMatcher.group(1);
      String className = classMatcher.group(1);
      String fullClassName = packageName + "." + className;

      try {
        Class<?> loadedClass = Class.forName(fullClassName);
        if (this.mType.isAssignableFrom(loadedClass)) {
          @SuppressWarnings("unchecked")
          Class<? extends T> castedClass = (Class<? extends T>) loadedClass;
          T instance = castedClass.getDeclaredConstructor().newInstance();
          runOn(instance);
        }
      } catch (ClassNotFoundException | NoSuchMethodException e) {
        // Silently ignore classes that can't be loaded or instantiated
      }
    }
  }

  /**
   * Override only function that gets ran at the end of processing
   * function to actually do something with the found classes.
   * 
   * @param instance - instance of the specified type to do stuff to.
   */
  public void runOn(T instance) {
    Debug.logError(new UnsupportedOperationException(
        "Subclass must implement runOn method. Otherwise the hook is pointless."));
  }
}
