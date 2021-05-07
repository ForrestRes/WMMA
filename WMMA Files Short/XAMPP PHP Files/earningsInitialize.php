<?php

/*
	This file initializes the information used to set up the earnings activity. 
	It requires the user_id and posts the information as: total earnings; payment period 
	start date; payment period end date; the number of shifts worked this period; the 
	number of hours worked this period;.
*/

require "connect.php";

//retrieve user id
$user_id = $_POST["user_id"];

//retrieve all shifts for user.
$shifts = mysqli_query($conn, "select shift_date, start_time, end_time from shifts where shifts_user_id like '$user_id' order by shift_date desc");

//checks if there are no shifts to track the earnings of
if(mysqli_num_rows($shifts)==0){
	return;
}

//retrieves the wage of the user to calculate earnings
$wage =mysqli_fetch_row( mysqli_query($conn, "select salary from users where user_id like '$user_id'"))[0];

//initialize hours and shifts
$num_hours=0;
$num_shifts=0;

//sets the current weekly pay period
if(strcmp(date("l"), "Friday")==0){
	$date_end = date("Y-m-d");
}else{
	$date_end = date("Y-m-d", strtotime("next friday"));
}
$date = date_create($date_end);
$date_start = date_format(date_modify($date, "-7 days"), "Y-m-d");

//looks through every shift
while($shift = mysqli_fetch_row($shifts)){
	//skips the shift if it has not happened yet
	if(date_create($shift[0])>=date_create(date("Y-m-d"))){
		continue;
	}
	//checks if the shift is in the pay period and adds its information to the total
	if((date_create($shift[0])<=date_create($date_end)) && (date_create($shift[0])>date_create($date_start))){
		$num_shifts++;
		$num_hours = $num_hours + ((strtotime($shift[2])- strtotime($shift[1]))/3600);
		continue;
	}

	//checks if the pay period has earnings to report and sends the information
	if($num_shifts>0)
	{
		echo number_format($wage*$num_hours, 2, '.', '') . ";";
		echo date_format(date_create($date_start), "M j, Y") . ";" . date_format(date_create($date_end), "M j, Y") . ";";
		echo $num_shifts . ";";
		echo $num_hours . ";";
	}
	$num_hours = 0;
	$num_shifts = 0;
	
	//resets pay period until the next shift falls within it
	while(!((date_create($shift[0])<=date_create($date_end)) && (date_create($shift[0])>date_create($date_start))))
	{
		//changes pay period back 1 week
		$date_end = $date_start;
		
		$date_start = date_format(date_modify(date_create($date_end), "-7 days"), "Y-m-d");
	}
	$num_shifts++;
	$num_hours = $num_hours + ((strtotime($shift[2])- strtotime($shift[1]))/3600);
	
}

//sends information for the final pay period if it has information
if($num_shifts>0)
{
	echo number_format($wage*$num_hours, 2, '.', '') . ";";
	echo date_format(date_create($date_start), "M j, Y") . ";" . date_format(date_create($date_end), "M j, Y") . ";";
	echo $num_shifts . ";";
	echo $num_hours . ";";
}
?>