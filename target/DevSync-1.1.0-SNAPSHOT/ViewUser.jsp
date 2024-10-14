<%@ page import="com.devsync.model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="com.devsync.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">

</head>
<body>
<section class="container px-4 mx-auto">
    <%
        HttpSession sessionn = request.getSession();
        User currentUser = (User) sessionn.getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>
    <div class="sm:flex sm:items-center sm:justify-between">
        <div>
            <div class="flex items-center gap-x-3">
                <h2 class="text-lg font-medium text-gray-800 dark:text-white">Customers</h2>
            </div>
            <li>
                <form action="/logout" method="post" class="inline">
                    <button type="submit" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                        <svg class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                            <path d="M10 15l-3-3h6l-3 3zm0-4H3V4h7V0h2v4h7v11H12v4h-2v-4zm2-2H3v3h9V9z"/>
                        </svg>
                        <span class="flex-1 ms-3 whitespace-nowrap">Logout</span>
                    </button>
                </form>
            </li>
        </div>

    </div>
    <a href="javascript:void(0);" onclick="openModal()" class="block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
        Ajoute un Task
    </a>

    <div id="authentication-modal" class="modal hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
        <div class="relative p-4 w-full max-w-md max-h-full">
            <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                <!-- Modal Header -->
                <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                    <h3 class="text-xl font-semibold text-gray-900 dark:text-white">Creer une tache</h3>
                    <a href="javascript:void(0);" onclick="closeModal()" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white">
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
                            <select name="userId" id="userId" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" <%= (currentUser.getUserType() == User.UserType.USER) ? "disabled" : "" %>>
                                <%
                                    if (currentUser.getUserType() == User.UserType.MANAGER) {
                                        // Si c'est un manager, afficher tous les utilisateurs
                                        List<User> users = (List<User>) request.getAttribute("users");
                                        if (users != null) {
                                            for (User user : users) {
                                %>
                                <option value="<%= user.getId() %>"><%= user.getName() %></option>
                                <%
                                        }
                                    }
                                } else {
                                    // Si c'est un user, pré-remplir avec son propre nom
                                %>
                                <option value="<%= currentUser.getId() %>" selected><%= currentUser.getName() %></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>

                        <!-- Status input (hidden) -->
                        <input type="hidden" name="status" value="NOT_STARTED" />

                        <!-- Submit button -->
                        <button type="submit" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Ajouter la tâche</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="flex flex-col mt-6">
        <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
            <div class="inline-block min-w-full py-2 align-middle md:px-6 lg:px-8">
                <div class="overflow-hidden border border-gray-200 dark:border-gray-700 md:rounded-lg">
                    <table class="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
                        <thead class="bg-gray-50 dark:bg-gray-800">
                        <tr>
                            <th scope="col" class="py-3.5 px-4 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                Title A
                            </th>
                            <th scope="col" class="px-12 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                Description
                            </th>
                            <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                Date de début
                            </th>
                            <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                Date de fin
                            </th>
                            <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                Status
                            </th>
                        </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200 dark:divide-gray-700 dark:bg-gray-900">

                        <!-- Boucle for classique en JSP pour afficher les tâches -->
                        <%
                            List<Task> tasks = (List<Task>) request.getAttribute("tasks");
                            if (tasks != null){
                            for (Task task : tasks) {

                        %>
                        <tr>
                            <td class="px-12 py-4 text-sm font-medium whitespace-nowrap">
                                <%= task.getTitle() %>
                            </td>
                            <td class="px-12 py-4 text-sm font-medium whitespace-nowrap">
                                <%= task.getDesctiption() %>
                            </td>
                            <td class="px-4 py-4 text-sm whitespace-nowrap">
                                <%= task.getCreationDate() %>
                            </td>
                            <td class="px-4 py-4 text-sm whitespace-nowrap">
                                <%= task.getDueDate() %>
                            </td>
                            <td class="px-4 py-4 text-sm whitespace-nowrap">
                                <%= task.getStatus() %>
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

    <div class="mt-6 sm:flex sm:items-center sm:justify-between ">
        <div class="text-sm text-gray-500 dark:text-gray-400">
            Page <span class="font-medium text-gray-700 dark:text-gray-100">1 of 10</span>
        </div>

        <div class="flex items-center mt-4 gap-x-4 sm:mt-0">
            <a href="#" class="flex items-center justify-center w-1/2 px-5 py-2 text-sm text-gray-700 capitalize transition-colors duration-200 bg-white border rounded-md sm:w-auto gap-x-2 hover:bg-gray-100 dark:bg-gray-900 dark:text-gray-200 dark:border-gray-700 dark:hover:bg-gray-800">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 rtl:-scale-x-100">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M6.75 15.75L3 12m0 0l3.75-3.75M3 12h18" />
                </svg>

                <span>
                    previous
                </span>
            </a>

            <a href="#" class="flex items-center justify-center w-1/2 px-5 py-2 text-sm text-gray-700 capitalize transition-colors duration-200 bg-white border rounded-md sm:w-auto gap-x-2 hover:bg-gray-100 dark:bg-gray-900 dark:text-gray-200 dark:border-gray-700 dark:hover:bg-gray-800">
                <span>
                    Next
                </span>

                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 rtl:-scale-x-100">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M17.25 8.25L21 12m0 0l-3.75 3.75M21 12H3" />
                </svg>
            </a>
        </div>
    </div>
</section>
</body>
<script>
    // Function to open the modal
    function openModal() {
        document.getElementById('authentication-modal').classList.remove('hidden');
    }

    // Function to close the modal
    function closeModal() {
        document.getElementById('authentication-modal').classList.add('hidden');
    }
</script>
</html>
