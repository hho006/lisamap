<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<title>Sign Up</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<head>
    <style>
        header {
            width: 100%;
            height: 60px;
            color: white;
            background-color: black;
            text-align: center;
            font: 40px bold;
            font-family: Arial, Helvetica, sans-serif;
            top: 0;
            position: fixed;
        }
        .header-div {
            height: 60px;
            line-height: 60px;
            vertical-align: middle;
        }
        footer {
            width: 100%;
            height: 30px;
            color: white;
            background-color: black;
            text-align: center;
            bottom: 0;
            position: fixed;
        }
        .footer-div {
            height: 30px;
            line-height: 30px;
            vertical-align: middle;
        }
        .nav-custom {
            width: 100%;
            border-radius: 0;
            top: 60px;
            position: fixed;
        }
        .navbar-nav > li > a {
            width: 115px;
            font-size: 15px;
            text-align: center;
        }
        .navbar-header > a {
            width: 115px;
            font-size: 15px;
            text-align: center;
        }
        .dropdown {
            position: relative;
            display: inline-block;
        }
        .dropdown-menu {
            display: none;
            position: absolute;
            background-color: #a0a0a0;
            min-width: 115px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }
        .dropdown:hover .dropdown-menu {
            display: block;
        }
        .content-block {
            width: 100%;
            height: 100%;
            padding-top: 120px;
            padding-bottom: 40px;
            padding-left: 30px;
            padding-right: 30px;
            color: black;
            background-color: white;
        }
    </style>
</head>

<body>
    <header><div class="header-div">LISA MAP</div></header>
    
    <nav class="navbar navbar-inverse nav-custom">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="<%= response.encodeURL(request.getContextPath() + "/") %>"><span class="glyphicon glyphicon-home"></span> Home</a>
            </div>
            <ul class="nav navbar-nav">
                <li><a href="<%= response.encodeURL(request.getContextPath() + "/map") %>"><span class="glyphicon glyphicon-globe"></span> My Map</a></li>
                <li><a href="<%= response.encodeURL(request.getContextPath() + "/photo/album") %>"><span class="glyphicon glyphicon-picture"></span> My Photo</a></li>
                <li><a href="<%= response.encodeURL(request.getContextPath() + "/video/album") %>"><span class="glyphicon glyphicon-facetime-video"></span> My Video</a></li>
                <li class="dropdown">
                    <a href="#"><span class="glyphicon glyphicon-comment"></span> My Blog</a>
                    <ul class="dropdown-menu">
                        <li><a href="<%= response.encodeURL(request.getContextPath() + "/blog") %>">View My Blog</a></li>
                        <li><a href="<%= response.encodeURL(request.getContextPath() + "/blog/search") %>">Search Blog</a></li>
                    </ul>
                </li>
                <li><a href="<%= response.encodeURL(request.getContextPath() + "/about") %>"><span class="glyphicon glyphicon-star-empty"></span> About Us</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <% if(session.getAttribute("username") == null) { %>
                    <li><a href="<%= response.encodeURL(request.getContextPath() + "/signup") %>"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                    <li><a href="<%= response.encodeURL(request.getContextPath() + "/login") %>"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                <% } else { %>
                    <li><a href="<%= response.encodeURL(request.getContextPath() + "/profile") %>"><span class="glyphicon glyphicon-user"></span> My Profile</a></li>
                    <li><a href="<%= response.encodeURL(request.getContextPath() + "/logout") %>"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                <% } %>
            </ul>
        </div>
    </nav>
    
    <div class="content-block">
        
        <form:form modelAttribute="userSignup" action="create-user" method="post">
            <fieldset>
                <legend>Register Now</legend>
                <p>
                    <label for="username">Enter User Name: </label>
                    <form:input id="username" path="username" />
                    <form:errors path="username" cssClass="error" />
                </p>
                <p>
                    <label for="password">Enter Password: </label>
                    <form:input id="password" path="password" />
                    <form:errors path="password" cssClass="error" />
                </p>
                <p>
                    <label for="email">Enter Email: </label> 
                    <form:input id="email" path="email" />
                    <form:errors path="email" cssClass="error" />
                </p>
                <p id="buttons">
                    <input id="reset" type="reset"> 
                    <input id="submit" type="submit" value="Register">
                </p>
            </fieldset>
        </form:form>
        
    </div>
    
    <footer><div class="footer-div">Powered by HOHOHO</div></footer>
</body>
</html>