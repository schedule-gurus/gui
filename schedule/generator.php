<?php

require '../config/config.php';
// error_reporting(E_ALL);

// if ( !isset($_SESSION['logged_in']) || !$_SESSION['logged_in'] ) {
//     header('Location: ../main/index.php');
// }

?>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Schedule Generator</title>
    <link rel="stylesheet" href="../login-page/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="../login-page/assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="../login-page/assets/css/Header-Blue.css">
    <link rel="stylesheet" href="../login-page/assets/css/styles.css">
    <link rel="stylesheet" href="../styles.css">
    <style>
    	.form-register input {
    		margin-top: 10px;
    	}
    	/*label {
    		margin-top:10px;
    		margin-left: 5px;
    	}*/
    	#inputMajorAbrv {
    		margin-bottom: 10px;
    	}
        img {
            margin-right: auto;
            margin-left: auto;
            width:250px;
            display:block;
        }
        h2 {
            color:maroon;
            font-weight: bold;
            text-align: center;
        }
        #ptag {
            /*color:black;*/
            text-align: center;
            font-size:17px;
        }
        #ptag2 {
            /*color:black;*/
            text-align: center;
            font-size:14px;
        }
        #classes {
            /*display: inline;
            float:left;*/
        }
        #classcode {
            /*width:60%;*/
             
            /*float:left;*/
        }
        /*label {
            float:left;
            display:inline;
        }*/
        select {
            width:38%;
            /*float:left;*/
            display: inline;
        }
        .clearfloat {
            clear:both;
        }
        .floatleft {
            float:left;
        }
        label {
            display:inline-block;
            padding-bottom: 0px;
        }
        input {
            margin-bottom:10px;
        }
        .rad {
            /*padding:10px;*/
            margin-right:10px;
            /*padding-bottom:10px;*/
            
        }
        .radr {
            margin-left:10px;
        }
        button {
            margin-top:10px;
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
                <div class="col-12">
                    <h2>Schedule Generator</h2>
                </div>
                <div class="col">
                    <div class="login-card"><img src="sc.png">
                        <p class="profile-name-card"> </p>
                        <form class="form-register" id="classes" onsubmit="return isValidForm()" action="" method="">
                            <p id="ptag" style="color:black">Please enter the classes you want to take in the following format:<br> <em>Department Code-Course Number</em><br>Ex: <em>CSCI-201</em></p>
                            <p id="ptag2" style="color:red"><em>Warning: Generating a new schedule while logged in will overwrite your previous one.</em></p>
                        	<input class="form-control" type="name" id="1" name="input1" placeholder="SOCI-200">
                            <input class="form-control" type="name" id="2" name="input2" placeholder="EE-109">
                            <input class="form-control" type="name" id="3" name="input3" placeholder="CTAN-452">
                            <input class="form-control" type="name" id="4" name="input4" placeholder="ITP-303">
                            <input class="form-control" type="name" id="5" name="input5" placeholder="CSCI-104">
                            <input class="form-control" type="name" id="6" name="input6" placeholder="MUSC-200">
                            <label for="sort">Sort schedules by:</label> <br>
                            <input type="radio" class="rad" name="sort" checked='checked' value="rmp">Rate my Professor
                            <input type="radio" class="rad radr" name="sort" value="dist">Distance


                        <button class="btn btn-primary btn-block btn-lg btn-signin" id="generate" type="submit">Generate</button>
                    </form></div>
            </div>
        </div>
    </div>
    </div>
    </div>
    <script src="../login-page/assets/js/jquery.min.js"></script>
    <script src="../login-page/assets/bootstrap/js/bootstrap.min.js"></script>
    <?php include '../main/footer.php'; ?>
</body>

</html>

<script>
var list = [];
var count = 0;

function validate(str) {
    str = str.replace(/\s/g, '');
    var res = str.split('-');
    if(res.length == 2 && res[0].length <=4 && res[0].length > 0 && res[1].length == 3) {
        str = "";
        str = res[0] + '-' + res[1];
        return str; 
    }
    return null;
};

function checkValid(id) {
    if(document.getElementById(id).value.trim().length != 0) {
        var s = validate(document.getElementById(id).value.trim());
        if(s != null) {
            list[count] = s;
            count = count + 1;
        } else {
            alert(`Input ` + id + ` is malformated!`);
            return false;
        }
    } 
    return true;
}
// Validate user input
function isValidForm(id) {
    for (let i = 1; i < 7; i++) {
        const res = checkValid(i)
        if (!res) {
            return  false
        }
    }
    console.log(list);
    return true;
};

document.querySelector("#generate").onclick = function(event) {
    console.log("pilar is pilar")
    event.preventDefault();
    list = [];
    count = 0;
    isValidForm();
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           // Typical action to be performed when the document is ready:
           console.log(xhttp.responseText);
        }
    };
    // TODO FIX THIS
    xhttp.open("GET", "http://localhost:8000/backend?list" + JSON.stringify(list), true);
    xhttp.send();
    // return isValidForm();

    // receive a json of list of schedule objects
        // sections - list of section objects
        // distance, avg rmp score

    // choose top 5? 10? 
    // choose to sort by distance or rmp score
};



</script>
