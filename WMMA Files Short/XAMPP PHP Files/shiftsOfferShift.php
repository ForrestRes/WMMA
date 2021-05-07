<?php

/*
	This file allows a user to offer a shift for trade.
*/

require "connect.php";

$user_id = $_POST["user_id"];
$shift_id = $_POST["shift_id"];

mysqli_query($conn, "update shifts set open_to_trade='1' where shift_id like '$shift_id'");

?>