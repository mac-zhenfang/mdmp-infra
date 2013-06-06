package com.mdmp.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


/**
 * Class Description
 *
 * @author Denny Ye
 * @since 2012-11-20
 * @version 1.0
 */
public class JsonUtils {

  private static ObjectMapper mapper = new ObjectMapper();
  
  /**
   * Object to string 
   * 
   * @param obj
   * @return
   */
  public static String convertFrom(Object obj) throws Exception {
    if (obj == null) {
      return "";
    }
    
    return mapper.writeValueAsString(obj);
  }
  
  /**
   * Object to string without specified element
   * 
   * @param obj
   * @param elementName
   * @return
   */
  public static String convertAndRemove(Object obj, String elementName) {
    JSONObject original = JSONObject.fromObject(obj);
    original.remove(elementName);
    return original.toString();
  }
  
  public static String convertAndReplace(Object obj, String elementName, String newValue) {
    JSONObject original = JSONObject.fromObject(obj);
    original.element(elementName, newValue);
    return original.toString();
  }
  
  public static <T> String convertFrom(List<T> list) {
    if (list == null || list.size() == 0) {
      return "[]";
    }
    
    JSONArray array = JSONArray.fromObject(list);
    return array.toString();
  }
  
  /**
   * @param jsonStr
   * @return
   */
  public static <T> T convertFrom(String jsonStr, Class<T> clazz) throws Exception {
    return mapper.readValue(jsonStr, clazz);
  }
  
  public static <T> List<T> convertToList(String jsonStr, Class<T> clazz) throws Exception {
    List<T> list = new ArrayList<T>();
    JSONArray array = JSONArray.fromObject(jsonStr);
    for (int i = 0; i < array.size(); i++) {
    	if(clazz==String.class){
    		list.add((T) array.getString(i));
    	} else {
    		list.add((T)mapper.readValue(array.getString(i), clazz));
    	}
    }
    
    return list;
  }
  
  public static Map<String, Object> toMap(JSONObject object) throws JSONException {
      Map<String, Object> map = new HashMap<String, Object>();
      Iterator<?> keys = object.keys();
      while (keys.hasNext()) {
          String key = (String) keys.next();
          map.put(key, fromJson(object.get(key)));
      }
      return map;
  }

  public static List toList(JSONArray array) throws JSONException {
      List list = new ArrayList();
      for (int i = 0; i < array.size(); i++) {
          list.add(fromJson(array.get(i)));
      }
      return list;
  }

  private static Object fromJson(Object json) throws JSONException {
      if (json == null) {
          return null;
      } else if (json instanceof JSONObject) {
          return toMap((JSONObject) json);
      } else if (json instanceof JSONArray) {
          return toList((JSONArray) json);
      } else {
          return json;
      }
  }
}



