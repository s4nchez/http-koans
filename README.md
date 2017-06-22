# HTTP Koans

## Running it locally

```bash
./gradlew build
java -jar build/libs/http-koans-1.0-SNAPSHOT.jar
```

Then use http://localhost:8000 to get started.

For the below exercises if you get stuck you can always have a read of the code, but its more fun to do it without looking at the Koans code.

These are short exercises so try doing them tdd style as it will help you get familiar with Http4k

## Ex. 0

- Make a hello world app using http4k

## Ex. 1.

- Make a request to the server on http://localhost:8000/1/run set the referer header to the address of your hello world app. 
  - Tip. You can do this using cUrl but you can also do this by building a Koans client using http4k.
- The Koans app will hit you back on a url, figure out url it is and what response you need to return for the koans app to be happy.

## Ex. 2

- Make a request to the server on http://localhost:8000/2/run set the referer header to the address of your hello world app. 
- The Koans app will hit you back on a url, figure out url it is and what response you need to return for the koans app to be happy.

