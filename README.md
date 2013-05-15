## PortletBox

This project provides a toolbox of utilities and tests for standards-compliant Java portlet containers.

### Maven Profiles

The parent-most pom contains Maven profiles for Liferay and Pluto:

Examples:

    mvn -P pluto clean install

    mvn -P liferay clean install liferay:deploy

In order to use these profiles, the PORTLETBOX_HOME environment variable must point to a folder that contains extracted versions of portal products. For example, if PORTLETBOX_HOME=/Users/myusername/portletbox then the following folder paths would be expected by the Maven profiles:

    /Users/myusername/portletbox/pluto-2.0.3/tomcat-7.0.21

    /Users/myusername/portletbox/liferay-portal-6.1.1/tomcat-7.0.27
