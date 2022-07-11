# spring5 + velocity2 不兼容的处理
## 相关xml配置:
```
<dependency>
  <groupId>com.clickpaas.arch</groupId>
  <artifactId>spring-context-velocity</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```
```aidl
    <bean id="velocityConfig"
		class="org.springframework.web.servlet.view.velocity.VelocityConfigurer">
		<property name="resourceLoaderPath">
			<value>/WEB-INF/velocity/</value>
		</property>
		<property name="velocityProperties">
			<props>
				<prop key="input.encoding">UTF-8</prop>
				<prop key="output.encoding">UTF-8</prop>
			</props>
		</property>
	</bean>

	<bean id="velocityViewResolver"
		class="org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver">
		<property name="order" value="1" />
		<property name="cache" value="false" />
		<property name="layoutUrl" value="/layout/main.vm" />
		<property name="prefix" value="/templates/" />
		<property name="suffix" value=".vm" />
		<property name="contentType" value="text/html;charset=UTF-8" />
		<property name="exposeRequestAttributes" value="true" />
		<property name="exposeSessionAttributes" value="true" />
		<property name="toolboxConfigLocation" value="/WEB-INF/velocity-toolbox.xml" />
	</bean>
		
```
```
<beans:beans xmlns="http://www.springframework.org/schema/security"
	xmlns:beans="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/security
          http://www.springframework.org/schema/security/spring-security.xsd">

	<http>
		<intercept-url pattern="/css/**" access="permitAll" />
		<intercept-url pattern="/fonts/**" access="permitAll" />
		<intercept-url pattern="/css/**" access="permitAll" />
		<intercept-url pattern="/js/**" access="permitAll" />
		<intercept-url pattern="/**" access="hasRole('ROLE_USER')" />
		<http-basic />
	</http>

	<authentication-manager>
		<authentication-provider>
			<user-service>
				<user name="${fixedUsername}" password="${fixedPassword}" authorities="ROLE_USER" />
			</user-service>
			<password-encoder ref="passwordEncoder"></password-encoder>
		</authentication-provider>
	</authentication-manager>
	<beans:bean id="passwordEncoder" class="org.springframework.security.crypto.password.NoOpPasswordEncoder" factory-method="getInstance" />

</beans:beans>
```
