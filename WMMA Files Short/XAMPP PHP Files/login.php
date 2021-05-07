<?php

/*
	This file checks login information against information in the database to allow 
	the user to login. The users account must be activated.
*/

require "connect.php";

//retrieve login information
$business_name = $_POST["business_name"];
$employee_id = $_POST["employee_id"];
$password = $_POST["password"];
$business_id = "";

//check if the business exists in the db
if(mysqli_num_rows(mysqli_query($conn, "select * from businesses where Business_Name like '$business_name'"))<1){
	echo "BUSINESS DOES NOT EXIST";
	$conn->close();
	return;
}

//get business id from business name
$mysql_business_qry = "select Business_ID from businesses where Business_Name like '$business_name'";
$business_id = mysqli_fetch_row(mysqli_query($conn, $mysql_business_qry))[0];

//check if the account exists and get account
$mysql_employee_qry = mysqli_query($conn, "select * from users inner join businesses on users.users_business_id = businesses.business_id where business_name like '$business_name' and Employee_ID like '$employee_id' and Password like '$password'");

if(mysqli_num_rows($mysql_employee_qry)>0){
	//check if account activated
	$employee = mysqli_fetch_row($mysql_employee_qry);
	if($employee[8]=1){
		echo "LOGIN SUCCESS:$employee[0]";
	}
	else{
		echo "ACCOUNT PENDING APPROVAL";
	}
}
else{
	echo "FAILED CREDENTIALS";
}

$conn->close();
?>