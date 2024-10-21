<%@ page import="com.devsync.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.devsync.model.Tag" %>
<%@ include file="./Dashboard.jsp"%>
<div class="p-4 sm:ml-64">

  <!-- Lien pour ouvrir le modal -->
  <a href="#authentication-modal" class="block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
    Ajoute un Tag
  </a>

  <!-- Main modal -->
  <div id="authentication-modal" class="modal hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div class="relative p-4 w-full max-w-md max-h-full">
      <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
        <!-- Modal Header -->
        <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
          <h3 class="text-xl font-semibold text-gray-900 dark:text-white">Register</h3>
          <a href="#" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white">
            <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
            </svg>
            <span class="sr-only">Fermer le modal</span>
          </a>
        </div>
        <!-- Modal Body -->
        <div class="p-4 md:p-5">
          <form class="space-y-4" action="tags" method="POST">
            <!-- Hidden action field -->
            <input type="hidden" name="action" value="add">

            <!-- Name input -->
            <div>
              <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"> nom  de tag</label>
              <input type="text" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="Nom" required />
            </div>


            <!-- Submit button -->
            <button type="submit" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Ajoutez l'utilisateur</button>
          </form>
        </div>
      </div>
    </div>
  </div>


  <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
      <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
      <tr>
        <th scope="col" class="px-6 py-3 w-[5%]">
          Name
        </th>

        <th scope="col" class="px-6 py-3 w-[20%]">
          Action
        </th>
      </tr>
      </thead>
      <tbody>
      <%
        List<Tag> tags = (List<Tag>) request.getAttribute("tags");
        if (tags != null) {
          for (Tag tag : tags) {
      %>
      <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
        <td class="px-6 py-4"><%= tag.getName() %></td>

        <td class="px-6 py-4">
          <a href="tags?action=delete&id=<%= tag.getId() %>"
             class="text-red-600 hover:underline ml-4" >
            Delete
          </a>
        </td>

      </tr>
      <%
          }
        }
      %>
      </tbody>
    </table>
  </div>

</div>
</div>
</body>
</html>
