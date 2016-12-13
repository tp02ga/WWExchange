<!DOCTYPE html >
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<html>

<head>
  <title>Your Listings</title>
  <link href="/css/style.css" rel="stylesheet" type="text/css"/>
  <script type="text/javascript">
    $(document).ready(function () {
      
    });
    
    function edit (id)
    {
      window.location.href = 'add_listing?id='+id;
    }

    function deleteListing (id)
    {
      var deleteEntry = confirm("Are you sure you wish to delete this listing?");
      if (deleteEntry)
      {
        $("#action").val("del:"+id);
        $("#MyListingsCommand").submit();
      }
    }
  </script>
</head>

<body>
  <div th:replace="/login/accountBand :: accountBand"></div>
  <div id="content">
    <div class="content">
      <form:form method="POST" commandName="MyListingsCommand">
        <form:hidden path="action"/>
        <table style="padding-left: 30px;" cellspacing="5">
          <tr>
            <td colspan="5"><h1>My Listings</h1></td>
          </tr>
          <c:forEach items="${MyListingsCommand.listings}" var="listing" varStatus="status">
            <form:hidden path="listings[${status.index}].id"/>
            <form:hidden path="listings[${status.index}].machineType"/>
            <form:hidden path="listings[${status.index}].model"/>
            <form:hidden path="listings[${status.index}].price"/>
            <tr>
              <td>
                <a href="javascript:edit(${listing.id})">edit</a>
              </td>
              <td>
                <a href="javascript:deleteListing(${listing.id})">delete</a>
              </td>
              <td>
                <img src="/thumbs/${listing.id}_0.jpg" width="60px"/> 
              </td>
              <td>
                <b>Type</b>: ${listing.machineType} <br/><b>Model</b>: ${listing.model} <br/><b>Price</b>: $${listing.price}
              </td>
            </tr>
          </c:forEach>
        </table>
      </form:form>
    </div>
  </div>
</body>
</html>