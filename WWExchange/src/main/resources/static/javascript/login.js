$(document).ready(function()
{
  $("#j_password").keypress(function (e) {
    if (e.which == 13)
    {
      $("#submitBtn").click();
    }
  });
  
  $("#login").click(function()
  {
    if (window.location.href.indexOf('localhost') > 0 || window.location.href.indexOf('ecosim') > 0)
    {
      window.location = "/login";
    }
    else
    {
      alert("Site is still under construction.");
    }
  });

  $("#register").click(function()
  {
    if (window.location.href.indexOf('localhost') > 0 || window.location.href.indexOf('ecosim') > 0)
    {
      window.location = "/login/register";
    }
    else
    {
      alert("Site is still under construction.");
    }
  });
});