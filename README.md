# rest-cache project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `rest-cache-1-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/rest-cache-1-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/rest-cache-1-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## Application info

Application hashes (SHA-256) any string that was passed by HTTP. Some of strings are stored in cahce which can be confugured. By the default the application is running on http://localhost:8080. You can access application methods by HTTP. All HTTP methods are shown below:

### initCache

This method is used to init cache. Capacity param can be any positive integer and type can be LRU or LFU string. Example of usage is shown below.

```
curl --location --request POST 'http://localhost:8080/rest/initCache' \ 
--header 'Content-Type: application/json' \ 
--data-raw '{ <
    "capacity": 5, 
    "type": "LRU" 
}'
```

### getResult

This method is used to get hash of input string. Example of usage is shown below.

`curl --location --request GET 'http://localhost:8080/rest/getResult/hello'`

### getCacheInfo

This method is used to get information about cache. Example of usage is shown below.

`curl --location --request GET 'http://localhost:8080/rest/getCacheInfo'`


