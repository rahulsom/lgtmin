<% if (request.getAttribute("message") != null) { %>
  <div class="alert alert-info">
    <%= request.getAttribute("message") %>
  </div>
<% } %>

<% if (session != null && session.getAttribute("message") != null) { %>
  <div class="alert alert-info">
    <%= session.getAttribute("message") %>
    <% session.removeAttribute("message"); %>
  </div>
<% } %>
