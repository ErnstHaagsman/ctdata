# determining the working directory of the bash script
here="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo $here
# navigating to the script's location
cd $here
echo -e "\n\n----------- Build Orchestrator ------------- \n\n"
gradle clean
gradle build
echo -e "\n\n--------------- Start the Orchestrator --------------\n\n"
cd build/libs
java -jar CtdataOrchestrationServer-1.0.0.jar
