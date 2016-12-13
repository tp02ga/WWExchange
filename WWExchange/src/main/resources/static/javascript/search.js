$(function()
{ // Document ready shorthand
  var $search = $('#search');// Cache the element for faster DOM searching
                              // since we are using it more than once
  original_val = $search.val(); // Get the original value to test against. We
                                // use .val() to grab value="Search"
  $search.focus(function()
  { // When the user tabs/clicks the search box.
    if ($(this).val() === original_val)
    { // If the value is still the default, in this case, "Search"
      $(this).val('');// If it is, set it to blank
    }
  }).blur(function()
  {// When the user tabs/clicks out of the input
    if ($(this).val() === '')
    {// If the value is blank (such as the user clicking in it and clicking
      // out)...
      $(this).val(original_val); // ... set back to the original value
    }
  });
  $search.keydown(function(event)
  {
    if (event.which == 13)
    {
      window.location.href = "/listings/listing?keyword=" + $(this).val();
    }
  });
  $search.keyup(function()
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