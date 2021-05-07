<?php

/*
	This file initializes the information displayed on the home page. It displays the user
	name, the number of unread messages, the next shift's information, and the next approved
	time away information.
*/

require "connect.php";

//retreive user id
$user_id = $_POST["user_id"];

//sends the user's name
$user_name = mysqli_fetch_row(mysqli_query($conn, "select first_name, last_name from users where user_id like '$user_id'"));
echo $user_name[0] . " " . $user_name[1] . ";";

//unread messages info
$chats_qry = mysqli_query($conn, "select * from chat_users where chat_users_user_id like '$user_id'");
$total_chats = mysqli_num_rows($chats_qry);
$total_unread = 0;
//checks the unread message count for every chat
for($i = 0; $i<$total_chats; $i++){
	$chat = mysqli_fetch_row($chats_qry);

	//get all messages in this chat
	$messages_qry = mysqli_query($conn, "select message_id from chat_messages where chat_messages_chat_id like '$chat[0]' Order by time_sent DESC");
	$num_messages = mysqli_num_rows($messages_qry);
	//get the id of the last read message for this user
	$last_read = mysqli_fetch_row(mysqli_query($conn, "select chat_users_message_id from chat_users where chat_id like '$chat[0]' and chat_users_user_id like '$user_id'"))[0];
	//if there is no last read message, then all messages are unread
	if(is_null($last_read)){
		$total_unread = $total_unread+$num_messages;
	}else{
		//else count the number of messages from the last read
		$check = $num_messages;
		for($j = 0; $j<$num_messages; $j++){
			$message = mysqli_fetch_row($messages_qry);
			if(strcmp($last_read, $message[0])==0){
				$check = $j;
				break;
			}
		}
		$total_unread = $total_unread + $check;
	}
}

echo $total_unread . ";";

//next shift info
$shifts = mysqli_query($conn, "select shift_date, start_time, end_time from shifts where shifts_user_id like '$user_id' order by shift_date asc");

//checks if there is no next shift
if(mysqli_num_rows($shifts)==0)
	echo "No Shift;";

//sends next shift information
while($shift = mysqli_fetch_row($shifts)){
	if(date_create($shift[0])<date_create(date("Y-m-d"))){
		continue;
	}
	echo date_format(date_create($shift[0]), "l F j, Y") . ";";

	echo date_format(date_create($shift[1]), "g:i A") . ";";

	echo date_format(date_create($shift[2]), "g:i A") . ";"; 

	break;
}

//next time away info
$away_qry = mysqli_query($conn, "select * from time_away_requests where requests_user_id like '$user_id' and (End_Date >= Date(Now())) Order By start_date asc");
//checks if there is no upcoming time away
if(mysqli_num_rows($away_qry)==0){
	echo "No Time Away;";
	return;
}
$away = mysqli_fetch_row($away_qry);

echo date_format(date_create($away[3]), "l F j, Y") . ";";
echo date_format(date_create($away[4]), "l F j, Y") . ";";

?>