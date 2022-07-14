# spring5 + velocity2 不兼容的处理
## maven 
```
<dependency>
  <groupId>com.clickpaas.arch</groupId>
  <artifactId>spring-context-velocity</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## example
https://github.com/clickpaas/light-task-scheduler/blob/1.6.9.1/lts-admin
https://github.com/clickpaas/light-task-scheduler/blob/1.6.9.1/lts-admin/src/main/resources/spring-web.xml
https://github.com/clickpaas/light-task-scheduler/blob/1.6.9.1/lts-admin/src/main/webapp/WEB-INF/web.xml

## 404 with suffix reason
```
    <mvc:annotation-driven>
        <mvc:path-matching suffix-pattern="true" />
    </mvc:annotation-driven>
```
## Encoder error
```
	<authentication-manager>
		<authentication-provider>
			<user-service>
				<user name="${fixedUsername}" password="${fixedPassword}" authorities="ROLE_USER" />
			</user-service>
			<password-encoder ref="passwordEncoder"></password-encoder>
		</authentication-provider>
	</authentication-manager>
	<beans:bean id="passwordEncoder" class="org.springframework.security.crypto.password.NoOpPasswordEncoder" factory-method="getInstance" />
```
## velocity change
$velocityCount replace with $foreach.count


