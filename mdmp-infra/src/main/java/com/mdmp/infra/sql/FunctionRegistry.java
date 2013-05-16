package com.mdmp.infra.sql;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mdmp.common.exception.MDMPException;
import com.mdmp.common.exception.UDFArgumentException;
import com.mdmp.infra.sql.udf.UDF;
import com.mdmp.infra.sql.udf.UDFLike;
import com.mdmp.infra.sql.udf.UDFOPDivide;
import com.mdmp.infra.sql.udf.UDFOPMinus;
import com.mdmp.infra.sql.udf.UDFOPMultiply;
import com.mdmp.infra.sql.udf.UDFOPPlus;
import com.mdmp.infra.sql.udf.generic.GenericUDAFEvaluator;
import com.mdmp.infra.sql.udf.generic.GenericUDAFResolver;
import com.mdmp.infra.sql.udf.generic.GenericUDF;
import com.mdmp.infra.sql.udf.generic.GenericUDFOPAnd;
import com.mdmp.infra.sql.udf.generic.GenericUDFOPEqual;
import com.mdmp.infra.sql.udf.generic.GenericUDFOPEqualOrGreaterThan;
import com.mdmp.infra.sql.udf.generic.GenericUDFOPEqualOrLessThan;
import com.mdmp.infra.sql.udf.generic.GenericUDFOPGreaterThan;
import com.mdmp.infra.sql.udf.generic.GenericUDFOPLessThan;
import com.mdmp.infra.sql.udf.generic.GenericUDFOPNot;
import com.mdmp.infra.sql.udf.generic.GenericUDFOPNotEqual;
import com.mdmp.infra.sql.udf.generic.GenericUDFOPOr;

public class FunctionRegistry {
	private static Log LOG = LogFactory.getLog("org.apache.hadoop.hive.ql.exec.FunctionRegistry");

	  /**
	   * The mapping from expression function names to expression classes.
	   */
	  static Map<String, FunctionInfo> mFunctions = new LinkedHashMap<String, FunctionInfo>();
	  static {
//	    registerUDF("concat", UDFConcat.class, false);
//	    registerUDF("substr", UDFSubstr.class, false);
//	    registerUDF("substring", UDFSubstr.class, false);
//	    registerUDF("space", UDFSpace.class, false);
//	    registerUDF("repeat", UDFRepeat.class, false);
//	    registerUDF("ascii", UDFAscii.class, false);
//	    registerUDF("lpad", UDFLpad.class, false);
//	    registerUDF("rpad", UDFRpad.class, false);
//
//	    registerGenericUDF("size", GenericUDFSize.class);
//
//	    registerUDF("round", UDFRound.class, false);
//	    registerUDF("floor", UDFFloor.class, false);
//	    registerUDF("sqrt", UDFSqrt.class, false);
//	    registerUDF("ceil", UDFCeil.class, false);
//	    registerUDF("ceiling", UDFCeil.class, false);
//	    registerUDF("rand", UDFRand.class, false);
//	    registerUDF("abs", UDFAbs.class, false);
//	    registerUDF("pmod", UDFPosMod.class, false);
//
//	    registerUDF("ln", UDFLn.class, false);
//	    registerUDF("log2", UDFLog2.class, false);
//	    registerUDF("sin", UDFSin.class, false);
//	    registerUDF("asin", UDFAsin.class, false);
//	    registerUDF("cos", UDFCos.class, false);
//	    registerUDF("acos", UDFAcos.class, false);
//	    registerUDF("log10", UDFLog10.class, false);
//	    registerUDF("log", UDFLog.class, false);
//	    registerUDF("exp", UDFExp.class, false);
//	    registerUDF("power", UDFPower.class, false);
//	    registerUDF("pow", UDFPower.class, false);
//	    registerUDF("sign", UDFSign.class, false);
//	    registerUDF("pi", UDFPI.class, false);
//	    registerUDF("degrees", UDFDegrees.class, false);
//	    registerUDF("radians", UDFRadians.class, false);
//	    registerUDF("atan", UDFAtan.class, false);
//	    registerUDF("tan", UDFTan.class, false);
//	    registerUDF("e", UDFE.class, false);
//
//	    registerUDF("conv", UDFConv.class, false);
//	    registerUDF("bin", UDFBin.class, false);
//	    registerUDF("hex", UDFHex.class, false);
//	    registerUDF("unhex", UDFUnhex.class, false);
//
//	    registerUDF("upper", UDFUpper.class, false);
//	    registerUDF("lower", UDFLower.class, false);
//	    registerUDF("ucase", UDFUpper.class, false);
//	    registerUDF("lcase", UDFLower.class, false);
//	    registerUDF("trim", UDFTrim.class, false);
//	    registerUDF("ltrim", UDFLTrim.class, false);
//	    registerUDF("rtrim", UDFRTrim.class, false);
//	    registerUDF("length", UDFLength.class, false);
//	    registerUDF("reverse", UDFReverse.class, false);
//	    registerGenericUDF("field", GenericUDFField.class);
//	    registerUDF("find_in_set", UDFFindInSet.class, false);

//	    registerUDF("like", UDFLike.class, true);
//	    registerUDF("rlike", UDFRegExp.class, true);
//	    registerUDF("regexp", UDFRegExp.class, true);
//	    registerUDF("regexp_replace", UDFRegExpReplace.class, false);
//	    registerUDF("regexp_extract", UDFRegExpExtract.class, false);
//	    registerUDF("parse_url", UDFParseUrl.class, false);
//	    registerGenericUDF("split", GenericUDFSplit.class);
//	    registerGenericUDF("str_to_map", GenericUDFStringToMap.class);
//
//	    registerUDF("positive", UDFOPPositive.class, true, "+");
//	    registerUDF("negative", UDFOPNegative.class, true, "-");
//
//	    registerUDF("day", UDFDayOfMonth.class, false);
//	    registerUDF("dayofmonth", UDFDayOfMonth.class, false);
//	    registerUDF("month", UDFMonth.class, false);
//	    registerUDF("year", UDFYear.class, false);
//	    registerUDF("hour", UDFHour.class, false);
//	    registerUDF("minute", UDFMinute.class, false);
//	    registerUDF("second", UDFSecond.class, false);
//	    registerUDF("from_unixtime", UDFFromUnixTime.class, false);
//	    registerUDF("unix_timestamp", UDFUnixTimeStamp.class, false);
//	    registerUDF("to_date", UDFDate.class, false);
//	    registerUDF("weekofyear", UDFWeekOfYear.class, false);
//
//	    registerUDF("date_add", UDFDateAdd.class, false);
//	    registerUDF("date_sub", UDFDateSub.class, false);
//	    registerUDF("datediff", UDFDateDiff.class, false);
//
//	    registerUDF("get_json_object", UDFJson.class, false);
//
//	    registerUDF("xpath_string", UDFXPathString.class, false);
//	    registerUDF("xpath_boolean", UDFXPathBoolean.class, false);
//	    registerUDF("xpath_number", UDFXPathDouble.class, false);
//	    registerUDF("xpath_double", UDFXPathDouble.class, false);
//	    registerUDF("xpath_float", UDFXPathFloat.class, false);
//	    registerUDF("xpath_long", UDFXPathLong.class, false);
//	    registerUDF("xpath_int", UDFXPathInteger.class, false);
//	    registerUDF("xpath_short", UDFXPathShort.class, false);
//	    registerGenericUDF("xpath", GenericUDFXPath.class);
//
	    registerUDF("+", UDFOPPlus.class, true);
	    registerUDF("-", UDFOPMinus.class, true);
	    registerUDF("*", UDFOPMultiply.class, true);
	    registerUDF("/", UDFOPDivide.class, true);
//	    registerUDF("%", UDFOPMod.class, true);
//	    registerUDF("div", UDFOPLongDivide.class, true);
//
//	    registerUDF("&", UDFOPBitAnd.class, true);
//	    registerUDF("|", UDFOPBitOr.class, true);
//	    registerUDF("^", UDFOPBitXor.class, true);
//	    registerUDF("~", UDFOPBitNot.class, true);
//
//	    registerGenericUDF("isnull", GenericUDFOPNull.class);
//	    registerGenericUDF("isnotnull", GenericUDFOPNotNull.class);
//
//	    registerGenericUDF("if", GenericUDFIf.class);
//	    registerGenericUDF("in", GenericUDFIn.class);
	    registerGenericUDF("and", GenericUDFOPAnd.class);
	    registerGenericUDF("or", GenericUDFOPOr.class);
	    registerGenericUDF("=", GenericUDFOPEqual.class);
	    registerGenericUDF("==", GenericUDFOPEqual.class);
	    registerGenericUDF("!=", GenericUDFOPNotEqual.class);
	    registerGenericUDF("<>", GenericUDFOPNotEqual.class);
	    registerGenericUDF("<", GenericUDFOPLessThan.class);
	    registerGenericUDF("<=", GenericUDFOPEqualOrLessThan.class);
	    registerGenericUDF(">", GenericUDFOPGreaterThan.class);
	    registerGenericUDF(">=", GenericUDFOPEqualOrGreaterThan.class);
	    registerGenericUDF("not", GenericUDFOPNot.class);
	    registerGenericUDF("!", GenericUDFOPNot.class);


	    // Aliases for Java Class Names
	    // These are used in getImplicitConvertUDFMethod
//	    registerUDF(Constants.BOOLEAN_TYPE_NAME, UDFToBoolean.class, false,
//	        UDFToBoolean.class.getSimpleName());
//	    registerUDF(Constants.TINYINT_TYPE_NAME, UDFToByte.class, false,
//	        UDFToByte.class.getSimpleName());
//	    registerUDF(Constants.SMALLINT_TYPE_NAME, UDFToShort.class, false,
//	        UDFToShort.class.getSimpleName());
//	    registerUDF(Constants.INT_TYPE_NAME, UDFToInteger.class, false,
//	        UDFToInteger.class.getSimpleName());
//	    registerUDF(Constants.BIGINT_TYPE_NAME, UDFToLong.class, false,
//	        UDFToLong.class.getSimpleName());
//	    registerUDF(Constants.FLOAT_TYPE_NAME, UDFToFloat.class, false,
//	        UDFToFloat.class.getSimpleName());
//	    registerUDF(Constants.DOUBLE_TYPE_NAME, UDFToDouble.class, false,
//	        UDFToDouble.class.getSimpleName());
//	    registerUDF(Constants.STRING_TYPE_NAME, UDFToString.class, false,
//	        UDFToString.class.getSimpleName());

	    // Aggregate functions
//	    registerGenericUDAF("max", new GenericUDAFMax());
//	    registerGenericUDAF("min", new GenericUDAFMin());
//
//	    registerGenericUDAF("sum", new GenericUDAFSum());
//	    registerGenericUDAF("count", new GenericUDAFCount());
//	    registerGenericUDAF("avg", new GenericUDAFAverage());
//
//	    registerGenericUDAF("std", new GenericUDAFStd());
//	    registerGenericUDAF("stddev", new GenericUDAFStd());
//	    registerGenericUDAF("stddev_pop", new GenericUDAFStd());
//	    registerGenericUDAF("stddev_samp", new GenericUDAFStdSample());
//	    registerGenericUDAF("variance", new GenericUDAFVariance());
//	    registerGenericUDAF("var_pop", new GenericUDAFVariance());
//	    registerGenericUDAF("var_samp", new GenericUDAFVarianceSample());
//	    registerGenericUDAF("covar_pop", new GenericUDAFCovariance());
//	    registerGenericUDAF("covar_samp", new GenericUDAFCovarianceSample());
//	    registerGenericUDAF("corr", new GenericUDAFCorrelation());
//	    registerGenericUDAF("histogram_numeric", new GenericUDAFHistogramNumeric());
//	    registerGenericUDAF("percentile_approx", new GenericUDAFPercentileApprox());
//	    registerGenericUDAF("collect_set", new GenericUDAFCollectSet());
//
//	    registerGenericUDAF("ngrams", new GenericUDAFnGrams());
//	    registerGenericUDAF("context_ngrams", new GenericUDAFContextNGrams());
//
//	    registerUDAF("percentile", UDAFPercentile.class);


	    // Generic UDFs
//	    registerGenericUDF("reflect", GenericUDFReflect.class);
//
//	    registerGenericUDF("array", GenericUDFArray.class);
//	    registerGenericUDF("map", GenericUDFMap.class);
//	    registerGenericUDF("struct", GenericUDFStruct.class);
//	    registerGenericUDF("create_union", GenericUDFUnion.class);
//
//	    registerGenericUDF("case", GenericUDFCase.class);
//	    registerGenericUDF("when", GenericUDFWhen.class);
//	    registerGenericUDF("hash", GenericUDFHash.class);
//	    registerGenericUDF("coalesce", GenericUDFCoalesce.class);
//	    registerGenericUDF("index", GenericUDFIndex.class);
//	    registerGenericUDF("instr", GenericUDFInstr.class);
//	    registerGenericUDF("locate", GenericUDFLocate.class);
//	    registerGenericUDF("elt", GenericUDFElt.class);
//	    registerGenericUDF("concat_ws", GenericUDFConcatWS.class);
//	    registerGenericUDF("array_contains", GenericUDFArrayContains.class);
//	    registerGenericUDF("sentences", GenericUDFSentences.class);
//	    registerGenericUDF("map_keys", GenericUDFMapKeys.class);
//	    registerGenericUDF("map_values", GenericUDFMapValues.class);

	    // Generic UDTF's
//	    registerGenericUDTF("explode", GenericUDTFExplode.class);
//	    registerGenericUDTF("json_tuple", GenericUDTFJSONTuple.class);
//	    registerGenericUDTF("parse_url_tuple", GenericUDTFParseUrlTuple.class);
	  }

	  public static void registerTemporaryUDF(String functionName,
	      Class<? extends UDF> UDFClass, boolean isOperator) {
	    registerUDF(false, functionName, UDFClass, isOperator);
	  }

	  static void registerUDF(String functionName, Class<? extends UDF> UDFClass,
	      boolean isOperator) {
	    registerUDF(true, functionName, UDFClass, isOperator);
	  }

	  public static void registerUDF(boolean isNative, String functionName,
	      Class<? extends UDF> UDFClass, boolean isOperator) {
	    registerUDF(isNative, functionName, UDFClass, isOperator, functionName
	        .toLowerCase());
	  }

	  public static void registerUDF(String functionName,
	      Class<? extends UDF> UDFClass, boolean isOperator, String displayName) {
	    registerUDF(true, functionName, UDFClass, isOperator, displayName);
	  }

	  public static void registerUDF(boolean isNative, String functionName,
	      Class<? extends UDF> UDFClass, boolean isOperator, String displayName) {
	    if (UDF.class.isAssignableFrom(UDFClass)) {
	      FunctionInfo fI = new FunctionInfo(isNative, displayName,
	          new GenericUDFBridge(displayName, isOperator, UDFClass));
	      mFunctions.put(functionName.toLowerCase(), fI);
	    } else {
	      throw new RuntimeException("Registering UDF Class " + UDFClass
	          + " which does not extend " + UDF.class);
	    }
	  }

	  public static void registerTemporaryGenericUDF(String functionName,
	      Class<? extends GenericUDF> genericUDFClass) {
	    registerGenericUDF(false, functionName, genericUDFClass);
	  }

	  static void registerGenericUDF(String functionName,
	      Class<? extends GenericUDF> genericUDFClass) {
	    registerGenericUDF(true, functionName, genericUDFClass);
	  }

	  public static void registerGenericUDF(boolean isNative, String functionName,
	      Class<? extends GenericUDF> genericUDFClass) {
	    if (GenericUDF.class.isAssignableFrom(genericUDFClass)) {
	      FunctionInfo fI = new FunctionInfo(isNative, functionName,
	          (GenericUDF) ReflectionUtils.newInstance(genericUDFClass, null));
	      mFunctions.put(functionName.toLowerCase(), fI);
	    } else {
	      throw new RuntimeException("Registering GenericUDF Class "
	          + genericUDFClass + " which does not extend " + GenericUDF.class);
	    }
	  }

	  public static void registerTemporaryGenericUDTF(String functionName,
	      Class<? extends GenericUDTF> genericUDTFClass) {
	    registerGenericUDTF(false, functionName, genericUDTFClass);
	  }

	  static void registerGenericUDTF(String functionName,
	      Class<? extends GenericUDTF> genericUDTFClass) {
	    registerGenericUDTF(true, functionName, genericUDTFClass);
	  }

	  public static void registerGenericUDTF(boolean isNative, String functionName,
	      Class<? extends GenericUDTF> genericUDTFClass) {
	    if (GenericUDTF.class.isAssignableFrom(genericUDTFClass)) {
	      FunctionInfo fI = new FunctionInfo(isNative, functionName,
	          (GenericUDTF) ReflectionUtils.newInstance(genericUDTFClass, null));
	      mFunctions.put(functionName.toLowerCase(), fI);
	    } else {
	      throw new RuntimeException("Registering GenericUDTF Class "
	          + genericUDTFClass + " which does not extend " + GenericUDTF.class);
	    }
	  }

	  public static FunctionInfo getFunctionInfo(String functionName) {
	    return mFunctions.get(functionName.toLowerCase());
	  }

	  /**
	   * Returns a set of registered function names. This is used for the CLI
	   * command "SHOW FUNCTIONS;"
	   *
	   * @return set of strings contains function names
	   */
	  public static Set<String> getFunctionNames() {
	    return mFunctions.keySet();
	  }

	  /**
	   * Returns a set of registered function names. This is used for the CLI
	   * command "SHOW FUNCTIONS 'regular expression';" Returns an empty set when
	   * the regular expression is not valid.
	   *
	   * @param funcPatternStr
	   *          regular expression of the interested function names
	   * @return set of strings contains function names
	   */
	  public static Set<String> getFunctionNames(String funcPatternStr) {
	    Set<String> funcNames = new TreeSet<String>();
	    Pattern funcPattern = null;
	    try {
	      funcPattern = Pattern.compile(funcPatternStr);
	    } catch (PatternSyntaxException e) {
	      return funcNames;
	    }
	    for (String funcName : mFunctions.keySet()) {
	      if (funcPattern.matcher(funcName).matches()) {
	        funcNames.add(funcName);
	      }
	    }
	    return funcNames;
	  }

	  /**
	   * Returns the set of synonyms of the supplied function.
	   *
	   * @param funcName
	   *          the name of the function
	   * @return Set of synonyms for funcName
	   */
	  public static Set<String> getFunctionSynonyms(String funcName) {
	    Set<String> synonyms = new HashSet<String>();

	    FunctionInfo funcInfo = getFunctionInfo(funcName);
	    if (null == funcInfo) {
	      return synonyms;
	    }

	    Class<?> funcClass = funcInfo.getFunctionClass();
	    for (String name : mFunctions.keySet()) {
	      if (name.equals(funcName)) {
	        continue;
	      }
	      if (mFunctions.get(name).getFunctionClass().equals(funcClass)) {
	        synonyms.add(name);
	      }
	    }

	    return synonyms;
	  }

	  static Map<TypeInfo, Integer> numericTypes = new HashMap<TypeInfo, Integer>();
	  static List<TypeInfo> numericTypeList = new ArrayList<TypeInfo>();

	  static void registerNumericType(String typeName, int level) {
	    TypeInfo t = TypeInfoFactory.getPrimitiveTypeInfo(typeName);
	    numericTypeList.add(t);
	    numericTypes.put(t, level);
	  }

	  static {
	    registerNumericType(Constants.TINYINT_TYPE_NAME, 1);
	    registerNumericType(Constants.SMALLINT_TYPE_NAME, 2);
	    registerNumericType(Constants.INT_TYPE_NAME, 3);
	    registerNumericType(Constants.BIGINT_TYPE_NAME, 4);
	    registerNumericType(Constants.FLOAT_TYPE_NAME, 5);
	    registerNumericType(Constants.DOUBLE_TYPE_NAME, 6);
	    registerNumericType(Constants.STRING_TYPE_NAME, 7);
	  }

	  /**
	   * Find a common class that objects of both TypeInfo a and TypeInfo b can
	   * convert to. This is used for comparing objects of type a and type b.
	   *
	   * When we are comparing string and double, we will always convert both of
	   * them to double and then compare.
	   *
	   * @return null if no common class could be found.
	   */
	  public static TypeInfo getCommonClassForComparison(TypeInfo a, TypeInfo b) {
	    // If same return one of them
	    if (a.equals(b)) {
	      return a;
	    }

	    for (TypeInfo t : numericTypeList) {
	      if (FunctionRegistry.implicitConvertable(a, t)
	          && FunctionRegistry.implicitConvertable(b, t)) {
	        return t;
	      }
	    }
	    return null;
	  }

	  /**
	   * Find a common class that objects of both TypeInfo a and TypeInfo b can
	   * convert to. This is used for places other than comparison.
	   *
	   * The common class of string and double is string.
	   *
	   * @return null if no common class could be found.
	   */
	  public static TypeInfo getCommonClass(TypeInfo a, TypeInfo b) {
	    Integer ai = numericTypes.get(a);
	    Integer bi = numericTypes.get(b);
	    if (ai == null || bi == null) {
	      // If either is not a numeric type, return null.
	      return null;
	    }
	    return (ai > bi) ? a : b;
	  }

	  /**
	   * Returns whether it is possible to implicitly convert an object of Class
	   * from to Class to.
	   */
	  public static boolean implicitConvertable(TypeInfo from, TypeInfo to) {
	    if (from.equals(to)) {
	      return true;
	    }
	    // Allow implicit String to Double conversion
	    if (from.equals(TypeInfoFactory.stringTypeInfo)
	        && to.equals(TypeInfoFactory.doubleTypeInfo)) {
	      return true;
	    }
	    // Void can be converted to any type
	    if (from.equals(TypeInfoFactory.voidTypeInfo)) {
	      return true;
	    }

	    // Allow implicit conversion from Byte -> Integer -> Long -> Float -> Double
	    // -> String
	    Integer f = numericTypes.get(from);
	    Integer t = numericTypes.get(to);
	    if (f == null || t == null) {
	      return false;
	    }
	    if (f.intValue() > t.intValue()) {
	      return false;
	    }
	    return true;
	  }

	  /**
	   * Get the GenericUDAF evaluator for the name and argumentClasses.
	   *
	   * @param name
	   *          the name of the UDAF
	   * @param argumentTypeInfos
	   * @return The UDAF evaluator
	   */
	  @SuppressWarnings("deprecation")
	  public static GenericUDAFEvaluator getGenericUDAFEvaluator(String name,
	      List<TypeInfo> argumentTypeInfos, boolean isDistinct,
	      boolean isAllColumns) throws SemanticException {

	    GenericUDAFResolver udafResolver = getGenericUDAFResolver(name);
	    if (udafResolver == null) {
	      return null;
	    }

	    TypeInfo[] parameters = new TypeInfo[argumentTypeInfos.size()];
	    for (int i = 0; i < parameters.length; i++) {
	      parameters[i] = argumentTypeInfos.get(i);
	    }

	    GenericUDAFEvaluator udafEvaluator = null;
	    if (udafResolver instanceof GenericUDAFResolver2) {
	      GenericUDAFParameterInfo paramInfo =
	          new SimpleGenericUDAFParameterInfo(
	              parameters, isDistinct, isAllColumns);
	      udafEvaluator =
	          ((GenericUDAFResolver2) udafResolver).getEvaluator(paramInfo);
	    } else {
	      udafEvaluator = udafResolver.getEvaluator(parameters);
	    }
	    return udafEvaluator;
	  }

	  /**
	   * This method is shared between UDFRegistry and UDAFRegistry. methodName will
	   * be "evaluate" for UDFRegistry, and "aggregate"/"evaluate"/"evaluatePartial"
	   * for UDAFRegistry.
	   * @throws UDFArgumentException
	   */
	  public static <T> Method getMethodInternal(Class<? extends T> udfClass,
	      String methodName, boolean exact, List<TypeInfo> argumentClasses)
	      throws UDFArgumentException {

	    List<Method> mlist = new ArrayList<Method>();

	    for (Method m : Arrays.asList(udfClass.getMethods())) {
	      if (m.getName().equals(methodName)) {
	        mlist.add(m);
	      }
	    }

	    return getMethodInternal(udfClass, mlist, exact, argumentClasses);
	  }

	  public static void registerTemporaryGenericUDAF(String functionName,
	      GenericUDAFResolver genericUDAFResolver) {
	    registerGenericUDAF(false, functionName, genericUDAFResolver);
	  }

	  static void registerGenericUDAF(String functionName,
	      GenericUDAFResolver genericUDAFResolver) {
	    registerGenericUDAF(true, functionName, genericUDAFResolver);
	  }

	  public static void registerGenericUDAF(boolean isNative, String functionName,
	      GenericUDAFResolver genericUDAFResolver) {
	    mFunctions.put(functionName.toLowerCase(), new FunctionInfo(isNative,
	        functionName.toLowerCase(), genericUDAFResolver));
	  }

	  public static void registerTemporaryUDAF(String functionName,
	      Class<? extends UDAF> udafClass) {
	    registerUDAF(false, functionName, udafClass);
	  }

	  static void registerUDAF(String functionName, Class<? extends UDAF> udafClass) {
	    registerUDAF(true, functionName, udafClass);
	  }

	  public static void registerUDAF(boolean isNative, String functionName,
	      Class<? extends UDAF> udafClass) {
	    mFunctions.put(functionName.toLowerCase(), new FunctionInfo(isNative,
	        functionName.toLowerCase(), new GenericUDAFBridge(
	        (UDAF) ReflectionUtils.newInstance(udafClass, null))));
	  }

	  public static void unregisterTemporaryUDF(String functionName) throws MDMPException {
	    FunctionInfo fi = mFunctions.get(functionName.toLowerCase());
	    if (fi != null) {
	      if (!fi.isNative()) {
	        mFunctions.remove(functionName.toLowerCase());
	      } else {
	        throw new MDMPException("Function " + functionName
	            + " is hive native, it can't be dropped");
	      }
	    }
	  }

	  public static GenericUDAFResolver getGenericUDAFResolver(String functionName) {
	    if (LOG.isDebugEnabled()) {
	      LOG.debug("Looking up GenericUDAF: " + functionName);
	    }
	    FunctionInfo finfo = mFunctions.get(functionName.toLowerCase());
	    if (finfo == null) {
	      return null;
	    }
	    GenericUDAFResolver result = finfo.getGenericUDAFResolver();
	    return result;
	  }

	  public static Object invoke(Method m, Object thisObject, Object... arguments)
	      throws MDMPException {
	    Object o;
	    try {
	      o = m.invoke(thisObject, arguments);
	    } catch (Exception e) {
	      String thisObjectString = "" + thisObject + " of class "
	          + (thisObject == null ? "null" : thisObject.getClass().getName());

	      StringBuilder argumentString = new StringBuilder();
	      if (arguments == null) {
	        argumentString.append("null");
	      } else {
	        argumentString.append("{");
	        for (int i = 0; i < arguments.length; i++) {
	          if (i > 0) {
	            argumentString.append(", ");
	          }
	          if (arguments[i] == null) {
	            argumentString.append("null");
	          } else {
	            argumentString.append("" + arguments[i] + ":"
	                + arguments[i].getClass().getName());
	          }
	        }
	        argumentString.append("} of size " + arguments.length);
	      }

	      throw new MDMPException("Unable to execute method " + m + " "
	          + " on object " + thisObjectString + " with arguments "
	          + argumentString.toString(), e);
	    }
	    return o;
	  }

	  /**
	   * Returns -1 if passed does not match accepted. Otherwise return the cost
	   * (usually 0 for no conversion and 1 for conversion).
	   */
	  public static int matchCost(TypeInfo argumentPassed,
	      TypeInfo argumentAccepted, boolean exact) {
	    if (argumentAccepted.equals(argumentPassed)) {
	      // matches
	      return 0;
	    }
	    if (argumentPassed.equals(TypeInfoFactory.voidTypeInfo)) {
	      // passing null matches everything
	      return 0;
	    }
	    if (argumentPassed.getCategory().equals(Category.LIST)
	        && argumentAccepted.getCategory().equals(Category.LIST)) {
	      // lists are compatible if and only-if the elements are compatible
	      TypeInfo argumentPassedElement = ((ListTypeInfo) argumentPassed)
	          .getListElementTypeInfo();
	      TypeInfo argumentAcceptedElement = ((ListTypeInfo) argumentAccepted)
	          .getListElementTypeInfo();
	      return matchCost(argumentPassedElement, argumentAcceptedElement, exact);
	    }
	    if (argumentPassed.getCategory().equals(Category.MAP)
	        && argumentAccepted.getCategory().equals(Category.MAP)) {
	      // lists are compatible if and only-if the elements are compatible
	      TypeInfo argumentPassedKey = ((MapTypeInfo) argumentPassed)
	          .getMapKeyTypeInfo();
	      TypeInfo argumentAcceptedKey = ((MapTypeInfo) argumentAccepted)
	          .getMapKeyTypeInfo();
	      TypeInfo argumentPassedValue = ((MapTypeInfo) argumentPassed)
	          .getMapValueTypeInfo();
	      TypeInfo argumentAcceptedValue = ((MapTypeInfo) argumentAccepted)
	          .getMapValueTypeInfo();
	      int cost1 = matchCost(argumentPassedKey, argumentAcceptedKey, exact);
	      int cost2 = matchCost(argumentPassedValue, argumentAcceptedValue, exact);
	      if (cost1 < 0 || cost2 < 0) {
	        return -1;
	      }
	      return Math.max(cost1, cost2);
	    }

	    if (argumentAccepted.equals(TypeInfoFactory.unknownTypeInfo)) {
	      // accepting Object means accepting everything,
	      // but there is a conversion cost.
	      return 1;
	    }
	    if (!exact && implicitConvertable(argumentPassed, argumentAccepted)) {
	      return 1;
	    }

	    return -1;
	  }

	  /**
	   * Gets the closest matching method corresponding to the argument list from a
	   * list of methods.
	   *
	   * @param mlist
	   *          The list of methods to inspect.
	   * @param exact
	   *          Boolean to indicate whether this is an exact match or not.
	   * @param argumentsPassed
	   *          The classes for the argument.
	   * @return The matching method.
	   */
	  public static Method getMethodInternal(Class<?> udfClass, List<Method> mlist, boolean exact,
	      List<TypeInfo> argumentsPassed) throws UDFArgumentException {

	    // result
	    List<Method> udfMethods = new ArrayList<Method>();
	    // The cost of the result
	    int leastConversionCost = Integer.MAX_VALUE;

	    for (Method m : mlist) {
	      List<TypeInfo> argumentsAccepted = TypeInfoUtils.getParameterTypeInfos(m,
	          argumentsPassed.size());
	      if (argumentsAccepted == null) {
	        // null means the method does not accept number of arguments passed.
	        continue;
	      }

	      boolean match = (argumentsAccepted.size() == argumentsPassed.size());
	      int conversionCost = 0;

	      for (int i = 0; i < argumentsPassed.size() && match; i++) {
	        int cost = matchCost(argumentsPassed.get(i), argumentsAccepted.get(i),
	            exact);
	        if (cost == -1) {
	          match = false;
	        } else {
	          conversionCost += cost;
	        }
	      }
	      if (LOG.isDebugEnabled()) {
	        LOG.debug("Method " + (match ? "did" : "didn't") + " match: passed = "
	                  + argumentsPassed + " accepted = " + argumentsAccepted +
	                  " method = " + m);
	      }
	      if (match) {
	        // Always choose the function with least implicit conversions.
	        if (conversionCost < leastConversionCost) {
	          udfMethods.clear();
	          udfMethods.add(m);
	          leastConversionCost = conversionCost;
	          // Found an exact match
	          if (leastConversionCost == 0) {
	            break;
	          }
	        } else if (conversionCost == leastConversionCost) {
	          // Ambiguous call: two methods with the same number of implicit
	          // conversions
	          udfMethods.add(m);
	          // Don't break! We might find a better match later.
	        } else {
	          // do nothing if implicitConversions > leastImplicitConversions
	        }
	      }
	    }

	    if (udfMethods.size() == 0) {
	      // No matching methods found
	      throw new NoMatchingMethodException(udfClass, argumentsPassed, mlist);
	    }
	    if (udfMethods.size() > 1) {
	      // Ambiguous method found
	      throw new AmbiguousMethodException(udfClass, argumentsPassed, mlist);
	    }
	    return udfMethods.get(0);
	  }

	  /**
	   * A shortcut to get the "index" GenericUDF. This is used for getting elements
	   * out of array and getting values out of map.
	   */
	  public static GenericUDF getGenericUDFForIndex() {
	    return FunctionRegistry.getFunctionInfo("index").getGenericUDF();
	  }

	  /**
	   * A shortcut to get the "and" GenericUDF.
	   */
	  public static GenericUDF getGenericUDFForAnd() {
	    return FunctionRegistry.getFunctionInfo("and").getGenericUDF();
	  }

	  /**
	   * Create a copy of an existing GenericUDF.
	   */
	  public static GenericUDF cloneGenericUDF(GenericUDF genericUDF) {
	    if (null == genericUDF) {
	      return null;
	    }

	    if (genericUDF instanceof GenericUDFBridge) {
	      GenericUDFBridge bridge = (GenericUDFBridge) genericUDF;
	      return new GenericUDFBridge(bridge.getUdfName(), bridge.isOperator(),
	          bridge.getUdfClass());
	    }

	    return (GenericUDF) ReflectionUtils
	        .newInstance(genericUDF.getClass(), null);
	  }

	  /**
	   * Create a copy of an existing GenericUDTF.
	   */
	  public static GenericUDTF cloneGenericUDTF(GenericUDTF genericUDTF) {
	    if (null == genericUDTF) {
	      return null;
	    }
	    return (GenericUDTF) ReflectionUtils.newInstance(genericUDTF.getClass(),
	        null);
	  }

	  /**
	   * Get the UDF class from an exprNodeDesc. Returns null if the exprNodeDesc
	   * does not contain a UDF class.
	   */
	  private static Class<? extends GenericUDF> getGenericUDFClassFromExprDesc(ExprNodeDesc desc) {
	    if (!(desc instanceof ExprNodeGenericFuncDesc)) {
	      return null;
	    }
	    ExprNodeGenericFuncDesc genericFuncDesc = (ExprNodeGenericFuncDesc) desc;
	    return genericFuncDesc.getGenericUDF().getClass();
	  }

	  /**
	   * Get the UDF class from an exprNodeDesc. Returns null if the exprNodeDesc
	   * does not contain a UDF class.
	   */
	  private static Class<? extends UDF> getUDFClassFromExprDesc(ExprNodeDesc desc) {
	    if (!(desc instanceof ExprNodeGenericFuncDesc)) {
	      return null;
	    }
	    ExprNodeGenericFuncDesc genericFuncDesc = (ExprNodeGenericFuncDesc) desc;
	    if (!(genericFuncDesc.getGenericUDF() instanceof GenericUDFBridge)) {
	      return null;
	    }
	    GenericUDFBridge bridge = (GenericUDFBridge) (genericFuncDesc
	        .getGenericUDF());
	    return bridge.getUdfClass();
	  }

	  /**
	   * Returns whether a GenericUDF is deterministic or not.
	   */
	  public static boolean isDeterministic(GenericUDF genericUDF) {
	    UDFType genericUDFType = genericUDF.getClass().getAnnotation(UDFType.class);
	    if (genericUDFType != null && genericUDFType.deterministic() == false) {
	      return false;
	    }

	    if (genericUDF instanceof GenericUDFBridge) {
	      GenericUDFBridge bridge = (GenericUDFBridge) (genericUDF);
	      UDFType bridgeUDFType = bridge.getUdfClass().getAnnotation(UDFType.class);
	      if (bridgeUDFType != null && bridgeUDFType.deterministic() == false) {
	        return false;
	      }
	    }

	    return true;
	  }

	  /**
	   * Returns whether the exprNodeDesc is a node of "and", "or", "not".
	   */
	  public static boolean isOpAndOrNot(ExprNodeDesc desc) {
	    Class<? extends GenericUDF> genericUdfClass = getGenericUDFClassFromExprDesc(desc);
	    return GenericUDFOPAnd.class == genericUdfClass
	        || GenericUDFOPOr.class == genericUdfClass
	        || GenericUDFOPNot.class == genericUdfClass;
	  }

	  /**
	   * Returns whether the exprNodeDesc is a node of "and".
	   */
	  public static boolean isOpAnd(ExprNodeDesc desc) {
	    return GenericUDFOPAnd.class == getGenericUDFClassFromExprDesc(desc);
	  }

	  /**
	   * Returns whether the exprNodeDesc is a node of "or".
	   */
	  public static boolean isOpOr(ExprNodeDesc desc) {
	    return GenericUDFOPOr.class == getGenericUDFClassFromExprDesc(desc);
	  }

	  /**
	   * Returns whether the exprNodeDesc is a node of "not".
	   */
	  public static boolean isOpNot(ExprNodeDesc desc) {
	    return GenericUDFOPNot.class == getGenericUDFClassFromExprDesc(desc);
	  }

	  /**
	   * Returns whether the exprNodeDesc is a node of "positive".
	   */
	  public static boolean isOpPositive(ExprNodeDesc desc) {
	    Class<? extends UDF> udfClass = getUDFClassFromExprDesc(desc);
	    return UDFOPPositive.class == udfClass;
	  }

	  private FunctionRegistry() {
	    // prevent instantiation
	  }
}
