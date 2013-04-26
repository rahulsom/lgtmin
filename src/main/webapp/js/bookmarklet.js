javascript:(function () {
  $.getJSON("http://lgtmin.in/g", function (data) {
    console.log(data);
    var oldMessage = $("textarea[name=\"comment[body]\"]").val()
    var msg = data.imageUrl;
    $("textarea[name=\"comment[body]\"]").val(oldMessage + '\n\n' + msg);
  });
})();