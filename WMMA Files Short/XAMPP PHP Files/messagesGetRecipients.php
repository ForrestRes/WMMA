<?php

/*
	This file retrieves all the recipients from a business that can be messaged by the user. 
*/

require "connect.php";

$user_id = $_POST["user_id"];

$business_id = mysqli_fetch_row(mysqli_query($conn, "select users_business_id from users where user_id like '$user_id'"))[0];

$recipient_qry = mysqli_query($conn, "select first_name, last_name, user_id from users where users_business_id like '$business_id' and user_id not like '$user_id' Order by last_name DESC");

$num = mysqli_num_rows($recipient_qry);

for($i = 0; $i<$num; $i++){
	$recipient = mysqli_fetch_row($recipient_qry);
	echo $recipient[0] . " " . $recipient[1] . ";" . $recipient[2] . ";";
}
?>