### Getting started
1. Fork and clone the repository.
2. Ensure you have the Java 8 JDK installed on your machine. If you don't, you can use
the appropriate [Amazon Correto distribution.](https://docs.aws.amazon.com/corretto/latest/corretto-8-ug/downloads-list.html)
for your operating system and architecture.
3. Install [Maven](https://maven.apache.org/). You'll need it to build the code.
4. Install [Docker](https://www.docker.com/). You'll need it to run your database.
5. Setup your development database with `./neg5.db/bin/bootstrap_local_db.sh`.
This script will create a locally running Docker container with a Postgres database running on port 9999. The
service is configured to connect to this database in the dev environment. It will also run all the required
flyway database migrations in `neg5.db/src/main/resources/migrations`.
6. Set the required environment variables listed under the "Environment Variables For Local Development" section.
7. Do a one-time install of all required libraries with `mvn clean install`.
8. Start the Service! Read the "Running the Service" section for instructions on how to do so.

### Environment Variables For Local Development
There are a handful of environment variables you need to set for the service to start correctly:
1. `NEG5_ENVIRONMENT`. This variable controls the configurations loaded into each environment Neg5 runs in and
corresponds to `Environment.java`. Set this variable to `DEV`.
2. `NEG5_JWT`. Secret used to generate tokens for user authentication. You can use
[randomkeygen](https://randomkeygen.com/) to set this variable to an appropriate value.

### Running the Service
You have a couple of options on how to start the service depending on your workflow:
1. Start the service with the provided script: `./service/bin/start_server.sh`. This
script will start the service and open a debug port on port 1044. You can attach to the debug port
with your IDE of choice.
2. Run through your IDE of choice. For example, open the `Main.java` file in Intellij and
run the main method. This will create a new run configuration that you can use to restart
the service.

### Apply Database Migrations
You can apply new database migrations after initial creation of your database by
running `./neg5.db/bin/apply_migrations.sh`. You can also choose to completely
wipe away and rebuild your local database using the `./neg5.db/bin/wipe_local_db.sh` and
`./neg5.db/bin/bootstrap_local_db.sh` scripts, respectively. 

### Get Familiar with the Tech

### Code Style and Patterns

### Submitting Changes

### Postman Testing
If you use Postman to test API endpoints, you can import this
[collection of API requests](https://www.getpostman.com/collections/166591131f6f1edca8bd) to simplify your testing
workflow.

### Deploying

To build the `neg5.service-1.0-SNAPSHOT-jar-with-dependencies.jar`
and deploy it to Heroku, run `./service/bin/deploy_heroku.sh`