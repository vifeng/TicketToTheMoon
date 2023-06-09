plugins {
	java
	war
	id("org.asciidoctor.jvm.convert") version "3.3.2"
	id("org.springframework.boot") version "3.1.0"
}
apply(plugin = "io.spring.dependency-management")


group = "com.vf"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val snippetsDir by extra { file("build/generated-snippets") }
val asciidocInputDir by extra { file("src/docs/asciidoc") }
val asciidoctorExt by configurations.creating


dependencies {
	// providedCompile(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
	asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.session:spring-session-core")
	implementation("org.modelmapper:modelmapper:2.3.8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

asciidoctorj {
	fatalWarnings(listOf(missingIncludes()))
}

tasks.withType<JavaCompile>().configureEach{
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>().configureEach{
    options.encoding = "UTF-8"
}

tasks.test {
	systemProperty("file.encoding", "UTF-8")
	useJUnitPlatform()
	outputs.dir(snippetsDir)
}

tasks.asciidoctor {
	inputs.dir(snippetsDir)
	configurations(listOf(asciidoctorExt))
	dependsOn("test")
	attributes(mapOf("snippets" to snippetsDir))
	outputOptions{
		backends ("html5")
	}
	options(mapOf("doctype" to "book"))
}

springBoot{
	mainClass.set("com.vf.tickettothemoon_BackEnd.TickettothemoonBackEndApplication")
}

tasks.jar {
	dependsOn("asciidoctor")
	from ("${snippetsDir}/html5") {
		into ("static/docs")
	}
}

tasks.war {
	dependsOn("asciidoctor")
	from ("${snippetsDir}/html5") {
		into ("static/docs")
	}
}

tasks.register<Delete>("clearAll") {
	delete("bin")
	delete (".classpath")
	delete (".gradle")
	delete (".nb-gradle")
	delete (".project")
	delete (".settings")
	delete (".vscode")
	delete (".DS_Store")
	delete (".idea")
    dependsOn(tasks.clean)
}
