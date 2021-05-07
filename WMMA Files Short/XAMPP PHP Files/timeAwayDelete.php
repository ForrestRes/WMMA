<?php

/*
	This file deletes a time away request.
*/

require "connect.php";

$request_id = $_POST["request_id"];

$delete_qry = mysqli_query($conn, "delete from time_away_requests where Request_ID like '$request_id'")

?>