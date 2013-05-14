package com.mdmp.infra.operator.sql.udf;

public class UDFLike extends UDF {
	  private final Text lastLikePattern = new Text();
	  private Pattern p = null;

	  // Doing characters comparison directly instead of regular expression
	  // matching for simple patterns like "%abc%".
	  private enum PatternType {
	    NONE, // "abc"
	    BEGIN, // "abc%"
	    END, // "%abc"
	    MIDDLE, // "%abc%"
	    COMPLEX, // all other cases, such as "ab%c_de"
	  }

	  private PatternType type = PatternType.NONE;
	  private final Text simplePattern = new Text();

	  private final BooleanWritable result = new BooleanWritable();

	  public UDFLike() {
	  }

	  public static String likePatternToRegExp(String likePattern) {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < likePattern.length(); i++) {
	      // Make a special case for "\\_" and "\\%"
	      char n = likePattern.charAt(i);
	      if (n == '\\'
	          && i + 1 < likePattern.length()
	          && (likePattern.charAt(i + 1) == '_' || likePattern.charAt(i + 1) == '%')) {
	        sb.append(likePattern.charAt(i + 1));
	        i++;
	        continue;
	      }

	      if (n == '_') {
	        sb.append(".");
	      } else if (n == '%') {
	        sb.append(".*");
	      } else {
	        sb.append(Pattern.quote(Character.toString(n)));
	      }
	    }
	    return sb.toString();
	  }

	  /**
	   * Parses the likePattern. Based on it is a simple pattern or not, the
	   * function might change two member variables. {@link #type} will be changed
	   * to the corresponding pattern type; {@link #simplePattern} will record the
	   * string in it for later pattern matching if it is a simple pattern.
	   * <p>
	   * Examples: <blockquote>
	   *
	   * <pre>
	   * parseSimplePattern("%abc%") changes {@link #type} to PatternType.MIDDLE
	   * and changes {@link #simplePattern} to "abc"
	   * parseSimplePattern("%ab_c%") changes {@link #type} to PatternType.COMPLEX
	   * and does not change {@link #simplePattern}
	   * </pre>
	   *
	   * </blockquote>
	   *
	   * @param likePattern
	   *          the input LIKE query pattern
	   */
	  private void parseSimplePattern(String likePattern) {
	    int length = likePattern.length();
	    int beginIndex = 0;
	    int endIndex = length;
	    char lastChar = 'a';
	    String strPattern = new String();
	    type = PatternType.NONE;

	    for (int i = 0; i < length; i++) {
	      char n = likePattern.charAt(i);
	      if (n == '_') { // such as "a_b"
	        if (lastChar != '\\') { // such as "a%bc"
	          type = PatternType.COMPLEX;
	          return;
	        } else { // such as "abc\%de%"
	          strPattern += likePattern.substring(beginIndex, i - 1);
	          beginIndex = i;
	        }
	      } else if (n == '%') {
	        if (i == 0) { // such as "%abc"
	          type = PatternType.END;
	          beginIndex = 1;
	        } else if (i < length - 1) {
	          if (lastChar != '\\') { // such as "a%bc"
	            type = PatternType.COMPLEX;
	            return;
	          } else { // such as "abc\%de%"
	            strPattern += likePattern.substring(beginIndex, i - 1);
	            beginIndex = i;
	          }
	        } else {
	          if (lastChar != '\\') {
	            endIndex = length - 1;
	            if (type == PatternType.END) { // such as "%abc%"
	              type = PatternType.MIDDLE;
	            } else {
	              type = PatternType.BEGIN; // such as "abc%"
	            }
	          } else { // such as "abc\%"
	            strPattern += likePattern.substring(beginIndex, i - 1);
	            beginIndex = i;
	            endIndex = length;
	          }
	        }
	      }
	      lastChar = n;
	    }

	    strPattern += likePattern.substring(beginIndex, endIndex);
	    simplePattern.set(strPattern);
	  }

	  private static boolean find(Text s, Text sub, int startS, int endS) {
	    byte[] byteS = s.getBytes();
	    byte[] byteSub = sub.getBytes();
	    int lenSub = sub.getLength();
	    boolean match = false;
	    for (int i = startS; (i < endS - lenSub + 1) && (!match); i++) {
	      match = true;
	      for (int j = 0; j < lenSub; j++) {
	        if (byteS[j + i] != byteSub[j]) {
	          match = false;
	          break;
	        }
	      }
	    }
	    return match;
	  }

	  public BooleanWritable evaluate(Text s, Text likePattern) {
	    if (s == null || likePattern == null) {
	      return null;
	    }
	    if (!likePattern.equals(lastLikePattern)) {
	      lastLikePattern.set(likePattern);
	      String strLikePattern = likePattern.toString();

	      parseSimplePattern(strLikePattern);
	      if (type == PatternType.COMPLEX) {
	        p = Pattern.compile(likePatternToRegExp(strLikePattern));
	      }
	    }

	    if (type == PatternType.COMPLEX) {
	      Matcher m = p.matcher(s.toString());
	      result.set(m.matches());
	    } else {
	      int startS = 0;
	      int endS = s.getLength();
	      // if s is shorter than the required pattern
	      if (endS < simplePattern.getLength()) {
	        result.set(false);
	        return result;
	      }
	      switch (type) {
	      case BEGIN:
	        endS = simplePattern.getLength();
	        break;
	      case END:
	        startS = endS - simplePattern.getLength();
	        break;
	      case NONE:
	        if (simplePattern.getLength() != s.getLength()) {
	          result.set(false);
	          return result;
	        }
	        break;
	      }
	      result.set(find(s, simplePattern, startS, endS));
	    }
	    return result;
	  }

	}