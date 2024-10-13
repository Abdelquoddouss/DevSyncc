<%@ page import="com.devsync.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.devsync.model.Task" %>
<%@ include file="./Dashboard.jsp"%>
<div class="p-4 sm:ml-64">

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <!-- Lien pour ouvrir le modal -->
    <a href="#authentication-modal" class="block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
        Ajoute un Task
    </a>

    <!-- Main modal -->
    <!-- Main modal -->
    <div id="authentication-modal" class="modal hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
        <div class="relative p-4 w-full max-w-md max-h-full">
            <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                <!-- Modal Header -->
                <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                    <h3 class="text-xl font-semibold text-gray-900 dark:text-white">Creer une tache</h3>
                    <a href="#" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white">
                        <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                        </svg>
                        <span class="sr-only">Fermer le modal</span>
                    </a>
                </div>
                <!-- Modal Body -->
                <div class="p-4 md:p-5">
                    <form class="space-y-4" action="tasks" method="POST">
                        <input type="hidden" name="action" value="add">

                        <!-- Title input -->
                        <div>
                            <label for="title" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Titre de la tache</label>
                            <input type="text" name="title" id="title" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="Titre" required />
                        </div>

                        <!-- Description input -->
                        <div>
                            <label for="description" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Description</label>
                            <input type="text" name="description" id="description" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="Description" required />
                        </div>

                        <!-- Date de création input -->
                        <div>
                            <label for="creationDate" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Date de creation</label>
                            <input type="date" name="creationDate" id="creationDate" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" required />
                        </div>

                        <!-- Due Date input -->
                        <div>
                            <label for="dueDate" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Due Date</label>
                            <input type="date" name="dueDate" id="dueDate" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" required />
                        </div>

                        <!-- Assign User input -->
                        <div>
                            <label for="userId" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Assigner un utilisateur</label>
                            <select name="userId" id="userId" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" required>
                                <%
                                    List<User> users = (List<User>) request.getAttribute("users");
                                    if (users != null) {
                                        for (User user : users) {
                                %>
                                <option value="<%= user.getId() %>"><%= user.getName() %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>

                        <!-- Status input (hidden) -->
                        <input type="hidden" name="status" value="NOT_STARTED" />

                        <!-- Submit button -->
                        <button type="submit" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Ajouter la tâche</button>
                    </form>                </div>
            </div>
        </div>
    </div>





<div class="relative overflow-x-auto shadow-md sm:rounded-lg">
    <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
        <tr>
            <th scope="col" class="px-6 py-3">
                Titre
            </th>
            <th scope="col" class="px-6 py-3">
                Description
            </th>
            <th scope="col" class="px-6 py-3">
                Date de creation
            </th>
            <th scope="col" class="px-6 py-3">
                Date d echeance
            </th>
            <th scope="col" class="px-6 py-3">
                Assigne a
            </th>
            <th scope="col" class="px-6 py-3">
                Statut
            </th>
            <th scope="col" class="px-6 py-3">
                Actions
            </th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Task> tasks = (List<Task>) request.getAttribute("tasks");
            if (tasks != null) {
                for (Task task : tasks) {
        %>
        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
            <td class="px-6 py-4"><%= task.getTitle() %></td>
            <td class="px-6 py-4"><%= task.getDesctiption() %></td>
            <td class="px-6 py-4"><%= task.getCreationDate() %></td>
            <td class="px-6 py-4"><%= task.getDueDate() %></td>
            <td class="px-6 py-4"><%= task.getUser() != null ? task.getUser().getName() : "Non Assigné" %></td>
            <td class="px-6 py-4"><%= task.getStatus() %></td>
            <td class="px-6 py-4">
                <a href="tasks?action=delete&id=<%= task.getId() %>" class="text-red-600 hover:underline">Supprimer</a>
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
</div>
</body>
</html>
