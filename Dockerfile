FROM java:8
COPY . /usr/src/app
WORKDIR /usr/src/app
COPY app.jar /usr/src/app
CMD ["java","-jar", "app.jar"]
