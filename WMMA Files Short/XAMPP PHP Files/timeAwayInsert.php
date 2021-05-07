<?php

/*
	This file allows a user to submit a time away request.
*/

require "connect.php";

$user_id = $_POST["user_id"];
$start_date = $_POST["start_date"];
$end_date = $_POST["end_date"];

$newStartDate = date('Y-m-d', strtotime($start_date));
$newEndDate = date('Y-m-d', strtotime($end_date));

//query all requests for user
$user_qry = mysqli_query($conn, "select * from time_away_requests where requests_user_id like '$user_id'");

//make sure it doesnt overlap with others
for($i = 0; $i<mysqli_num_rows($user_qry); $i++){
	$request = mysqli_fetch_row($user_qry);
	if(!($request[4]<$newStartDate||$newEndDate<$request[3])){
		echo "Overlap";
		return;
		}
}

//insert
if(mysqli_query($conn, "insert into time_away_requests (requests_user_id, Approved, start_date, end_date) values ('$user_id', 0, '$start_date', '$end_date')")){
	echo "Success";
}
else{
	echo "Error";
}

?>