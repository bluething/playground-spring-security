The differences between `UserDetailsManager` and `UserDetailsService`:  
- UserDetailsService is a minimum requirement needed by Spring Security.   
- UserDetailsManager has more ability, to manage the user.  
- `UserDetailsManager` basically extends `UserDetailsService`.  
- UserDetailsManager is a good example of ISP.

When we use `JdbcUserDetailsManager` then we need to provide some tables.

When we use password encoder rather than `NoOpPasswordEncoder` then we need to have same encoding-decoding password mechanism.

### Requirement

MySQL data  
```sql
CREATE TABLE users (
	username VARCHAR(45) NOT NULL,
	password TEXT NOT NULL,
	enabled INT NULL,
	PRIMARY KEY(username)
);

CREATE TABLE authorities (
	id INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(45) NOT NULL,
	authority VARCHAR(45),
	PRIMARY KEY(id)
);
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