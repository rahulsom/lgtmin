<%
  def props = request.getAttribute('image').properties
  log.info "Props: ${props}"
%>
{
  "markdown": "[![LGTM](<%=request.getAttribute('imageUrl')%>)](<%=request.getAttribute('dataUrl')%>)",
  "imageUrl": "<%=request.getAttribute('imageUrl')%>",
  "dataUrl": "<%=request.getAttribute('dataUrl')%>"
}
