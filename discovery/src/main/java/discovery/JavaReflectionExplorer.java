package discovery;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class JavaReflectionExplorer {

	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Object[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		System.out.println("Starting getClasses. packagename=" + packageName);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration resources = classLoader.getResources(path);
		ArrayList<File> dirs = new ArrayList();
		while (resources.hasMoreElements()) {
			URL resource = (URL)resources.nextElement();
			System.out.println("resource=" + resource);
			dirs.add(new File(resource.getFile()));
		}
		ArrayList classes = new ArrayList();
		for (File directory : dirs) {
			System.out.println("directory=" + directory.getAbsolutePath());
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}


	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	public static ArrayList findClasses(File directory, String packageName) throws ClassNotFoundException {
		System.out.println("Starting findClasses: " + directory + " packagename=" + packageName);
		ArrayList classes = new ArrayList();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	public static ArrayList<Method> getOneInOneOutMethods(String fullClassName, String type) {
		Class<?> thisClass = null;
		ArrayList<Method> result = new ArrayList<Method>();
		try {
			thisClass = Class.forName(fullClassName);
			System.out.println("Methods for " + thisClass.getName());
			Method methods[] = thisClass.getMethods();
			for (Method method : methods) {
				System.out.println(method.toString().replace("public static ", ""));
				if (type.contains("int-int") && method.getReturnType() == int.class && 
						method.getParameterCount() == 1 && method.getParameters()[0].getType() == int.class) {
					System.out.println("Found int-int function: " + method.toString().replace("public static ", ""));
					result.add(method);
				}
				if (type.contains("double-double") && method.getReturnType() == double.class && 
						method.getParameterCount() == 1 && method.getParameters()[0].getType() == double.class) {
					System.out.println("Found double-double function: " + method.toString().replace("public static ", ""));
					result.add(method);
				}
				if (type.contains("number-boolean") && method.getReturnType() == boolean.class && 
						method.getParameterCount() == 1 && (method.getParameters()[0].getType() == int.class || 
							method.getParameters()[0].getType() == long.class ||
							method.getParameters()[0].getType() == double.class )) {
					System.out.println("Found int-boolean function: " + method.toString().replace("public static ", ""));
					result.add(method);
				}
			}
//			System.out.println("Fields");
//			Field[] fields = thisClass.getFields();
//			for(int i = 0; i < fields.length; i++) {
//				System.out.println(fields[i].toString().replace("public static ", ""));
//			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}


	public static void main(String[] args) {
		System.out.println("Starting JavaReflectionExplorer");
		getOneInOneOutMethods("java.lang.Math", "int-int");
		getOneInOneOutMethods("java.lang.Math", "double-double");
		getOneInOneOutMethods("discovery.TimeLord", "number-boolean");

		// log(x) = y   ==> e^y = x     log10(100.0)=2.0  ==> 10^2 = 100
		double x = 100;
		System.out.println("log("+x+")=" + Math.log10(x));

	}

}
