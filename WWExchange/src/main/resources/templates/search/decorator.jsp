<!DOCTYPE html >
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<% request.removeAttribute("targetURL"); %>
<html>
<head>
<title>Woodworking Exchange - <sitemesh:write property='title'/></title>
<meta charset="iso-8859-1" />
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
    <link href="../../css/ie6.css" rel="stylesheet" type="text/css" />
  <![endif]-->
<!--[if IE 7]>
        <link href="../../css/ie7.css" rel="stylesheet" type="text/css" />  
  <![endif]-->
  <link href="../../css/redmond/jquery-ui-1.8.19.custom.css" rel="stylesheet" type="text/css"/>
  <script type="text/javascript" src="../../javascript/jquery-1.7.2.min.js"></script>
  <script type="text/javascript" src="../../javascript/jquery-ui-1.8.19.custom.min.js"></script>
  <script type="text/javascript" src="../../javascript/common.js"></script>
  <script type="text/javascript">
    $(function(){ //Document ready shorthand
      var $search = $('#search');//Cache the element for faster DOM searching since we are using it more than once
      original_val = $search.val(); //Get the original value to test against. We use .val() to grab value="Search"
      $search.focus(function(){ //When the user tabs/clicks the search box.
        if($(this).val()===original_val){ //If the value is still the default, in this case, "Search"
          $(this).val('');//If it is, set it to blank
        }
      })
      .blur(function(){//When the user tabs/clicks out of the input
        if($(this).val()===''){//If the value is blank (such as the user clicking in it and clicking out)...
          $(this).val(original_val); //... set back to the original value
        }
      });
      $search.keydown(function (event) {
        if (event.which == 13)
        {
          window.location.href = "../listings/listing?keyword="+$(this).val();
        }
      });
      $search.keyup(function ()
      {
        if ($(this).val() == "")
        {
          $(this).css("color", "#999999");
        }
        else
        {
          $(this).css("color", "#000000"); 
        }
      });
    });
  </script>
  <sitemesh:write property='head'/>
</head>

<body>
  <div id="background">
    <div id="page">
      <div class="header">
        <div class="footer">
          <div class="body">
            <div id="sidebar">
              <a href="../../index.html"><img id="logo" src="../../images/Logo.png" style="width: 95%; margin-left: 6px;" alt="" title="" /></a>

              <ul class="navigation">
                <li><a href="../../index.html">HOME</a></li>
                <li><a href="../../about.html">ABOUT</a></li>
                <li><a href="../../listings/listing">EQUIPMENT FOR SALE</a></li>
                <li><a href="../../listings/add_listing">SELL YOUR EQUIPMENT</a></li>
                <li class="last"><a href="../../contact">CONTACT</a></li>
              </ul>
              <div class="footenote">
                <span>&copy; Copyright &copy; 2012.</span> <span>Woodworking Exchange all rights reserved</span>
              </div>
            </div>
            <sitemesh:write property='body'/>
          </div>
        </div>
        <div class="shadow">&nbsp;</div>
      </div>
    </div>
  </div>
</body>
</html>