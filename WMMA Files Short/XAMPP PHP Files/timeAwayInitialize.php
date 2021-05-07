<?php

/*
	This file retrieves information about all upcoming time away requests for a user.
*/

require "connect.php";

$user_id = $_POST["user_id"];

$user_qry = mysqli_query($conn, "select * from time_away_requests where requests_user_id like '$user_id' and (End_Date >= Date(Now())) Order By start_date asc");

echo mysqli_num_rows($user_qry) . ";";

for($i = 0; $i<mysqli_num_rows($user_qry); $i++){
	$request = mysqli_fetch_row($user_qry);
	//request id
	echo $request[0] . ";";

	//approval
	if($request[2]==1)
		echo "Approved;";
	else 
		echo "Not Approved;";

	//start date
	echo $request[3] . ";";

	//end date
	echo $request[4] . ";";
}

?>