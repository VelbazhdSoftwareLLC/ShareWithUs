<?php
require 'common.php';
require 'database.php';

if(isset($_POST['join'])) {
	open_my_db();

	$instance_hash = "";
	$names = "";
	$email= "";
	$phone = "";

	//TODO Have in mind that JSON message can be broken.
	$json = json_decode($_POST['join'], true);
	foreach ($json as $key => $value) {
		//TODO Move keys as constants in separate PHP file.
		if($key == 'instance_hash') {
			$instance_hash = mysql_real_escape_string( $value );
		}
		if($key == 'names') {
			$names = mysql_real_escape_string( $value );
		}
		if($key == 'email') {
			$email = mysql_real_escape_string( $value );
		}
		if($key == 'phone') {
			$phone = mysql_real_escape_string( $value );
		}
	}

	//TODO Replace SQL with stored procedure call.
	query_my_db( "INSERT INTO consultants (instance_hash, names, email, phone) VALUES ('".$instance_hash."', '".$names."', '".$email."', '".$phone."');" );

	$admin = query_my_db( "select adminmail from servinfo;" );

        close_my_db();

	if($admin != false){
		$message .= 'Name: ' . $name;
		$message .= "\n";
		$message .= "Email: " . $email;
		$message .=  "\n";
		$message .= "Phone: " . $phone;
		$message .=  "\n";
		mail($admin[ 0 ][ 0 ], "Consultant submittion!", $message, "From: " . EMAIL_FROM . "\nReply-to: " . EMAIL_REPLAYTO);
	}
} 

?>
