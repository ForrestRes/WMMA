<?php

/*
	This file registers a new employee user in the system. It takes in a business's name, 
	an employee name, an employee id, and a password. It checks if the business exists and
	if there is already an employee with the same id.
*/

require "connect.php";

//retrieve user information
$businessName = $_POST["business_name"];
$firstName = $_POST["first_name"];
$lastName = $_POST["last_name"];
$employeeID = $_POST["employee_id"];
$password = $_POST["password"];
$businessID = "";

//check if the busines already exists in the db
if(mysqli_num_rows(mysqli_query($conn, "select * from businesses where Business_Name like '$businessName'"))<0){
	echo "BUSINESS DOES NOT EXIST";
	$conn->close();
	return;
}

//get business id from business name
$mysql_business_qry = "select Business_ID from businesses where Business_Name like '$businessName'";
$businessID = mysqli_fetch_row(mysqli_query($conn, $mysql_business_qry))[0];

//check if a user with the same employee id already exists for that business
$mysql_employee_qry = "select * from users where Users_Business_ID like '$businessID' and Employee_ID like '$employeeID'";
if(mysqli_num_rows(mysqli_query($conn, $mysql_employee_qry))>0){
	echo "EMPLOYEE ID ALREADY TAKEN";
	$conn->close();
	return;
}

//insert user into the system
$mysql_employee_insert = "insert into users (Employee_ID, Is_Manager, Users_Business_ID, First_Name, Last_Name, Password, Approved) values ('$employeeID', 0, '$businessID', '$firstName', '$lastName', '$password', 0)";
if(mysqli_query($conn, $mysql_employee_insert)===TRUE){
		echo"REGISTRATION COMPLETE, \r\n Your account is pending manager approval. You will be returned to login.";
	}
	else{
		echo"ERROR WITH REGISTRATION";
	}

$conn->close();
return;

?>