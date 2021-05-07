<?php

/*
	This file initializes the information used to set up the messages activity. It returns
	the recipients and unread message count for each chat to be displayed. 
*/

require "connect.php";

//retrieve user id
$user_id = $_POST["user_id"];

$user_qry = mysqli_query($conn, "select * from chat_users where chat_users_user_id like '$user_id'");

$total_rows = mysqli_num_rows($user_qry);

echo $total_rows . ";";

//checks each chat that the user is in
for($i = 0; $i<$total_rows; $i++){
	$request = mysqli_fetch_row($user_qry);
	//returns chatId
	echo $request[0] . ";";

	//recipients

	//search chat_users table with chatId to get all user_ids who arent = user_id
	$recipient_qry = mysqli_query($conn, "select chat_users_user_id from chat_users where chat_ID like '$request[0]'and chat_users_user_id not like '$user_id'");

	$num_recipients = mysqli_num_rows($recipient_qry);

	//returns all recipient names
	$recipient = mysqli_fetch_row($recipient_qry);
	$name_qry  = mysqli_query($conn, "select first_name, last_name from users where user_id like '$recipient[0]'");
	$name = mysqli_fetch_row($name_qry);
	echo $name[0] . " " . $name[1];

	for($j = 1; $j<$num_recipients; $j++){
		echo ", ";
		$recipient = mysqli_fetch_row($recipient_qry);
		$name_qry  = mysqli_query($conn, "select first_name, last_name from users where user_id like '$recipient[0]'");
		$name = mysqli_fetch_row($name_qry);
		echo $name[0] . " " . $name[1];
	}

	echo ";";


//unreadCount
	//get all messages
	$messages_qry = mysqli_query($conn, "select message_id from chat_messages where chat_messages_chat_id like '$request[0]' Order by time_sent DESC");
	$num_messages = mysqli_num_rows($messages_qry);

	//checks if the user has read any messages
	if(is_null($request[3])){
		echo $num_messages . ";";
	}else{
		//finds the number of messages since last message read
		$check = $num_messages;
		for($j = 0; $j<$num_messages; $j++){
		$message = mysqli_fetch_row($messages_qry);
			if(strcmp($request[3], $message[0])==0){
				$check = $j;
				break;
			}
		}
		echo $check . ";";
	}
}

?>
