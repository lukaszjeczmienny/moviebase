# To run application type:
`gradlew run` app will use port `7777`

# API specification can be accessed under
`hostAddress:7777/api/swagger`

# To generate application JAR with all dependencies and ready to run type:
`gradlew shadowDistZip`

* It will appear inside `build/distributions` dir

# Standalone run
* Unzip distribution archive
* Enter `bin` dir, it will contain two scripts
* For Unix OS use `moviebase`
* For Windows OS use `moviebase.bat`