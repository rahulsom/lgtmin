<% if (request.getAttribute("message") != null) { %>
  <div class="alert">
    <%= request.getAttribute("message") %>
  </div>
<% } %>

<% if (session != null && session.getAttribute("message") != null) { %>
  <div class="alert">
    <%= session.getAttribute("message") %>
    <% session.removeAttribute("message"); %>
  </div>
<% } %>
