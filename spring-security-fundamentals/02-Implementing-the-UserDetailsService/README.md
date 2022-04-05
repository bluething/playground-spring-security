![spring security flow](https://github.com/bluething/playground-spring-security/blob/main/spring-security-fundamentals/images/highlevelspringsecurityflow.drawio.png?raw=true)

### Requirement

MySQL data  
```sql
CREATE TABLE user (
	id INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(45) NULL,
	password VARCHAR(45) NOT NULL,
	PRIMARY KEY(id)
);

INSERT INTO user VALUES(1, "fulan", "12345");
```

Docker command  
```shell
# pull the image
docker pull mysql

# run the image with mapping the port
sudo docker run -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -p 3307:3306 --name mysql-local mysql

# enter the shell
sudo docker exec -it mysql-local bash
```