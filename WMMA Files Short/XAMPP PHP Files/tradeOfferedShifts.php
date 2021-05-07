<?php

/*
	This file retrieves information for all shifts offered and available to the user.
*/

require "connect.php";

$user_id = $_POST["user_id"];

$shifts_offered = mysqli_query($conn, "select * from shifts where shifts_user_id not like '$user_id' and open_to_trade like '1'");
$my_shifts = mysqli_query($conn, "select shift_date from shifts where shifts_user_id like '$user_id'");

while($shift_offered = mysqli_fetch_row($shifts_offered)){
	//check if overlaps with an existing shift
	$check = false;
	while($my_shift = mysqli_fetch_row($my_shifts)){
		if(date_create($shift_offered[1])==date_create($my_shift[0])){
			$check = true;
			break;
		}
	}
	if($check ==true){
		continue;
	}
	//date
	echo date_format(date_create($shift_offered[1]), "l F j, Y") . ";";

	//start time
	echo date_format(date_create($shift_offered[2]), "g:i A") . ";";

	//end time
	echo date_format(date_create($shift_offered[3]), "g:i A") . ";";

	//name 
	$name =mysqli_fetch_row( mysqli_query($conn, "select first_name, last_name from users where user_id like '$shift_offered[0]'"));
	echo $name[0] . " " . $name[1] . ";";

	//id
	echo $shift_offered[4] . ";";


}

?>