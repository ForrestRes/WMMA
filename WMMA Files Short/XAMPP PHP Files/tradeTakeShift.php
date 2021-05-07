<?php

/*
	This file allows a user to take a shift from another user.
*/

require "connect.php";

$user_id = $_POST["user_id"];
$shift_id = $_POST["shift_id"];

mysqli_query($conn, "update shifts set shifts_user_id='$user_id', open_to_trade='0' where shift_id='$shift_id'");

?>