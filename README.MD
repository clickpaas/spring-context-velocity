# spring5 + velocity2 不兼容的处理
## spring xml config:
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