### Getting started

### Deploying

To build the `neg5.service-1.0-SNAPSHOT-jar-with-dependencies.jar`
and deploy it to Heroku, run this command. This assumes you have
access to deploy through Heroku.
```
mvn install && cd service && mvn heroku:deploy
```