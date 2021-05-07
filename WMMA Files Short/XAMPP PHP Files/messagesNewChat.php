<?php

/*
	This file sets up a new chat with the given recipients and message. It first checks 
	if the chat already exists.
*/

require "connect.php";

retreive user id
$user_id = $_POST["user_id"];
$recipient_ids_string = $_POST["recipient_ids"];
$message = $_POST["message"];

//seperates all of the recipient ids
$array_of_ids = array();

$user_count = 0;
while(strlen($recipient_ids_string)>0){
	$substring = substr($recipient_ids_string, 0, strpos($recipient_ids_string, ";"));

	$array_of_ids[$user_count] = $substring;
	$user_count++;

	if(strpos($recipient_ids_string, ";")+1 == strlen($recipient_ids_string)){
		break;
	}
	
	$recipient_ids_string = substr($recipient_ids_string, strpos($recipient_ids_string, "1")+1);
}

//check if this chat already exists	

$existing_chat_id = -1;

$all_chat_ids_qry = mysqli_query($conn, "select * from chats");

//for each unique chat id
$check = false;
while($chat_id = mysqli_fetch_row($all_chat_ids_qry)){
	$all_users_in_chat_qry = mysqli_query($conn, "select chat_users_user_id from chat_users where chat_id like '$chat_id[0]'");
	//if the chat doesnt have the right amount of users, skip to next chat
	if(!(mysqli_num_rows($all_users_in_chat_qry)==$user_count+1)){
		continue;
	}
	//for each user in this particular chat
	while($user = mysqli_fetch_row($all_users_in_chat_qry)){
		$check = false;
		//screen against user_id
		if(strcmp($user_id, $user[0])==0){
			$check = true;
		}
		//screen against all wanted users
		for($i = 0; $i<$user_count;$i++){
		//if this is a wanted user  the check is true
			if(strcmp($array_of_ids[$i], $user[0])==0){
				$check = true;
				break;
			}
		}
		//if the user isnt wanted, break out of looking at users
		if($check==false){
			break;
		}
	}
	//if all users wanted then we found our chat
	if($check==true){
		$existing_chat_id = $chat_id[0];
		break;
	}
}


///////////////////////////if exists
if(!($existing_chat_id == -1)){
	//get sender id
	$sender_id = mysqli_fetch_row(mysqli_query($conn, "select chat_user_id from chat_users where chat_users_user_id like '$user_id'"));
	//send message
	mysqli_query($conn, "insert into chat_messages (chat_messages_chat_id, sender_id, message_content) values ('$existing_chat_id', '$sender_id[0]', '$message')");
	//echo chat id
	echo $existing_chat_id . " ". $user_id. " ". $message. ";";
	return;
}

///////////////////////////the chat doesnt exists

//create chat
mysqli_query($conn, "insert into chats () values ()");
//find the new chat id that has no users
$existing_chat_id = mysqli_fetch_row(mysqli_query($conn, "select * from chats where not exists(select * from chat_users where chats.chat_id like chat_users.chat_id)"))[0];
//create chat users

mysqli_query($conn, "insert into chat_users (chat_id, chat_users_user_id) values ('$existing_chat_id', '$user_id')");
for($i = 0; $i<$user_count;$i++){
	mysqli_query($conn, "insert into chat_users (chat_id, chat_users_user_id) values ('$existing_chat_id', '$array_of_ids[$i]')");
}
//get sender id
$sender_id = mysqli_fetch_row(mysqli_query($conn, "select chat_user_id from chat_users where chat_users_user_id like '$user_id'"));
//send message
mysqli_query($conn, "insert into chat_messages (chat_messages_chat_id, sender_id, message_content) values ('$existing_chat_id', '$sender_id[0]', '$message')");

//echo chat id
echo $existing_chat_id;

?>