<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %> 

<portlet:defineObjects />

<p>This is portletViewMode.jsp</p>
<p><%= renderRequest.getAttribute("myInjectedBeanInfo") %></p>
<p>currentTimeFromActionRequest=<%= renderRequest.getParameter("currentTimeFromActionRequest") %>
<p>myRequestScopedBean.currentTime=${myRequestScopedBean.currentTime}</p>
<p>myRequestScopedBean.myInjectedBeanInfo=${myRequestScopedBean.myInjectedBeanInfo}</p>
<portlet:actionURL var="portletActionURL" />
<form action="${portletActionURL}" method="post">
	<input value="Invoke ACTION_PHASE" type="submit"></input>
</form>