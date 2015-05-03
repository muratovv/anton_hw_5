package com.company.Reflector;

import com.company.Utils.DefaultValues;
import com.company.Utils.StringHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

public class MethodReflector
{
	ArrayList<String> modificators = new ArrayList<>();
	ArrayList<String> exceptions = new ArrayList<>();
	ArrayList<String> paramsTypes = new ArrayList<>();
	String name = "";
	String returnType = "";

	ArrayList<String> genericTypes = new ArrayList<>();

	Executable executable;


	public MethodReflector(Executable executable)
	{
		initExecutable(executable);
	}

	private void initExecutable(Executable method)
	{
		this.executable = method;
		getParamsTypes();
		getName();
		getModificators();
		getExceptions();
		getGenericTypes();
		getReturnType();
	}

	private void getExceptions()
	{
		for(Type type : executable.getGenericExceptionTypes())
		{
			exceptions.add(type.getTypeName());
		}

	}

	private void getReturnType()
	{
		if(executable instanceof Method)
		{
			Method method = ((Method) executable);
			if(method.getGenericReturnType() instanceof ParameterizedType)
			{
				returnType = ReflectorHelper.getParametrezedType(method.getGenericReturnType());
			}
			else
			{
				returnType = method.getGenericReturnType().getTypeName();
			}
		}
		else { returnType = ""; }
	}

	private void getName()
	{
		name = executable.getName();
		if(executable instanceof Constructor)
		{
			Constructor constructor = ((Constructor) executable);
			name = constructor.getDeclaringClass().getSimpleName();
		}
	}

	private void getParamsTypes()
	{
		for(Type type : executable.getGenericParameterTypes())
		{
			String typeString;
			if(type instanceof ParameterizedType)
			{
				typeString = ReflectorHelper.getParametrezedType(type);
			}
			else
			{
				typeString = type.getTypeName();
			}
			if( type.getTypeName().contains(executable.getDeclaringClass().getPackage().getName()))
			{
				typeString = ((Class) type).getSimpleName();
			}
			paramsTypes.add(typeString);
		}
	}

	private void getModificators()
	{
		modificators = ReflectorHelper.getModificators(executable.getModifiers());
		modificators.remove(ReflectorHelper.VOLATILE);
	}

	private void getGenericTypes()
	{
		for(TypeVariable type : executable.getTypeParameters())
		{
			genericTypes.add(ReflectorHelper.getTypeVariableType(type));
		}
	}

	private String generateBodyCode()
	{
		String code = "";
		if(!modificators.contains(ReflectorHelper.ABSTRACT))
		{
			code += "{\n" + StringHelper.makeIndents(0);
			code += StringHelper.makeIndents(1) + generateMethodReturn() + generateConstructorBody();
			code += StringHelper.makeIndents(0) + "}";
		}
		return code;
	}

	private String generateMethodReturn()
	{
		if(executable instanceof Method)
		{
			Method method = ((Method) executable);
			return "return " + DefaultValues.getDefaultValue(method.getReturnType()) + ";\n";
		}
		return "";
	}

	private String generateConstructorBody()
	{
		if(executable instanceof Constructor)
		{
			Constructor constructor = ((Constructor) executable);
			String result = "super";
			ArrayList<String> params = new ArrayList<>();
			if(constructor.getDeclaringClass().getSuperclass() != null &&
					constructor.getDeclaringClass().getSuperclass().getDeclaredConstructors().length > 0)
			{
				for(TypeVariable typeVariable : constructor.getDeclaringClass().getSuperclass().getDeclaredConstructors()[0].getTypeParameters())
				{
					params.add(DefaultValues.getDefaultValue(typeVariable.getClass()));
				}
			}
			result += StringHelper.join(params, "(", "", ", ",")");
			if(params.size() == 0)
				result += "()";
			result += ";\n";
			return result;
		}
		return "";
	}

	private String generateParameters()
	{
		int counter = 0;
		String code = "(";
		for(int i = 0; i < paramsTypes.size(); i++)
		{
			String paramsType = paramsTypes.get(i);
			code += paramsType + " parameter" + ++counter;
			if(i < paramsTypes.size() - 1)
			{
				code += ", ";
			}
		}

		code += ")";
		return code;
	}

	public String BuildCode()
	{
		if(!executable.isSynthetic())
		return StringHelper.join(modificators, "", "", " ", " ") +
				StringHelper.join(genericTypes, "<", "", ", ", "> ") +
				returnType + " " +
				name + generateParameters() +
				StringHelper.join(exceptions, " throws ", "", ", ", "") +
				generateBodyCode();
		else return "";
	}

	@Override
	public String toString()
	{
		return BuildCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) { return true; }
		if(o == null || getClass() != o.getClass()) { return false; }

		MethodReflector that = (MethodReflector) o;

		if(exceptions != null ? !exceptions.equals(that.exceptions) : that.exceptions != null)
		{ return false; }
		if(genericTypes != null ? !genericTypes.equals(that.genericTypes) : that.genericTypes != null)
		{ return false; }
		if(modificators != null ? !modificators.equals(that.modificators) : that.modificators != null)
		{ return false; }
		if(name != null ? !name.equals(that.name) : that.name != null) { return false; }
		if(paramsTypes != null ? !paramsTypes.equals(that.paramsTypes) : that.paramsTypes != null)
		{ return false; }
		if(returnType != null ? !returnType.equals(that.returnType) : that.returnType != null)
		{ return false; }

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = modificators != null ? modificators.hashCode() : 0;
		result = 31 * result + (exceptions != null ? exceptions.hashCode() : 0);
		result = 31 * result + (paramsTypes != null ? paramsTypes.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (returnType != null ? returnType.hashCode() : 0);
		result = 31 * result + (genericTypes != null ? genericTypes.hashCode() : 0);
		return result;
	}
}

