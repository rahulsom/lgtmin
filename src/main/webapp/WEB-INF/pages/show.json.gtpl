<% def appUtil = request.getAttribute('appUtil') %>
<%=appUtil.patchUrl(request.getAttribute("image").toJson(), request)%>