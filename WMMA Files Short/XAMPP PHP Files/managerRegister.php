<?php

/*
	This file registers the business and manager in the system. It querries to make sure both the manager and business do not already exist.
*/

require "connect.php";

$businessName = $_POST["business_name"];
$managerFirstName = $_POST["manager_first_name"];
$managerLastName = $_POST["manager_last_name"];
$managerEmployeeID = $_POST["manager_id"];
$password = $_POST["password"];
$businessID = "";

if(mysqli_query($conn, "select * from businesses where Business_Name like '$businessName'")==True){
	echo "BUSINESS ALREADY EXISTS";
	$conn->close();
	return;
}

$mysql_business_insert = "insert into businesses (Business_Name) values ('$businessName')";

if(mysqli_query($conn, $mysql_business_insert)==TRUE){
	$mysql_business_qry = "select Business_ID from businesses where Business_Name like '$businessName'";
	$businessID = mysqli_fetch_row(mysqli_query($conn, $mysql_business_qry))[0];

	$mysql_employee_insert = "insert into users (Employee_ID, Is_Manager, Users_Business_ID, First_Name, Last_Name, Password, Approved) values ('$managerEmployeeID', 1, '$businessID' , '$managerFirstName', '$managerLastName', '$password', 1)";
	if(mysqli_query($conn, $mysql_employee_insert)===TRUE){
		echo"REGISTRATION COMPLETE, \r\n You will be returned to login page.";
	}
	else{
		$mysql_business_dlt = "delete from businesses where Business_Name like '$businessName'";
		mysqli_query($conn, $mysql_business_dlt);
		echo"ERROR WITH REGISTRATION";
	}
}
else{
	echo"ERROR WITH REGISTRATION BUSINESS";
}

$conn->close();
return;

?>