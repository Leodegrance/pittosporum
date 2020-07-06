package com.pittosporum;


import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import io.github.yedaxia.apidocs.plugin.markdown.MarkdownDocPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.pittosporum")
public class ApplicationStarter {

    private static void initApiDocument(){
        DocsConfig config = new DocsConfig();
        config.setProjectPath("application");
        config.addJavaSrcPath("backbone/src/main/java/");
        config.addJavaSrcPath("application/src/main/java/");
        config.setProjectName("pittosporum-application");
        config.setApiVersion("1.0");
        config.setDocsPath("D:/workspace/pittosporum-api-doc");
        config.setAutoGenerate(Boolean.TRUE);
        config.addPlugin(new MarkdownDocPlugin());
        Docs.buildHtmlDocs(config);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);

        initApiDocument();
    }
    
}
