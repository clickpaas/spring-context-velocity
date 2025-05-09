# spring5/spring6 + velocity2 compatible(兼容spring5/spring6 velocity2)
## maven 
add dependency for spring6:(添加spring6依赖)
```
<dependency>
    <groupId>com.clickpaas.arch</groupId>
    <artifactId>spring-context-velocity</artifactId>
    <version>6.2.0-SNAPSHOT</version>
</dependency>
```
add dependency for spring5:(添加spring5依赖)
```
<dependency>
  <groupId>com.clickpaas.arch</groupId>
  <artifactId>spring-context-velocity</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```
remove dependency:(删除依赖)
```
<dependency>
	<groupId>org.apache.velocity</groupId>
	<artifactId>velocity</artifactId>
	<version>1.x</version>
</dependency>
<dependency>
	<groupId>org.apache.velocity</groupId>
	<artifactId>velocity-tools</artifactId>
	<version>2.x</version>
</dependency>
<dependency>
    <groupId>com.alibaba.spring</groupId>
    <artifactId>spring-webmvc-velocity</artifactId>
    <version>1.4.3.18.RELEASE</version>
</dependency>
```

## example project (示例工程)
- https://github.com/clickpaas/light-task-scheduler/blob/1.6.9.1/lts-admin
- https://github.com/clickpaas/light-task-scheduler/blob/1.6.9.1/lts-admin/src/main/resources/spring-web.xml
- https://github.com/clickpaas/light-task-scheduler/blob/1.6.9.1/lts-admin/src/main/webapp/WEB-INF/web.xml

## 404 with suffix reason (404 前缀导致的问题处理)
```
    <mvc:annotation-driven>
        <mvc:path-matching suffix-pattern="true" />
    </mvc:annotation-driven>
```
## Encoder error (编码问题处理)
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
## velocity template change (velocity模板页面的改动)
$velocityCount replace with $foreach.count


##If your project failed to resolve the dependency, try to add the following repository:
```
<repositories>
    <repository>
        <id>sonatype-nexus</id>
        <url>https://oss.sonatype.org/content/repositories/releases</url>
        <releases>
            <enabled>true</enabled>
        </releases>
    </repository>
</repositories>
```