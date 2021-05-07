# Media Store Service Functional Automated Tests
## Setup:
* Download & Install Java JDK
* Download & Install latest version of [maven](https://maven.apache.org/download.cgi)


## Running Tests From Command Line
### To run tests with default parameters from command line run:
```
mvn clean test -U -DxmlFileName=testng.xml -Denv={environment} -Dtestrail.userid={user_id} -Dtestrail.apikey={user_password}
```
The following flags can be used:

-Denvironment: The environment you wish to use (i.e. PROD, PREPROD, PREPROD_GREEN, etc.)

-DxmlFileName: Static value set to 'testng.xml'


## Test Rail Project
https://plutotv.testrail.net/index.php?/projects/overview/124

(c) 2020 Pluto Inc.
