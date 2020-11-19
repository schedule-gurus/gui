<?php
require '../config/config.php';
// if you're not logged in
if ( !isset($_SESSION['logged_in']) || !$_SESSION['logged_in'] ) {
	$error = "You cannot visualize your schedule if you're not logged in.";
} else {
	$mysqli = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
	if($mysqli->connect_errno) {
		echo $mysqli->connect_error;
		exit();
	}
	$mysqli->set_charset('utf8');

	$sql = "SELECT enrolled.sectionID as id, sections.session as session, sections.title as title, 
		sections.type as type, sections.startTime as start, sections.endTime as end, sections.instructor as instructor, sections.location as location, sections.dotw as dotw, sections.abrv as abrv FROM enrolled 
		LEFT JOIN sections
			ON sections.ID = enrolled.sectionID
    	LEFT JOIN instructors
			ON sections.instructor = instructors.ID 
		LEFT JOIN locations 
			ON sections.location = locations.ID 
		WHERE " . $_SESSION['id'] . " = enrolled.userID AND enrolled.sectionID = sections.ID;";
	// var_dump($sql);
	$results = $mysqli->query($sql);
	if ( !$results ) {
		echo $mysqli->error;
		exit();
	}

	if(mysqli_num_rows($results) == 0) {
		$error = "You have no schedule saved.";
	}

	$mysqli->close();
}
?>

<!DOCTYPE html>
<html>
<head><meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <link rel="stylesheet" href="../login-page/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="../login-page/assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="../login-page/assets/css/Header-Blue.css">
    <link rel="stylesheet" href="../login-page/assets/css/styles.css">
    <!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"> -->
    <link rel="stylesheet" href="../styles.css">
	<title>Schedule Visualizer</title>
	<style>
		.text-success, .text-danger {
			text-align: center;
		}
	</style>
</head>
<body>
<div>
    <div class="header-blue">

    	<?php include '../main/nav.php'; ?>




        <div class="container hero">
        	<div class="row">
			<div class="col-12">
				<a href="schedule.php" role="button" class="btn btn-primary btn-dark" id="back">Back to Schedule</a>
			</div>
		</div>

		<div class="row">
			<div class="col-12">

			<?php if ( isset($error) && !empty($error) ) : ?>

					<div class="text-danger">
						<?php echo $error; ?>
					</div>

				<?php else : ?>

					<!-- TODO: add visualizer -->

				<?php endif; ?>
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