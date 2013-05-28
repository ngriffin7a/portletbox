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

### Contributing

* If you haven't done so already, create an account at GitHub

* Visit the following page with your browser and **fork** the portletbox project to your account:
  https://github.com/ngriffin7a/portletbox

* Clone the repository to your local hard drive:

	git clone https://github.com/yourUserId/portletbox.git 
	cd portletbox

* Specify "upstream" as one of your remotes:

	git remote add upstream https://github.com/ngriffin7a/portletbox

* PLEASE KEEP THE BRANCHES OF YOUR FORK IN SYNC with upstream. For example:

	git pull --rebase upstream master

* Create a branch with a meaningful name (like an issue identifier), for example:

	git branch PORTLETSPEC3-7

* Switch to the new branch:

	git checkout PORTLETSPEC3-7

* Do your development/bugfix work in this branch, commit files, and then push to origin. For example:

	git commit -a
	git push origin PORTLETSPEC3-7

  _Note that when you commit, include the issue identifier in the comment. For example: PORTLETSPEC3-7 Added fixes_

* Click on the **branch** selection menu, and click on the branch (i.e.: PORTLETSPEC3-7)

* Send a pull request using the button at the GitHub website:
	* Click on the **Pull Request** button towards the top of the page
	* Make sure the **base repo** is set to: ngriffin7a/portletbox
	* Make sure the **base branch** is set to: master
	* Make sure the **head repo** is set to: yourUserId/portletbox
	* Make sure the **head branch** is set to: PORTLETSPEC3-7
	* Click on the **Send Pull Request** button
