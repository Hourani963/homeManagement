# homeManagement

## Data base (Mysql) (conception tool looping)

![image](https://user-images.githubusercontent.com/81326209/212572779-8e92d843-8bc5-401b-b757-b2c34da744ec.png)


## API (spring boot)


## FrontEnd (Angular)

# To use it
1) Download and install docker
2) create a new folder and create a file called "docker-compose.yml" in that folder
3) Copy and paste this docker compose text in your docker compose file
```
version: '3.8'

services:
  api:
    image: syfsa/homemanagement-api:v1
    ports:
    - "8080:8080"
    depends_on: 
      - db 
    environment: # Pass environment variables to the service
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/homedb                                   
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
    networks:
      - backend
      - frontend
    container_name: apiurl


  homemanagementfront:
    image: syfsa/homemanagement-front:v1
    ports:
      - "4200:4200"
    depends_on:
      - api
    networks:
      - frontend
      - backend


  db:
    image: mysql:5.7
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: homedb
      MYSQL_PASSWORD: 
    volumes:
      - db-data:/var/lib/mysql
    networks:
      - backend  
    container_name: mysqlurl

  phpmyadmin:
      image: phpmyadmin:latest
      container_name: pma
      links:
        - db
      environment:
        PMA_HOST: db
        PMA_PORT: 3306
        PMA_ARBITRARY: 1
      ports:
        - 8001:80
      networks:
      - backend


volumes:
  db-data:


networks:
  backend:
  frontend:
```

4) run "docker-compose up" in a cmd of the folder of docker-compose.yml
5) once finished successfully you can open the project on
```
localhost:4200
```
