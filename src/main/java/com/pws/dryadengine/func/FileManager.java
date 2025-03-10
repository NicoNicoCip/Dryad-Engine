package com.pws.dryadengine.func;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public abstract class FileManager {

  /** 
   * Gets the absolute path of the project's root directory. 
   */
  public static String getProjectPath() {
    return Paths.get("").toAbsolutePath().toString().replace("\\", "/") + "/";
  }

  /** 
   * Creates a new file with metadata stored in the first line. 
   */
  public static File createFile(String filePath) {
    try {
      File file = new File(getProjectPath() + filePath);

      if (file.getParentFile() != null) {
        file.getParentFile().mkdirs();
      }

      if (!file.exists()) {
        file.createNewFile();
        updateFile(file, "", false); // Initialize with metadata
      }
      return file;
    } catch (Exception e) {
      Debug.logError(e);
      return null;
    }
  }

  /** 
   * Writes data to the file, either appending or overwriting it. 
   */
  public static boolean updateFile(File file, String text, boolean append) {
    try {
      BasicFileAttributes attrs = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class,LinkOption.NOFOLLOW_LINKS);
      String metadata = file.getName() + ", " + file.getPath() + ", " + attrs.creationTime() + ", "
          + attrs.lastModifiedTime() + ", " + attrs.size() + " bytes";

      // Read existing content (excluding metadata)
      String existingData = readFile(file);
      String[] parts = existingData.split("\\[META-END\\]", 2); // Escape `[` and `]`
      String fileContent = (parts.length > 1) ? parts[1] : "";

      // If appending, keep existing content
      String newContent = append ? fileContent + text : text;

      // Write metadata and new content
      try (FileWriter writer = new FileWriter(file, false)) {
        writer.write("[" + metadata + "][META-END]" + newContent);
      }

      return true;
    } catch (Exception e) {
      Debug.logError(e);
      return false;
    }
  }

  /** 
   * Reads the full contents of a file as a string. 
   */
  public static String readFile(String filePath) {
    return readFile(new File(getProjectPath(), filePath));
  }

  /**
   * Reads the full contents of a file as a string. 
   */
  public static String readFile(File file) {
    try (Scanner scanner = new Scanner(file)) {
      StringBuilder output = new StringBuilder();
      while (scanner.hasNextLine()) {
        output.append(scanner.nextLine()).append("\n");
      }
      return output.toString().trim(); // Remove trailing newline
    } catch (Exception e) {
      Debug.logError(e);
      return null;
    }
  }

  /** 
   * Extracts the metadata from a file. 
   */
  public static String extractMetadata(File file) {
    String content = readFile(file);
    if (content != null && content.contains("[META-END]")) {
      return content.split("\\[META-END\\]", 2)[0]; // Extract metadata part
    }
    return null;
  }

  /**
   * Reads a file and splits its content into an array based on a separator. 
   */
  public static String[] readLines(File file, String separator) {
    return readFile(file).split(separator);
  }

  /**
   * Deletes a file. 
   */
  public static boolean deleteFile(File file) {
    return file.delete();
  }

  /**
   * Deletes a file by path. 
   */
  public static boolean deleteFile(String filePath) {
    return new File(getProjectPath(), filePath).delete();
  }

  /** 
   * Checks if a file exists. 
   */
  public static boolean fileExists(File file) {
    return file.exists();
  }

  /** 
   * Checks if a file exists by path. 
   */
  public static boolean fileExists(String filePath) {
    return new File(getProjectPath(), filePath).exists();
  }

  /** 
   * Retrieves a File object for a given path. 
   */
  public static File getFile(String filePath) {
    return new File(getProjectPath(), filePath);
  }
}
