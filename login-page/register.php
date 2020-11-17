<?php

require '../config/config.php';

?>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Register Page</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="assets/css/Header-Blue.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <style>
    	.form-register input {
    		margin-top: 10px;
    	}
    	label {
    		margin-top:10px;
    		margin-left: 5px;
    	}
    </style>
</head>

<body>
    <div>
        <div class="header-blue">

            <!-- Put the nav bar in a separate folder for consistency across pages -->
            <?php include '../main/nav.php'; ?>


        <div class="container hero">
            <div class="row">
                <div class="col">
                    <div class="login-card"><img class="profile-img-card" src="assets/img/avatar_2x.png">
                        <p class="profile-name-card"> </p>
                        <form class="form-register">
                        	<span class="reauth-email"> </span>
                        	<input class="form-control" type="name" id="inputFname" required="" placeholder="First Name" autofocus="">
                        	<input class="form-control" type="name" id="inputLname" required="" placeholder="Last Name" autofocus="">
                        	<input class="form-control" type="email" id="inputEmail" required="" placeholder="Email address" autofocus="">
                        	<input class="form-control" type="password" id="inputPassword" required="" placeholder="Password" autofocus="">
                        	<label for="gradYear">Graduation Year:</label>
                        	<select required="" id="gradYear"></select>
                        	<input class="form-control" type="major" id="inputMajor" required="" placeholder="Major" autofocus="">
                        	<input class="form-control" type="minor" id="inputMinor" placeholder="Minor" autofocus="">

                        <button class="btn btn-primary btn-block btn-lg btn-signin" type="submit">Sign Up</button></form></div>
            </div>
        </div>
    </div>
    </div>
    </div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>

<script>
var start = 2018;
// var end = new Date().getFullYear();
var end = 2030;
var options = "";
for(var year = start ; year <=end; year++){
  options += "<option>"+ year +"</option>";
}
document.getElementById("gradYear").innerHTML = options;
</script>
