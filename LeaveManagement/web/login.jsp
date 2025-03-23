<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
    <h2>Login</h2>
    <form action="login" method="post">
        Username: <input type="text" name="username" id="username" list="saved-users"><br>
        Password: <input type="password" name="password"><br>
        <input type="checkbox" name="remember"> Remember me <br>
        <input type="submit" value="Login">
    </form>

    <datalist id="saved-users">
        <% 
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().startsWith("remember_")) {
                        String savedUser = c.getName().substring(9); // Bỏ remember_
        %>
                        <option value="<%= savedUser %>">
        <%
                    }
                }
            }
        %>
    </datalist>
    <script>
document.getElementById('username').addEventListener('change', function() {
    var selectedUser = this.value;
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var c = cookies[i].trim();
        if (c.startsWith('remember_' + selectedUser + '=')) {
            var pass = c.split('=')[1];
            document.getElementsByName('password')[0].value = pass;
            break;
        }
    }
});
</script>
</body>
</html>


