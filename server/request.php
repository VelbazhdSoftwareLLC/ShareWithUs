<?php
require 'database.php';

if(isset($_POST['request'])) {
	open_my_db();

	$message_hash = "";
	$instance_hash = "";
	$message = "";

	//TODO Have in mind that JSON message can be broken.
	$json = json_decode($_POST['request'], true);
	foreach ($json as $key => $value) {
		//TODO Move keys as constants in separate PHP file.
		if($key == 'message_hash') {
			$message_hash = mysql_real_escape_string( $value );
		}
		if($key == 'instance_hash') {
			$instance_hash = mysql_real_escape_string( $value );
		}
		if($key == 'message') {
			$message = mysql_real_escape_string( $value );
		}
	}

	//TODO Replace SQL with stored procedure call.
	query_my_db( "INSERT INTO correspondence (message_hash, instance_hash, message) VALUES ('".$message_hash."', '".$instance_hash."', '".$message."');" );

  close_my_db();

	//TODO Inform all moderators.
}

?>
