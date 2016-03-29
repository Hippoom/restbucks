FROM java:8

COPY build/version build/libs/*.jar /opt/restbucks/ordering/

# Define mountable directories
VOLUME /opt/restbucks/ordering/config
VOLUME /opt/restbucks/ordering/logs

WORKDIR /opt/restbucks/ordering

CMD java -jar restbucks-ordering-"$(cat version)".jar

EXPOSE 8080



