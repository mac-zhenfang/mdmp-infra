package com.mdmp.infra.rule;

import java.util.logging.Level;
import java.io.*;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.logging.Logger;

import com.sun.tools.javac.*;

/*
 * Created on 2006-3-8
 * @author icerain 我的Blog: http://blog.matrix.org.cn/page/icess
 */

//定义一个接口, 用来转换各种数学符号为Java类库中的表达式.

//下面是用来计算的代码.

/**
 * 018 * 利用Simpson公式计算积分,在输入被积公式时候请注意使用如下格式. 019 * 1.只使用圆括号() , 没有别的括号可以使用.如:
 * 1/(1+sin(x)) 020 * 2.在输入超越函数的时候,变量和数值用括号扩起来 如:sin(x) 而不要写为 sinx 021 *
 * 3.在两个数或者变量相乘时候,不要省略乘号* 如:2*a 不要写为 2a 022 * 4.在写幂运算的时候,请使用如下格式: 023 *
 * 利用动态编译来计算Simpson积分,使用该方法 编程相对简单,运行效率有点慢. 024 * @author icerain 025 *
 */
public class Simpson implements IOperator {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Simpson.class
			.getName());

	private String expression = null;

	private String variable = null;

	private String[] variableValue = new String[3];

	// private static Main javac = new Main();

	public Simpson() {
		logger.setLevel(Level.WARNING);
		init();
	}

	/** 初始化用户输入,为技术Simpson积分做准备. */
	private void init() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入函数表达式 如 1+sin(a) + cos(a)/a :");
		// String input = scanner.nextLine();
		// 读入被积函数的表达式
		expression = scanner.nextLine().trim().toLowerCase();
		System.out.println("请输入变量字符 如 a :");
		// 读入变量字符
		variable = scanner.nextLine().trim().toLowerCase();

		// 处理多元函数 目前不实现该功能
		// String[] tempVars = tempVar.split(" ");
		// for(int i = 0; i < tempVars.length; i ++) {
		// variable[i] = tempVars[i];
		// }

		System.out.println("请输入积分区间和结点数 如 2 5.4 10 :");
		// 读取复合Simpson公式的积分参数
		String tempValue = scanner.nextLine().trim();
		String[] tempValues = tempValue.split(" ");
		for (int i = 0; i < tempValues.length; i++) {
			variableValue[i] = tempValues[i];
		}

	}

	/** 计算 Simpson积分的值 */
	public double getSimpsonValue() {
		// 保存中间结果
		double value1 = 0;
		double value2 = 0;
		double tempValue = 0;
		int i = 0;
		// 解析输入的积分参数值
		int n = Integer.parseInt(variableValue[2]);
		double a = Double.parseDouble(variableValue[0]);
		double b = Double.parseDouble(variableValue[1]);
		double h = (b - a) / n;
		// 计算value1
		for (i = 0; i < n; i++) {
			tempValue = a + (i + 0.5) * h;
			String code = getSourceCode(expression, getVariable(),
					Double.toString(tempValue));
			try {
				value1 += run(compile(code));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				if (logger.isLoggable(Level.INFO)) {
					logger.info("something is wrong");
				}
			}
		}
		// 计算value2
		for (i = 1; i < n; i++) {
			tempValue = a + i * h;
			String code = getSourceCode(expression, getVariable(),
					Double.toString(tempValue));
			try {
				value2 += run(compile(code));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if (logger.isLoggable(Level.INFO)) {
					logger.info("something is wrong");
				}
			}
		}

		// 计算f(a) f(b) 的函数值
		double valueA = getFunctionValue(a);
		double valueB = getFunctionValue(b);
		// 计算Simpson公式的值
		double resultValue = (valueA + valueB + 4 * value1 + 2 * value2) * h
				/ 6;

		return resultValue;
	}

	// 计算F(a) 的值
	private double getFunctionValue(double varValue) {
		String code = getSourceCode(expression, getVariable(),
				Double.toString(varValue));
		double result = 0;
		try {
			result = run(compile(code));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (logger.isLoggable(Level.INFO)) {
				logger.info("something is wrong");
			}
		}
		return result;
	}

	/**
	 * 154 * 得到用户输入表达式转换为Java中的可计算表达式的函数 155 * @param ex 输入的表达式 如: 1/(1 +
	 * sin(x)) 156 * @param var 表达式中的变量 如: x
	 * 
	 * @param value
	 *            变量的取值 如: 4.3
	 * @return Java中可以直接计算的表达式 如: 1/(1 + Math.sin(x)) 159
	 */
	private String getSourceCode(String ex, String var, String value) {
		String expression = ex;
		// 计算多个变量的函数的时候使用

		expression = expression.replaceAll(var, value);

		// 处理数学符号
		if (expression.contains(SIN)) {
			expression = expression.replaceAll(SIN, J_SIN);
		} else if (expression.contains(COS)) {
			expression = expression.replaceAll(COS, J_COS);
		} else if (expression.contains(TAN)) {
			expression = expression.replaceAll(TAN, J_TAN);
		} else if (expression.contains(ASIN)) {
			expression = expression.replaceAll(ASIN, J_ASIN);
		} else if (expression.contains(ACOS)) {
			expression = expression.replaceAll(ACOS, J_ACOS);
		} else if (expression.contains(ATAN)) {
			expression = expression.replaceAll(ATAN, J_ATAN);
		} else if (expression.contains(EXP)) {
			expression = expression.replaceAll(EXP, J_EXP);
		} else if (expression.contains(LOG)) {
			expression = expression.replaceAll(LOG, J_LOG);
		} else if (expression.contains(POW)) {
			expression = expression.replaceAll(POW, J_POW);
		} else if (expression.contains(SQRT)) {
			expression = expression.replaceAll(SQRT, J_SQRT);
		} else if (expression.contains(FABS)) {
			expression = expression.replaceAll(FABS, J_FABS);
		}

		return expression;
	}

	/** 编译JavaCode，返回java文件 */
	private synchronized File compile(String code) throws Exception {
		File file;
		// 创建一个临时java源文件
		file = File.createTempFile("JavaRuntime", ".java",
				new File(System.getProperty("user.dir")));
		if (logger.isLoggable(Level.INFO)) {
			logger.info(System.getProperty("user.dir"));
		}
		// 当Jvm 退出时 删除该文件
		file.deleteOnExit();
		// 得到文件名和类名
		String filename = file.getName();
		if (logger.isLoggable(Level.INFO)) {
			logger.info("FileName: " + filename);
		}
		String classname = getClassName(filename);
		// 将代码输出到源代码文件中
		PrintWriter out = new PrintWriter(new FileOutputStream(file));
		// 动态构造一个类,用于计算
		out.write("public class " + classname + "{"
				+ "public static double main1(String[] args)" + "{");
		out.write("double result = " + code + ";");
		// 用于调试
		// out.write("System.out.println(result);");
		out.write("return new Double(result);");
		out.write("}}");
		// 关闭文件流
		out.flush();
		out.close();
		// 设置编译参数
		String[] args = new String[] { "-d", System.getProperty("user.dir"),
				filename };
		// 调试
		if (logger.isLoggable(Level.INFO)) {
			logger.info("编译参数: " + args[0]);
		}
		// Process process = Runtime.getRuntime().exec("javac " + filename);
		int status = Main.compile(args);
		// 输出运行的状态码.
		// 状态参数与对应值
		// 　　EXIT_OK 0
		// 　　EXIT_ERROR 1
		// 　　EXIT_CMDERR 2
		// 　　EXIT_SYSERR 3
		// 　　EXIT_ABNORMAL 4
		if (logger.isLoggable(Level.INFO)) {
			logger.info("Compile Status: " + status);
		}
		// System.out.println(process.getOutputStream().toString());
		return file;
	}

	/**
	 * 248 * 运行程序 如果出现Exception 则不做处理 抛出! 249 * @param file 运行的文件名 250 * @return
	 * 得到的Simpson积分公式的结果 251 * @throws Exception 抛出Exception 不作处理 252
	 */
	private synchronized double run(File file) throws Exception {
		String filename = file.getName();
		String classname = getClassName(filename);
		Double tempResult = null;
		// System.out.println("class Name: " +classname);
		// 当Jvm 退出时候 删除生成的临时文件
		new File(file.getParent(), classname + ".class").deleteOnExit();
		try {
			Class cls = Class.forName(classname);
			// System.out.println("run..");
			// 映射main1方法
			Method calculate = cls.getMethod("main1",
					new Class[] { String[].class });
			// 执行计算方法 得到计算的结果
			tempResult = (Double) calculate.invoke(null,
					new Object[] { new String[0] });
		} catch (SecurityException se) {
			System.out.println("something is wrong !!!!");
			System.out.println("请重新运行一遍");
		}
		// 返回值
		return tempResult.doubleValue();
	}

	/** 调试函数 */
	// private void debug(String msg) {
	// System.err.println(msg);
	// }

	/** 得到类的名字 */
	private String getClassName(String filename) {
		return filename.substring(0, filename.length() - 5);
	}

	// getter and setter
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String[] getVariableValue() {
		return variableValue;
	}

	public void setVariableValue(String[] variableValue) {
		this.variableValue = variableValue;
	}

	// private static Main javac = new Main();
	
	/** 主函数 */
	public static void main(String[] args) throws Exception {
		Simpson sim = new Simpson();
		System.out.println("结果如下:");
		System.out.print(sim.getSimpsonValue());
		System.exit(0);
	
	}
}