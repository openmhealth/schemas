package org.openmhealth.schema.support

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.support.AnnotationConsumer
import org.junit.platform.commons.JUnitException
import org.openmhealth.schema.configuration.JacksonConfiguration.objectMapper
import org.openmhealth.schema.domain.SchemaId
import java.io.IOException
import java.util.stream.Stream
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.io.path.Path


@Target(FUNCTION)
@Retention(RUNTIME)
@ArgumentsSource(DataFileArgumentsProvider::class)
annotation class DataFileSource(
    val baseDirectory: String = "../test-data",
    val schemaId: String,
    val filename: String,
)

class DataFileArgumentsProvider : ArgumentsProvider, AnnotationConsumer<DataFileSource> {

    private lateinit var path: String

    override fun accept(annotation: DataFileSource) {
        val schemaId = SchemaId.fromString(annotation.schemaId)

        path = listOf(
            schemaId.namespace,
            schemaId.name,
            schemaId.version,
            "shouldPass",
            annotation.filename
        )
            .joinToString(separator = "/", prefix = annotation.baseDirectory + "/")
    }

    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {

        val file = Path(path).toFile()
        val jsonNode = try {
            objectMapper.readTree(file)
        } catch (e: IOException) {
            throw JUnitException("File [$path] could not be read", e)
        }

        return listOf(jsonNode)
            .stream()
            .map { Arguments.of(it) }
    }
}
