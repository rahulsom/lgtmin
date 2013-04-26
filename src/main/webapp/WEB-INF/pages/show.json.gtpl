<%
  def image = request.getAttribute("image");
  log.info("image: ${image.toJson()}");
%><%=image.toJson()%>