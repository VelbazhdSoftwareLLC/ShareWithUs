<?php
define("HOSTNAME", "localhost");
define("USERNAME", "root");
define("PASSWORD", "");
define("DBNAME", "veldsoft_share");

$GLOBALS[ 'link' ] = null;

function open_my_db() {
	$GLOBALS[ 'link' ] = mysql_connect(HOSTNAME, USERNAME, PASSWORD);
	mysql_select_db( DBNAME );
	mysql_query("SET NAMES 'utf8'");
}

function query_my_db( $qrystr ) {
	$qrystr = preg_replace('/\;$/', '', $qrystr);

	$resstrs = "";
	if( !$GLOBALS['link'] ) {
		$resstrs = false;
	} else {
		$result = mysql_query($qrystr, $GLOBALS['link']);

		$j = 0;
		if($result!=1 && $result!=false) {
			while($row = mysql_fetch_row($result)) {
				for($i=0; $i<mysql_num_fields($result); $i++) {
					$resstrs[ $j ][ $i ] = $row[ $i ];
				}

				$j++;
			}
		} else {
			$resstrs = false;
		}
	}

	return( $resstrs );
}

function close_my_db() {
	if( $GLOBALS['link'] ) {
		mysql_close( $GLOBALS['link'] );
	}
}
?>
