package com.mdmp.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class Context {
  
  private static Properties prop = new Properties();
  
  static {
    //load default property file
    loadPropertiesFile("/properties");

  }
  
  private static void loadPropertiesFile(String file) {
    //FileInputStream input = null;
    InputStream input = null;
    try {
//      URL uri = Context.class.getClassLoader().getResource("/");
//      String path = uri.getPath() + file;
//      System.out.println("xxxxxxxx===>"+path);
//      File fileName = new File(path);
//      input = new FileInputStream(fileName);
      prop.load(Context.class.getResourceAsStream(file));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
  
  /**
   * Obtains value by specified key
   * 
   * @param key
   * @return might be null if this key is absent
   */
  public String get(String key) {
    return prop.getProperty(key);
  }
  
  /**
   * @param key
   * @param defaultValue
   * @return
   */
  public String get(String key, String defaultValue) {
    return prop.getProperty(key, defaultValue);
  }
  
  /**
   * Designed for API to obtains URL path except HTTP method
   * <br>
   * e.g : key.url = POST:/a/b/c
   * getUrl("key.url") = "/a/b/c"
   * getMethod("key.url") = "POST"
   * 
   * @param key
   * @return
   */
  public static String getUrl(String key) {
    String value = prop.getProperty(key);
    if (value != null) {
      String[] parts = value.split(":");
      if (parts.length == 2) {
        return parts[1];
      }
    }
    
    throw new IllegalArgumentException("key:" + key + ", value:" + value 
        + ". expect format <METHOD>:<url>");
  }
  
  public static String getMethod(String key) {
    String value = prop.getProperty(key);
    if (value != null) {
      String[] parts = value.split(":");
      if (parts.length == 2) {
        return parts[0];
      }
    }
    
    throw new IllegalArgumentException("key:" + key + ", value:" + value 
        + ". expect format <METHOD>:<url>");
  }
  
  public Set<Object> getAllKeys() {
    return prop.keySet();
  }
  
}


