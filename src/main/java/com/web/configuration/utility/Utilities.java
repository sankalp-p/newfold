package com.web.configuration.utility;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class Utilities  {

	String projectlocation =null;

	public String getProjectPath()
	{
		this.projectlocation = System.getProperty("user.dir");
		return this.projectlocation;
	}

	public Properties readConfigFile()
	{
	
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(this.getProjectPath()+"/src/main/java/com/web/configuration/config.properties");
			prop.load(input);
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}finally {
			if(input !=null)
			{
				try 
				{
					input.close();
				}
				catch(IOException e)
				{
					System.out.println(e.getMessage());
				}
			}
		}
		return prop;
	}
	
	public Properties readWebObjectRepoFile(String filename)
	{
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(this.getProjectPath()+"/src/main/java/com/web/objectrepository/"+filename+".properties");
			prop.load(input);
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}finally {
			if(input !=null)
			{
				try 
				{
					input.close();
				}
				catch(IOException e)
				{
					System.out.println(e.getMessage());
				}
			}
		}
		return prop;
	}

	public static Object[][] readData(String jsonFileName, String testcaseName ) throws Exception
	{
		JsonElement dataset = null;
		List<TestData> testData = null;
		Object [][] returnValue = null;
		
		JsonElement jsonData = new JsonParser().parse(new FileReader (System.getProperty("user.dir")+"/src/test/resources/testdata/"+jsonFileName+".json"));
		try
		{
			dataset = jsonData.getAsJsonObject().get(testcaseName);
			testData = new Gson().fromJson(dataset, new TypeToken<List<TestData>>(){}.getType());
			returnValue = new Object[testData.size()][1];
		}catch (NullPointerException ee)
		{
		dataset = jsonData.getAsJsonObject().get("defaultData");
		testData = new Gson().fromJson(dataset, new TypeToken<List<TestData>>(){}.getType());
		returnValue = new Object[testData.size()][1];
		}
		catch(Exception e)
		{
			throw new Exception ("Unable to read data from Json file"+ e.getMessage());
		}testData = new Gson().fromJson(dataset, new TypeToken<List<TestData>>(){}.getType());
		returnValue = new Object[testData.size()][1];
				int index= 0;
				for (Object [] each: returnValue)
				{
					each[0]= testData.get(index++);
				}
		return returnValue;
		
	}

}

