package com.stan.gmongo.orm.config

import groovy.transform.InheritConstructors
import groovy.util.logging.Slf4j

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

import com.stan.gmongo.api.client.GMongoClientProvider.MongoClientBuilder
import com.stan.gmongo.orm.exception.ConfigurationException
import com.stan.gmongo.orm.exception.NotFoundConfigurationFileException

@InheritConstructors
@Slf4j
class GroovyConfigurationSource extends AbstractConfigurationSource {

	private final static String CONFIG_FILE_NAME = 'META-INF/config/gmongo.config'
	public final static String CONFIG_PATH_PARAM_NAME = "com.stan.gmongo.orm.config_path"

	GMongoConfiguration initialize(Map settings){
		log.info("Reading connections settings from file ${settings[CONFIG_PATH_PARAM_NAME]}")
		def classloader = Thread.currentThread().getContextClassLoader()
		URL configUrl = classloader.getResource(settings[CONFIG_PATH_PARAM_NAME])
		if(configUrl == null){
			log.error("Cannot find configuration file with path ${settings[CONFIG_PATH_PARAM_NAME]}")
			throw new NotFoundConfigurationFileException("Cannot find configuration with path ${settings[CONFIG_PATH_PARAM_NAME]}")
		}

		def shell = getShell(classloader)
		def configClosure
		try{
			configClosure = shell.evaluate('return' + new File(configUrl.file).text)
		}
		catch(e){
			log.error("Error evaluating configuration file ${settings[CONFIG_PATH_PARAM_NAME]}", e)
			throw new ConfigurationException(e)
		}

		def builder = evaluateBuilder configClosure
		def configuration = new GMongoConfiguration()
		builder.with {
			configuration.addresses = addresses
			configuration.authentication = authentication
			configuration.options = options
			configuration.clientType = type
			configuration.database = database
		}
		configuration
	}

	private MongoClientBuilder evaluateBuilder(Closure config){
		def builder = new MongoClientBuilder()

		def clone = config.rehydrate(builder, this, this)
		clone.delegate = builder
		clone.resolveStrategy = Closure.DELEGATE_ONLY
		clone.call()
		builder
	}

	private GroovyShell getShell(ClassLoader classloader){
		def importCustomizer = new ImportCustomizer()
		importCustomizer.addStarImport 'com.stan.gmongo.api.client.GMongoClientProvider.create'
		importCustomizer.addImport 'com.stan.gmongo.api.client.GMongoClientType'
		importCustomizer.addImport 'com.stan.gmongo.api.client.GMongoClientProvider.MongoClientBuilder'

		def configuration = new CompilerConfiguration()
		configuration.addCompilationCustomizers(importCustomizer)
		new GroovyShell(classloader, configuration)
	}
}

