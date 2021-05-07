<?php

/*
	This file returns information about a single time away request.
*/

require "connect.php";

$request_id = $_POST["request_id"];

$user_qry = mysqli_query($conn, "select * from time_away_requests where request_id like '$request_id'");

$result = mysqli_fetch_row($user_qry);

echo $result[2] . ";" . $result[3] . ";" . $result[4] . ";"; 

?>