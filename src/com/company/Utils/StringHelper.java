package com.company.Utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by mfv on 5/2/15.
 */
public class StringHelper
{
	public static String indent = "\t";

	public static String join(Collection<?> collection, String collectionPrefix, String prefix, String postfix, String collectionPostfix)
	{
		String result = collectionPrefix;
		if(collection.size() == 0)
		{ return ""; }
		Iterator<?> iterator = collection.iterator();
		for(int i = 0; i < collection.size(); i++)
		{
			result += prefix + iterator.next();
			if(i != collection.size() - 1)
			{
				result += postfix;
			}
		}
		result += collectionPostfix;
		return result;

	}

	public static String makeIndents(int quantity)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < quantity; i++)
		{
			sb.append(indent);
		}
		return sb.toString();
	}

	public static String makeCodeBlock(String text, int branchIndent)
	{
		String result = "";
		String branch = makeIndents(branchIndent) + "{\n";
		result += branch;
		for(String s : text.split("\n"))
		{
			result += makeIndents(branchIndent + 1) + s + "\n";
		}
		result += "\n" + makeIndents(branchIndent) + "}";
		return result;
	}
}
