# wisp-resource-loader

A testable way to load resources.
See [ResourceLoader](https://github.com/cashapp/wisp/blob/main/wisp-resource-loader/src/main/kotlin/wisp/resources/ResourceLoader.kt)
for documentation.

Also see [wisp-resource-loader-testing](https://github.com/cashapp/wisp/tree/main/wisp-resource-loader-testing)
.

## Usage

```kotlin
val resourceLoader: ResourceLoader = ResourceLoader(
  mapOf(
    "classpath:" to ClasspathResourceLoaderBackend,
    "memory:" to MemoryResourceLoaderBackend(),
    "filesystem:" to FilesystemLoaderBackend,
    "environment:" to EnvironmentLoaderBackend,
  )
)

val resource = resourceLoader.utf8("classpath:/wisp/resources/ResourceLoaderTest.txt")
```
