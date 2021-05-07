<?php

/*
	This file retrieves the messages and senders for a single chat.
*/

require "connect.php";

//retrieve user id
$user_id = $_POST["user_id"];
$chat_id = $_POST["chat_id"];

//gets all messages for a chat
$messages_qry = mysqli_query($conn, "select message_content, time_sent, sender_id, message_id from chat_messages where chat_messages_chat_id like '$chat_id' order by time_sent asc");

echo mysqli_num_rows($messages_qry). ";";

while($message = mysqli_fetch_row($messages_qry)){
	//get name
	$sender_user_id = mysqli_fetch_row(mysqli_query($conn, "select chat_users_user_id from chat_users where chat_user_id like '$message[2]'"));
	$sender_name = mysqli_fetch_row(mysqli_query($conn, "select first_name, last_name from users where user_id like '$sender_user_id[0]'"));
	echo $sender_name[0] . " " . $sender_name[1] . ";";

	//get message
	echo $message[0]. ";";

	//get date/time
	echo $message[1].";";
}

$most_recent = mysqli_fetch_row( mysqli_query($conn, "select message_id from chat_messages where chat_messages_chat_id like '$chat_id' order by time_sent desc"));

//$message has the latest message
mysqli_query($conn, "update chat_users set chat_users_message_id='$most_recent[0]' where chat_id like '$chat_id' and chat_users_user_id like '$user_id'");
?>