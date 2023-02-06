package com.web.configuration.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
}

