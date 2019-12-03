package org.dziuba.properties;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.Map;
import java.util.Properties;

@Mojo(name = "read-project-properties", defaultPhase = LifecyclePhase.INITIALIZE, threadSafe = true)
public class ReadProjectProperties extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter
    private File configFile;

    @Parameter
    private String configRoot = "";

    public void execute() throws MojoExecutionException {
        getLog().info("Running config-properties-maven-plugin");

        if (!configFile.exists()) {
            throw new MojoExecutionException(configFile.getAbsolutePath() + " does not exist");
        }

        final Properties projectProperties = project.getProperties();

        Config config = loadConfig();

        for (Map.Entry<String, ConfigValue> entry: config.entrySet()) {
            projectProperties.put(entry.getKey(), entry.getValue().unwrapped());
        }
    }

    private Config loadConfig() {
        Config defaultConfig = ConfigFactory.parseFile(configFile);

        if (configRoot.isEmpty()) {
            return defaultConfig;
        } else {
            return defaultConfig.getConfig(configRoot);
        }
    }

}
