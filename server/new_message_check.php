<?php
require 'database.php';

if(isset($_POST['new_message_check'])) {
	open_my_db();

	$instance_hash = "";
	$last_message_hash = "";

	//TODO Have in mind that JSON message can be broken.
	$json = json_decode($_POST['new_message_check'], true);
	foreach ($json as $key => $value) {
		//TODO Move keys as constants in separate PHP file.
		if($key == 'instance_hash') {
			$instance_hash = mysql_real_escape_string( $value );
		}
		if($key == 'last_message_hash') {
			$last_message_hash = mysql_real_escape_string( $value );
		}
	}

	//TODO Call stored procedure.
	//TODO Test very carefully because there maybe an error.
	if($last_message_hash === '') {
		$result = query_my_db( "SELECT `message_hash`, `registered` FROM `correspondence` WHERE `message_hash` IN (SELECT `message_hash` FROM `correspondence` WHERE parent_hash='' AND (SELECT count(*) FROM `consultants` WHERE active='Y' AND instance_hash='".$instance_hash."') = 1 UNION SELECT `c1`.`message_hash` FROM `correspondence` as `c1`, `correspondence` as `c2`  WHERE `c1`.`parent_hash`<>'' AND `c1`.`parent_hash`=`c2`.`message_hash` AND `c2`.`instance_hash`='".$instance_hash."') ORDER BY `registered` ASC LIMIT 1;" );
	} else {
		$result = query_my_db( "SELECT `message_hash`, `registered` FROM `correspondence` WHERE `message_hash` IN (SELECT `message_hash` FROM `correspondence` WHERE parent_hash='' AND `registered` > (SELECT `registered` FROM `correspondence` WHERE `message_hash` = '".$last_message_hash."') AND (SELECT count(*) FROM `consultants` WHERE active='Y' AND instance_hash='".$instance_hash."') = 1 UNION SELECT `c1`.`message_hash` FROM `correspondence` as `c1`, `correspondence` as `c2`  WHERE `c1`.`parent_hash`<>'' AND `c1`.`parent_hash`=`c2`.`message_hash` AND `c2`.`instance_hash`='".$instance_hash."' AND `c1`.`registered` > (SELECT `registered` FROM `correspondence` WHERE `message_hash` = '".$last_message_hash."')) ORDER BY `registered` ASC LIMIT 1;" );
	}

	$response = '{';
	$response .= "\n";
	if($result != false){
		$response .= '"found":"true",';
		$response .= "\n";
		$response .= '"message_hash":"' . trim($result[0][0],"\r\n") . '",';
		$response .= "\n";
		$response .= '"registered":"' . trim($result[0][1],"\r\n") . '"';
	} else {
		$response .= '"found":"false",';
		$response .= "\n";
		$response .= '"message_hash":"",';
		$response .= "\n";
		$response .= '"registered":""';
	}
	$response .= "\n";
	$response .= "}";

	echo( $response );

	close_my_db();
}

?>
