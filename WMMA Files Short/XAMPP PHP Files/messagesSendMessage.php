<?php

/*
	This file sends a message in a chat.
*/

require "connect.php";

$user_id = $_POST["user_id"];
$chat_id = $_POST["chat_id"];
$message = $_POST["message"];

$sender_id = mysqli_fetch_row(mysqli_query($conn, "select chat_user_id from chat_users where chat_users_user_id like '$user_id' and chat_id like '$chat_id'
"));

mysqli_query($conn, "insert into chat_messages (chat_messages_chat_id, sender_id, message_content) value ('$chat_id', '$sender_id[0]', '$message')");
?>