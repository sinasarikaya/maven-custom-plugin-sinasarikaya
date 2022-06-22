package com.sina.summarize;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Properties;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Developer;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "summarize", defaultPhase = LifecyclePhase.COMPILE)
public class Summarize extends AbstractMojo {
	
	@Parameter(defaultValue = "${project}", required = true)
	private MavenProject project;
	
	public void execute() throws MojoExecutionException, MojoFailureException {

		File file;
		FileWriter fileWriter;
		String projectInfo =  project.getGroupId() + "." + project.getArtifactId() + "." + project.getVersion();
		List<Dependency> dependencies = project.getDependencies();		
		List<Plugin> plugins =  project.getBuildPlugins();
		List<Developer> developers = project.getDevelopers();
		Properties properties = project.getProperties();		
		
		int developerCounter = 1;

		
		try {
			file = new File("../summarize/target/outputFile.txt");
			fileWriter = new FileWriter("../summarize/target/outputFile.txt");
			
			fileWriter.write("Project info : " + projectInfo + "\n");
			fileWriter.write("Developers : \n" );
			
			for(Developer developer :  developers) {
				fileWriter.write("Developer " + developerCounter + " Name : " + developer.getName() + "\n");
				developerCounter++;
			}
			
			fileWriter.write("Release Date : " + properties.getProperty("release.date") + "\n");
			
			fileWriter.write("Dependencies : \n" );
			for(Dependency dependency : dependencies) { 
				fileWriter.write("Dependency : " + dependency.getGroupId() + "." + dependency.getArtifactId() + "\n");
			}
			
			fileWriter.write("Plugins : \n" );
			for(Plugin plugin : plugins) { 
				fileWriter.write("Plugin : " + plugin.getArtifactId() + "\n");
			}
			
			fileWriter.close();
			
			
		} catch (Exception exception) {
			System.out.println("An unexpected error is occurred.");  
			exception.printStackTrace();  
		}
		
	}


}
