<?php

/*
	This file retrieves information for upcoming shifts to be displayed. 
*/

require "connect.php";

$user_id = $_POST["user_id"];

$shifts = mysqli_query($conn, "select shift_date, start_time, end_time, shift_id, open_to_trade from shifts where shifts_user_id like '$user_id' order by shift_date asc");

while($shift = mysqli_fetch_row($shifts)){
	if(date_create($shift[0])<date_create(date("Y-m-d"))){
		continue;
	}
	echo date_format(date_create($shift[0]), "l F j, Y") . ";";

	
	echo date_format(date_create($shift[1]), "g:i A") . ";";

	
	echo date_format(date_create($shift[2]), "g:i A") . ";";

	echo $shift[3]. ";";

	if($shift[4]==0){
		echo "Not Offered;";
	}else{
		echo "Offered;";
	}
}
?>